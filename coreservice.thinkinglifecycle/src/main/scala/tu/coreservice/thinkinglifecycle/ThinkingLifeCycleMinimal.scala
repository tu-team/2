package tu.coreservice.thinkinglifecycle

import tu.model.knowledge.communication.{ContextHelper, Context, Request}
import tu.coreservice.action.selector.Selector
import tu.coreservice.action.Action
import tu.model.knowledge.Resource
import tu.model.knowledge.way2think.{JoinWay2ThinkModel, Way2ThinkModel}
import tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think
import tu.coreservice.action.UnexpectedException
import tu.model.knowledge.critic.CriticModel
import tu.model.knowledge.action.ActionModel
import tu.model.knowledge.training.Goal
import tu.model.knowledge.selector.SelectorRequest
import org.slf4j.LoggerFactory
import tu.coreservice.utilities.TestDataGenerator


/**
 * Minimal implementation of ThinkingLifeCycle.
 * @author max talanov
 *         date 2012-07-11
 *         time: 11:42 PM
 */

class ThinkingLifeCycleMinimal
  extends ThinkingLifeCycle {

  val log = LoggerFactory.getLogger(this.getClass)
  val selector = new Selector
  var globalContext = ContextHelper(List[Resource](), "globalContext")

  def apply(request: Request) {

    log info "apply(" + request + ": Request))"
    globalContext = ContextHelper(List[Resource](request.inputText), request.inputText, "globalContext")
    globalContext.domainModel = TestDataGenerator.generateDomainModelConceptNetwork

    val goalManager = new GoalManager

    // get selector resources for request this is first goal = Goal("ProcessIncident")
    // val resources: List[Resource] = selector.apply(request)
    // currently all goals are in goals list in KBPrimitive

    // process resources
    while (goalManager.currentGoal != None) {
      // get next goal
      // process next goal
      val goalOption = goalManager.currentGoal
      goalOption match {
        case Some(goal: Goal) => {
          log info "Goal:" + goal
          val resources: List[Resource] = selector.apply(goal)
          val contexts = processResources(resources)
          val mergedContexts = ContextHelper.mergeLast(contexts)
          val lastResult = globalContext.lastResult
          val domainModel = globalContext.domainModel
          globalContext = ContextHelper.merge(globalContext, mergedContexts)
          globalContext.lastResult = lastResult
          globalContext.domainModel = domainModel
          log info contexts.toString()
        }
        case None => //End
      }
      goalManager.nextGoal
    }
    log info "apply()"
  }

  def processSelectorRequest(request: SelectorRequest): List[Context] = {
    val resources: List[Resource] = selector.apply(request)
    val contexts = processResources(resources)
    contexts
  }

  def processResources(resources: List[Resource]): List[Context] = {
    log info "Resources: " + resources
    val contexts: List[List[Context]] = for (r <- resources) yield {
      val resContext = translate(r, this.globalContext)
      log info "resContext " + resContext
      if (resContext != null) {
        val domainModel = globalContext.domainModel
        this.globalContext = ContextHelper.mergeLast(List[Context](globalContext, resContext))
        globalContext.domainModel = domainModel
        log info "globalContext " + this.globalContext.toString
      }
      log info "resContext " + resContext
      resContext.lastResult match {
        case Some(sR: SelectorRequest) => {
          this.processSelectorRequest(sR)
        }
        case _ => List[Context](resContext)
      }
    }
    log info "contexts " + contexts.flatten.toString()
    contexts.flatten
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
