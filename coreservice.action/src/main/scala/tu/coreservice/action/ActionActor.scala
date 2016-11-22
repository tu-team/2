package tu.coreservice.action


import akka.actor.Actor.Receive
import event.{Stop, Start}
import org.slf4j.LoggerFactory
import tu.model.knowledge.communication.ContextHelper
import tu.model.knowledge.Resource


/**
 * Actor to start inbound Action as Actor.
 * @author alex toschev
 *         date 2014-07-10
 *         time: 10:15 AM
 */

class ActionActor extends  akka.actor.Actor {

  val log = LoggerFactory.getLogger(this.getClass)

  def actorRefFactory = context
  /**
   * Starts inbound Action with inputContext.
   */
  var outputContext = ContextHelper(List[Resource](), "OutputContex")


  override def preStart() {
    // initialization code here
    log debug("{} started", this.getClass.getName)
    outputContext = ContextHelper(List[Resource](), "OutputContex")

  }


  override def receive: Receive ={
    case Start(action, inputContext) => {
      // start inbound Action with inputContext
      outputContext = action(inputContext)
    }
    case Stop => sender()!outputContext;  context.stop(self);
  }
}
