package tu.coreservice.linkparser

import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.Resource
import tu.model.knowledge.annotator.{AnnotatedSentence, AnnotatedNarrative}
import tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think
import tu.coreservice.action.UnexpectedException
import relex.entity.EntityMaintainer
import relex.output.OpenCogScheme
import relex.RelationExtractor
import relex.tree.PhraseTree

/**
 * @author max talanov
 *        Date: 7/31/12
 *        Time: 4:18 AM
 */

class LinkParser extends Way2Think{
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

  def processSentences(sentence: AnnotatedSentence, context: Context) = {

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
}
