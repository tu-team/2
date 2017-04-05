package tu.coreservice.action.critic.state

import tu.coreservice.action.critic.{Critic, CriticLink}
import tu.dataservice.knowledgebaseserver.KBAdapter
import tu.model.knowledge.selector.SelectorRequest
import tu.model.knowledge.communication.ShortTermMemory
import tu.model.knowledge.neugogar.RoboticStateDataContainer
import tu.model.knowledge.training.Goal
import tu.model.knowledge.{KBNodeId, KnowledgeURI, Probability, Resource}
import tu.model.knowledge.Constant.CURRENT_GOAL_RESOURCE

abstract class CheckMyState(_excluded: List[CriticLink], _included: List[CriticLink], _uri: KnowledgeURI, _probability: Probability)
  extends Critic(_excluded, _included, _uri, _probability) {

  var _goal: Option[Goal] = None

  def check(input: Integer): Boolean
  def resourceName: String

  override def start(): Boolean = false
  override def stop(): Boolean = false

  protected def apply(inputContext: ShortTermMemory, way2thinkURI: String, way2thinkName: String): ShortTermMemory = {
    _goal match {
      case None => emptyContext()
      case Some(goal) =>
        inputContext.findByName(CURRENT_GOAL_RESOURCE) match {
          case None => emptyContext()
          case Some(goalResource) =>
            KBAdapter.kb.loadChild(KBNodeId.apply(goalResource),"goal").get("name") match {
              case None => emptyContext()
              case Some(contextGoal) =>
                if (!goal.equals(Goal(contextGoal))) emptyContext()
                else {
                  inputContext.findByName(resourceName) match {
                    case Some(resource: RoboticStateDataContainer) =>
                      if (!check(resource._data)) emptyContext()
                      else {
                        inputContext.lastResult = Some(SelectorRequest(List(KnowledgeURI(way2thinkURI)), KnowledgeURI(way2thinkName)))
                        inputContext
                      }
                    case _: Any => emptyContext()
                  }
                }
            }
        }
    }
  }
}
