package tu.coreservice.action

import tu.model.knowledge.{Probability, KnowledgeURI, Resource}
import tu.model.knowledge.communication.ShortTermMemory


/**
 * Abstract class of an actions.
 * @author alex toschev
 *         date 2012-07-01
 *         time: 3:31 PM
 */

abstract class Action(_uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def start(): Boolean

  def stop(): Boolean

  /**
   * Generic method of the action to be applied over input ShortTermMemory and put all results in output ShortTermMemory.
   * @param inputContext ShortTermMemory of all inbound parameters
   * @return output ShortTermMemory.
   */
  def apply(inputContext: ShortTermMemory): ShortTermMemory

}
