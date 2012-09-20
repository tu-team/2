package tu.coreservice.action.way2think.simulation

import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.annotator.AnnotatedNarrative
import tu.model.knowledge.domain.ConceptNetwork
import tu.model.knowledge.{Constant, Resource}
import tu.exception.NoExpectedInformationException

/**
 * Wrapper class for Simulation to provide Way2Think interface.
 * @author max talanov
 *         date 2012-06-25
 *         time: 12:41 PM
 */

class SimulationWay2Think extends Way2Think {
  /**
   * Way2Think interface.
   * @param inputContext Context of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: Context) = {
    try {
      inputContext.findByName(Constant.LINK_PARSER_RESULT_NAME) match {
        case Some(narrative: AnnotatedNarrative) => {
          inputContext.simulationModel match {
            case Some(model: ConceptNetwork) => {
              val conceptNetworkOption = this.apply(narrative, model)
              conceptNetworkOption match {
                case Some(cn: ConceptNetwork) => {
                  val context = ContextHelper(List[Resource](), cn, this.getClass.getName + " result")
                  context.simulationResult = Some(cn)
                }
                case None => {
                  // val cry4Help = Cry4HelpWay2Think("$No_matches_detected_in_domain_model")
                  // ContextHelper(List[Resource](cry4Help), cry4Help, this.getClass.getName + " result")
                  throw new NoExpectedInformationException("$No_matches_detected_in_domain_model")
                }
              }
            }
            case None => {
              // val cry4Help = Cry4HelpWay2Think("$No_model_specified")
              // ContextHelper(List[Resource](cry4Help), cry4Help, this.getClass.getName + " result")
              throw new NoExpectedInformationException("$No_domain_model_specified")
            }
          }
          // simulation model
          inputContext.simulationModel match {
            case Some(model: ConceptNetwork) => {
              val conceptNetworkOption = this.apply(narrative, model)
              conceptNetworkOption match {
                case Some(cn: ConceptNetwork) => {
                  val context = ContextHelper(List[Resource](), cn, this.getClass.getName + Constant.RESULT)
                  context.simulationResult = cn
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
        }
        case None => {
          //val cry4Help = Cry4HelpWay2Think("$Context_lastResult_is_None")
          // ContextHelper(List[Resource](cry4Help), cry4Help, this.getClass.getName + " result")
          throw new NoExpectedInformationException("$Context_lastResult_is_None")
        }
      }
    } catch {
      case e: ClassCastException => {
        //val cry4Help = Cry4HelpWay2Think("$Context_lastResult_is_not_expectedType " + e.getMessage)
        //ContextHelper(List[Resource](cry4Help), cry4Help, this.getClass.getName + " result")
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
  def apply(currentSituation: AnnotatedNarrative, domainModel: ConceptNetwork): Option[ConceptNetwork] = {
    val s = new Simulation()
    s.apply(currentSituation, domainModel)
  }

  def start() = false

  def stop() = false
}
