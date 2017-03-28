package tu.coreservice.action.critic.spike

import tu.coreservice.action.critic.CriticLink
import tu.model.knowledge.communication.ShortTermMemory
import tu.model.knowledge.{KnowledgeURI, Probability}

class HandFamily(_excluded: List[CriticLink], _included: List[CriticLink], _uri: KnowledgeURI, _probability: Probability)
  extends FamilyCritic(_excluded, _included, _uri, _probability) {

  val HAND_FAMILY = "hand"

  def this() = this(List[CriticLink](), List[CriticLink](),KnowledgeURI("HandFamily"), new Probability())

  override def apply(inputContext: ShortTermMemory): ShortTermMemory = {
    apply(inputContext, HAND_FAMILY)
  }
}
