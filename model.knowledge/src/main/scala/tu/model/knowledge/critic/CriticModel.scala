package tu.model.knowledge.critic

/**
 * @author max
 *         date 2012-07-12
 *         time: 12:08 AM
 */

import tu.model.knowledge._
import howto.Solution
import tu.model.knowledge.domain.ConceptNetwork
import tu.model.knowledge.action.ActionModel

class CriticModel(_uri: KnowledgeURI, _probability: Probability = new Probability())
  extends ActionModel(_uri, _probability) {

  def this(map: Map[String, String]) = {
    this(new KnowledgeURI(map), new Probability(map))
  }


  def load(kb: KB, selfMap: Map[String,  String]): CriticModel = {
    val ID = new KBNodeId(selfMap)
    var name =kb.loadChild(ID, Constant.CRITIC_MODEL_NAME,Constant.CRITIC_MODEL_NAME_LINK).toString()
    return new CriticModel(KnowledgeURI(name))

  }
}

object CriticModel{
  def apply(name: String): CriticModel = {
    new CriticModel(KnowledgeURI(name))
  }
}