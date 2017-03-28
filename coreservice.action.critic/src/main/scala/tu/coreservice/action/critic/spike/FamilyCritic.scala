package tu.coreservice.action.critic.spike

import tu.coreservice.action.critic.{Critic, CriticLink}
import tu.model.knowledge.Constant.SPIKE_RESOURCE
import tu.model.knowledge.communication.ShortTermMemory
import tu.model.knowledge.neugogar.{RoboticData, RoboticDataContainer, SpikeDataContainer}
import tu.model.knowledge.{Constant, KnowledgeURI, Probability}
import tu.model.knowledge.selector.SelectorRequest

abstract class FamilyCritic(_excluded: List[CriticLink], _included: List[CriticLink], _uri: KnowledgeURI, _probability: Probability)
  extends Critic(_excluded, _included, _uri, _probability) {

  override def start(): Boolean = false
  override def stop(): Boolean = false

  protected def apply(inputContext: ShortTermMemory, family: String): ShortTermMemory = {
    inputContext.findByName(SPIKE_RESOURCE) match {
      case Some(resource: RoboticDataContainer) =>
        val timeList = resource._data.toStream.map((rd: RoboticData) => rd._time).toList
        val spikeData = new SpikeDataContainer(family,timeList)
        inputContext._frames += KnowledgeURI(SPIKE_RESOURCE) -> spikeData
        inputContext.lastResult = Some(SelectorRequest(List(KnowledgeURI(Constant.SELECTOR_REQUEST_SPIKE_GENERATOR_URI)),
                                        KnowledgeURI(Constant.SELECTOR_REQUEST_SPIKE_GENERATOR_URI_NAME)))
        inputContext
      case _:Any => inputContext
    }
  }
}
