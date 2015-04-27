package tu.coreservice.action.way2think

/**
 * Test class for Simulation.
 * @author max talanov
 *         date 2012-05-28
 *         time: 11:38 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import simulation.Simulation
import tu.model.knowledge.domain.{Concept, ConceptNetwork}
import tu.model.knowledge.KnowledgeURI
import tu.coreservice.utilities.TestDataGenerator

@RunWith(classOf[JUnitRunner])
class SimulationTest extends FunSuite {



  test("Simulation exact match should work") {
    val sim = new Simulation()
    val res: Option[ConceptNetwork] = sim(TestDataGenerator.generateProblemDescriptionAnnotatedNarrative,
      TestDataGenerator.generateDomainModelConceptNetwork)

    // check concepts
    val concepts: List[Concept] = TestDataGenerator.generateProblemDescriptionAnnotatedNarrative.concepts
    res match {
      case Some(instanceNetwork: ConceptNetwork) => {
        val checkedNodes = instanceNetwork.nodes.filter(
          (c: Concept) => {
            c.generalisations.frames.exists {
              uriConceptPair: Pair[KnowledgeURI, Concept] => {
                concepts.contains(uriConceptPair._2)
              }
            }
          }
        )
        assert(checkedNodes.size > 0)
      }
      case None => assert(false)
    }

  }

  test("Simulation ambiguious references should work") {
    val sim = new Simulation()
    val res: Option[ConceptNetwork] = sim(TestDataGenerator.generateProblemDescriptionAnnotatedNarrativeAmbiguous,
      TestDataGenerator.generateDomainModelConceptNetwork)

    // check concepts
    val concepts: List[Concept] = TestDataGenerator.generateProblemDescriptionAnnotatedNarrative.concepts
    res match {
      case Some(instanceNetwork: ConceptNetwork) => {
        val checkedNodes = instanceNetwork.nodes.filter(
          (c: Concept) => {
            c.generalisations.frames.exists {
              uriConceptPair: Pair[KnowledgeURI, Concept] => {
                concepts.contains(uriConceptPair._2)
              }
            }
          }
        )
        assert(checkedNodes.size > 0)
      }
      case None => assert(false)
    }
  }

}
