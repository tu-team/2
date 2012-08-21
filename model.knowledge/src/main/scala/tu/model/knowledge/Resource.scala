package tu.model.knowledge

import java.util.Calendar

/**
 * @author max talanov
 *         Time: 11:19 PM
 */

abstract class Resource(_uri: KnowledgeURI, _probability: Probability = new Probability()) {

  def this(uri: KnowledgeURI) = {
    this(uri, new Probability())
  }

  def uri: KnowledgeURI = _uri

  def probability: Probability = _probability

  val created: Calendar = new java.util.GregorianCalendar()

  override def toString = {
    if (uri != null) {
      uri.toString + ":" + probability.toString
    } else {
      this.getClass.getName
    }
  }
          /*
  def export:Map[String, String] = {
    Map(
      "namespace" -> _uri.namespace(),
      "name" -> _uri.name,
      "revision" -> _uri.revision(),
      "frequency" -> _probability.getFrequency().toSring,
      "confidence" -> _probability.getConfidence().toSring
    )
  }

            */
}

/*
object Resource {
  def importFrom(map:Map[String, String]):Resource =
  {
      //log warning("{} parsing", "Resource)

    val x = new Resource(new KnowledgeURI(), new Probability(Double.parceDouble(map.get("frequency")),  Double.parceDouble(map.get("confidence"))))
  }
}
  */