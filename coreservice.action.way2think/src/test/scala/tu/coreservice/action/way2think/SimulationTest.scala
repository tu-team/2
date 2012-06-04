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
import tu.model.knowledge.annotator.AnnotatedWord
import tu.model.knowledge.semanticnetwork.SemanticNetworkLink

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
  val userWord = AnnotatedWord("user")
  val customerWord = AnnotatedWord("customer")

  val wordsURI = KnowledgeURI("words")
  val words = new KLine(Map[KnowledgeURI, Resource](userWord.uri -> userWord, customerWord.uri -> customerWord), wordsURI)
  val userConcept = Concept(words,"user")
}
