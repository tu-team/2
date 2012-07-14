package tu.coreservice.thinkinglifecycle

import tu.model.knowledge.communication.{ContextHelper, Context, Request}
import tu.coreservice.action.selector.Selector
import tu.coreservice.action.Action
import tu.model.knowledge.Resource
import tu.model.knowledge.way2think.{JoinWay2ThinkModel, Way2ThinkModel}
import tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think
import tu.coreservice.action.way2think.UnexpectedException
import tu.model.knowledge.critic.CriticModel
import tu.model.knowledge.action.ActionModel


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

    var globalContext = ContextHelper(List[Resource](), request.toString)

    // get selector resources for request this is first goal = Goal("ProcessIncident")
    // val resources: List[Resource] = selector.apply(request)
    //TODO currently all goals are in goals list in KBPrimitive

    // process resources
    for (goal <- selector.goals) {
      // get next goal
      // process next goal
      val resources: List[Resource] = selector.apply(goal)
      val contexts: List[Context] = for (r <- resources) yield {
        translate(r, globalContext)
      }
      globalContext = ContextHelper.mergeLast(contexts)
    }
  }

  def translate(resource: Resource, globalContext: Context): Context = {
    resource match {
      case joinWay2Think: JoinWay2ThinkModel => {
        // run JoinProcessor
        val parameters = joinWay2Think.parameters
        val actions: List[Action] = parameters.map {
          a: ActionModel => this.instantiate(a.uri.name)
        }
        JoinProcessor(actions, globalContext)
      }
      case w2t: Way2ThinkModel => {
        val action = this.instantiate(w2t.uri.name)
        action.apply(globalContext)
      }
      case critic: CriticModel => {
        val action = this.instantiate(critic.uri.name)
        action.apply(globalContext)
      }
    }
  }

  def instantiate(className: String): Action = {
    val clazz = Class.forName(className)
    try {
      val temp = clazz.newInstance()
      val instance = temp.asInstanceOf[Action]
      instance
    } catch {
      case e: ClassCastException => {
        Cry4HelpWay2Think("$Not_alloved_class " + clazz.getName)
        throw new UnexpectedException("$Not_alloved_class " + clazz.getName)
      }
    }
  }
}
