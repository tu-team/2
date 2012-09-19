package tu.model.knowledge.communication

import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.{Resource, Probability, KnowledgeURI}

/**
 * @author max talanov
 *         date 2012-09-09
 *         time: 10:24 PM
 */
class Response(_inputText: KnowledgeString, _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Resource(_uri, _probability) {

}
