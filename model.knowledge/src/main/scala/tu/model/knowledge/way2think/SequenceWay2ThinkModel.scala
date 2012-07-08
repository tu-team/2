package tu.model.knowledge.way2think

/**
 * @author max talanov
 *         date 2012-07-09
 *         time: 12:13 AM
 */

import tu.model.knowledge.{Probability, KnowledgeURI}

class SequenceWay2ThinkModel(val parameters: List[Way2ThinkModel], _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Way2ThinkModel(_uri, _probability) {

}
