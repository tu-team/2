package tu.coreservice.linkparser

import scala.collection.JavaConversions._
import java.util.ArrayList
import java.util.TreeMap
import relex.algs.SentenceAlgorithmApplier
import relex.anaphora.Antecedents
import relex.anaphora.Hobbs
import relex.chunk.ChunkRanker
import relex.chunk.LexChunk
import relex.concurrent.RelexContext
import relex.entity.EntityMaintainer
import relex.entity.EntityTagger
import relex.morphy.Morphy
import relex.morphy.MorphyFactory
import relex.parser.LGParser
import relex.parser.LocalLGParser
import relex.parser.RemoteLGParser
import relex.stats.TruthValue
import relex.stats.SimpleTruthValue
import relex.tree.{PhraseTree, PhraseMarkup}
import relex.{Sentence, Document, ParseStats, Version}
import org.slf4j.LoggerFactory
import relex.feature.FeatureNode
import java.util
import org.specs.specification.Context
import tu.model.knowledge.communication.ContextHelper
import tu.model.knowledge.Resource
import tu.model.knowledge.annotator.AnnotatedSentence

/**
 * Based on RelationExtractor
 * @author max talanov
 *         date 2012-07-28
 *         time: 5:21 PM
 */



class RelationExtractorKB(useSocket: Boolean, sentences: List[AnnotatedSentence]) {

  val log = LoggerFactory.getLogger(this.getClass)
  /**Syntax processing */
  private var parser: LGParser = if (useSocket) {
    new RemoteLGParser()
  } else {
    new LocalLGParser()
  }
  parser.getConfig.setStoreConstituentString(true)
  parser.getConfig.setLoadSense(true)
  /**The LinkParserClient to be used - this class isn't thread safe! */
  val morphy: MorphyKB = MorphyFactory.getImplementation("tu.coreservice.linkparser.MorphyKB").asInstanceOf[MorphyKB]
  morphy.sentences = sentences
  private var context: RelexContext = new RelexContext(parser, morphy)
  /**Dependency processing */
  private var sentenceAlgorithmApplier: SentenceAlgorithmApplier = new SentenceAlgorithmApplier()
  /**Penn tree-bank style phrase structure markup. */
  private var phraseMarkup: PhraseMarkup = new PhraseMarkup()
  var do_tree_markup: Boolean = false
  /**Anaphora resolution */
  var antecedents: Antecedents = new Antecedents()
  private var hobbs: Hobbs = new Hobbs(antecedents)
  var do_anaphora_resolution: Boolean = false
  /**Document - holder of sentences */
  private var doco: Document = new Document()
  /**Stanford parser compatibility mode */
  var do_stanford: Boolean = false
  /**Penn tagset compatibility mode */
  var do_penn_tagging: Boolean = false
  /**Expand preposition markup to two dependencies. */
  var do_expand_preps: Boolean = false
  /**Perform entity substitution before parsing */
  var do_pre_entity_tagging: Boolean = false
  /**Perform entity tagging after parse */
  var do_post_entity_tagging: Boolean = false
  var tagger: EntityTagger = null
  /**Statistics */
  private var stats: ParseStats = new ParseStats()
  var _starttime: Long = System.currentTimeMillis
  private var sumtime: TreeMap[String, Long] = new TreeMap[String,Long]()
  private var cnttime: TreeMap[String, Long] = new TreeMap[String, Long]()

  def startime = _starttime

  init(useSocket)

  def this() {
    this(false, List[AnnotatedSentence]())
  }

  private def prt_chunks(chunks: List[Nothing]) {
    for (ch <- chunks) {
      System.out.println(ch.toString)
    }
    System.out.println("\n======\n")
  }

  private def discriminate(ranker: ChunkRanker) {
    val chunks: ArrayList[LexChunk] = ranker.getChunks
    for (ch <- chunks) {
      val sz: Int = ch.size
      var weight: Double = sz - 3
      if (weight < 0) weight = -weight
      weight = 1.0 - 0.2 * weight
      val tv: TruthValue = ch.getTruthValue
      val stv: SimpleTruthValue = tv.asInstanceOf[SimpleTruthValue]
      var confidence: Double = stv.getConfidence
      confidence *= weight
      stv.setConfidence(confidence)
    }
  }

  final val verbosity: Int = 1
  final val DEFAULT_MAX_PARSES: Int = 100
  final val DEFAULT_MAX_SENTENCE_LENGTH: Int = 1024
  final val DEFAULT_MAX_PARSE_SECONDS: Int = 30
  final val DEFAULT_MAX_PARSE_COST: Int = 1000

  private def init(useSocket: Boolean) {

    parser = if (useSocket) {
      new RemoteLGParser()
    } else {
      new LocalLGParser()
    }

    parser.getConfig.setStoreConstituentString(true)
    parser.getConfig.setLoadSense(true)
    val morphy: Morphy = MorphyFactory.getImplementation("tu.coreservice.linkparser.MorphyKB")
    context = new RelexContext(parser, morphy)

    sentenceAlgorithmApplier = new SentenceAlgorithmApplier()
    setMaxParses(DEFAULT_MAX_PARSES)
    setMaxParseSeconds(DEFAULT_MAX_PARSE_SECONDS)
    setMaxCost(DEFAULT_MAX_PARSE_COST)
    phraseMarkup = new PhraseMarkup()
    antecedents = new Antecedents()
    hobbs = new Hobbs(antecedents)
    do_anaphora_resolution = false
    doco = new Document()
    do_tree_markup = false
    do_stanford = false
    do_penn_tagging = false
    do_expand_preps = false
    do_pre_entity_tagging = false
    do_post_entity_tagging = false
    tagger = null
    stats = new ParseStats()
    sumtime = new TreeMap[String, Long]()
    cnttime = new TreeMap[String, Long]()
  }

  def getVersion: String = {
    parser.getVersion + "\t" + Version.getVersion
  }

  /**
   * Set the max number of parses.
   * This will NOT reduce processing time; all parses are still computed,
   * but only this many are returned.
   */
  def setMaxParses(maxParses: Int) {
    parser.getConfig.setMaxLinkages(maxParses)
  }

  def setMaxCost(maxCost: Int) {
    parser.getConfig.setMaxCost(maxCost)
  }

  def setAllowSkippedWords(allow: Boolean) {
    parser.getConfig.setAllowSkippedWords(allow)
  }

  def setMaxParseSeconds(maxParseSeconds: Int) {
    parser.getConfig.setMaxParseSeconds(maxParseSeconds)
  }

  /**
   * Clear out the cache of old sentences.
   *
   * The Anaphora resolver keeps a list of sentences previously seen,
   * so that anaphora resolution can be done. When starting the parse
   * of a new text, this cache needs to be cleaned out. This is the
   * way to do so.
   */
  def clear() {
    antecedents.clear()
    hobbs = new Hobbs(antecedents)
  }

  def processSentence(sentence: String): Sentence = {
    processSentence(sentence, null)
  }

  def processSentence(sentence: String, _entityMaintainer: EntityMaintainer): Sentence = {
    _starttime = System.currentTimeMillis
    var entityMaintainer = _entityMaintainer
    if (entityMaintainer == null) {
      entityMaintainer = new EntityMaintainer()
    }
    var sntc: Sentence = null
    try {
      if (verbosity > 0) {
        _starttime = System.currentTimeMillis
      }
      sntc = parseSentence(sentence, entityMaintainer)
      if (verbosity > 0) {
        reportTime("Link-parsing: ")
      }
      for (parse <- sntc.getParses) {
        if (do_expand_preps) {
          parse.getLeft.set("expand-preps", new FeatureNode("T"))
        }
        if (do_post_entity_tagging && (tagger != null)) {
          tagger.tagEntities(sentence)
          tagger.tagParse(parse)
        }
        entityMaintainer.tagConvertedSentence(parse)
        sentenceAlgorithmApplier.applyAlgs(parse, context)
        if (do_stanford) sentenceAlgorithmApplier.extractStanford(parse, context)
        if (do_penn_tagging) sentenceAlgorithmApplier.pennTag(parse, context)
        entityMaintainer.repairSentence(parse.getLeft)
        if (do_tree_markup) {
          phraseMarkup.markup(parse)
          val pt: PhraseTree = new PhraseTree(parse.getLeft)
          parse.setPhraseString(pt.toString)
        }
      }
      sntc.simpleParseRank()
      if (do_anaphora_resolution) {
        hobbs.addParse(sntc)
        hobbs.resolve(sntc)
      }
    }
    catch {
      case e: Exception => {
        System.err.println("Error: Failed to process sentence: " + sentence)
        e.printStackTrace
      }
    }
    if (verbosity > 0) reportTime("RelEx processing: ")
    sntc
  }

  /**
   * Parses a sentence, using the parser. The private ArrayList of
   * currentParses is filled with the ParsedSentences Uses an optional
   * EntityMaintainer to work on a converted sentence.
   */
  private def parseSentence(_sentence: String, entityMaintainer: EntityMaintainer): Sentence = {

    var sentence = _sentence
    if (entityMaintainer != null) {
      entityMaintainer.convertSentence(sentence, null)
      sentence = entityMaintainer.getConvertedSentence
    }
    if (sentence == null) return null
    val orig_sentence = entityMaintainer.getOriginalSentence
    var sent: Sentence = null
    if (sentence.length < DEFAULT_MAX_SENTENCE_LENGTH) {
      sent = parser.parse(sentence)
    }
    else {
      System.err.println("Sentence too long!: " + sentence)
      sent = new Sentence()
    }
    sent.setSentence(orig_sentence)
    sent
  }

  private def reportTime(msg: String) {
    val now: Long = System.currentTimeMillis
    val elapsed: Long = now - _starttime
    _starttime = now
    var sum: Long = sumtime.get(msg)
    var cnt: Long = cnttime.get(msg)
    if (sum == null) {
      sum = 0L
      cnt = 0L
    }
    cnt += 1
    sum += elapsed
    sumtime.put(msg, sum)
    cnttime.put(msg, cnt)
    val avg: Long = sum / cnt
    System.err.println(msg + elapsed + " milliseconds (avg=" + avg + " millisecs, cnt=" + cnt + ")")
  }

}


