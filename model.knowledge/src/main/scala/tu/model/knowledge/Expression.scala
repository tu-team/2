package tu.model.knowledge

import primitive.KnowledgeBoolean

/**
 * @author toscheva
 * Date: 03.05.12
 * Time: 11:18
 */

abstract class Expression(_uri: KnowledgeURI, _probability: Probability) extends Resource(_uri, _probability) {

  def this(_uri: KnowledgeURI) = {
    this(_uri, new Probability())
  }

  def apply: KnowledgeBoolean

}
