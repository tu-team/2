package tu.coreservice.action.critic.analyser

import tu.Critic
import tu.model.knowledge.domain.ConceptNetwork
import tu.model.knowledge.selector.SelectorRequest
import tu.model.knowledge.{Probability, KnowledgeURI}

/**
 * @author max talanov
 *         date 2012-06-28
 *         time: 4:01 PM
 */

case class DirectInstructionAnalyser(_exclude: List[Critic], _include: List[Critic])
  extends Critic(_exclude, _include) {
  val selectorRequestURIName = "DirectInstruction"
  val actionName = "action"

  def apply(currentSituation: ConceptNetwork, domainModel: ConceptNetwork): SelectorRequest = {
    //if current situation contains action and has link to an object to be applied
    val actions = currentSituation.getNodeByGeneralisationName(actionName)
    if (actions.size > 0) {
      val p = 0.5
    }
    new SelectorRequest(KnowledgeURI(selectorRequestURIName), KnowledgeURI(selectorRequestURIName + "Request"), new Probability())
  }

}
