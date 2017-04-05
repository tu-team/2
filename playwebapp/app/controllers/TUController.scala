package controllers

import javax.inject._

import com.fasterxml.jackson.databind.JsonNode
import com.tuproject.spec.Request
import play.api.mvc._
import play.libs.Json
import services.ThinkingService;

@Singleton
class TUController @Inject()(TUService: ThinkingService) extends Controller {

  def trainAction = Action(parse.json) { request => {
    val requestBody = Json.fromJson(request.body.as[JsonNode], classOf[Request])
    TUService.train(requestBody)
    Ok("OK")
  }
  }


  def requestAction = Action(parse.json) { request => {
    val requestBody = Json.fromJson(request.body.as[JsonNode], classOf[Request])
    TUService.request(requestBody)
    Ok("OK")
  }
  }

  def roboticDataAction = Action(parse.json) { request => {
    val roboticData = request.body.toString()
    TUService.roboticRequest(roboticData)
    Ok("OK")
  }
  }

  def cleanKnowledgeBaseAction = Action {
    TUService.clean()
    Ok("OK")
  }


}