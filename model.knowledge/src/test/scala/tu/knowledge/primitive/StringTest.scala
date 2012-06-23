package tu.knowledge.primitive

/**
 * @author max
 * date 2012-05-01
 * time: 11:34 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.{Probability, KnowledgeURI}
import tu.model.knowledge.primitive.KnowledgeString

@RunWith(classOf[JUnitRunner])
class StringTest extends FunSuite {

  val namespace = "testNamespace"
  val name = "name"
  val revision = "rev"
  val uri = new KnowledgeURI(namespace, name, revision)
  val probability = new Probability
  val testValue: String = "0.1"

  test("test Ok") {
    assert(true)
  }

  test("String should store string value") {
    val s = new KnowledgeString(testValue, uri, probability)
    assert(s.value == testValue)
  }

}
