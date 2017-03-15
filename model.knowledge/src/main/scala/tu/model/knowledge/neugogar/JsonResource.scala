package tu.model.knowledge.neugogar

import com.fasterxml.jackson.databind.JsonNode
import tu.model.knowledge.{KnowledgeURI, Probability, Resource}

/**
  * Created by bublik on 06.03.17.
  */
class JsonResource(_uri: KnowledgeURI, _probability: Probability = new Probability(), _jsonNode: JsonNode) extends Resource(_uri, _probability) {

  def getJsonNode: JsonNode = {
    _jsonNode
  }

  def apply(json: JsonNode): JsonResource = {
    new JsonResource(KnowledgeURI("jsonNode"), new Probability(), json)
  }

  def this(value: JsonNode) = {
    this(KnowledgeURI("tu.model.knowledge.neugogar.JsonResource"), new Probability(), value: JsonNode)
  }
}
