package tu.coreservice.action.critic.robotic

import com.fasterxml.jackson.databind.JsonNode
import org.json4s.JsonAST.JValue
import org.json4s.jackson.JsonMethods
import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.communication.{ContextHelper, ShortTermMemory}
import tu.model.knowledge.neugogar.{RoboticData, RoboticDataContainer}
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.{KnowledgeURI, Probability, Resource}

import scala.collection.JavaConversions._


/**
  * Created by bublik on 04.03.17.
  */
class UnderstandRequestCritic(_uri: KnowledgeURI, _probability: Probability = new Probability()) extends Way2Think(_uri, _probability) {


  def this() = this(KnowledgeURI("ParseJsonWay2Think"))

  /**
    * Generic method of the action to be applied over input ShortTermMemory and put all results in output ShortTermMemory.
    *
    * @param inputContext ShortTermMemory of all inbound parameters
    * @return output ShortTermMemory.
    */
  override def apply(inputContext: ShortTermMemory): ShortTermMemory = {
    inputContext.lastResult match {
      case Some(data: KnowledgeString) => {
        JsonMethods.parseOpt(data.value) match {
          case Some(jsonValue: JValue) => {
            val parsedJson = JsonMethods.asJsonNode(jsonValue)
            val channelValid = parsedJson.get("channel") != null && parsedJson.get("channel").isIntegralNumber
            val dataValid = parsedJson.get("data") != null && parsedJson.get("data").isArray
            var result = ContextHelper(List[Resource](), this.getClass.getName)
            if (channelValid && dataValid) {
              val channel = parsedJson.get("channel").asInt()
              val data = parsedJson.get("data")
              val roboticData = for (jsonNode: JsonNode <- data.elements().toList) yield {
                val data = jsonNode.get("data").asText()
                val time = jsonNode.get("time").asLong()
                RoboticData(data, time)
              }
              val roboticDataContainer: RoboticDataContainer = new RoboticDataContainer(channel, roboticData)
              result = ContextHelper(List[Resource](roboticDataContainer), roboticDataContainer, this.getClass.getName)
            }
            result
          }
          case _=> emptyContext()
        }

      }
      case None => emptyContext()
    }
  }


  override def start(): Boolean = false

  override def stop(): Boolean = false
}
