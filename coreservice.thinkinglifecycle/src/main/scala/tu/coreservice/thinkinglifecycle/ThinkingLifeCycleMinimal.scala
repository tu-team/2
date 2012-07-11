package tu.coreservice.thinkinglifecycle

import tu.model.knowledge.communication.Request
import tu.coreservice.action.selector.Selector
import tu.model.knowledge.Resource
import tu.model.knowledge.way2think.{JoinWay2ThinkModel, Way2ThinkModel}
import tu.coreservice.action.critic.Critic

/**
 * Minimal implementation of ThinkingLifeCycle.
 * @author max talanov
 *         date 2012-07-11
 *         time: 11:42 PM
 */

class ThinkingLifeCycleMinimal
  extends ThinkingLifeCycle {

  val selector = new Selector

  def apply(request: Request) {

    // get selector resources for request this is first goal = Goal("ProcessIncident")
    val resources: List[Resource] = selector.apply(request)

    // process resources
    for (r <- resources) {
      r match {
        case w2t: Way2ThinkModel => {
          // translate way2think
        }
        case critic: Critic => {
          // translate Critic
        }
        case joinWay2Think: JoinWay2ThinkModel => {
          // run JoinProcessor
        }
      }
    }

  }
}
