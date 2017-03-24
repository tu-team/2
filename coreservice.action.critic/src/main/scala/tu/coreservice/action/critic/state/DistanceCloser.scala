package tu.coreservice.action.critic.state

import tu.coreservice.action.critic.CriticLink
import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.Constant.{MIN_UPDATE_INTERVAL, DISTANCE_CLOSER_DATA_RESOURCE}
import tu.model.knowledge.{KnowledgeURI, Probability}

class DistanceCloser(_way2think: Way2Think, _excluded: List[CriticLink], _included: List[CriticLink], _uri: KnowledgeURI, _probability: Probability)
  extends CheckMyState(_excluded, _included, _uri, _probability) {

  def this(_way2think: Way2Think) = this(_way2think, List[CriticLink](), List[CriticLink](),
                                          KnowledgeURI.apply("DistanceCloser"), new Probability())

  private var way2think = _way2think

  override def setWay2Think(newW2T: Way2Think): Unit = way2think = newW2T
  override def getWay2Think: Way2Think = way2think
  override def check(input: Integer): Boolean = MIN_UPDATE_INTERVAL <= input
  override def resourceName: String = DISTANCE_CLOSER_DATA_RESOURCE
}
