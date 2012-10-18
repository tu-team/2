package tu.coreservice.action.way2think.reformulation

import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.communication.{ContextHelper, ShortTermMemory}
import tu.model.knowledge.domain.ConceptNetwork
import tu.model.knowledge.Resource
import tu.exception.NoExpectedInformationException

/**
 * @author max
 *         date 2012-07-16
 *         time: 5:52 PM
 */

class ReformulationWay2Think extends Way2Think {

  /**
   * Way2Think interface.
   * @param inputContext ShortTermMemory of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: ShortTermMemory) = {

    try {
      val lastResult: ConceptNetwork = inputContext.lastResult.asInstanceOf[ConceptNetwork]
      inputContext.reformulationModel match {
        case Some(model: ConceptNetwork) => {
          val conceptNetworkOption = this.apply(lastResult, model)
          conceptNetworkOption match {
            case Some(cn: ConceptNetwork) => {
              ContextHelper(List[Resource](), cn, this.getClass.getName + " result")
            }
            case None => {
              // val cry4Help = Cry4HelpWay2Think("$No_matches_detected_in_domain_model")
              // ContextHelper(List[Resource](cry4Help), cry4Help, this.getClass.getName + " result")
              throw new NoExpectedInformationException("$No_matches_detected_in_domain_model")
            }
          }
        }
        case None => {
          // val cry4Help = Cry4HelpWay2Think("$No_domain_model_specified")
          // ContextHelper(List[Resource](cry4Help), cry4Help, this.getClass.getName + " result")
          throw new NoExpectedInformationException("$No_domain_model_specified")
        }
      }
    } catch {
      case e: ClassCastException => {
        // val cry4Help = Cry4HelpWay2Think("$Context_lastResult_is_not_expectedType " + e.getMessage)
        // ContextHelper(List[Resource](cry4Help), cry4Help, this.getClass.getName + " result")
        throw new NoExpectedInformationException("$Context_lastResult_is_not_expectedType " + e.getMessage)
      }
    }
  }

  /**
   * Estimates confidence and probability of output SelectorRequest
   * @param currentSituation description of current situation as ConceptNetwork
   * @param domainModel overall domain model to be used to analyse current situation as ConceptNetwork.
   * @return SelectorRequest with set probability
   */
  def apply(currentSituation: ConceptNetwork, domainModel: ConceptNetwork): Option[ConceptNetwork] = {
    val s = new Reformulation()
    s.apply(currentSituation, domainModel)
  }

  def start() = false

  def stop() = false

}
