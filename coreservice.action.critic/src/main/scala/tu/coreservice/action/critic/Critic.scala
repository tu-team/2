package tu.coreservice.action.critic

import tu.model.knowledge.domain.ConceptNetwork
import tu.model.knowledge.selector.SelectorRequest
import tu.coreservice.action.Action
import tu.model.knowledge.{Probability, KnowledgeURI}


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
   * Estimates confidence and probability of output SelectorRequest
   * @param currentSituation description of current situation as ConceptNetwork
   * @param domainModel overall domain model to be used to analyse current situation as ConceptNetwork.
   * @return SelectorRequest with set probability
   */
  def apply(currentSituation: ConceptNetwork, domainModel: ConceptNetwork): SelectorRequest
}
