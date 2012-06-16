package tu.coreservice.action.way2think

/**
 * @author max
 *         date 2012-05-28
 *         time: 11:38 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

@RunWith(classOf[JUnitRunner])
class SimulationTest extends FunSuite {



  test("test Ok") {
    assert(condition = true)
  }

  test("implicit information identification") {
    val storyText = "la la la"
    // TODO add splitter here
    /* val story = new AnnotatedNarrative()
    val model = new SemanticNetwork()
    Simulation.appendImplicitInformation(story, model)
    */
  }

}
