package tu.coreservice.action.way2think.reformulation

import tu.model.knowledge.domain.{Concept, ConceptNetwork}
import tu.coreservice.action.way2think.SimulationReformulationAbstract
import tu.model.knowledge.KnowledgeURI
import org.slf4j.LoggerFactory

/**
 * @author max talanov
 *         date 2012-07-03
 *         time: 11:33 AM
 */

class Reformulation extends SimulationReformulationAbstract {

  val name: String = "Reformulation"

  /**
   * Runs through Concepts from inbound ConceptNetwork and creates ConceptNetwork of instances of the model.
   * @param toReformulate ConceptNetwork to Reformulate.
   * @param model ConceptNetwork base model.
   * @return ConceptNetwork reformulation result.
   */
  def apply(toReformulate: ConceptNetwork, model: ConceptNetwork): Option[ConceptNetwork] = {

    val log = LoggerFactory.getLogger(this.getClass)

    val known: List[Concept] = this.filterConceptList(toReformulate.nodes, model)

    val notKnown: List[Concept] = toReformulate.nodes.filter {
      c: Concept => !model.nodes.contains(c)
    }

    val foundGeneralisations = filterGeneralisations(notKnown, model)
    val generalisationsMap: List[Map[KnowledgeURI, Concept]] = getGeneralisations(foundGeneralisations, model)
    val unAmbiguous: List[Concept] = processAmbiguous(generalisationsMap, toReformulate)

    val combined = known ::: unAmbiguous
    log info("known concepts={}", known)
    log info("un ambiguous={}", unAmbiguous)
    if (combined.size > 0) {
      Some(instantiateConcepts(combined, name, model))
    } else {
      None
    }
  }

  /**
   * Filters generalisations of concepts in specified list.
   * @param in List[Concept] to filter.
   * @param model to find Generalisations
   * @return List[Concept] filtered concepts.
   */
  private def filterGeneralisations(in: List[Concept], model: ConceptNetwork): List[Concept] = {
    in.filter {
      c: Concept => {
        c.generalisations.frames.filter {
          uriConcept: Pair[KnowledgeURI, Concept] => model.nodes.contains(uriConcept._2)
        }.size > 0
      }
    }
  }

  /**
   * Get generalisations of concepts and stores them in List of Pair-s Concept and it's generalisations Map.
   * @param in List[Concept] to get generalisations.
   * @param model to filter specified List[Concept].
   * @return List of Pair-s Concept and it's generalisations Map.
   */
  private def getGeneralisations(in: List[Concept], model: ConceptNetwork): List[Map[KnowledgeURI, Concept]] = {
    in.map {
      c: Concept => {
        val gens: Map[KnowledgeURI, Concept] = c.generalisations.frames.filter {
          uriConcept: Pair[KnowledgeURI, Concept] => model.nodes.contains(uriConcept._2)
        }
        gens
      }
    }
  }

  def processAmbiguous(in: List[Map[KnowledgeURI, Concept]], currentModel: ConceptNetwork): List[Concept] = {
    val mostReferencedConcept: List[Concept] = in.map {
      uriConceptMap: Map[KnowledgeURI, Concept] => {
        val concepts = uriConceptMap.values
        concepts.reduceLeft(
          (c1, c2) => {
            if (countLinks(c1, currentModel) > countLinks(c2, currentModel)) c1
            else c2
          }
        )
      }
    }
    mostReferencedConcept
  }

  def countLinks(concept: Concept, in: ConceptNetwork): Int = {
    this.countLinks(concept, in.nodes)
  }

}
