package tu.coreservice.action.critic.classifier

import tu.coreservice.action.critic.{Critic, CriticLink}
import tu.model.knowledge.communication.ShortTermMemory
import tu.model.knowledge.neugogar.RoboticDataContainer
import tu.model.knowledge.selector.SelectorRequest
import tu.model.knowledge.{Constant, KnowledgeURI, Probability}


/**
  * Created by bublik on 04.03.17.
  */
abstract class SpikeClassifierCritic(_exclude: List[CriticLink], _include: List[CriticLink], _uri: KnowledgeURI, _probability: Probability = new Probability()

                               ) extends Critic(_exclude, _include, _uri, _probability) {


  override def start(): Boolean = false

  override def stop(): Boolean = false


  def classify(inputContext: ShortTermMemory, channel: Integer): ShortTermMemory = {
    inputContext.findByName(Constant.SPIKE_RESOURCE) match {
      case Some(roboticDataContainer: RoboticDataContainer) => {
        if (roboticDataContainer.channel == channel) {
          inputContext.lastResult = Some(SelectorRequest(List(KnowledgeURI(Constant.SELECTOR_REQUEST_SPIKE_GENERATOR_URI)), KnowledgeURI(Constant.SELECTOR_REQUEST_SPIKE_GENERATOR_URI_NAME)))
        }
        inputContext
      }
      case _: Any => {
        inputContext
      }
    }
  }
}