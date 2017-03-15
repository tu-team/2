package tu.coreservice.action.critic.classifier

import com.fasterxml.jackson.databind.JsonNode
import tu.coreservice.action.critic.{Critic, CriticLink}
import tu.model.knowledge.communication.ShortTermMemory
import tu.model.knowledge.neugogar.{JsonResource, RoboticData, RoboticDataContainer}
import tu.model.knowledge.selector.SelectorRequest
import tu.model.knowledge.{Constant, KnowledgeURI, Probability}

import scala.collection.JavaConversions._

/**
  * Created by bublik on 04.03.17.
  */
abstract class SpikeClassifierCritic(_exclude: List[CriticLink], _include: List[CriticLink], _uri: KnowledgeURI, _probability: Probability = new Probability()

                               ) extends Critic(_exclude, _include, _uri, _probability) {


  override def start(): Boolean = false

  override def stop(): Boolean = false


  def classify(inputContext: ShortTermMemory, channel: Integer): ShortTermMemory = {

    inputContext.lastResult match {


      case Some(resource: JsonResource) => {
        var parsed = resource.getJsonNode
        var channel = parsed.get("channel").asInt()
        var roboticData = for (jsonNode: JsonNode <- parsed.get("data").elements().toList) yield {
          var data = jsonNode.get("data").asText()
          var time = jsonNode.get("time").asLong()
          RoboticData(channel, data, time)
        }

        inputContext.frames += KnowledgeURI(Constant.SPIKE_RESOURCE) -> new RoboticDataContainer(roboticData)

        if (resource.getJsonNode.get("channel").asInt(-1) == channel) {
          inputContext.lastResult = Some(SelectorRequest(List(KnowledgeURI(Constant.SELECTOR_REQUEST_SPIKE_GENERATOR_URI)), KnowledgeURI(Constant.SELECTOR_REQUEST_SPIKE_GENERATOR_URI_NAME)))
        }
        inputContext
      }
      case Some (_: Any) =>{
        inputContext
      }
      case None => {
        inputContext
      }
    }
  }
}