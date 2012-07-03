package tu.coreservice.action.way2think.reformulation

import tu.model.knowledge.annotator.AnnotatedNarrative
import tu.model.knowledge.domain.ConceptNetwork
import tu.coreservice.action.way2think.SimulationReformulationAbstract

/**
 * @author max talanov
 *         date 2012-07-03
 *         time: 11:33 AM
 */

class Reformulation extends SimulationReformulationAbstract{

  val name: String = "Reformulation"

  /**
   * Runs through Concepts from inbound ConceptNetwork and creates ConceptNetwork of instances of the model.
   * @param in ConceptNetwork to Reformulate.
   * @param model ConceptNetwork base model.
   * @return ConceptNetwork reformulation result.
   */
  def apply(in: ConceptNetwork, model: ConceptNetwork): Option[ConceptNetwork] = {
    None
  }

}
