package tu.model.knowledge

/**
 * @author max talanov
 *         date 2012-06-04
 *         time: 12:28 AM
 */

object Constant {

  val tuIniAddress = "tu.properties"
  val IssueMissingFactor: Double = 0.1
  val DomainMissingFactor: Double = 1.0
  val DistanceThreadHold: Double = 1.0

  val RelexFeatures: List[String] = List("_subj", "_obj", "_iobj", "_advmod", "of")
  val RelexFeaturesPhrases: List[String] = List("of")

  val solutionsName = "stored_solutions_name"
  val goalsName = "goals_name"
  val domainName = "domain_name"
  val simulationName = "simulation_name"
  val reformulationName = "reformulation_name"

  val selfReflectiveCritics = "selfReflectiveCritics"

  val defaultDomainName="ITSM"

  val savedAnnotations = "savedWordAnnotations"

  val DELIMITER = "/"
  val REVISION_DELIMITER = "@"
  val UID_DELIMITER = "#"
  val UID_INSTANCE_DELIMITER = "&ID="
  val defaultNamespace = "tu-project.com"
  val defaultConceptsNamespace = "concepts/"
  val defaultRevision = "1.0"
  val coma = ","
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
  val ROOT_NODES_LINK_NAME = "rootNodesLink"
  val LINKS_LINK_NAME = "links"
  val CONCEPTS_NETWORK_NAME = "conceptNetwork"
  val SOLUTION_NAME = "solution"
  val NO_NAME = "NONAME"
  val CONTENT = "content"
  val LINKS = "links"
  val TAGS = "tags"
  val CRITIC_MODEL_NAME = "CriticModelName"
  val CRITIC_MODEL_NAME_LINK = "CriticModelNameLink"

  val NO_KB_NODE = -1L
  val KB_ID = "KB_ID"
  val KB_CLASS_NAME = "KB_CLASS_NAME"
  val SELECTOR_REQUEST_CRY4HELP_URI = "tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think"
  val SELECTOR_REQUEST_CRY4HELP_URI_NAME = "Cry4Help"
  val RESULT = "result"
  val NOT_UNDERSTOOD_PREFIX = "$I_can_not_understand_following_concept "
  val INPUT_TEXT = "inputtext"

  final val NOUN_F: String = "noun"
  final val VERB_F: String = "verb"
  final val ADJ_F: String = "adj"
  final val ADV_F: String = "adv"
  final val ROOT_F: String = "root"
  final val TYPE_F: String = "type"
  final val NEG_F: String = "neg"

  val subjectConceptName = "subject"
  val objectConceptName = "object"
  val actionConceptName = "action"
  val desireConceptName = "desire"
  val formOfPoliteness = "formOfPoliteness"
  val conceptSuffix = "Concept"
  val objectLinkName = "obj"

  val understoodConcepts = "$UnderstoodConcepts"
  val notUnderstoodConcepts = "$NotUnderstoodConcepts"
  val FOUND_SOLUTIONS = "$FoundSolution"
  val resultToReport = "$resultToReport"

}
