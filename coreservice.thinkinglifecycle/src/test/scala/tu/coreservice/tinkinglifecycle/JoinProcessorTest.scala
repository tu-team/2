package tu.coreservice.tinkinglifecycle

/**
 * @author max talanov
 *         date 2012-07-10
 *         time: 4:01 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.coreservice.action.Action
import tu.model.knowledge.{Resource, KnowledgeURI}
import tu.coreservice.action.critic.CriticLink
import tu.coreservice.action.critic.analyser.{DirectInstructionAnalyserCritic, ProblemDescriptionAnalyserCritic, ProblemDescriptionWithDesiredStateAnalyserCritic}
import tu.coreservice.thinkinglifecycle.JoinProcessor
import tu.model.knowledge.communication.ContextHelper

@RunWith(classOf[JUnitRunner])
class JoinProcessorTest extends FunSuite {

  test("test Ok") {
    assert(condition = true)
  }

  test("Joining three critics should work") {
    val actions: List[Action] = List(new DirectInstructionAnalyserCritic(List[CriticLink](), List[CriticLink](), KnowledgeURI("DIA")),
      new ProblemDescriptionAnalyserCritic(List[CriticLink](), List[CriticLink](), KnowledgeURI("PDA")),
      new ProblemDescriptionWithDesiredStateAnalyserCritic(List[CriticLink](), List[CriticLink](), KnowledgeURI("PDWDS")))

    JoinProcessor(actions, ContextHelper(List[Resource](), "testContext"))
  }

}
