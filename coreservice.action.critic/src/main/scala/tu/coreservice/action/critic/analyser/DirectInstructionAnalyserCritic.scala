package tu.coreservice.action.critic.analyser

import tu.coreservice.action.critic.{CriticLink, Critic}
import tu.model.knowledge.{Probability, KnowledgeURI}
import tu.model.knowledge.domain.ConceptNetwork
import tu.model.knowledge.communication.Context

/**
 * Critic adapter DirectInstructionAnalyser.
 * @author max talanov
 *         date 2012-07-01
 *         time: 6:45 PM
 */

case class DirectInstructionAnalyserCritic (_exclude: List[CriticLink], _include: List[CriticLink], _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Critic(_exclude, _include, _uri, _probability) {
  def start() = false

  def stop() = false

  /**
   * Generic method of the action to be applied over input Context and put all results in output Context.
   * @param inputContext Context of all inbound parameters
   * @return output Context.
   */
  def apply(inputContext: Context) = null

  /**
   * Estimates confidence and probability of output SelectorRequest
   * @param currentSituation description of current situation as ConceptNetwork
   * @param domainModel overall domain model to be used to analyse current situation as ConceptNetwork.
   * @return SelectorRequest with set probability
   */
  def apply(currentSituation: ConceptNetwork, domainModel: ConceptNetwork) = null
}
