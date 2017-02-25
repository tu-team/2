package coreservice.action.critic.analyser

/**
 * @author max talanov
 *         date 2012-07-10
 *         time: 1:47 PM
 */

import java.util.concurrent.TimeUnit

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.slf4j.LoggerFactory
import tu.coreservice.action.critic.CriticLink
import tu.coreservice.action.critic.analyser.{DirectInstructionAnalyserCritic, _}
import tu.coreservice.action.event.{Start, Stop}
import tu.coreservice.action.{Action, ActionActor}
import tu.model.knowledge.communication.{ContextHelper, ShortTermMemory}
import tu.model.knowledge.{KnowledgeURI, Resource}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global

@RunWith(classOf[JUnitRunner])
class ActionActorTest extends FunSuite {

  val log = LoggerFactory.getLogger(this.getClass)

  implicit val system = ActorSystem("test-actor")
  implicit val timeout = Timeout(120*60*1000, TimeUnit.MILLISECONDS)

  test("Start 3 Actions in paralel") {
    val critics: List[Action] = List(new DirectInstructionAnalyserCritic(List[CriticLink](), List[CriticLink](), KnowledgeURI("DIA")),
      new ProblemDescriptionAnalyserCritic(List[CriticLink](), List[CriticLink](), KnowledgeURI("PDA")),
      new ProblemDescriptionWithDesiredStateAnalyserCritic(List[CriticLink](), List[CriticLink](), KnowledgeURI("PDWDS")))
    val actionActors = List(system.actorOf(Props[ActionActor]), system.actorOf(Props[ActionActor]), system.actorOf(Props[ActionActor]))
    // start actors
    //for (a <- actionActors) {
    //  ActorDSL.actor(a);
    //}
    // asynchronous call
    var i = 0
    for (a <- actionActors) {
      a ! Start(critics(i), ContextHelper(List[Resource](), "name"))
      i = i + 1
    }


    // join
    for (a <- actionActors) {
      a ? (Stop)  map  {m =>
        m match {
          case res: ShortTermMemory => log info res.toString
        }
      }
    }
  }

  test("Join action actors") {

    val actions: List[Action] = List(new DirectInstructionAnalyserCritic(List[CriticLink](), List[CriticLink](), KnowledgeURI("DIA")),
      new ProblemDescriptionAnalyserCritic(List[CriticLink](), List[CriticLink](), KnowledgeURI("PDA")),
      new ProblemDescriptionWithDesiredStateAnalyserCritic(List[CriticLink](), List[CriticLink](), KnowledgeURI("PDWDS")))

    val actionActors: List[ActorRef] = for (a <- actions) yield {
      ActorDSL.actor(new ActionActor)
    }



    var i = 0
    for (aA <- actionActors) {
      aA ! Start(actions(i), ContextHelper(List[Resource](), "name"))
      i = i + 1
    }


    //system.shutdown()
    // join
    //val contexts: List[ShortTermMemory] =
    val contexts = for (a <- actionActors) yield {
      val vl = a ? Stop;
      val vl2= Await.result(vl,timeout.duration).asInstanceOf[ShortTermMemory]
       vl2  match {
        case res: ShortTermMemory =>
          log info res.toString
          res
        case _ => fail("No short term memory")
      }
    }

    //system.awaitTermination()

  }

}
