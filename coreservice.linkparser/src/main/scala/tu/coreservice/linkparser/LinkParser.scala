package tu.coreservice.linkparser

import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.Resource
import tu.model.knowledge.annotator.{AnnotatedSentence, AnnotatedNarrative}
import tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think
import tu.coreservice.action.UnexpectedException
import relex.entity.EntityMaintainer
import relex.output.OpenCogScheme
import relex.{Sentence, RelationExtractor}
import relex.tree.PhraseTree
import relex.feature.FeatureNode
import org.slf4j.LoggerFactory


/**
 * @author max talanov
 *        Date: 7/31/12
 *        Time: 4:18 AM
 */

class LinkParser extends Way2Think{

  val log = LoggerFactory.getLogger(this.getClass)

  def start() = false

  def stop() = false

  /**
   * Way2Think interface.
   * @param inputContext Context of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: Context): Context = {

    val narrative = getLastResult(inputContext)
    val sentences: List[AnnotatedSentence] = narrative.sentences
    val text = narrative.text

    val outputContext = ContextHelper(List[Resource](), this.getClass.getName)
    outputContext
  }

  def getLastResult(inputContext: Context): AnnotatedNarrative = {
    try {
      val narrative: AnnotatedNarrative = inputContext.lastResult.asInstanceOf[AnnotatedNarrative]
      narrative
    } catch {
      case e: ClassCastException => {
        val cry4Help = Cry4HelpWay2Think("$Context_lastResult_is_not_expectedType " + e.getMessage)
        // throw new UnexpectedException("$Context_lastResult_is_not_expectedType " + e.getMessage)
        ContextHelper(List[Resource](cry4Help), cry4Help, this.getClass.getName + " result")
        throw new UnexpectedException("$Context_lastResult_is_not_expectedType " + e.getMessage)
      }
    }
  }

  def processSentences(sentences: List[AnnotatedSentence], context: Context) = {
    sentences.map{
      sentence: AnnotatedSentence => {
        val phrasesTree: PhraseTree = processSentence(sentence, context)
        val node: FeatureNode = new FeatureNode()
        node.set("head", parse.getLeft.get("head"))
        node.set("background", parse.getLeft.get("background"))
        printRec(node.get("head"))
      }
    }
  }

  def processSentence(sentence: AnnotatedSentence, context: Context): PhraseTree = {
    //run relex and extract sentences
    val em: EntityMaintainer = new EntityMaintainer()
    val relExt = setup(context)
    val relexSentence = relExt.processSentence(sentence.text, em)
    val tree = relexSentence.getParses.get(0).getPhraseTree
    tree
  }

  def setup(context: Context): RelationExtractorKB = {
    // relex.RelationExtractor -n 4 -l -t -f -r -a
    val re = new RelationExtractorKB(false, context)
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

  def printRec(feature: FeatureNode) {
    val leftWall = "LEFT-WALL"
    try {

      if (feature.get("orig_str") != null) log info "orig_str" + feature.get("orig_str").getValue

      if (feature.get("_subj") != null) log info "_subj" + printRec(feature.get("_subj"))
      if (feature.get("_obj") != null) log info "_obj=" + printRec(feature.get("_obj"))
      if (feature.get("_iobj") != null) log info "_iobj=" + printRec(feature.get("_iobj"))
      if (feature.get("_advmod") != null) log info "_advmod=" + printRec(feature.get("_advmod"))

      if (feature.get("name") != null) log info "name=" + feature.get("name").getValue
      if (feature.get("tense") != null) log info "tense=" + feature.get("tense").getValue
      if (feature.get("PREP-FLAG") != null) log info "PREP-FLAG=" + feature.get("PREP-FLAG").getValue
      if (feature.get("pos") != null) log info "pos=" + feature.get("pos").getValue

      if (feature.get("links") != null) {
        // log info "links=" + feature.get("links").toString(getZHeadsFilter)
        log info "==>"
        printRec(feature.get("links"))
      }
      val next = feature.get("NEXT")
      if (next != null) {
        log info "=>"
        printRec(next)
      }

      // if (feature.get("head") != null) log info "head=" + feature.get("head").getValue
      // if (feature.get("background") != null) log info "background=" + feature.get("background").getValue
      // if (feature.get("words") != null) log info "words=" + feature.get("words").getValue
    } catch {
      case e: RuntimeException => {
        log error e.getMessage
      }
    }
  }
}
