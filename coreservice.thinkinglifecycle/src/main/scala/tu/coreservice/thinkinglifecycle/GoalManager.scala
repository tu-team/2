package tu.coreservice.thinkinglifecycle

import tu.model.knowledge.training.Goal
import tu.dataservice.knowledgebaseserver.KBAdapter
import tu.model.knowledge.communication.ShortTermMemory

/**
 * @author max talanov
 *         date 2012-07-18
 *         time: 8:35 PM
 */

class GoalManager(targetGoals: List[Goal]) {

  private var currentIndex = 0

  /**
   * Gets next to specified goal.
   * @param currentGoal Goal to process.
   * @return next Goal.
   */
  def nextGoal(currentGoal: Goal): Option[Goal] = {
    if (!targetGoals.contains(currentGoal)) {
      None
    } else {
      val index = targetGoals.indexOf(currentGoal) + 1
      if (index > targetGoals.size) {
        None
      } else {
        Some(targetGoals(index))
      }
    }
  }

  def nextGoal(inputContext: ShortTermMemory): Option[Goal] = {
    inputContext.nextGoal match {
      case Some(g: Goal) => Some(g)
      case None => {
        nextGoal
      }
    }
  }
  
  def nextGoal: Option[Goal] = {
    this.currentIndex += 1
    if (this.currentIndex >= targetGoals.size) {
      None
    } else {
      Some(targetGoals(currentIndex))
    }
  }

  def resetIndex: Int = {
    this.currentIndex = 0
    this.currentIndex
  }

  def currentGoal: Option[Goal] = {
    if (currentIndex >= targetGoals.size || currentIndex < 0) {
      None
    } else {
      Some(targetGoals(this.currentIndex))
    }
  }
  
}
