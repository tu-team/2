package tu.coreservice.thinkinglifecycle

import java.util.concurrent.TimeUnit

import akka.actor.{ActorDSL, ActorRef, ActorSystem}
import akka.util.Timeout
import tu.model.knowledge.communication.{ContextHelper, ShortTermMemory}
import tu.coreservice.action.{Action, ActionActor}
import tu.coreservice.action.event.{Start, Stop}
import org.slf4j.LoggerFactory
import akka.pattern._

import scala.concurrent.Await

/**
 * Class to process parallel Actions and join results.
 * @author max talanov
 *         date 2012-07-10
 *         time: 3:20 PM
 */

object JoinProcessor {

  val log = LoggerFactory.getLogger(this.getClass)
  implicit val system = ActorSystem("test-actor");
  implicit val timeout = Timeout(120*60*1000, TimeUnit.MILLISECONDS);
  def apply(actions: List[Action], context: ShortTermMemory): ShortTermMemory = {
    // initialisation and asynchronous call
    val actionActors: List[ActorRef] = for (a <- actions) yield {
      val aA =ActorDSL.actor(new ActionActor)
      aA ! Start(a, context)
      aA
    }
    // join
    val contexts = for (a <- actionActors) yield {
      val vl = a ? Stop;
      val vl2= Await.result(vl,timeout.duration).asInstanceOf[ShortTermMemory]
      vl2  match {
        case res: ShortTermMemory =>
          log info res.toString
          res

      }
    }
    ContextHelper.merge(contexts)
  }
}
