package tu.knowledge.primitive

import tu.model.knowledge.primitive.Number
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import tu.model.knowledge.{Probability, KnowledgeURI}


/**
 * @author max
 *         date 2012-05-01
 *         time: 6:36 PM
 */

@RunWith(classOf[JUnitRunner])
class NumberTest extends FunSuite {

  val namespace = "testNamespace"
  val name = "name"
  val revision = "rev"
  val uri = new KnowledgeURI(namespace, name, revision)
  val probability = new Probability
  val testValue: Double = 0.1

  test("Nuber stores Double value") {
    val n = new Number(testValue, uri, probability)
    assert(n.value == testValue)
  }

}
