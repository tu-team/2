package tu.model.knowledge.primitive

import tu.model.knowledge.{Probability, KnowledgeURI, Resource}

/**
 * Stores string as model element.
 * @author max talanov
 *         date 2012-05-01
 *         time: 11:37 PM
 */

case class KnowledgeString(_value: String, _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(_value: String, _uri: KnowledgeURI) = {
    this(_value, _uri, new Probability())
  }

  def value = _value

  override def toString = _value

  def this(map: Map[String, String]) = {
    this(
      map.get("value") match {
        case Some(x) => x
        case None => ""
      },
      new KnowledgeURI(map), new Probability(map)
    )
  }

  override def export: Map[String, String] = {
    Map("value" -> this.value) ++ uri.export ++ probability.export
  }

}

object KnowledgeString {

  def apply(content: String, name: String): KnowledgeString = {
    new KnowledgeString(content, KnowledgeURI(name))
  }

}
