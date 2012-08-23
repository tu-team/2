package tu.model.knowledge.way2think

import tu.model.knowledge.{Probability, KnowledgeURI}
import tu.model.knowledge.action.ActionModel

/**
 * @author max talanov
 *         date 2012-07-06
 *         time: 4:12 PM
 */

class Way2ThinkModel(_uri: KnowledgeURI, _probability: Probability = new Probability())
  extends ActionModel(_uri, _probability) {

  def this(map: Map[String, String]) = {
    this(new KnowledgeURI(map), new Probability(map))
  }

}

object Way2ThinkModel {
  def apply(name: String): Way2ThinkModel = {
    new Way2ThinkModel(KnowledgeURI(name))
  }
}