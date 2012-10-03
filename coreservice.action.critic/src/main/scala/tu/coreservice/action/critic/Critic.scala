package tu.coreservice.action.critic

import tu.coreservice.action.Action
import tu.model.knowledge.{Probability, KnowledgeURI}
import tu.model.knowledge.communication.Context


/**
 * Critic trait.
 * @author toschev alex
 * @author talanov max
 *         Date: 03.05.12
 *         Time: 11:05
 */

abstract class Critic(_excluded: List[CriticLink], _included: List[CriticLink], _uri: KnowledgeURI, _probability: Probability)
  extends Action(_uri, _probability) {

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
   * Generic method of the action to be applied over input Context and put all results in output Context.
   * @param inputContext Context of all inbound parameters
   * @return output Context.
   */
  def apply(inputContext: Context): Context
}
