package tu.knowledge.primitive

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.Probability

/**
 * @author max
 * date 2012-05-01
 * time: 11:14 PM
 */

@RunWith(classOf[JUnitRunner])
class ProbabilityTest extends FunSuite {

  val frequency: Double = 0.3
  val confidence: Double = 0.4

  test("test Ok") {
    assert(true)
  }

  test("probability must store 2 doubles: frequency and confidence") {
    val p = new Probability(frequency, confidence)
    assert(p.frequency == frequency)
    assert(p.confidence == confidence)
  }

  test("Probability created with empty constructor must store default values") {
    val p = new Probability()
    assert(p.frequency == 0.0)
    assert(p.confidence == 1.0)
  }

}
