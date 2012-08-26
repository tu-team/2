package tu.model.knowledge

/**
 * @author max
 *         date 2012-06-04
 *         time: 12:28 AM
 */

object Constant {

  val DELIMITER = "."
  val REVISION_DELIMITER = "/"
  val UID_DELIMITER = "#"
  val defaultNamespace = "2/"
  val defaultConceptsNamespace = "concepts/"
  val defaultRevision = "0.0"
  val INSTANCE_ID_LENGTH = 900

  val SELECTOR_REQUEST_DIRECT_INSTRUCTION_URI_NAME = "DirectInstructionRequest"
  val SELECTOR_REQUEST_PROBLEM_DESCRIPTION_URI_NAME = "ProblemDesriptionRequest"
  val SELECTOR_REQUEST_SIMULATION_URI_NAME = "Simulation"
  val SELECTOR_REQUEST_SIMULATION_URI = "tu.coreservice.action.way2think.simulation.SimulationWay2Think"
  val SELECTOR_REQUEST_REFORMULATION_URI_NAME = "Reformulation"
  val SELECTOR_REQUEST_REFORMULATION_URI = "tu.coreservice.action.way2think.reformulation.ReformulationWay2Think"
  val ACTION_NAME = "actionConcept"
  val SYSTEM_NAME = "systemConcept"
  val SUBJECT_NAME = "subjectConcept"
  val PROBLEM_NAME = "problemConcept"
  val DESIRE_NAME = "desireConcept"
  val LAST_RESULT_NAME = "LastResult"
  val CLASSIFICATION_RESULT_NAME = "ClassificationResult"
  val LINK_PARSER_RESULT_NAME = "LinkedNarrative"

  val LINK_NAME = "link"
  val GENERALISATION_LINK_NAME = "generalisationLink"
  val SPECIALISATION_LINK_NAME = "specialisationLink"
  val PHRASES_LINK_NAME = "phrasesLink"
  val CONCEPT_LINK_NAME = "phrasesLink"
}
