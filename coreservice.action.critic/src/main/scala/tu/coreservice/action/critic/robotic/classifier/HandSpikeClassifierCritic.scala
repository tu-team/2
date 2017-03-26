package tu.coreservice.action.critic.robotic.classifier

import tu.coreservice.action.critic.CriticLink
import tu.model.knowledge.communication.ShortTermMemory
import tu.model.knowledge.{KnowledgeURI, Probability}

/**
  * Created by bublik on 04.03.17.
  */
class HandSpikeClassifierCritic(_exclude: List[CriticLink], _include: List[CriticLink], _uri: KnowledgeURI, _probability: Probability = new Probability()

                          ) extends SpikeClassifierCritic(_exclude, _include, _uri, _probability) {

  val HAND_CHANNEL_NUMBER = 0
  def this() = this(List[CriticLink](), List[CriticLink](), KnowledgeURI("HandSpikeClassifierCritic"))

  /**
    * Generic method of the action to be applied over input ShortTermMemory and put all results in output ShortTermMemory.
    *
    * @param inputContext ShortTermMemory of all inbound parameters
    * @return output ShortTermMemory.
    */
  override def apply(inputContext: ShortTermMemory): ShortTermMemory = classify(inputContext, HAND_CHANNEL_NUMBER)

}
