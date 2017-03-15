package tu.coreservice.action.critic

import tu.coreservice.action.{Action, AkkaAction}
import tu.model.knowledge.{KnowledgeURI, Probability}
import tu.model.knowledge.communication.ShortTermMemory


/**
 * Critic trait.
 * @author toschev alex
 * @author talanov max
 *         Date: 03.05.12
 */

abstract class Critic(_excluded: List[CriticLink], _included: List[CriticLink], _uri: KnowledgeURI, _probability: Probability)
  extends AkkaAction(_uri, _probability) {

  /**
   * Returns excluded by current CriticLink-s, if(current) => (!excluded)
   * @return List[Critic] excluded critics.
   */
  def exclude(): List[CriticLink] = _excluded

  /**
   * Returns included in current CriticLink-s, if(current) => (included).
   * @return List[Critic] included critics.
   */
  def include(): List[CriticLink] = _included

  /**
   * Generic method of the action to be applied over input ShortTermMemory and put all results in output ShortTermMemory.
   * @param inputContext ShortTermMemory of all inbound parameters
   * @return output ShortTermMemory.
   */
  def apply(inputContext: ShortTermMemory): ShortTermMemory
}
