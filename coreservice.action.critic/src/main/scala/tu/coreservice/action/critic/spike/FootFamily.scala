package tu.coreservice.action.critic.spike

import tu.coreservice.action.critic.CriticLink
import tu.model.knowledge.communication.ShortTermMemory
import tu.model.knowledge.{KnowledgeURI, Probability}

class FootFamily (_excluded: List[CriticLink], _included: List[CriticLink], _uri: KnowledgeURI, _probability: Probability)
  extends FamilyCritic(_excluded, _included, _uri, _probability) {

  val FOOT_FAMILY = "foot"

  def this() = this(List[CriticLink](), List[CriticLink](),KnowledgeURI("FootFamily"), new Probability())

  override def apply(inputContext: ShortTermMemory): ShortTermMemory = {
    apply(inputContext,FOOT_FAMILY)
  }
}
