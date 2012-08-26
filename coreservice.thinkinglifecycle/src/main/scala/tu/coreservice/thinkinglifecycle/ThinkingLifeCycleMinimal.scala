package tu.coreservice.thinkinglifecycle

import tu.model.knowledge.communication.{ContextHelper, Context, Request}
import tu.coreservice.action.selector.Selector
import tu.coreservice.action.Action
import tu.model.knowledge.Resource
import tu.model.knowledge.way2think.{JoinWay2ThinkModel, Way2ThinkModel}
import tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think
import tu.model.knowledge.critic.CriticModel
import tu.model.knowledge.action.ActionModel
import tu.model.knowledge.training.Goal
import tu.model.knowledge.selector.SelectorRequest
import org.slf4j.LoggerFactory
import tu.coreservice.utilities.TestDataGenerator
import tu.exception.UnexpectedException
import tu.dataservice.knowledgebaseserver.KBPrototype


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
    globalContext.simulationModel = TestDataGenerator.generateSimulationModelConceptNetwork
    globalContext.reformulationModel = TestDataGenerator.generateReformulationModelConceptNetwork

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
          this.globalContext = copyGlobalContext(mergedContexts)
          log info "out Contexts: " + contexts.toString()
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
    log info "processResources(" + resources + ": List[Resource]): List[Context]"
    val contexts: List[List[Context]] = for (r <- resources) yield {
      val resContext = translate(r, this.globalContext)
      if (resContext != null) {
        val domainModel = globalContext.domainModel
        globalContext = copyGlobalContext(resContext)
      }
      val contextToCheck = resContext.lastResult match {
        case Some(sR: SelectorRequest) => {
          this.processSelectorRequest(sR)
        }
        case _ => List[Context](resContext)
      }
      contextToCheck
    }
    log info "processResources(): List[Context] = " + contexts.flatten.toString()
    contexts.flatten
  }

  //TODO start reflective critics and Cry4Help Way2Think here
  def processReflectiveCritics(contextToCheck: Context): Context = {
    KBPrototype.getReflectiveCritics()
    contextToCheck
  }

  def translate(resource: Resource, globalContext: Context): Context = {
    log info "translate(" + resource + ": Resource, " + globalContext + ": Context)"
    resource match {
      case joinWay2Think: JoinWay2ThinkModel => {
        // run JoinProcessor
        val parameters = joinWay2Think.parameters
        val actions: List[Action] = parameters.map {
          a: ActionModel => this.instantiate(a.uri.name)
        }
        val res = JoinProcessor(actions, globalContext)
        log info "translate(): Context " + res.toString
        res
      }
      case w2t: Way2ThinkModel => {
        val action = this.instantiate(w2t.uri.name)
        val res = action.apply(globalContext)
        log info "translate(): Context " + res.toString
        res
      }
      case critic: CriticModel => {
        val action = this.instantiate(critic.uri.name)
        val res = action.apply(globalContext)
        log info "translate(): Context " + res.toString
        res
      }
    }

  }

  def instantiate(className: String): Action = {
    log info "instantiate(" + className + ": String): Action"
    val clazz = Class.forName(className)
    try {
      val temp = clazz.newInstance()
      val instance = temp.asInstanceOf[Action]
      log info "instantiate(): Action = " + instance.toString
      instance
    } catch {
      case e: ClassCastException => {
        Cry4HelpWay2Think("$Not_alloved_class " + clazz.getName)
        throw new UnexpectedException("$Not_alloved_class " + clazz.getName)
      }
    }
  }

  def copyGlobalContext(resContext: Context): Context = {
    this.globalContext = ContextHelper.mergeFirstAndLastResult(List(this.globalContext, resContext))
    this.globalContext
  }
}
