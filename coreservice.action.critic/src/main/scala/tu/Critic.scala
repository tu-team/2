package tu

import model.knowledge.communication.Context
import model.knowledge.domain.ConceptNetwork
import model.knowledge.selector.SelectorRequest


/**
 * Critic trait.
 * @author toschev alex
 * @author talanov max
 * Date: 03.05.12
 * Time: 11:05
 */

abstract class Critic(_excluded: List[Critic], _included: List[Critic]) {

  /**
   * Returns excluded by current Critics, if(current) => (!excluded)
   * @return List[Critic] excluded critics.
   */
  def exclude(): List[Critic] = _excluded

  /**
   * Returns included in current Critics, if(current) => (included).
   * @return List[Critic] included critics.
   */
  def include(): List[Critic] = _included

  /**
   * Estimates confidence and probability of output SelectorRequest
   * @param currentSituation description of current situation as ConceptNetwork
   * @param domainModel overall domain model to be used to analyse current situation as ConceptNetwork.
   * @return SelectorRequest with set probability
   */
  def apply(currentSituation: ConceptNetwork, domainModel: ConceptNetwork): SelectorRequest
}
