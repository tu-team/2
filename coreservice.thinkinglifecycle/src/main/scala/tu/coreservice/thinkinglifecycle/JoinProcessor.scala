package tu.coreservice.thinkinglifecycle

import org.specs.specification.Context
import tu.model.knowledge.Resource
import tu.model.knowledge.communication.ContextHelper
import tu.coreservice.action.{Action, ActionActor}
import tu.coreservice.action.event.{Stop, Start}
import org.slf4j.LoggerFactory

/**
 * Class to process parallel Actions and join results.
 * @author max talanov
 *         date 2012-07-10
 *         time: 3:20 PM
 */

object JoinProcessor {

  val log = LoggerFactory.getLogger(this.getClass)

  def apply(actions: List[Action], context: Context) = {

    // initialisation and asynchronous call
    val actionActors: List[ActionActor] = for (a <- actions) yield {
      val aA: ActionActor = new ActionActor
      aA ! Start(a, ContextHelper(List[Resource](), "name"))
      aA
    }
    // join
    for (a <- actionActors) {
      a !? Stop match {
        case res: Context => log info res.toString
      }
    }
  }
}
