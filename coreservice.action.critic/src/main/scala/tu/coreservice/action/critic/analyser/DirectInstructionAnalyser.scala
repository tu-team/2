package tu.coreservice.action.critic.analyser

import tu.Critic
import tu.model.knowledge.domain.{ConceptLink, Concept, ConceptNetwork}
import tu.model.knowledge.selector.SelectorRequest
import tu.model.knowledge.{Constant, Probability, KnowledgeURI}

/**
 * @author max talanov
 *         date 2012-06-28
 *         time: 4:01 PM
 */

case class DirectInstructionAnalyser(_exclude: List[Critic], _include: List[Critic])
  extends Critic(_exclude, _include) {


  def apply(currentSituation: ConceptNetwork, domainModel: ConceptNetwork): SelectorRequest = {
    var frequencyConfidence: Pair[Double, Double] = (1.0, 1.0)
    // current situation must be either request to the system or impersonal sentence
    val subjects = currentSituation.getNodeByGeneralisationName(Constant.SUBJECT_NAME)
    frequencyConfidence = if (subjects.size > 1) {
      (frequencyConfidence._1 - 1.0, 1.0)
    } else if (subjects.size == 1 && subjects(0).uri.name != Constant.SYSTEM_NAME) {
      (frequencyConfidence._1 - 1.0, 1.0)
    } else {
      (frequencyConfidence._1 - 0.0, 1.0)
    }

    //if current situation contains action and has link to an object to be applied
    if (frequencyConfidence._1 > 0) {
      val actions = currentSituation.getNodeByGeneralisationName(Constant.ACTION_NAME)
      frequencyConfidence = if (actions.size == 1) {
        (frequencyConfidence._1 - 0.0, 0.9)
      } else if (actions.size > 1) {
        (frequencyConfidence._1 - 0.5, 0.9)
      } else {
        (frequencyConfidence._1 - 1.0, 1.9)
      }
      // if action has object
      if (frequencyConfidence._1 > 0) {
        val filteredActions = actions.filter{
          concept: Concept => {
            concept.links.filter{
              link: ConceptLink => {
                if (link.source != concept) {
                  link.destination != null
                } else {
                  link.source != null
                }
              }
            }.size > 0
          }
        }
        if (filteredActions.size > 0) {
          (frequencyConfidence._1 - 0.0, 0.9)
        } else {
          (frequencyConfidence._1 - 1.0, 1.0)
        }
      }
    }

    if (frequencyConfidence._1 < 0) {
      frequencyConfidence = (0.0, frequencyConfidence._2)
    }

    new SelectorRequest(KnowledgeURI(Constant.SELECTOR_REQUEST_URI_NAME), KnowledgeURI(Constant.SELECTOR_REQUEST_URI_NAME + "Request"),
      new Probability(frequencyConfidence._1, frequencyConfidence._2))
  }

}
