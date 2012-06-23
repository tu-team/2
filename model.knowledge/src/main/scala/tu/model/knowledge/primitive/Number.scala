package tu.model.knowledge.primitive

import tu.model.knowledge.{Probability, KnowledgeURI, Resource}


/**
 * @author max
 *         date 2012-05-01
 *         time: 7:46 AM
 */

class Number(_value: Double, _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def value: Double = _value

}
