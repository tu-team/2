package coreservice.action.critic.analyser

/**
 * @author max talanov
 *         date 2012-07-10
 *         time: 1:47 PM
 */

import java.lang.Double
import java.util.Map.Entry
import java.{lang, util}

import akka.actor._
import akka.util.Timeout
import com.typesafe.config._
import scala.concurrent.duration._
import akka.pattern.ask
import akka.dispatch.ExecutionContexts._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.coreservice.action.critic.analyser._
import tu.coreservice.action.{Action, ActionActor}
import tu.coreservice.action.event.{Stop, Start}
import tu.model.knowledge.communication.{ContextHelper, ShortTermMemory}
import tu.model.knowledge.{KnowledgeURI, Resource}
import tu.coreservice.action.critic.analyser.DirectInstructionAnalyserCritic
import tu.coreservice.action.critic.CriticLink
import org.slf4j.LoggerFactory
import scala.concurrent.{Promise, Await}
import scalaz.concurrent.Future
import akka.pattern._

import scala.concurrent.Await


import scalaz.std.map

import scala.concurrent.ExecutionContext.Implicits.global

@RunWith(classOf[JUnitRunner])
class ActionActorTest extends FunSuite {

  val log = LoggerFactory.getLogger(this.getClass)

  implicit val system = ActorSystem("test-actor");
  implicit val timeout = Timeout.longToTimeout(120*60*1000);

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
