package tu.coreservice.thinkinglifecycle

import tu.model.knowledge.Resource
import tu.model.knowledge.communication.{Context, ContextHelper}
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

  def apply(actions: List[Action], context: Context): Context = {
    // initialisation and asynchronous call
    val actionActors: List[ActionActor] = for (a <- actions) yield {
      val aA: ActionActor = new ActionActor
      aA.start()
      aA ! Start(a, context)
      aA
    }
    // join
    val contexts: List[Context] = for (a <- actionActors) yield {
      a !? Stop match {
        case res: Context => {
          log info res.toString
          res
        }
      }
    }
    ContextHelper.merge(contexts)
  }
}
