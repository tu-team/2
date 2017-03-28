package tu.model.knowledge.neugogar

import tu.model.knowledge.{Constant, KnowledgeURI, Probability, Resource}

case class SpikeDataContainer(_family: String, _time: List[Long], _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(_family: String, _time: List[Long]) = this(_family,_time,KnowledgeURI(Constant.SPIKE_RESOURCE), new Probability())
}
