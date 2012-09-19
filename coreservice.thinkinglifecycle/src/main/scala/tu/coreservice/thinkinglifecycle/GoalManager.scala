package tu.coreservice.thinkinglifecycle

import tu.model.knowledge.training.Goal
import tu.dataservice.knowledgebaseserver.KBAdapter
import tu.model.knowledge.communication.Context
import tu.model.knowledge.action.ActionModel

/**
 * @author max talanov
 *         date 2012-07-18
 *         time: 8:35 PM
 */

class GoalManager {

  private val goals = KBAdapter.workflow
  private var currentIndex = 0

  /**
   * Gets next to specified goal.
   * @param currentGoal Goal to process.
   * @return next Goal.
   */
  def nextGoal(currentGoal: Goal): Option[Goal] = {
    if (!goals.contains(currentGoal)) {
      None
    } else {
      val index = goals.indexOf(currentGoal) + 1
      if (index > goals.size) {
        None
      } else {
        Some(goals(index))
      }
    }
  }

  def nextGoal(inputContext: Context): Option[Goal] = {
    inputContext.nextGoal match {
      case Some(g: Goal) => Some(g)
      case None => {
        nextGoal
      }
    }
  }

  def nextGoal: Option[Goal] = {
    this.currentIndex += 1
    if (this.currentIndex >= goals.size) {
      None
    } else {
      Some(goals(currentIndex))
    }
  }

  def resetIndex: Int = {
    this.currentIndex = 0
    this.currentIndex
  }

  def currentGoal: Option[Goal] = {
    if (currentIndex >= goals.size || currentIndex < 0) {
      None
    } else {
      Some(goals(this.currentIndex))
    }
  }

  def currentTrainingGoal: List[Goal] = {
    KBAdapter.trainingGoal.keys.toList
  }

}
