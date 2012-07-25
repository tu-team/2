package tu.coreservice.action.critic

import tu.model.knowledge.domain.ConceptNetwork
import tu.model.knowledge.selector.SelectorRequest
import tu.coreservice.action.Action
import tu.model.knowledge.{Resource, Probability, KnowledgeURI}
import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think


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

  /**
   * Generic method of the action to be applied over input Context and put all results in output Context.
   * @param inputContext Context of all inbound parameters
   * @return output Context.
   */
  def apply(inputContext: Context): Context = {
    // get lastResult ConceptNetwork from inputContext
    try {
      val lastResult: ConceptNetwork = inputContext.lastResult.asInstanceOf[ConceptNetwork]
      inputContext.domainModel match {
        case Some(domainModel: ConceptNetwork) => {
          val selectorRequest = this.apply(lastResult, domainModel)
          ContextHelper(List[Resource](), selectorRequest, this.getClass.getName + " result")
        }
        case None => {
          val cry4Help = Cry4HelpWay2Think("$No_domain_model_specified")
          // throw new UnexpectedException("$No_domain_model_specified")
          ContextHelper(List[Resource](cry4Help), cry4Help, this.getClass.getName + " result")
        }
      }
    } catch {
      case e: ClassCastException => {
        val cry4Help = Cry4HelpWay2Think("$Context_lastResult_is_not_expectedType " + e.getMessage)
        // throw new UnexpectedException("$Context_lastResult_is_not_expectedType " + e.getMessage)
        ContextHelper(List[Resource](cry4Help), cry4Help, this.getClass.getName + " result")
      }
    }
  }
}
