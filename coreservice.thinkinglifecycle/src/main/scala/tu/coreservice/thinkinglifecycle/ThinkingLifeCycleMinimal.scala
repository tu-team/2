package tu.coreservice.thinkinglifecycle

import java.net.InetAddress
import java.util.concurrent.TimeUnit

import akka.pattern.ask
import akka.util.Timeout
import org.slf4j.LoggerFactory
import tu.coreservice.action.selector.Selector
import tu.dataservice.knowledgebaseserver.KBAdapter
import tu.dataservice.memory.LongTermMemory
import tu.model.knowledge.action.ActionModel
import tu.model.knowledge.annotator.AnnotatedPhrase
import tu.model.knowledge.communication.{ShortTermMemory, _}
import tu.model.knowledge.critic.CriticModel
import tu.model.knowledge.domain.ConceptNetwork
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.selector.SelectorRequest
import tu.model.knowledge.training.Goal
import tu.model.knowledge.way2think.{JoinWay2ThinkModel, Way2ThinkModel}
import tu.model.knowledge.{Constant, KnowledgeURI, Resource}

import scala.concurrent.Await
import scala.util.{Failure, Success}
import scalaj.http.{Http, HttpOptions, HttpResponse}


/**
  * Minimal implementation of ThinkingLifeCycle.
  *
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
    globalContext = ContextHelper(List[Resource](request.inputResource), request.inputResource, "globalContext")
    globalContext.domainModel = LongTermMemory.domainModel(request.domainName)
    globalContext.simulationModel = LongTermMemory.simulationModel(request.domainName)
    globalContext.reformulationModel = LongTermMemory.reformulationModel(request.domainName)
    globalContext.solutions = LongTermMemory.solutions(request.domainName)
  }

  /**
    * Runs Goals linked to Request as work-flows.
    *
    * @param request to process.
    */
  def apply(request: TrainingRequest): ShortTermMemory = {
    log debug "apply(" + request + ": TrainingRequest))"
    initializeGlobalContext(request)
    runGoals(KBAdapter.trainingWorkflow)
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
    *
    * @param request to process.
    */
  def apply(request: Request): ShortTermMemory = {
    log debug "apply(" + request + ": Request))"
    initializeGlobalContext(request)
    if(globalContext.solutions == Nil) {
      val annotatedPhrase = new AnnotatedPhrase(
        List(
          AnnotatedPhrase(request.inputResource.toString)
        ), request.inputResource.uri
      )
      val re: List[AnnotatedPhrase] = List(annotatedPhrase)
      globalContext._notUnderstoodPhrases = re
    }
    runGoals(KBAdapter.roboticDataWorkflow)
  }

  def apply(request: RoboticDataRequest): ShortTermMemory = {
    initializeGlobalContext(request)
    runGoals(KBAdapter.roboticDataWorkflow)
  }


  def runGoals(targetGoals: List[Goal]): ShortTermMemory = {
    val goalManager = new GoalManager(targetGoals)
    // get selector resources for request this is first goal = Goal("ProcessIncident")
    // val resources: List[Resource] = selector.apply(request)
    // currently all goals are in goals list in KBPrimitive

    // process resources
    while (goalManager.currentGoal.isDefined) {
      // get next goal
      // process next goal
      val goal = goalManager.currentGoal.get
      log debug "Goal:" + goal
      val resources: List[Resource] = selector.apply(goal)
      val contexts = processResources(resources)
      this.globalContext = mergeContexts(contexts)
      val refContexts = processReflectiveCritics(globalContext)
      this.globalContext = mergeContexts(refContexts)
      goalManager.nextGoal(globalContext)
    }
    log debug "apply()"
    val checkCry4Help = KnowledgeURI("Cry4Help")
    if(globalContext._lastReflectiveResult.last.uri.equals(checkCry4Help)) {
      val error = new Error("Error occurred. Cry4Help scenario initialization.")
      globalContext._lastError = Option[Error](error)
      return initializeCry4Help(globalContext)
    }
    globalContext
  }

  private def initializeCry4Help(context: ShortTermMemory): ShortTermMemory = {
    val cry4Help = KnowledgeURI("Cry4Help")
    val address = InetAddress.getLocalHost.toString
    val hostname = InetAddress.getLocalHost.getHostName
    val priority = 1
    val request = context.notUnderstoodPhrases

    /*val response = Http("http://tu-communication-server.com/url")
        .param("address", address)
        .param("hostname", hostname)
        .param("priority", priority.toString)
        .param("request", request.toString())
      .header("Content-Type", "application/json")
      .header("Charset", "UTF-8")
      .option(HttpOptions.readTimeout(10000)).asString*/

    //mocked response for unit tests
    val mockedResponse = new HttpResponse[String]("OK", 200, Map[String, IndexedSeq[String]]()).toString
    context.userResponse = new Response(new KnowledgeString(mockedResponse, cry4Help), cry4Help)
    context
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
    *
    * @param resources      to process.
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
        resContext.lastResult match {
          case Some(sR: SelectorRequest) => {
            this.processSelectorRequest(sR)
          }
          case _ => List[ShortTermMemory](resContext)
        }
      } else {
        resContext.lastReflectiveResult match {
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
    *
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
          //TODO run concurrently
          var contexts = for (action <- joinWay2Think.parameters) yield {
            translate(action, globalContext)
          }
          ContextHelper.merge(contexts)
      }
      case actionModel: ActionModel => {
        implicit val timeout = Timeout(20, TimeUnit.SECONDS)
        val actionActor = AkkaProvider.find(actionModel.uri.name)
        val future = actionActor ? globalContext
        val akkaResult = Await.result(future, timeout.duration)
        akkaResult match {
          case Success(stm: ShortTermMemory) => {
            stm
          }
          case Failure(e: Throwable) => {
            throw new RuntimeException(e.getMessage, e)
          }
        }
      }
    }
  }

  def copyGlobalContext(resContext: ShortTermMemory): ShortTermMemory = {
    this.globalContext = ContextHelper.mergeWithBaseContext(globalContext, List(this.globalContext, resContext))
    this.globalContext
  }
}
