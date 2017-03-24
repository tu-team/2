package tu.coreservice.action.critic.state

import tu.coreservice.action.critic.{Critic, CriticLink}
import tu.coreservice.action.way2think.Way2Think
import tu.dataservice.knowledgebaseserver.KBAdapter
import tu.model.knowledge.communication.ShortTermMemory
import tu.model.knowledge.neugogar.RoboticStateDataContainer
import tu.model.knowledge.training.Goal
import tu.model.knowledge.{KBNodeId, KnowledgeURI, Probability, Resource}
import tu.model.knowledge.Constant.CURRENT_GOAL_RESOURCE

abstract class CheckMyState(_excluded: List[CriticLink], _included: List[CriticLink], _uri: KnowledgeURI, _probability: Probability)
  extends Critic(_excluded, _included, _uri, _probability) {

  var goal: Option[Goal] = None

  // maybe move to ShortTermMemory
  private val emptyContext: ShortTermMemory = ShortTermMemory(Map[KnowledgeURI, Resource](),KnowledgeURI.apply("Empty"))

  def setWay2Think(newW2T: Way2Think): Unit
  def getWay2Think: Way2Think
  def check(input: Integer): Boolean
  def resourceName: String

  override def start(): Boolean = false
  override def stop(): Boolean = false

  /**
    * Generic method of the action to be applied over input ShortTermMemory and put all results in output ShortTermMemory.
    *
    * @param inputContext ShortTermMemory of all inbound parameters
    * @return output ShortTermMemory.
    */
  // TODO: decide what better: throw exception or return empty context
  override def apply(inputContext: ShortTermMemory): ShortTermMemory = {
    goal match {
      case None => emptyContext //throw new GoalException("Target goal is not set to critic.")
      case Some(_goal) =>
        inputContext.findByName(CURRENT_GOAL_RESOURCE) match {
          case None => emptyContext //throw new GoalException("No goal in input context.")
          case Some(goalResource) =>
            KBAdapter.kb.loadChild(KBNodeId.apply(goalResource),"goal").get("name") match {
              case None => emptyContext //throw new GoalException("No goal has been found by URI="+goalResource.uri._name)
              case Some(contextGoal) =>
                if (!_goal.equals(Goal.apply(contextGoal))) emptyContext
                else {
                  inputContext.findByName(resourceName) match {
                    case None => emptyContext
                    case Some(resource: RoboticStateDataContainer) =>
                      if (!check(resource._data)) throw new IllegalArgumentException
                      else {
                        getWay2Think.apply(inputContext)
                      }
                  }
                }
            }
        }
    }
  }
}
