package tu.coreservice.action.selector

import tu.model.knowledge.training.Goal
import tu.model.knowledge.Resource
import tu.dataservice.knowledgebaseserver.KBPrototype
import tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think
import tu.model.knowledge.communication.Request


/**
 * @author max talanov
 *         date 2012-07-05
 *         time: 6:50 PM
 */

class Selector {

  def apply(goal: Goal): List[Resource] = {
    //TODO get rid of KBPrototype
    KBPrototype.goalResourceMap.get(goal) match {
      case Some(resources) => {
        resources
      }
      case None => {
        List(Cry4HelpWay2Think("$Can_not_find_resources_for_goal"))
      }
    }
  }

  def apply(request: Request): List[Resource] = {
    KBPrototype.goalResourceMap.get(Goal("ProcessIncident")) match {
      case Some(resources) => {
        resources
      }
      case None => {
        List(Cry4HelpWay2Think("$Can_not_find_resources_for_goal"))
      }
    }
  }

}
