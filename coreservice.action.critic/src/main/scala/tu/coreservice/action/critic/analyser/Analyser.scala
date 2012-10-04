package tu.coreservice.action.critic.analyser

import tu.coreservice.action.critic.{CriticLink, Critic}
import tu.model.knowledge.{Resource, Probability, KnowledgeURI}
import tu.model.knowledge.domain.ConceptNetwork
import tu.model.knowledge.selector.SelectorRequest
import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.annotator.AnnotatedNarrative
import tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think

/**
 * @author max talanov
 *         date 2012-10-04
 *         time: 12:19 AM
 */
abstract class Analyser(_exclude: List[CriticLink], _include: List[CriticLink], _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Critic(_exclude, _include, _uri, _probability) {

  /**
   * Estimates confidence and probability of output SelectorRequest
   * @param currentSituation description of current situation as ConceptNetwork
   * @param domainModel overall domain model to be used to analyse current situation as ConceptNetwork.
   * @return SelectorRequest with set probability
   */
  def apply(currentSituation: ConceptNetwork, domainModel: ConceptNetwork): SelectorRequest

  override def apply(inputContext: Context): Context = {
    // get lastResult ConceptNetwork from inputContext
    try {
      inputContext.lastResult match {
        case Some(narrative: AnnotatedNarrative) => {
          inputContext.domainModel match {
            case Some(domainModel: ConceptNetwork) => {
              val selectorRequest = this.apply(narrative.conceptNetwork, domainModel)
              ContextHelper(List[Resource](), selectorRequest, this.getClass.getName + " result")
            }
            case None => {
              val cry4Help = Cry4HelpWay2Think("$No_domain_model_specified")
              // throw new UnexpectedException("$No_domain_model_specified")
              ContextHelper(List[Resource](cry4Help), cry4Help, this.getClass.getName + " result")
            }
          }
        }
        case None => {
          val cry4Help = Cry4HelpWay2Think("$Context_lastResult_is_None")
          // throw new UnexpectedException("$Context_lastResult_is_not_expectedType " + e.getMessage)
          ContextHelper(List[Resource](cry4Help), cry4Help, this.getClass.getName + " result")
        }
      }
    } catch {
      case e: ClassCastException => {
        val cry4Help = Cry4HelpWay2Think("$Context_lastResult_is_not_expected_type " + e.getMessage)
        // throw new UnexpectedException("$Context_lastResult_is_not_expectedType " + e.getMessage)
        ContextHelper(List[Resource](cry4Help), cry4Help, this.getClass.getName + " result")
      }
    }
  }
}
