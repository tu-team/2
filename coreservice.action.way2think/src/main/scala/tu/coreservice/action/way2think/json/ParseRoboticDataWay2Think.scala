package tu.coreservice.action.way2think.json

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
class ParseRoboticDataWay2Think(_uri: KnowledgeURI, _probability: Probability = new Probability()

                              ) extends Way2Think( _uri, _probability) {



  def this() = this( KnowledgeURI("ParseJsonWay2Think"))

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
            var channel = parsedJson.get("channel").asInt()
            val data = parsedJson.get("data")

            val roboticDataContainer: RoboticDataContainer = Option.apply(data) match {
              case Some(data: JsonNode) => {
                var roboticData = for (jsonNode: JsonNode <- data.elements().toList) yield {
                  var data = jsonNode.get("data").asText()
                  var time = jsonNode.get("time").asLong()
                  RoboticData(data, time)
                }
                new RoboticDataContainer(channel, roboticData)
              }
              case None => {
                new RoboticDataContainer(channel, List[RoboticData]())
              }
            }
            ContextHelper(List[Resource](roboticDataContainer), roboticDataContainer, this.getClass.getName)
          }
          case None => {
            ContextHelper(List[Resource](), this.getClass.getName)
          }
        }
      }
      case None => {
        ContextHelper(List[Resource](), this.getClass.getName)
      }
    }
  }

  override def start(): Boolean = false

  override def stop(): Boolean = false
}
