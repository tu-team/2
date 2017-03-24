package tu.model.knowledge.neugogar

import tu.model.knowledge.{KnowledgeURI, Probability, Resource}

case class RoboticStateDataContainer (_data: Integer, _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(_data: Integer, _name: String) = this(_data,KnowledgeURI.apply(_name), new Probability())
}
