package tu.coreservice.action.way2think.correlation

import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.communication.{ContextHelper, ShortTermMemory}
import tu.model.knowledge.{Resource, Constant}
import tu.model.knowledge.annotator.AnnotatedNarrative
import tu.model.knowledge.domain.{Concept, ConceptNetwork}
import tu.exception.UnexpectedException

/**
 * @author max talanov
 *         date 2012-09-10
 *         time: 11:23 PM
 */
class CorrelationWay2Think extends Way2Think {
  /**
   * Way2Think interface.
   * @param inputContext ShortTermMemory of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: ShortTermMemory) = {
    try {
      inputContext.findByName(Constant.LINK_PARSER_RESULT_NAME) match {
        case Some(narrative: AnnotatedNarrative) => {
          inputContext.simulationModel match {
            case Some(model: ConceptNetwork) => {
              inputContext.simulationResult match {
                case Some(result: ConceptNetwork) => {
                  val tripleResult: Option[Triple[List[Concept], List[Concept], List[Concept]]] = this.apply(narrative, result, model)
                  tripleResult match {
                    case Some(tr: Triple[List[Concept], List[Concept], List[Concept]]) => {
                      val updatedSimulationResult = ConceptNetwork(result.nodes ::: tr._1, this.getClass.getName + "Simulation" + Constant.RESULT)
                      val context = ContextHelper(List[Resource](), updatedSimulationResult, this.getClass.getName + "ShortTermMemory" + Constant.RESULT)
                      if (tr._2.size > 0) {
                        val updatedSimulationModel = ConceptNetwork(model.nodes ::: tr._2, this.getClass.getName + "Model" + Constant.RESULT)
                        context.simulationModel = Some(updatedSimulationModel)
                      }
                      if (tr._3.size > 0) {
                        context.notUnderstoodConcepts = tr._3
                      }
                      context
                    }
                    case None => {
                      throw new UnexpectedException("$No_matches_detected_in_domain_model")
                    }
                  }
                }
                case None => {
                  val tripleResult: Option[Triple[List[Concept], List[Concept], List[Concept]]] = this.apply(narrative, model)
                  tripleResult match {
                    case Some(tr: Triple[List[Concept], List[Concept], List[Concept]]) => {
                      val updatedSimulationResult = ConceptNetwork(tr._1, this.getClass.getName + "Training" + Constant.RESULT)
                      val context = ContextHelper(List[Resource](), updatedSimulationResult, this.getClass.getName + "ShortTermMemory" + Constant.RESULT)
                      if (tr._2.size > 0) {
                        val updatedSimulationModel = ConceptNetwork(model.nodes ::: tr._2, this.getClass.getName + "Model" + Constant.RESULT)
                        context.simulationResult = Some(updatedSimulationModel)
                      }
                      if (tr._3.size > 0) {
                        context.notUnderstoodConcepts = tr._3
                      }
                      context
                    }
                    case None => {
                      throw new UnexpectedException("$No_matches_detected_in_domain_model")
                    }
                  }
                }
              }
            }
            case None => {
              throw new UnexpectedException("$No_domain_model_specified")
            }
          }
        }
        case None => {
          throw new UnexpectedException("$Context_lastResult_is_None")
        }
      }
    } catch {
      case e: ClassCastException => {
        throw new UnexpectedException("$Context_lastResult_is_not_expectedType " + e.getMessage)
      }
    }
  }

  /**
   * Searches for mapping paths from simulation result to domainModel via clarification.
   * @param clarification AnnotatedNarrative of clarification
   * @param simulationResult description of current situation as ConceptNetwork
   * @param domainModel overall domain model to be used to analyse current situation as ConceptNetwork.
   * @return shortest maps, domainModel concepts List, notUnderstood concepts List.
   */
  def apply(clarification: AnnotatedNarrative, simulationResult: ConceptNetwork, domainModel: ConceptNetwork):
  Option[Triple[List[Concept], List[Concept], List[Concept]]] = {
    val s = new Correlation()
    s.apply(clarification, simulationResult, domainModel)
  }

  /**
   * Searches for mapping paths from simulation result to domainModel via clarification.
   * @param clarification AnnotatedNarrative of clarification
   * @param domainModel overall domain model to be used to analyse current situation as ConceptNetwork.
   * @return shortest maps, domainModel concepts List, notUnderstood concepts List.
   */
  def apply(clarification: AnnotatedNarrative, domainModel: ConceptNetwork):
  Option[Triple[List[Concept], List[Concept], List[Concept]]] = {
    val s = new Correlation()
    s.apply(clarification, domainModel)
  }

  def start() = false

  def stop() = false
}
