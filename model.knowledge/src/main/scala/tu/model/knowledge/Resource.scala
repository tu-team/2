package tu.model.knowledge

import java.sql.Timestamp
import java.util.Calendar

/**
 * @author max talanov
 *         Time: 11:19 PM
 */

abstract class Resource(_uri: KnowledgeURI, _probability: Probability) {

  def this(uri: KnowledgeURI) = {
    this(uri, new Probability())
  }

  def uri: KnowledgeURI = _uri

  def probability: Probability = _probability

  val created: Calendar = new java.util.GregorianCalendar()

}
