package tu.model.knowledge.critic

/**
 * @author max talanov
 *         date 2012-07-12
 *         time: 12:08 AM
 */

import tu.model.knowledge._
import tu.model.knowledge.action.ActionModel

class CriticModel(_uri: KnowledgeURI, _probability: Probability = new Probability())
  extends ActionModel(_uri, _probability) {

  def this(map: Map[String, String]) = {
    this(new KnowledgeURI(map), new Probability(map))
  }
}

object CriticModel {
  def apply(name: String): CriticModel = {
    new CriticModel(KnowledgeURI(name))
  }

  def load(kb: KB, selfMap: Map[String, String]): CriticModel = {
    val name = selfMap("class")
    new CriticModel(KnowledgeURI(name))
  }
}