package tu.coreservice.action

import tu.model.knowledge.{Probability, KnowledgeURI, Resource}
import tu.model.knowledge.communication.Context

/**
 * Abstract class of an actions.
 * @author max talanov
 *         date 2012-07-01
 *         time: 3:31 PM
 */

abstract class Action(_uri: KnowledgeURI, _probability: Probability)
  extends Resource (_uri, _probability) {

  def start(): Boolean

  def stop(): Boolean

  /**
   * Generic method of the action to be applied over input Context and put all results in output Context.
   * @param inputContext Context of all inbound parameters
   * @return output Context.
   */
  def apply(inputContext: Context): Context

}
