package tu.coreservice.thinkinglifecycle

import tu.model.knowledge.communication.{TrainingRequest, ContextHelper, ShortTermMemory, Request}
import tu.coreservice.action.selector.Selector
import tu.coreservice.action.Action
import tu.model.knowledge.{Constant, Resource}
import tu.model.knowledge.way2think.{JoinWay2ThinkModel, Way2ThinkModel}
import tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think
import tu.model.knowledge.critic.CriticModel
import tu.model.knowledge.action.ActionModel
import tu.model.knowledge.training.Goal
import tu.model.knowledge.selector.SelectorRequest
import org.slf4j.LoggerFactory
import tu.exception.UnexpectedException
import tu.dataservice.knowledgebaseserver.KBAdapter
import tu.dataservice.memory.LongTermMemory


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

  /**
   * Runs Goals linked to Request as work-flows.
   * @param request to process.
   */
  def apply(request: TrainingRequest): ShortTermMemory = {
    log info "apply(" + request + ": TrainingRequest))"
    globalContext = ContextHelper(List[Resource](request.inputText), request.inputText, "globalContext")
    globalContext.domainModel = LongTermMemory.domainModel(request.domainName)
    globalContext.simulationModel = LongTermMemory.simulationModel(request.domainName)
    globalContext.reformulationModel = LongTermMemory.reformulationModel(request.domainName)
    val goalManager = new GoalManager
    var resGoals: List[Goal] = List[Goal]()
    // process resources
    while (goalManager.currentGoal != None) {
      // get next goal
      // process next goal
      val goalOption = goalManager.currentTrainingGoal
      goalOption match {
        case Some(goal: Goal) => {
          log info "Goal:" + goal
          resGoals = resGoals ::: List(goal)
          val resources: List[Resource] = selector.apply(goal)
          val contexts = processResources(resources)
          this.globalContext = mergeContexts(contexts)
          val refContexts = processReflectiveCritics(globalContext)
          this.globalContext = mergeContexts(refContexts)
          log info "out Contexts: " + contexts.toString()
        }
        case None => //End
      }
      goalManager.nextGoal(globalContext)
    }
    log info "apply()"
    globalContext
  }

  /**
   * Runs Goals linked to Request as work-flows.
   * @param request to process.
   */
  def apply(request: Request): ShortTermMemory = {
    log info "apply(" + request + ": Request))"
    globalContext = ContextHelper(List[Resource](request.inputText), request.inputText, "globalContext")
    globalContext.domainModel = KBAdapter.domainModel(request.domainName)
    globalContext.simulationModel = KBAdapter.simulationModel(request.domainName)
    globalContext.reformulationModel = KBAdapter.reformulationModel(request.domainName)
    val goalManager = new GoalManager

    var resGoals: List[Goal] = List[Goal]()

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
          resGoals = resGoals ::: List(goal)
          val resources: List[Resource] = selector.apply(goal)
          val contexts = processResources(resources)
          this.globalContext = mergeContexts(contexts)
          val refContexts = processReflectiveCritics(globalContext)
          this.globalContext = mergeContexts(refContexts)
          log info "out Contexts: " + contexts.toString()
        }
        case None => //End
      }
      goalManager.nextGoal(globalContext)
    }
    log info "apply()"
    globalContext
  }

  def mergeContexts(contexts: List[ShortTermMemory]): ShortTermMemory = {
    val mergedRefContexts = ContextHelper.mergeLast(contexts)
    this.globalContext = copyGlobalContext(mergedRefContexts)
    this.globalContext
  }

  def processSelectorRequest(request: SelectorRequest): List[ShortTermMemory] = {
    val resources: List[Resource] = selector.apply(request)
    val contexts = processResources(resources)
    contexts
  }

  /**
   * Runs through resources and interprets them as Critics or Way2Think with global context, stores result in global context.
   * @param resources to process
   * @return List of Contexts results of processing
   */
  def processResources(resources: List[Resource]): List[ShortTermMemory] = {
    log info "processResources(" + resources + ": List[Resource]): List[ShortTermMemory]"
    val contexts: List[List[ShortTermMemory]] = for (r <- resources) yield {
      val resContext = translate(r, this.globalContext)
      if (resContext != null) {
        globalContext = copyGlobalContext(resContext)
      }
      val contextToCheck = resContext.lastResult match {
        case Some(sR: SelectorRequest) => {
          this.processSelectorRequest(sR)
        }
        case _ => List[ShortTermMemory](resContext)
      }
      contextToCheck
    }
    log info "processResources(): List[ShortTermMemory] = " + contexts.flatten.toString()
    contexts.flatten
  }

  /**
   * Start reflective critics and Cry4Help Way2Think
   * @param contextToCheck the context to check by reflective critics
   * @return ShortTermMemory with SelectorRequest-s
   */
  def processReflectiveCritics(contextToCheck: ShortTermMemory): List[ShortTermMemory] = {
    val reflectiveCritics: List[CriticModel] = KBAdapter.getReflectiveCritics
    processResources(reflectiveCritics)
  }

  def translate(resource: Resource, globalContext: ShortTermMemory): ShortTermMemory = {
    log info "translate(" + resource + ": Resource, " + globalContext + ": ShortTermMemory)"
    resource match {
      case joinWay2Think: JoinWay2ThinkModel => {
        // run JoinProcessor
        val parameters = joinWay2Think.parameters
        val actions: List[Action] = parameters.map {
          a: ActionModel => this.instantiate(a.uri.name)
        }
        val res = JoinProcessor(actions, globalContext)
        log info "translate(): ShortTermMemory " + res.toString
        res
      }
      case w2t: Way2ThinkModel => {
        val action = this.instantiate(w2t.uri.name)
        val res = action.apply(globalContext)
        log info "translate(): ShortTermMemory " + res.toString
        res
      }
      case critic: CriticModel => {
        val action = this.instantiate(critic.uri.name)
        val res = action.apply(globalContext)
        log info "translate(): ShortTermMemory " + res.toString
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

  def copyGlobalContext(resContext: ShortTermMemory): ShortTermMemory = {
    this.globalContext = ContextHelper.mergeFirstAndLastResult(List(this.globalContext, resContext))
    this.globalContext
  }
}
