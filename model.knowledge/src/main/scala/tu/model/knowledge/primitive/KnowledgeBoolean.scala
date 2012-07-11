package tu.model.knowledge.primitive

import tu.model.knowledge.{Resource, Probability, KnowledgeURI}

/**
 * Stores Boolean as model element
 * @author talanovm
 *         date 2012-05-06
 *         time: 11:45 PM
 */

case class KnowledgeBoolean(_value: Boolean, _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(_value: Boolean, _uri: KnowledgeURI) = {
    this(_value: Boolean, _uri: KnowledgeURI, new Probability())
  }

  def value = _value

}
