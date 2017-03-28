package tu.coreservice.action.critic.state

import tu.coreservice.action.critic.CriticLink
import tu.model.knowledge.communication.ShortTermMemory
import tu.model.knowledge.{KnowledgeURI, Probability}

class ChannelOverflow(_way2thinkURI: String, _way2thinkURIName: String, _excluded: List[CriticLink],
                      _included: List[CriticLink], _uri: KnowledgeURI, _probability: Probability)
  extends CheckMyState(_excluded, _included, _uri, _probability) {

  def this(_way2thinkURI: String, _way2thinkURIName: String) =
    this(_way2thinkURI, _way2thinkURIName,List[CriticLink](), List[CriticLink](),
      KnowledgeURI("ChannelOverflow"), new Probability())

  val CHANNEL_OVERFLOW_DATA_RESOURCE = "activeChannels"
  val ACTIVE_CHANNELS_MAX_NUMBER = 50
    
  override def resourceName: String = CHANNEL_OVERFLOW_DATA_RESOURCE
  override def check(input: Integer): Boolean = ACTIVE_CHANNELS_MAX_NUMBER >= input

  /**
    * Generic method of the action to be applied over input ShortTermMemory and put all results in output ShortTermMemory.
    *
    * @param inputContext ShortTermMemory of all inbound parameters
    * @return output ShortTermMemory.
    */
  override def apply(inputContext: ShortTermMemory): ShortTermMemory = apply(inputContext,_way2thinkURI,_way2thinkURIName)
}
