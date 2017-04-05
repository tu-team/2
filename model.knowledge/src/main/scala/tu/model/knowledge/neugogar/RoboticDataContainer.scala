package tu.model.knowledge.neugogar

import tu.model.knowledge.{Constant, KnowledgeURI, Probability, Resource}

/**
  * Created by bublik on 14.03.17.
  */
case class RoboticDataContainer (_channel: Integer,_data: List[RoboticData], _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(_channel: Integer, _values: List[RoboticData]) = {
    this(_channel, _values: List[RoboticData], KnowledgeURI(Constant.SPIKE_RESOURCE), new Probability())
  }

  def channel = _channel

  def values = _data

}
