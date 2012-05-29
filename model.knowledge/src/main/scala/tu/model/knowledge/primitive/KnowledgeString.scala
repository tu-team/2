package tu.model.knowledge.primitive

import tu.model.knowledge.{Probability, KnowledgeURI, Resource}

/**
 * @author max
 *         date 2012-05-01
 *         time: 11:37 PM
 */

class KnowledgeString(_value: String, _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(_value: String, _uri: KnowledgeURI) = {
    this(_value, _uri, new Probability())
  }

  def value = _value

  override def toString = _value

}
