package tu.coreservice.action

import actors.Actor
import event.{Stop, Start}
import org.slf4j.LoggerFactory
import tu.model.knowledge.communication.ContextHelper
import tu.model.knowledge.Resource


/**
 * Actor to start inbound Action as Actor.
 * @author max talanov
 *         date 2012-07-10
 *         time: 10:15 AM
 */

class ActionActor extends Actor {

  val log = LoggerFactory.getLogger(this.getClass)

  /**
   * Starts inbound Action with inputContext.
   */
  def act() {
    log debug("{} started", this.getClass.getName)
    var outputContext = ContextHelper(List[Resource](), "OutputContex")
    loop {
      react {
        case Start(action, inputContext) => {
          // start inbound Action with inputContext
          outputContext = action(inputContext)
        }
        case Stop => reply(outputContext); exit()
      }
    }
  }
}
