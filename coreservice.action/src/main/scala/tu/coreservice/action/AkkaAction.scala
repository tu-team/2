package tu.coreservice.action


import tu.model.knowledge.communication.{ContextHelper, ShortTermMemory}
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
    case _: Any => sender() ! emptyContext()
  }

  override def preStart(): Unit = start()

  override def postStop(): Unit = stop()

  final def emptyContext(): ShortTermMemory = ContextHelper(List(), this.getClass.getName)

}
