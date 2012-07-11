package tu.coreservice.action.critic.analyser

import tu.model.knowledge.domain.{ConceptLink, Concept, ConceptNetwork}
import tu.model.knowledge.selector.SelectorRequest
import tu.model.knowledge.{Constant, Probability, KnowledgeURI}
import tu.model.knowledge.annotator.AnnotatedNarrative

/**
 * Direct Instruction Analyser, detects direct instruction and return Selector request with proper Probability.
 * @author max talanov
 *         date 2012-06-28
 *         time: 4:01 PM
 */

class DirectInstructionAnalyser {

  /**
   * Detects direct instruction and return SelectorRequest with proper Probability.
   * @param currentSituation AnnotatedNarrative to analyse.
   * @return SelectorRequest with calculated probability.
   */
  def apply(currentSituation: AnnotatedNarrative): SelectorRequest = {
    this.apply(currentSituation.concepts)
  }

  /**
   * Detects direct instruction and return SelectorRequest with proper Probability.
   * @param currentSituation ConceptNetwork to analyse.
   * @return SelectorRequest with calculated probability.
   */
  def apply(currentSituation: ConceptNetwork): SelectorRequest = {
    this.apply(currentSituation.nodes)
  }

  /**
   * Detects direct instruction and return SelectorRequest with proper Probability.
   * @param currentSituation List[Concept] to analyse.
   * @return SelectorRequest with calculated probability.
   */
  def apply(currentSituation: List[Concept]): SelectorRequest = {
    var frequencyConfidence: Pair[Double, Double] = (1.0, 1.0)
    // current situation must be either request to the system or impersonal sentence
    val subjects = ConceptNetwork.getNodeByGeneralisationName(currentSituation, Constant.SUBJECT_NAME)
    frequencyConfidence = if (subjects.size > 1) {
      (frequencyConfidence._1 - 1.0, 1.0)
    } else if (subjects.size == 1 && subjects(0).uri.name != Constant.SYSTEM_NAME) {
      (frequencyConfidence._1 - 1.0, 1.0)
    } else {
      (frequencyConfidence._1 - 0.0, 1.0)
    }

    //if current situation contains action and has link to an object to be applied
    if (frequencyConfidence._1 > 0) {
      val actions = ConceptNetwork.getNodeByGeneralisationName(currentSituation, Constant.ACTION_NAME)
      frequencyConfidence = if (actions.size == 1) {
        (frequencyConfidence._1 - 0.0, 0.9)
      } else if (actions.size > 1) {
        (frequencyConfidence._1 - 0.5, 0.9)
      } else {
        (frequencyConfidence._1 - 1.0, 1.9)
      }
      // if action has object
      if (frequencyConfidence._1 > 0) {
        val filteredActions = actions.filter {
          concept: Concept => {
            concept.links.filter {
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

    new SelectorRequest(KnowledgeURI(Constant.SELECTOR_REQUEST_DIRECT_INSTRUCTION_URI_NAME), KnowledgeURI(Constant.SELECTOR_REQUEST_DIRECT_INSTRUCTION_URI_NAME),
      new Probability(frequencyConfidence._1, frequencyConfidence._2))
  }

  /**
   * Start method used by ThinkingLifeCycle, not implemented currently.
   * @return true if succeed, otherwise false.
   * @version 2.0
   */
  def start() = false

  /**
   * Stop method used by ThinkingLifeCycle, not implemented currently.
   * @return true if succeed, otherwise false.
   * @version 2.0
   */
  def stop() = false
}

object DirectInstructionAnalyser {
  def apply(name: String): DirectInstructionAnalyser = {
    new DirectInstructionAnalyser()
  }
}
