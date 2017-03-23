package tu.coreservice.action.critic.state

import tu.coreservice.action.critic.CriticLink
import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.training.Goal
import tu.model.knowledge.{KnowledgeURI, Probability}

class ChannelOverflow(_way2Think: Way2Think, _excluded: List[CriticLink], _included: List[CriticLink], _uri: KnowledgeURI, _probability: Probability)
  extends CheckMyState(_excluded, _included, _uri, _probability) {

  val MAX_ACTIVE_CHANNELS_NUMBER = 50
  val ACTIVE_CHANNELS = "active_channels"

  private var goal: Option[Goal] = None

    //def this() = this(List[CriticLink](), List[CriticLink](), KnowledgeURI("ChannelOverflow"))

  override def setGoal(newGoal: Option[Goal]): Unit = goal = newGoal
  override def getGoal: Option[Goal] = goal
  override def getWay2Think: Way2Think = _way2Think
  override def resourceName: String = ACTIVE_CHANNELS
  override def check(input: Integer): Boolean = MAX_ACTIVE_CHANNELS_NUMBER >= input
}
