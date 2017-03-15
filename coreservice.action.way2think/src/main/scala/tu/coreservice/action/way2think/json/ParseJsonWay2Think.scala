package tu.coreservice.action.way2think.json

import org.json4s.JsonAST.JValue
import org.json4s.jackson.JsonMethods
import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.communication.ShortTermMemory
import tu.model.knowledge.neugogar.JsonResource
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.{KnowledgeURI, Probability}


/**
  * Created by bublik on 04.03.17.
  */
class ParseJsonWay2Think( _uri: KnowledgeURI, _probability: Probability = new Probability()

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
            val parsed = JsonMethods.asJsonNode(jsonValue)
            inputContext.lastResult = Some(new JsonResource(parsed))
            inputContext
          }
          case None => {
            inputContext
          }
      }
    }
  }
  }

  override def start(): Boolean = false

  override def stop(): Boolean = false
}
