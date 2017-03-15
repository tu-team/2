package tu.coreservice.thinkinglifecycle

import java.util.concurrent.TimeUnit

import akka.pattern.ask
import akka.actor.{Actor, ActorRef, ActorSystem, Props, Terminated}
import akka.util.Timeout

import scala.concurrent.Await


class AkkaProvider extends Actor{

  var actorMap = Map.empty[String, ActorRef]

  override def receive: Receive = {
    case Find(className: String) =>
      sender() ! getOrCreateActor(className)
    case Terminated(ref) =>
      actorMap = actorMap filterNot { case (_, v) => v == ref }
  }

  private def getOrCreateActor(className: String): ActorRef = {
    actorMap.getOrElse(className, {
      val clazz = Class.forName(className)
      val actor = context actorOf Props(clazz)
      actorMap += className -> actor
      context watch actor
      actor
    })
  }

}

/**
  * TODO
  */
object AkkaProvider {

  private val AKKA_SYSTEM_NAME = "test-actor"

  private val AKKA_SYSTEM = ActorSystem(AKKA_SYSTEM_NAME)

  private implicit val TIMEOUT = Timeout(10, TimeUnit.SECONDS)

  private val SUPERVISOR = AKKA_SYSTEM.actorOf(Props[AkkaProvider])

  def find(className: String): ActorRef = {
    val future = SUPERVISOR ? Find(className)
    Await.result(future, TIMEOUT.duration).asInstanceOf[ActorRef]
  }

}


