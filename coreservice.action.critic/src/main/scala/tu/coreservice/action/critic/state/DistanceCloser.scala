package tu.coreservice.action.critic.state

import tu.coreservice.action.critic.CriticLink
import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.training.Goal
import tu.model.knowledge.{KnowledgeURI, Probability}

class DistanceCloser(_way2Think: Way2Think, _excluded: List[CriticLink], _included: List[CriticLink], _uri: KnowledgeURI, _probability: Probability)
  extends CheckMyState(_excluded, _included, _uri, _probability) {

  val MIN_UPDATE_INTERVAL = 1000
  val UPDATED_DISTANCE_CHANNEL = "updated_distance_channel"

  private var goal: Option[Goal] = None

  override def setGoal(newGoal: Option[Goal]): Unit = goal = newGoal
  override def getGoal: Option[Goal] = goal
  override def getWay2Think: Way2Think = _way2Think
  override def check(input: Integer): Boolean = MIN_UPDATE_INTERVAL <= input
  override def resourceName: String = UPDATED_DISTANCE_CHANNEL
}
