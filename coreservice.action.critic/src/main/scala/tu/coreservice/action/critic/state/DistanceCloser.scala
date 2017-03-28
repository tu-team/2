package tu.coreservice.action.critic.state

import tu.coreservice.action.critic.CriticLink
import tu.model.knowledge.communication.ShortTermMemory
import tu.model.knowledge.{KnowledgeURI, Probability}

class DistanceCloser(_way2thinkURI: String, _way2thinkURIName: String, _excluded: List[CriticLink],
                     _included: List[CriticLink], _uri: KnowledgeURI, _probability: Probability)
  extends CheckMyState(_excluded, _included, _uri, _probability) {

  def this(_way2thinkURI: String, _way2thinkURIName: String) =
    this(_way2thinkURI, _way2thinkURIName, List[CriticLink](), List[CriticLink](),
      KnowledgeURI.apply("DistanceCloser"), new Probability())

  val DISTANCE_CLOSER_DATA_RESOURCE = "timeFromLastUpdate"
  val MIN_UPDATE_INTERVAL = 1000

  override def check(input: Integer): Boolean = MIN_UPDATE_INTERVAL <= input
  override def resourceName: String = DISTANCE_CLOSER_DATA_RESOURCE

  /**
    * Generic method of the action to be applied over input ShortTermMemory and put all results in output ShortTermMemory.
    *
    * @param inputContext ShortTermMemory of all inbound parameters
    * @return output ShortTermMemory.
    */
  override def apply(inputContext: ShortTermMemory): ShortTermMemory = apply(inputContext,_way2thinkURI,_way2thinkURIName)
}
