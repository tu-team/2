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
import tu.coreservice.utilities.TestDataGenerator

@RunWith(classOf[JUnitRunner])
class SimulationTest extends FunSuite {

  test("test Ok") {
    assert(condition = true)
  }

  test("Simulation exact match should work") {
    val sim = new Simulation()
    val res = sim(TestDataGenerator.generateProblemDescriptionAnnotatedNarrative,
      TestDataGenerator.generateDomainModelConceptNetwork)
    // expect()()

  }

}
