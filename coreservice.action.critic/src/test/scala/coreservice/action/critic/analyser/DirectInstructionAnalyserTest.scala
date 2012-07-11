package coreservice.action.critic.analyser

/**
 * @author max
 *         date 2012-06-28
 *         time: 5:16 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.coreservice.action.critic.analyser.DirectInstructionAnalyser
import tu.coreservice.utilities.TestDataGenerator
import tu.model.knowledge.selector.SelectorRequest

@RunWith(classOf[JUnitRunner])
class DirectInstructionAnalyserTest extends FunSuite {

  test("test Ok") {
    assert(condition = true)
  }

  test("DirectInstruction analyser should return 1") {
    val dIA = new DirectInstructionAnalyser()
    val res: SelectorRequest = dIA(TestDataGenerator.generateDirectInstructionAnnotatedNarrative)
    expect(1.0)(res.probability.frequency)
  }

  test("DirectInstruction analyser should return 0") {
    val dIA = new DirectInstructionAnalyser()
    val res: SelectorRequest = dIA(TestDataGenerator.generateProblemDescriptionAnnotatedNarrative)
    expect(0.0)(res.probability.frequency)
  }

}
