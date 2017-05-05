package tu.coreservice.action


import tu.model.knowledge.communication.{ContextHelper, ShortTermMemory}
import tu.model.knowledge.{KnowledgeURI, Probability}

import scala.util.{Failure, Success}



/**
  * Actor to start inbound Action as Actor.
  *
  * @author alex toschev
  *         date 2014-07-10
  *         time: 10:15 AM
  */

abstract class AkkaAction(_uri: KnowledgeURI, _probability: Probability) extends Action(_uri, _probability) with akka.actor.Actor {

  override def receive = {
    case executionContext: ShortTermMemory => {
      try {
        val result = apply(executionContext)
        sender() ! Success(result)
      } catch {
        case e: Throwable =>{
          sender() ! Failure(e)
        }
      }
    }
    case _: Any => sender() ! emptyContext()
  }

  override def preStart(): Unit = start()

  override def postStop(): Unit = stop()

  final def emptyContext(): ShortTermMemory = ContextHelper(List(), this.getClass.getName)

}
