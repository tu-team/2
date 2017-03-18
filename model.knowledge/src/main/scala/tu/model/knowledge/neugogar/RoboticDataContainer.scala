package tu.model.knowledge.neugogar

import tu.model.knowledge.{Constant, KnowledgeURI, Probability, Resource}

/**
  * Created by bublik on 14.03.17.
  */
case class RoboticDataContainer (channel: Integer,_data: List[RoboticData], _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(channel: Integer, value: List[RoboticData]) = {
    this(channel, value: List[RoboticData], KnowledgeURI(Constant.SPIKE_RESOURCE), new Probability())
  }

}
