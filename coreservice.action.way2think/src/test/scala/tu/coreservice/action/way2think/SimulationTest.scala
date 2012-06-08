package tu.coreservice.action.way2think

/**
 * @author max
 *         date 2012-05-28
 *         time: 11:38 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.domain.Concept
import tu.model.knowledge.{KnowledgeURI, Resource, KLine}
import tu.model.knowledge.semanticnetwork.SemanticNetworkLink
import tu.model.knowledge.annotator.{AnnotatedPhrase, AnnotatedWord}

@RunWith(classOf[JUnitRunner])
class SimulationTest extends FunSuite {

  test("test Ok") {
    assert(condition = true)
  }

  /**
   * Simulation structures
   */

  val namespace = "2/concepts/"
  val revision = "0.0"
  val generalisationURI = KnowledgeURI("generalisation")
  val generalisations = KLine(generalisationURI)
  val specialisationURI = KnowledgeURI("specialisation")
  val specialisations = KLine(specialisationURI)

  val userConcept = Concept("user")
  val computerConcept = Concept("computer")
  val softwareConcept = Concept("sofware")
  val photoShopConcept = Concept("Adobe Photoshop")
  val firefoxConcept = Concept("FireFox")

  val userPhrase = AnnotatedPhrase("user")
  val customerPhrase = AnnotatedPhrase("customer")
  val notebookPhrase = AnnotatedPhrase("notebook")
  val laptopPhrase = AnnotatedPhrase("laptop")
  val desktopPhrase = AnnotatedPhrase("desktop")
  val computerPhrase = AnnotatedPhrase("computer")


}
