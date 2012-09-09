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
  val INSTANCE_ID_RANDOM_SEED = 900

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
  val DEFAULT_LINK_NAME = "defaultLink"
  val GENERALISATION_LINK_NAME = "generalisationLink"
  val SPECIALISATION_LINK_NAME = "specialisationLink"
  val PHRASES_LINK_NAME = "phrasesLink"
  val SENTENCES_LINK_NAME = "sentencesLink"
  val CONCEPT_LINK_NAME = "conceptLink"
  val CONCEPTS_LINK_NAME = "conceptsLink"
  val CONCEPT_LINK_SOURCE_NAME = "conceptLinkSrc"
  val CONCEPT_LINK_DESTINATION_NAME = "conceptLinkDst"
  val NODES_LINK_NAME = "nodesLink"
  val LINKS_LINK_NAME = "links"
  val NO_NAME = "NONAME"
  val CONTENT = "content"
  val LINKS = "links"
  val TAGS = "tags"

  val NO_KB_NODE = -1L
  val KB_ID = "KB_ID"
  val KB_CLASS_NAME = "KB_CLASS_NAME"
  val SELECTOR_REQUEST_CRY4HELP_URI = "tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think"
  val SELECTOR_REQUEST_CRY4HELP_URI_NAME = "Cry4Help"
}
