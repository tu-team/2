package tu.model.knowledge.training

import tu.model.knowledge.{Probability, KnowledgeURI, Resource}

/**
 * @author max talanov
 *         date 2012-07-05
 *         time: 7:37 PM
 */

class Goal(_uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Resource(_uri, _probability) {

  def eq(other: Goal): Boolean = {
    other.uri.name.equals(this.uri.name)
  }

  def equals(other: Goal): Boolean = {
    other.uri.name.equals(this.uri.name)
  }

  def this(map: Map[String, String]) = {
    this(new KnowledgeURI(map), new Probability(map))
  }

}

object Goal {
  def apply(name: String): Goal = {
    new Goal(KnowledgeURI(name))
  }
}
