package tu.model.knowledge.way2think

import tu.model.knowledge.{Probability, KnowledgeURI}
import tu.model.knowledge.action.ActionModel

/**
 * @author max talanov
 *         date 2012-07-09
 *         time: 12:12 AM
 */

class JoinWay2ThinkModel(val parameters: List[Way2ThinkModel], _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends ActionModel(_uri, _probability){

}
