package tu.coreservice.action


import tu.model.knowledge.communication.ShortTermMemory
import tu.model.knowledge.{KnowledgeURI, Probability}


/**
 * Actor to start inbound Action as Actor.
 * @author alex toschev
 *         date 2014-07-10
 *         time: 10:15 AM
 */

abstract class AkkaAction(_uri: KnowledgeURI, _probability: Probability) extends Action(_uri, _probability) with akka.actor.Actor {

  override def receive = {
    case executionContext: ShortTermMemory => sender() ! apply(executionContext)
    case otherwise: Any => unhandled(otherwise)
  }

  override def preStart(): Unit = start()

  override def postStop(): Unit = stop()

}
