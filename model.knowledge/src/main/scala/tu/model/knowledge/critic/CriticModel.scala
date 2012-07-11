package tu.model.knowledge.critic

/**
 * @author max
 *         date 2012-07-12
 *         time: 12:08 AM
 */

import tu.model.knowledge.{Probability, Resource, KnowledgeURI}

class CriticModel(_uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Resource(_uri, _probability) {

}

object CriticModel{
  def apply(name: String): CriticModel = {
    new CriticModel(KnowledgeURI(name))
  }
}