package tu.coreservice.action.way2think.reformulation

import tu.model.knowledge.domain.{Concept, ConceptNetwork}
import tu.coreservice.action.way2think.SimulationReformulationAbstract
import tu.model.knowledge.KnowledgeURI

/**
 * @author max talanov
 *         date 2012-07-03
 *         time: 11:33 AM
 */

class Reformulation extends SimulationReformulationAbstract {

  val name: String = "Reformulation"

  /**
   * Runs through Concepts from inbound ConceptNetwork and creates ConceptNetwork of instances of the model.
   * @param in ConceptNetwork to Reformulate.
   * @param model ConceptNetwork base model.
   * @return ConceptNetwork reformulation result.
   */
  def apply(in: ConceptNetwork, model: ConceptNetwork): Option[ConceptNetwork] = {

    val known: List[Concept] = this.filterConceptList(in.nodes, model)

    val notKnown: List[Concept] = in.nodes.filter {
      c: Concept => !model.nodes.contains(c)
    }

    val foundGeneralisations = findGeneralisations(notKnown, model)

    None
  }

  private def findGeneralisations(in: List[Concept], model: ConceptNetwork): List[Concept] = {
    in.filter {
      c: Concept => {
        c.generalisations.frames.filter {
          uriConcept: Pair[KnowledgeURI, Concept] => model.nodes.contains(uriConcept._2)
        }.size > 0
      }
    }
  }

}
