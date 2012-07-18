package tu.coreservice.action.critic.analyser

import tu.model.knowledge.domain.{ConceptNetwork, Concept}
import tu.model.knowledge.selector.SelectorRequest
import tu.model.knowledge.{Probability, KnowledgeURI, Constant}
import tu.model.knowledge.annotator.AnnotatedNarrative

/**
 * Detects problem description with desired state and return SelectorRequest with proper Probability
 * @author max talanov
 *         date 2012-07-02
 *         time: 5:22 PM
 */

class ProblemDescriptionWithDesiredStateAnalyser {

  /**
   * Detects problem description with desired state and return SelectorRequest with proper Probability.
   * @param currentSituation AnnotatedNarrative to analyse.
   * @return SelectorRequest with calculated probability.
   */
  def apply(currentSituation: AnnotatedNarrative): SelectorRequest = {
    this.apply(currentSituation.concepts)
  }

  /**
   * Detects problem description with desired state and return SelectorRequest with proper Probability.
   * @param currentSituation ConceptNetwork to analyse.
   * @return SelectorRequest with calculated probability.
   */
  def apply(currentSituation: ConceptNetwork): SelectorRequest = {
    this.apply(currentSituation.nodes)
  }

  /**
   * Detects problem description with desired state and return SelectorRequest with proper Probability.
   * @param currentSituation List[Concept] to analyse.
   * @return SelectorRequest with calculated probability.
   */
  def apply(currentSituation: List[Concept]): SelectorRequest = {
    var frequencyConfidence: Pair[Double, Double] = (1.0, 1.0)

    // current situation must have at least one subject and it must not be System name.
    val subjects = ConceptNetwork.getNodeByGeneralisationName(currentSituation, Constant.SUBJECT_NAME)
    frequencyConfidence = if (subjects.size > 1) {
      (frequencyConfidence._1 - 0.0, 1.0)
    } else if (subjects.size == 1 && subjects(0).uri.name != Constant.SYSTEM_NAME) {
      (frequencyConfidence._1 - 0.0, 1.0)
    } else {
      (frequencyConfidence._1 - 1.0, 1.0)
    }

    if (frequencyConfidence._1 > 0) {
      val actions = ConceptNetwork.getNodeByGeneralisationNameRec(currentSituation, Constant.PROBLEM_NAME)
      frequencyConfidence = if (actions.size == 1) {
        (frequencyConfidence._1 - 0.0, 0.9)
      } else if (actions.size > 1) {
        (frequencyConfidence._1 - 0.5, 0.9)
      } else {
        (frequencyConfidence._1 - 1.0, 1.9)
      }

      val desires = ConceptNetwork.getNodeByGeneralisationNameRec(currentSituation, Constant.DESIRE_NAME)
      frequencyConfidence = if (actions.size == 1) {
        (frequencyConfidence._1 - 0.0, 0.9)
      } else if (actions.size > 1) {
        (frequencyConfidence._1 - 0.5, 0.9)
      } else {
        (frequencyConfidence._1 - 1.0, 1.9)
      }
    }

    new SelectorRequest(KnowledgeURI(Constant.SELECTOR_REQUEST_DIRECT_INSTRUCTION_URI_NAME),
      KnowledgeURI(Constant.SELECTOR_REQUEST_PROBLEM_DESCRIPTION_URI_NAME),
      new Probability(frequencyConfidence._1, frequencyConfidence._2))
  }

}
