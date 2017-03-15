package tu.model.knowledge.neugogar

import tu.model.knowledge.{KnowledgeURI, Probability, Resource}

/**
  * Created by bublik on 14.03.17.
  */
case class RoboticDataContainer (_data: List[RoboticData], _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(value: List[RoboticData]) = {
    this(value: List[RoboticData], KnowledgeURI("tu.model.knowledge.neugogar.RoboticDataContainer"), new Probability())
  }

}
