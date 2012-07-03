package tu.coreservice.action.way2think

import tu.model.knowledge.annotator.AnnotatedPhrase
import tu.model.knowledge.domain.{Concept, ConceptNetwork}

/**
 * @author max talanov
 *         date 2012-07-03
 *         time: 12:01 PM
 */

trait SimulationReformulationAbstract {

  /**
   * Filters list of concepts, returns only concepts found in model.
   * @param currentConcepts List[Concept] to filter.
   * @param model ConceptNetwork to be filtered at.
   * @return List[Concept] filtered.
   */
  def filterConceptList(currentConcepts: List[Concept], model: ConceptNetwork): List[Concept] = {
    val modelConcepts: List[Concept] = currentConcepts.filter {
      c => model.nodes.contains(c)
    }
    modelConcepts
  }

}
