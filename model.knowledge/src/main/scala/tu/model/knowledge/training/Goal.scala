package tu.model.knowledge.training

import tu.model.knowledge.{Probability, KnowledgeURI, Resource}

/**
 * @author max talanov
 *         date 2012-07-05
 *         time: 7:37 PM
 */

class Goal(_uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Resource(_uri, _probability) {

}
