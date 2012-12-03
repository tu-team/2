/**
 * @author max talanov
 *         Date: 8/2/12
 *         Time: 3:52 AM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.slf4j.LoggerFactory
import relex.corpus.{DocSplitterFactory, DocSplitter}
import relex.entity.EntityMaintainer
import relex.feature.FeatureNode
import relex.output.OpenCogScheme
import relex.parser.{LGParser, LocalLGParser}
import relex.{RelationExtractor, ParsedSentence, Sentence}
import tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think
import tu.coreservice.linkparser.{RelationExtractorKB, LinkParser}
import scala.collection.JavaConversions._
import tu.coreservice.utilities.{URIHelper, TestDataGenerator}
import tu.exception.UnexpectedException
import tu.model.knowledge.annotator.{AnnotatedPhrase, AnnotatedNarrative, AnnotatedSentence}
import tu.model.knowledge.communication.{ContextHelper, ShortTermMemory}
import tu.model.knowledge.frame.Frame
import tu.model.knowledge.narrative.Narrative
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.{Constant, KnowledgeURI, Resource}
import org.linkgrammar.LinkGrammar
import tu.nlp.server.NLPFactory

@RunWith(classOf[JUnitRunner])
class LinkParserTest extends FunSuite {

  val log = LoggerFactory.getLogger(this.getClass)

  test("Ok") {
    assert(true)
  }

  test("Lisk parser should be ok") {
    val lp = new LinkParser()
    val res = lp.apply(createContext)
    log info res.toString
    assert(res != null)
  }

  private def createContext: ShortTermMemory = {
    val testString = KnowledgeString("Please", "Please")
    val please: AnnotatedPhrase = AnnotatedPhrase("Please", TestDataGenerator.formOfPoliteness)
    val install: AnnotatedPhrase = AnnotatedPhrase("install", TestDataGenerator.installActionInst)
    val fireFox: AnnotatedPhrase = AnnotatedPhrase("FireFox", TestDataGenerator.firefoxConceptInst)
    val sentence: AnnotatedSentence = AnnotatedSentence("Please install FireFox.", List(please, install, fireFox))
    val narrative: AnnotatedNarrative = new AnnotatedNarrative(List[AnnotatedSentence](sentence), KnowledgeURI("TestNarrative"))
    val context = ContextHelper(List[Resource](), narrative, "TestContext")
    context
  }

  test("Link parser should be stable") {
    val lp: LocalLGParser = new LocalLGParser
    val src = "Browser is an object.";
    val dst = "(S (NP Browser) (VP is (NP an object)) .)\n"

    for (i <- 1 until 1000) {
      log.debug("i={}", i)
      val sntc: relex.Sentence = lp.parse(src)
      log.debug(("FOUND " + sntc.getParses.size + " sentence(s)"))

      if (sntc.getParses.size > 0) {
        val sentence: relex.ParsedSentence = sntc.getParses.get(0)
        log.debug("ParsedSentence.getLinkString():\n{}", sentence.getLinkString)
        log.debug(sentence.getPhraseString)
        expect(dst)(sentence.getPhraseString)
      }
      else {
        log.debug("No parse found for sentence")
        assert(false)
      }
    }

    lp.close
  }

  test("Recreated LinkParser should be stable") {
    val src = "Browser is an object.";
    val dst = "(S (NP Browser) (VP is (NP an object)) .)\n"
    for (i <- 1 until 1000) {
      val lp: LocalLGParser = new LocalLGParser
      log.debug("i={}", i)
      val sntc: relex.Sentence = lp.parse(src)
      log.debug(("FOUND " + sntc.getParses.size + " sentence(s)"))
      if (sntc.getParses.size > 0) {
        val sentence: relex.ParsedSentence = sntc.getParses.get(0)
        log.debug("ParsedSentence.getLinkString():\n{}", sentence.getLinkString)
        log.debug(sentence.getPhraseString)
        expect(dst)(sentence.getPhraseString)
      }
      else {
        log.debug("No parse found for sentence")
        assert(false)
      }
      lp.close
    }
  }

  /*

  test("Lisk Grammer should be stable") {
    org.linkgrammar.LinkGrammar.init()
    org.linkgrammar.LinkGrammar.parse("Browser is an object")
    log.debug(org.linkgrammar.LinkGrammar.getConstituentString)
    org.linkgrammar.LinkGrammar.close()

  }
  */

  final val DEFAULT_MAX_SENTENCE_LENGTH: Int = 1024

  private def parseSentence(_sentence: String, entityMaintainer: EntityMaintainer, parser: LGParser): Sentence = {

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


  test("Repetitive sentence parse should be stable") {
    val src = "Browser is an object."
    val dst = "(S (NP Browser) (VP is (NP an object)) .)\n"
    val entityMaintainer = new EntityMaintainer()
    for (i <- 1 until 100) {
      val lp: LocalLGParser = new LocalLGParser
      log.debug("i={}", i)
      val sntc: relex.Sentence = parseSentence(src, entityMaintainer, lp)
      log.debug(("FOUND " + sntc.getParses.size + " sentence(s)"))
      if (sntc.getParses.size > 0) {
        val sentence: relex.ParsedSentence = sntc.getParses.get(0)
        log.debug("ParsedSentence.getLinkString():\n{}", sentence.getLinkString)
        log info("relexSentence={}", sentence)
        log.debug("sentence.getPhraseString()={}", sentence.getPhraseString)
        expect(dst)(sentence.getPhraseString)
      }
      else {
        log.debug("No parse found for sentence")
        assert(false)
      }
      lp.close()
    }
  }


  def setup: RelationExtractor = {
    // relex.RelationExtractor -n 4 -l -t -f -r -a
    val re = new RelationExtractor(false)
    // -n 4
    re.setMaxParses(1)
    // -l -f -a
    val opencog: OpenCogScheme = new OpenCogScheme()
    opencog.setShowLinkage(true)
    opencog.setShowFrames(true)
    re.do_anaphora_resolution = true
    opencog.setShowAnaphora(true)
    // -t
    re.do_tree_markup = true
    re.do_pre_entity_tagging = true
    re.do_post_entity_tagging = true
    re
  }


  /**
   * Way2Think interface.
   * @return outputContext
   */
  private  def parsePhrase(sentenceRaw: String): List[AnnotatedPhrase] = {

    val nlpServer = NLPFactory.createProcessor()

    val sentenceURI = new KnowledgeURI("tu-project.com", "sentence", Constant.defaultRevision)


    val sentenceList = nlpServer.splitSentences(sentenceRaw )

    val outputContext = ContextHelper(List[Resource](), this.getClass.getName)
    var annotatedSentences: List[AnnotatedSentence] = List[AnnotatedSentence]()
    var sntOrder=0

    sentenceList.foreach (sentence=>{
      //check sentence using auto-corrector
      //append extracted sentence to context and increase counter for sentence

      //run relex and extract sentences

      val relexSentence = nlpServer.processSentence(sentence)
      log info ("relexSentence={}", relexSentence)
      val parse = relexSentence.getParses.get(0)

      var phrases = processNodeRec(parse.getLeft.get("head"))



      //rearrange phrases according to sentence occurrence
      phrases = phrases.sortBy(b => b.sentenceIndex)

      outputContext.frames += (new KnowledgeURI(URIHelper.uriProjectName, sentenceURI.name + "-" + sntOrder, URIHelper.version())
        -> new KnowledgeString(sentence, sentenceURI))

      //also add sentences to sentence
      val annotatedSentence = AnnotatedSentence(sentence, phrases,
        new KnowledgeURI(URIHelper.uriProjectName, sentenceURI.name + "-" + sntOrder, URIHelper.version()))
      sntOrder = sntOrder + 1

      annotatedSentences ::= annotatedSentence
      log info ("created phrases={}", phrases)
      log info ("created sentences={}", annotatedSentences)

    })


    val annotatedNarrative = AnnotatedNarrative(annotatedSentences, KnowledgeURI(this.getClass.getName + " result"))
    log info ("created narrative={}", annotatedNarrative)
    outputContext.lastResult = Some(annotatedNarrative)
    log debug ("apply()= {}", outputContext.toString)
    annotatedSentences(0).phrases

}


  def getName(feature: FeatureNode): String = {

    if (feature.get("name") != null) {
      val name = feature.get("name").getValue
      log debug "name=" + feature.get("name").getValue
      name
    } else if (feature.get("orig_str") != null) {
      val origStr = feature.get("orig_str").getValue
      log debug "orig_str" + feature.get("orig_str").getValue
      origStr
    } else {
      throw new UnexpectedException("$No_name_specified")
    }
  }


  def start() = false

  def stop() = false

  private def processNodeRec(feature: FeatureNode): List[AnnotatedPhrase] = {
    var phrases = List[AnnotatedPhrase]()
    try {
      val name: String = getName(feature)
      //apply sentence index
      val sentenceIndex = feature.get("nameSource").get("index_in_sentence").getValue.toDouble

      val currentPhrase = if (name.contains("_")) {
        //split phrase by two
        AnnotatedPhrase(name.split("_").map(b => AnnotatedPhrase(b)).toList, sentenceIndex)
      }
      else {
        AnnotatedPhrase(name, sentenceIndex)
      }

      if (feature.get("links") != null) {
        val filteredFeatures = feature.get("links").getFeatureNames.filter {
          n: String => Constant.RelexFeatures.contains(n)
        }
        var notCompound = false
        if (filteredFeatures.size > 0) {
          filteredFeatures.foreach(f => {
            val childPhrases = processNodeRec(feature.get("links").get(f))
            if (Constant.RelexFeaturesPhrases.contains(f)) {
              val connectionPhrase = AnnotatedPhrase(f, sentenceIndex)
              if (childPhrases.size > 0) {
                val compoundPhrase = AnnotatedPhrase(List(currentPhrase, connectionPhrase, childPhrases(0)), sentenceIndex)
                phrases :::= List(compoundPhrase) ::: childPhrases.slice(1, childPhrases.size)
              } else {
                val compoundPhrase = AnnotatedPhrase(List(currentPhrase, connectionPhrase), sentenceIndex)
                phrases ::= compoundPhrase
              }
            } else {
              notCompound = true
              phrases :::= childPhrases
            }
          })
          if (notCompound) {
            phrases ::= currentPhrase
          }
        }
      } else {
        phrases ::= currentPhrase
      }

      val next = feature.get("NEXT")
      if (next != null) {
        log debug "=>"
        phrases :::= processNodeRec(next)
      }
      phrases
    } catch {
      case e: RuntimeException => {
        log error e.getMessage
        throw new UnexpectedException("$Wrong_feature_requested " + e.getMessage)
      }
    }
  }

  test("Test stable with RelationExtractorKB") {
    val src = "Browser is an object."
    val dst = "(S (NP Browser) (VP is (NP an object)) .)"

    //however we still need to use splitter
    //create relex server
    var relexServer= NLPFactory.createProcessor()
    val parsedSentences=   List[AnnotatedSentence](AnnotatedSentence(src,parsePhrase(src) ))


    for (i <- 1 until 100) {
      relexServer= NLPFactory.createProcessor()
      val relexSentence: relex.Sentence = relexServer.processSentence (src,parsedSentences )
      log.debug(("FOUND " + relexSentence.getParses.size + " sentence(s)"))
      if (relexSentence.getParses.size > 0) {
        val sentence: ParsedSentence = relexSentence.getParses.get(0)
        log.debug("ParsedSentence.getLinkString():\n{}", sentence.getLinkString)
        log info("relexSentence={}", relexSentence)
        log.debug("sentence.getPhraseString()={}", sentence.getPhraseString)
        expect(dst)(sentence.getPhraseString)
      }
      else {
        log.debug("No parse found for sentence")
        assert(false)
      }
    }
  }

  test("LinkParser emulation stable test") {
    val src = "Browser is an object."
    val dst = "(S (NP Browser) (VP is (NP an object)) .)\n"
    val reKB = setup(List[AnnotatedSentence](AnnotatedSentence(src, List[AnnotatedPhrase]())))
    for (i <- 1 until 100) {
      val relexSentence: relex.Sentence = reKB.processSentence(src)
      log.debug(("FOUND " + relexSentence.getParses.size + " sentence(s)"))
      if (relexSentence.getParses.size > 0) {
        val sentence: ParsedSentence = relexSentence.getParses.get(0)
        log.debug("ParsedSentence.getLinkString():\n{}", sentence.getLinkString)
        log info("relexSentence={}", relexSentence)
        log.debug("sentence.getPhraseString()={}", sentence.getPhraseString)
        expect(dst.trim)(sentence.getPhraseString.trim)
      }
      else {
        log.debug("No parse found for sentence")
        assert(false)
      }

    }
  }

  def setup(sentences: List[AnnotatedSentence]): RelationExtractorKB = {
    // relex.RelationExtractor -n 4 -l -t -f -r -a
    val re = new RelationExtractorKB(false, sentences)
    // -n 4
    re.setMaxParses(1)
    // -l -f -a
    val opencog: OpenCogScheme = new OpenCogScheme()
    opencog.setShowLinkage(true)
    opencog.setShowFrames(true)
    re.do_anaphora_resolution = true
    opencog.setShowAnaphora(true)
    // -t
    re.do_tree_markup = true
    re.do_pre_entity_tagging = true
    re.do_post_entity_tagging = true
    re
  }

  test("LinkParser stable test") {
    val src = "Browser is an object."
    val dst = "(S (NP Browser) (VP is (NP an object)) .)\n"
    val browserPhrase = AnnotatedPhrase("Browser")
    val isPhrase = AnnotatedPhrase("be")
    val anPhrase = AnnotatedPhrase("an")
    val objectPhrase = AnnotatedPhrase("object")
    val narrative = new AnnotatedNarrative(List[AnnotatedSentence](
      AnnotatedSentence(src, List[AnnotatedPhrase](browserPhrase, isPhrase, anPhrase, objectPhrase))),
      KnowledgeURI("narrative"))
    val linkParser = new LinkParser()
    val context = ContextHelper(List[Resource](), narrative, "testContext")
    for (i <- 1 until 100) {
      val res = linkParser.apply(context)
      log.debug("Link parser result ={}", res.lastResult.toString)
      /* log.debug(("FOUND " + relexSentence.getParses.size + " sentence(s)"))
      if (relexSentence.getParses.size > 0) {
        val sentence: ParsedSentence = relexSentence.getParses.get(0)
        log.debug("ParsedSentence.getLinkString():\n{}", sentence.getLinkString)
        log info("relexSentence={}", relexSentence)
        log.debug("sentence.getPhraseString()={}", sentence.getPhraseString)
        expect(dst.trim)(sentence.getPhraseString.trim)
      }
      else {
        log.debug("No parse found for sentence")
        assert(false)
      }  */

    }
  }
}


