package tu.knowledge.training

/**
 * @author max talanov
 *        Date: 8/23/12
 *        Time: 9:16 AM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.training.Goal

@RunWith(classOf[JUnitRunner])
class GoalTest extends FunSuite {

  test("test Ok") {
    assert(condition = true)
  }

  test("Goal constructors test") {
    val g = new Goal(Map("name" -> "name"))
    expect("name")(g.uri.name)
  }

}