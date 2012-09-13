package tu.coreservice.action.critic.manager

import tu.model.knowledge.{Resource, Probability, KnowledgeURI}
import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.coreservice.utilities.URIHelper
import tu.model.knowledge.helper.URIGenerator

/**
 * @author max talanov
 *         date 2012-09-08
 *         time: 10:09 PM
 */
class DoNotUnderstandManager(_uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Resource(_uri, _probability) {


  def this()=this(URIGenerator.generateURI("DoNotUnderstandManager"))

  def start() = false

  def stop() = false

  /**
   * Starts DoNotUnderstand that invokes Cry4Help.
   * @param inputContext Context of all inbound parameters
   * @return output Context.
   */
  def apply(inputContext: Context): Context = {

    inputContext.lastError match {
      case Some(error: Error) => {
        val d = new DoNotUnderstand()
        ContextHelper(List[Resource](), d(error), this.getClass.getName + " result")
      }
    }
  }
}
