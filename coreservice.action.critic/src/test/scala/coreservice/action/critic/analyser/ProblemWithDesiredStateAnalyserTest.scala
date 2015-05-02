package coreservice.action.critic.analyser

/**
 * @author max
 *         date 2012-07-02
 *         time: 5:56 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.coreservice.action.critic.analyser.{ProblemDescriptionWithDesiredStateAnalyser, ProblemDescriptionAnalyser}
import tu.model.knowledge.selector.SelectorRequest
import tu.coreservice.utilities.TestDataGenerator

@RunWith(classOf[JUnitRunner])
class ProblemWithDesiredStateAnalyserTest extends FunSuite {


  test("ProblemDescriptionAnalyser analyser should return 0") {
    val analyser = new ProblemDescriptionWithDesiredStateAnalyser()
    val res: SelectorRequest = analyser(TestDataGenerator.generateDirectInstructionAnnotatedNarrative)
    assertResult(0.0)(res.probability.frequency)
  }

  test("ProblemDescriptionAnalyser analyser should return 1") {
    val analyser = new ProblemDescriptionWithDesiredStateAnalyser()
    val res: SelectorRequest = analyser(TestDataGenerator.generateProblemDescriptionWithDesiredStateAnnotatedNarrative)
    assertResult(1.0)(res.probability.frequency)
  }

}
