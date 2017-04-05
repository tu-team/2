package services

import javax.inject.Singleton

import org.slf4j.LoggerFactory
import tu.coreservice.thinkinglifecycle.ThinkingLifeCycleMinimal
import tu.dataservice.knowledgebaseserver.KBAdapter
import tu.model.knowledge.communication.{Request, RoboticDataRequest, TrainingRequest}
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.{Constant, KnowledgeURI}


/**
  * Created by alexanderdemin on 19/02/2017.
  */
@Singleton
class ThinkingService {

  val log = LoggerFactory.getLogger(this.getClass)

  def train(request: com.tuproject.spec.Request) = {
    val requestText = request.getRequestText
    log.debug("Running thinking lifecycle train")
    val r = new TrainingRequest(KnowledgeString(requestText, "inputtext"), KnowledgeURI("testRequest"), KnowledgeURI(Constant.defaultDomainName))
    var lifeCycle = new ThinkingLifeCycleMinimal()
    lifeCycle(r)
    log.info("End")
  }

  def request(request: com.tuproject.spec.Request) = {
    val requestText = request.getRequestText
    log.debug("Running thinking lifecycle request")
    val r = new Request(KnowledgeString(requestText, "inputtext"), KnowledgeURI("testRequest"), KnowledgeURI(Constant.defaultDomainName))
    var lifeCycle = new ThinkingLifeCycleMinimal()
    lifeCycle(r)
    log.info("End")
  }

  def roboticRequest(body: String) = {
    val r = new RoboticDataRequest(KnowledgeString(body, "rawroboticdata"), KnowledgeURI("roboticDataRequest"), KnowledgeURI(Constant.defaultDomainName))
    var lifeCycle = new ThinkingLifeCycleMinimal()
    lifeCycle(r)
  }

  def clean() = {
    KBAdapter.cleanDatabase()
  }

}
