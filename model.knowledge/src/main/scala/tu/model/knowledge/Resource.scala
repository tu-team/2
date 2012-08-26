package tu.model.knowledge

import java.util.Calendar
import util.Random
import org.slf4j.LoggerFactory

/**
 * @author max talanov
 *         Time: 11:19 PM
 */

abstract class Resource(_uri: KnowledgeURI, _probability: Probability = new Probability()) {

  def this(uri: KnowledgeURI) = {
    this(uri, new Probability())
  }

  def this(map:Map[String, String]) = {
    this(new KnowledgeURI(map), new Probability(map))
  }

  def uri: KnowledgeURI = _uri

  def probability: Probability = _probability

  val created = new java.util.Date

  override def toString = {
    if (uri != null) {
      uri.toString + ":" + probability.toString
    } else {
      this.getClass.getName
    }
  }

  def export:Map[String, String] = {
    uri.export ++ probability.export
  }

}
