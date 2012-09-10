package tu.coreservice.action.selector

import tu.model.knowledge.training.Goal
import tu.model.knowledge.{KnowledgeURI, Resource}
import tu.dataservice.knowledgebaseserver.KBAdapter
import tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think
import tu.model.knowledge.communication.Request
import tu.model.knowledge.selector.SelectorRequest
import tu.model.knowledge.domain.Concept


/**
 * @author max talanov
 *         date 2012-07-05
 *         time: 6:50 PM
 */

class Selector {

  def apply(goal: Goal): List[Resource] = {
    //TODO get rid of KBAdapter
    KBAdapter.getByGoalName(goal.uri.name) match {
      case Some(resources) => {
        resources
      }
      case None => {
        List(Cry4HelpWay2Think("$Can_not_find_resources_for_goal"))
      }
    }
  }

  /*
  def apply(request: Request): List[Resource] = {
    KBAdapter.goalResourceMap.get(Goal("ProcessIncident")) match {
      case Some(resources) => {
        resources
      }
      case None => {
        List(Cry4HelpWay2Think("$Can_not_find_resources_for_goal"))
      }
    }
  }
   */

  def apply(request: SelectorRequest): List[Resource] = {
    val resourcesOption: List[Option[Resource]] = request.resourceURIList.map {
      uri: KnowledgeURI => KBAdapter.stringResourcesMap.get(uri.name)
    }
    val filteredResources: List[Option[Resource]] = resourcesOption.filter{
      case Some(_) => true
      case None => false
    }
    val res: List[Resource] = filteredResources.map {
      case Some(r: Resource) => r
    }
    res
  }

  //def workflow = KBAdapter.workflow

}
