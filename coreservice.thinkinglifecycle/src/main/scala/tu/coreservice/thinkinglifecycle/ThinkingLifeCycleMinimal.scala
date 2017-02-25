package tu.coreservice.thinkinglifecycle

import org.slf4j.LoggerFactory
import tu.coreservice.action.Action
import tu.coreservice.action.selector.Selector
import tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think
import tu.dataservice.knowledgebaseserver.KBAdapter
import tu.dataservice.memory.LongTermMemory
import tu.exception.UnexpectedException
import tu.model.knowledge.Resource
import tu.model.knowledge.action.ActionModel
import tu.model.knowledge.communication.{ShortTermMemory, _}
import tu.model.knowledge.critic.CriticModel
import tu.model.knowledge.domain.ConceptNetwork
import tu.model.knowledge.selector.SelectorRequest
import tu.model.knowledge.training.Goal
import tu.model.knowledge.way2think.{JoinWay2ThinkModel, Way2ThinkModel}


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

  private def initializeGlobalContext(request: Request) = {
    globalContext = ContextHelper(List[Resource](request.inputText), request.inputText, "globalContext")
    globalContext.domainModel = LongTermMemory.domainModel(request.domainName)
    globalContext.simulationModel = LongTermMemory.simulationModel(request.domainName)
    globalContext.reformulationModel = LongTermMemory.reformulationModel(request.domainName)
    globalContext.solutions = LongTermMemory.solutions(request.domainName)
  }

  /**
   * Runs Goals linked to Request as work-flows.
   * @param request to process.
   */
  def apply(request: TrainingRequest): ShortTermMemory = {
    log debug "apply(" + request + ": TrainingRequest))"
    initializeGlobalContext(request)
    val goalManager = new GoalManager
    var resGoals: List[Goal] = List[Goal]()
    // process resources
    while (goalManager.currentTrainingGoal != None) {
      // get next goal
      // process next goal
      val goalOption = goalManager.currentTrainingGoal
      goalOption match {
        case Some(goal: Goal) => {
          log debug "Goal:" + goal
          resGoals = resGoals ::: List(goal)
          val resources: List[Resource] = selector.apply(goal)
          val contexts = processResources(resources)
          this.globalContext = mergeContexts(contexts)
          val refContexts = processReflectiveCritics(globalContext)
          this.globalContext = mergeContexts(refContexts)
          log debug "out Contexts: " + contexts.toString()
        }
        case None => //End
      }
      goalManager.nextGoal(globalContext)
    }
    log debug "apply()"
    globalContext.lastResult match {
      case Some(r: ConceptNetwork) => {
        globalContext.domainModel match {
          case Some(model: ConceptNetwork) => {
            model.nodes = model.nodes ::: r.nodes
            globalContext.domainModel = Some(model)
            LongTermMemory.saveModel(request.domainName, model)
          }
          case None => {
            // no domain model  => do nothing
          }
        }
      }
      case None => {
        //no updated concepts => do nothing
      }
    }
    globalContext
  }

  /**
   * Runs Goals linked to Request as work-flows.
   * @param request to process.
   */
  def apply(request: Request): ShortTermMemory = {
    log debug "apply(" + request + ": Request))"
    initializeGlobalContext(request)
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
          log debug "Goal:" + goal
          resGoals = resGoals ::: List(goal)
          val resources: List[Resource] = selector.apply(goal)
          val contexts = processResources(resources)
          this.globalContext = mergeContexts(contexts)
          val refContexts = processReflectiveCritics(globalContext)
          this.globalContext = mergeContexts(refContexts)
          // log debug "out Contexts: " + contexts.toString()
        }
        case None => //End
      }
      goalManager.nextGoal(globalContext)
    }
    log debug "apply()"
    globalContext
  }

  def mergeContexts(contexts: List[ShortTermMemory]): ShortTermMemory = {
    val mergedRefContexts = ContextHelper.mergeLast(contexts)
    this.globalContext = copyGlobalContext(mergedRefContexts)
    this.globalContext
  }

  def processSelectorRequest(request: SelectorRequest): List[ShortTermMemory] = {
    val resources: List[Resource] = selector.apply(request)
    val contexts = processResources(resources, reflectiveFlag = true)
    contexts
  }

  /**
   * Runs through resources and interprets them as Critics or Way2Think with global context, stores result in global context.
   * @param resources to process.
   * @param reflectiveFlag Boolean flag to identify reflectiveResult use.
   * @return List of Contexts results of processing.
   */
  def processResources(resources: List[Resource], reflectiveFlag: Boolean = false): List[ShortTermMemory] = {
    log trace "processResources(" + resources + ": List[Resource] " + reflectiveFlag + " Boolean ): List[ShortTermMemory]"
    val contexts: List[List[ShortTermMemory]] = for (r <- resources) yield {
      val resContext = translate(r, this.globalContext)
      if (resContext != null) {
        globalContext = copyGlobalContext(resContext)
      }
      val contextToCheck = if (!reflectiveFlag) {
        (resContext.lastResult) match {
          case Some(sR: SelectorRequest) => {
            this.processSelectorRequest(sR)
          }
          case _ => List[ShortTermMemory](resContext)
        }
      } else {
        (resContext.lastReflectiveResult) match {
          case Some(sR: SelectorRequest) => {
            this.processSelectorRequest(sR)
          }
          case _ => List[ShortTermMemory](resContext)
        }
      }
      contextToCheck
    }
    log debug "processResources(): List[ShortTermMemory] = " + contexts.flatten.toString()
    contexts.flatten
  }

  /**
   * Start reflective critics and Cry4Help Way2Think
   * @param contextToCheck the context to check by reflective critics
   * @return ShortTermMemory with SelectorRequest-s
   */
  def processReflectiveCritics(contextToCheck: ShortTermMemory): List[ShortTermMemory] = {
    val reflectiveCritics: List[CriticModel] = KBAdapter.getReflectiveCritics
    processResources(reflectiveCritics, true)
  }

  def translate(resource: Resource, globalContext: ShortTermMemory): ShortTermMemory = {
    log debug "translate(" + resource + ": Resource, " + globalContext + ": ShortTermMemory)"
    resource match {
      case joinWay2Think: JoinWay2ThinkModel => {
        // run JoinProcessor
        val parameters = joinWay2Think.parameters
        val actions: List[Action] = parameters.map {
          a: ActionModel => this.instantiate(a.uri.name)
        }
        val res = JoinProcessor(actions, globalContext)
        log debug "translate(): ShortTermMemory " + res.toString
        res
      }
      case w2t: Way2ThinkModel => {
        val action = this.instantiate(w2t.uri.name)
        val res = action.apply(globalContext)
        log debug "translate(): ShortTermMemory " + res.toString
        res
      }
      case critic: CriticModel => {
        val action = this.instantiate(critic.uri.name)
        val res = action.apply(globalContext)
        log debug "translate(): ShortTermMemory " + res.toString
        res
      }
    }

  }

  def instantiate(className: String): Action = {
    log debug "instantiate(" + className + ": String): Action"
    val clazz = Class.forName(className)
    try {
      val temp = clazz.newInstance()
      val instance = temp.asInstanceOf[Action]
      log debug "instantiate(): Action = " + instance.toString
      instance
    } catch {
      case e: ClassCastException => {
        Cry4HelpWay2Think("$Not_alloved_class " + clazz.getName)
        throw new UnexpectedException("$Not_alloved_class " + clazz.getName)
      }
    }
  }

  def copyGlobalContext(resContext: ShortTermMemory): ShortTermMemory = {
    this.globalContext = ContextHelper.mergeWithBaseContext(globalContext, List(this.globalContext, resContext))
    this.globalContext
  }
}
