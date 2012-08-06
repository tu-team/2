package tu.coreservice.action.critic.analyser

import tu.coreservice.action.critic.{CriticLink, Critic}
import tu.model.knowledge.{Resource, Probability, KnowledgeURI}
import tu.model.knowledge.domain.{Concept, ConceptNetwork}
import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.selector.SelectorRequest
import tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think
import tu.coreservice.action.UnexpectedException


/**
 * Critic adapter DirectInstructionAnalyser.
 * @author max talanov
 *         date 2012-07-01
 *         time: 6:45 PM
 */

class DirectInstructionAnalyserCritic (_exclude: List[CriticLink], _include: List[CriticLink], _uri: KnowledgeURI,
                                       _probability: Probability = new Probability())
  extends Critic(_exclude, _include, _uri, _probability) {

  def this() = this(List[CriticLink](), List[CriticLink](), KnowledgeURI("DirectInstructionAnalyserCritic"))

  def start() = false

  def stop() = false

  /**
   * Estimates confidence and probability of output SelectorRequest
   * @param currentSituation description of current situation as ConceptNetwork
   * @param domainModel overall domain model to be used to analyse current situation as ConceptNetwork.
   * @return SelectorRequest with set probability
   */
  def apply(currentSituation: ConceptNetwork, domainModel: ConceptNetwork): SelectorRequest = {
    val dIA = new DirectInstructionAnalyser()
    val selectorRequest: SelectorRequest = dIA(currentSituation)
    selectorRequest
  }
}
