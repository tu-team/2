package tu.model.knowledge.way2think

import tu.model.knowledge.{Probability, KnowledgeURI}
import tu.model.knowledge.action.ActionModel

/**
 * @author max talanov
 *         date 2012-07-09
 *         time: 12:12 AM
 */

class JoinWay2ThinkModel(val parameters: List[ActionModel], _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends ActionModel(_uri, _probability) {

  def this(map: Map[String, String]) = {
    this(new KnowledgeURI(map), new Probability(map))
  }

}

object JoinWay2ThinkModel {
  def apply(parameters: List[ActionModel], name: String): JoinWay2ThinkModel = {
    new JoinWay2ThinkModel(parameters, KnowledgeURI(name))
  }
}
