package tu.model.knowledge

import java.util.Calendar
import util.Random
import org.slf4j.LoggerFactory

/**
 * @author max talanov
 *         Time: 11:19 PM
 */

abstract class Resource(_uri: KnowledgeURI, _probability: Probability = new Probability(), var KB_ID:Long = -1) {

  def this(uri: KnowledgeURI) = {
    this(uri, new Probability())
  }

  def this(map:Map[String, String]) = {
    this(new KnowledgeURI(map), new Probability(map),
       map.get("KB_ID")
      match {
        case Some(x) => x.toLong
        case None => -1
      })
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

  def exportLinks:Map[String, String] = {
    Map()
  }

  def save(kb:KB, parent:Resource, key:String):Boolean =
    save(kb, parent, key, Constant.DEFAULT_LINK_NAME)

  def save(kb:KB, parent:Resource, key:String, linkType:String):Boolean =
    kb.saveResource(this, parent, key, linkType)

  def loadLinks(kb:KB):Boolean = true

}
