package tu.model.knowledge.communication

/**
 * @author max talanov
 *         date 2012-07-06
 *         time: 6:42 PM
 */

import tu.model.knowledge.{Probability, Resource, KnowledgeURI}
import tu.model.knowledge.primitive.KnowledgeString

class Request(_inputText: KnowledgeString, _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Resource(_uri, _probability) {

  def inputText = _inputText

}
