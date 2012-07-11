package coreservice.action.critic.analyser

/**
 * @author max talanov
 *         date 2012-07-02
 *         time: 4:21 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.coreservice.action.critic.analyser.{ProblemDescriptionAnalyser, DirectInstructionAnalyser}
import tu.model.knowledge.selector.SelectorRequest
import tu.coreservice.utilities.TestDataGenerator

@RunWith(classOf[JUnitRunner])
class ProblemDescriptionAnalyserTest extends FunSuite {

  test("test Ok") {
    assert(condition = true)
  }

  test("ProblemDescriptionAnalyser analyser should return 0") {
    val analyser = new ProblemDescriptionAnalyser()
    val res: SelectorRequest = analyser(TestDataGenerator.generateDirectInstructionAnnotatedNarrative)
    expect(0.0)(res.probability.frequency)
  }

  test("ProblemDescriptionAnalyser analyser should return 1") {
    val analyser = new ProblemDescriptionAnalyser()
    val res: SelectorRequest = analyser(TestDataGenerator.generateProblemDescriptionAnnotatedNarrative)
    expect(1.0)(res.probability.frequency)
  }
}
