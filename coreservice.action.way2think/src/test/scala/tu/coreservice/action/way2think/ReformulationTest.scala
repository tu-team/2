package tu.coreservice.action.way2think

/**
 * @author max
 *         date 2012-07-04
 *         time: 7:21 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import reformulation.Reformulation
import simulation.Simulation
import tu.model.knowledge.domain.{Concept, ConceptNetwork}
import tu.coreservice.utilities.TestDataGenerator
import tu.model.knowledge.KnowledgeURI

@RunWith(classOf[JUnitRunner])
class ReformulationTest extends FunSuite {

  test("test Ok") {
    assert(condition = true)
  }


  test("Reformulation exact match should work") {
    val sim = new Reformulation()
    val res: Option[ConceptNetwork] = sim(TestDataGenerator.generateProblemDescriptionSimulation,
      TestDataGenerator.generateDomainModelConceptNetwork)
    assert(res.size > 0)
    // check concepts
    val concepts: List[Concept] = TestDataGenerator.generateProblemDescriptionReformulationTest.nodes
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
      case None => assert(condition = false)
    }

  }

}
