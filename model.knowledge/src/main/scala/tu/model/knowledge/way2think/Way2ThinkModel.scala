package tu.model.knowledge.way2think

import tu.model.knowledge.{Resource, Probability, KnowledgeURI}

/**
 * @author max talanov
 *         date 2012-07-06
 *         time: 4:12 PM
 */

class Way2ThinkModel(_uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Resource(_uri, _probability)

object Way2ThinkModel {
  def apply(name: String): Way2ThinkModel = {
    new Way2ThinkModel(KnowledgeURI(name))
  }
}