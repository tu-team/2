package tu.coreservice.action.way2think.cry4help

import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.communication.{Response, ContextHelper, ShortTermMemory}
import tu.model.knowledge.{Constant, KnowledgeURI, Resource}
import tu.model.knowledge.primitive.KnowledgeString
import tu.coreservice.utilities.LocalizedResources
import tu.model.knowledge.domain.Concept
import tu.model.knowledge.annotator.AnnotatedPhrase

/**
 * @author max talanov
 *         date 2012-06-25
 *         time: 4:25 PM
 */

class Cry4HelpWay2Think(var _inputContext: ShortTermMemory, _uri: KnowledgeURI)
  extends Way2Think(_uri) {

  def this() = {
    this(ContextHelper.apply(List[Resource](KnowledgeString("", "Cry4HelpMessage")), ""), KnowledgeURI("Cry4HelpMessage"))
  }

  /**
   * Way2Think interface.
   * @param inputContext ShortTermMemory of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: ShortTermMemory): ShortTermMemory = {
    this._inputContext = inputContext
    val provider = Cry4HelpProviders.GetProvider()

    //provider.showInformation(LocalizedResources.GetString("$ErrorOccurred"))
    //provider.showInformation(inputContext.lastError.toString)
    if (inputContext.notUnderstoodConcepts.size > 0 || inputContext.notUnderstoodPhrases.size > 0) {
      if (inputContext.notUnderstoodConcepts.size > 0) {
        val unkConceptStr = inputContext.notUnderstoodConcepts.map {
          c: Concept => {
            c.uri.name.toString
          }
        }.mkString(", ")
        provider.showInformation(String.format("%1$s %2$s .", LocalizedResources.GetString("$ClarifyConcepts"), unkConceptStr))
      }

      if (inputContext.notUnderstoodPhrases.size > 0) {
        val unkPhraseStr = inputContext.notUnderstoodPhrases.map {
          c: AnnotatedPhrase => {
            c.toString
          }
        }.mkString(", ")
        provider.showInformation(String.format("%1$s %2$s .", LocalizedResources.GetString("$ClarifyPhrases"), unkPhraseStr))
      }
    }
    else {
      //old way approach
      inputContext.frames.foreach(f => {
        provider.showInformation(LocalizedResources.GetString(f._2.toString))
      })
    }
    val userResponseText = provider.askQuestion("")
    val outputContext: ShortTermMemory = ContextHelper(List[Resource](), this.getClass.getName)
    outputContext.userResponse = Some(new Response(KnowledgeString(userResponseText, "responsetext"), KnowledgeURI("Response")))
    outputContext
  }

  def start() = false

  def stop() = false

  override def toString: String = {
    this.getClass.getName + ":" + _inputContext.toString()
  }
}

object Cry4HelpWay2Think {
  def apply(message: String): Cry4HelpWay2Think = {
    val res = List[Resource](KnowledgeString(message, "Cry4HelpMessage"))
    val context = ContextHelper.apply(res, message)
    val cry4helpWay2Think = new Cry4HelpWay2Think()
    cry4helpWay2Think.apply(context)
    cry4helpWay2Think
  }
}
