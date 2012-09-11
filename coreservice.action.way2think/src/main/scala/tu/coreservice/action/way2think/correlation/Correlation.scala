package tu.coreservice.action.way2think.correlation

import tu.model.knowledge.annotator.AnnotatedNarrative
import tu.model.knowledge.domain.{Concept, ConceptNetwork}
import tu.coreservice.action.way2think.SimulationReformulationAbstract

/**
 * @author max
 *         date 2012-09-10
 *         time: 11:15 PM
 */
class Correlation extends SimulationReformulationAbstract {

  def apply(clarification: AnnotatedNarrative, simulationResult: ConceptNetwork, domainModel: ConceptNetwork): Option[ConceptNetwork] = {
    val notKnown: List[Concept] = filterConceptListNegative(simulationResult.nodes, domainModel)

    None
  }

  /**
   * Crates mapping of notKnown List[Concept] to targetModel via mappingNarrative concepts, creating new concept List.
   * @param notKnown List of concepts to be mapped.
   * @param mappingNarrative AnnotatedNarrative to be used for mapping.
   * @param targetModel the List of Concept-s to be mapped to.
   * @return notKnown List[Concept] mapped to targetModel
   */
  def processNotKnown(notKnown: List[Concept], mappingNarrative: AnnotatedNarrative, targetModel: ConceptNetwork): List[Concept] = {
    List[Concept]()
  }
}
