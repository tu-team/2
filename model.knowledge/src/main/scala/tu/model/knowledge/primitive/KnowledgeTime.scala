package tu.model.knowledge.primitive

import tu.model.knowledge.{Resource, Probability, KnowledgeURI}
import java.util.Date


/**
 * @author adel
 * Date: 24.06.12
 * Time: 13:53
 */

class KnowledgeTime(val value: Date, _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Resource(_uri, _probability) {

  def this(map: Map[String, String]) = {
    this(
      map.get("value") match {
          case Some(x) => new Date (x.toString.toLong)
          case None => new Date
      },
      new KnowledgeURI(map), new Probability(map))
  }

  override def export: Map[String, String] = {
    Map("value" -> this.value.getTime.toString) ++ uri.export ++ probability.export
  }

}