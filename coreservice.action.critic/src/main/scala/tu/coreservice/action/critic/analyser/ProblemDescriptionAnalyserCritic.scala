package tu.coreservice.action.critic.analyser

import tu.model.knowledge.{Probability, KnowledgeURI}
import tu.coreservice.action.critic.{CriticLink, Critic}
import tu.model.knowledge.domain.ConceptNetwork
import tu.model.knowledge.selector.SelectorRequest

/**
 * ProblemDescriptionAnalyserCritic Action adapter to ProblemDescriptionAnalyser
 * @author max talanov
 *         date 2012-07-10
 *         time: 12:17 PM
 */

class ProblemDescriptionAnalyserCritic(_exclude: List[CriticLink], _include: List[CriticLink], _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Analyser(_exclude, _include, _uri, _probability) {

  def this() = this(List[CriticLink](), List[CriticLink](), KnowledgeURI("ProblemDescriptionAnalyserCritic"))

  def start() = false

  def stop() = false

  /**
   * Estimates confidence and probability of output SelectorRequest
   * @param currentSituation description of current situation as ConceptNetwork
   * @param domainModel overall domain model to be used to analyse current situation as ConceptNetwork.
   * @return SelectorRequest with set probability
   */
  def apply(currentSituation: ConceptNetwork, domainModel: ConceptNetwork): SelectorRequest = {
    val a = new ProblemDescriptionAnalyser()
    val selectorRequest: SelectorRequest = a.apply(currentSituation)
    selectorRequest
  }
}
