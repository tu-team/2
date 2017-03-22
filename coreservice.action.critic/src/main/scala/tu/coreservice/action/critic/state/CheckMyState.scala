package tu.coreservice.action.critic.state

import tu.coreservice.action.critic.{Critic, CriticLink}
import tu.coreservice.action.way2think.Way2Think
import tu.dataservice.knowledgebaseserver.providers.N4JKB
import tu.model.knowledge.communication.ShortTermMemory
import tu.model.knowledge.training.Goal
import tu.model.knowledge.{KBNodeId, KnowledgeURI, Probability}

abstract class CheckMyState(_excluded: List[CriticLink], _included: List[CriticLink], _uri: KnowledgeURI, _probability: Probability)
  extends Critic(_excluded, _included, _uri, _probability) {

  val CURRENT_GOAL_RESOURCE = "current_goal"

  def setGoal(newGoal: Option[Goal]): Unit
  def getGoal: Option[Goal]
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
  override def apply(inputContext: ShortTermMemory): ShortTermMemory = {
    getGoal match {
      case None => inputContext
      case Some(goal) =>
        inputContext.findByName(CURRENT_GOAL_RESOURCE) match {
          case None => inputContext
          case Some(resource) =>
            N4JKB.loadChild(KBNodeId.apply(resource), "goal").get("name") match {
              case None => inputContext
              case Some(name) =>
                //TODO: ask about proper class for loading goals
                if (!goal.equals(Goal.apply(name))) inputContext
                else {
                  inputContext.findByName(resourceName) match {
                    case None => inputContext
                    case Some(resource2) =>
                      N4JKB.loadChild(KBNodeId.apply(resource2), resourceName+"-key").get("content") match {
                        case None => inputContext
                        case Some(data) =>
                          if (!check(Integer.parseInt(data))) inputContext
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
}
