package tu.model.knowledge.way2think

import tu.model.knowledge.{Probability, KnowledgeURI}

/**
 * @author max talanov
 *         date 2012-07-09
 *         time: 12:12 AM
 */

class JoinWay2ThinkModel(val parameters: List[Way2ThinkModel], _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Way2ThinkModel(_uri, _probability){

}
