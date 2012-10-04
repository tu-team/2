package tu.coreservice.action.critic.manager

import tu.model.knowledge.{Resource, Probability, KnowledgeURI}
import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.helper.URIGenerator
import tu.coreservice.action.critic.{CriticLink, Critic}
import tu.exception.UnexpectedStateException

/**
 * @author max talanov
 *         date 2012-09-08
 *         time: 10:09 PM
 */
class DoNotUnderstandManager(_exclude: List[CriticLink], _include: List[CriticLink], _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Critic(_exclude, _include, _uri, _probability) {


  def this() = this(List[CriticLink](), List[CriticLink](), URIGenerator.generateURI("DoNotUnderstandManager"))

  def start() = false

  def stop() = false

  /**
   * Starts DoNotUnderstand that invokes Cry4Help.
   * @param inputContext Context of all inbound parameters
   * @return output Context.
   */
  override def apply(inputContext: Context): Context = {
    inputContext.lastError match {
      case Some(error: Error) => {
        val d = new DoNotUnderstand()
        ContextHelper(List[Resource](), d(error), this.getClass.getName + " result")
      }
      case None => {
        if (inputContext.notUnderstoodConcepts.size > 0) {
          val d = new DoNotUnderstand()
          ContextHelper(List[Resource](), d(inputContext.notUnderstoodConcepts), this.getClass.getName + " result")
        } else {
          throw new UnexpectedStateException("$DoNotUnderstandManager_was_invoked_incorrectly")
        }
      }
    }
  }
}
