package coreservice.action.critic.analyser

/**
 * @author max talanov
 *         date 2012-07-10
 *         time: 1:47 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.coreservice.action.critic.analyser._
import tu.coreservice.action.{Action, ActionActor}
import tu.coreservice.action.event.{Stop, Start}
import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.{KnowledgeURI, Resource}
import tu.coreservice.action.critic.analyser.DirectInstructionAnalyserCritic
import tu.coreservice.action.critic.CriticLink
import org.slf4j.LoggerFactory

@RunWith(classOf[JUnitRunner])
class ActionActorTest extends FunSuite {

  val log = LoggerFactory.getLogger(this.getClass)

  test("test Ok") {
    assert(condition = true)
  }

  test("Start 3 Actions in paralel") {
    val critics: List[Action] = List(new DirectInstructionAnalyserCritic(List[CriticLink](), List[CriticLink](), KnowledgeURI("DIA")),
      new ProblemDescriptionAnalyserCritic(List[CriticLink](), List[CriticLink](), KnowledgeURI("PDA")),
      new ProblemDescriptionWithDesiredStateAnalyserCritic(List[CriticLink](), List[CriticLink](), KnowledgeURI("PDWDS")))
    val actionActors = List(new ActionActor(), new ActionActor(), new ActionActor())
    // start actors
    for (a <- actionActors) {
      a.start()
    }
    // asynchronous call
    var i = 0
    for (a <- actionActors) {
      a ! Start(critics(i), ContextHelper(List[Resource](), "name"))
      i = i + 1
    }
    // join
    for (a <- actionActors) {
      a !? Stop match {
        case res: Context => log info res.toString
      }
    }

  }

}
