package tu.coreservice.utilities

import tu.model.knowledge.domain.{ConceptLink, Concept}
import tu.model.knowledge.annotator.{AnnotatedNarrative, AnnotatedPhrase}
import tu.model.knowledge.{KnowledgeURI, Probability}

/**
 * Test data generator object.
 * @author talanov max
 *         date 2012-06-16
 *         time: 3:55 PM
 */

object TestDataGenerator {
  /**
   * Simulation structures
   */
  val namespace = "2/concepts/"
  val revision = "0.0"

  /**
   * concepts
   */
  val subjectConcept = Concept("subject")
  val objectConcept = Concept("object")
  val userConcept = Concept("user")
  val computerConcept = Concept("computer")
  val softwareConcept = Concept("sofware")
  val photoShopConcept = Concept.createSubConcept(softwareConcept, "Adobe Photoshop")
  val browserConcept = Concept.createSubConcept(softwareConcept, "Browser")
  val firefoxConcept = Concept.createSubConcept(browserConcept, "Mozilla Firefox")
  val internetExplorerConcept = Concept.createSubConcept(browserConcept, "Microsoft Internet Explorer")
  val networkConcept = Concept("network")
  val internetConcept = Concept.createSubConcept(networkConcept, "internet")
  val sharedResourcesConcept = Concept("sharedResources")
  val sharedDiskConcept = Concept.createSubConcept(sharedResourcesConcept, "sharedDisk")
  val accountConcept = Concept("account")

  // actions
  val actionConcept = Concept("action")
  val installConcept = Concept.createSubConcept(actionConcept, "install")
  val removeConcept = Concept.createSubConcept(actionConcept, "remove")
  val cleanConcept = Concept.createSubConcept(actionConcept, "clean")

  var concepts = List[Concept](subjectConcept, objectConcept, userConcept, computerConcept, softwareConcept, photoShopConcept, browserConcept,
    firefoxConcept, internetExplorerConcept, networkConcept, internetConcept, sharedResourcesConcept, sharedDiskConcept, accountConcept, actionConcept, installConcept, removeConcept, cleanConcept)

  /**
   * links
   */
  /**
   * has
   */
  val has = ConceptLink(subjectConcept, objectConcept, "has")
  val hasComputer = ConceptLink.createSubConceptLink(has, userConcept, computerConcept, "hasComputer", new Probability(1.0, 1.0))
  val userComputerLinkedPair = ConceptLink.likConcepts(hasComputer, userConcept, computerConcept)
  val hasSoftware = ConceptLink.createSubConceptLink(has, computerConcept, softwareConcept, "hasSoftware", new Probability(1.0, 0.9))
  val computerSoftwareLinkedPair = ConceptLink.likConcepts(hasSoftware, computerConcept, softwareConcept)
  val hasAccount = ConceptLink.createSubConceptLink(has, userConcept, accountConcept, "hasAccount", new Probability(1.0, 0.9))
  val userAccountLinkedPair = ConceptLink.likConcepts(has, userConcept, accountConcept)
  /**
   * uses
   */
  val isUsedFor = ConceptLink(subjectConcept, objectConcept, "isUsedFor")
  val browserIsUsedForInternet = ConceptLink.createSubConceptLink(isUsedFor, browserConcept, internetConcept, "browserIsUsedForInternet")

  var conceptLinks: List[ConceptLink] = List(has, hasComputer, hasSoftware, hasAccount)

  /**
   * Domain model concept network
   */
  val domainModel = (concepts, conceptLinks, KnowledgeURI("domainModel"))

  /**
   * phrases
   */
  val userPhrase = AnnotatedPhrase("user", userConcept)
  val customerPhrase = AnnotatedPhrase("customer", userConcept)
  val notebookPhrase = AnnotatedPhrase("notebook", computerConcept)
  val laptopPhrase = AnnotatedPhrase("laptop", computerConcept)
  val desktopPhrase = AnnotatedPhrase("desktop", computerConcept)
  val computerPhrase = AnnotatedPhrase("computer", computerConcept)

  /**
   * Test phrases
   */
  // Please install Firefox
  val please = AnnotatedPhrase("Please")
  val install = AnnotatedPhrase("install")
  val fireFox = AnnotatedPhrase("FireFox")
  val pleaseInstallFF = AnnotatedNarrative(List(please, install, fireFox), KnowledgeURI("pleaseInstallFF"))

  // Please install Firefox annotated
  val pleaseAnnotatedPhrase = AnnotatedPhrase("Please", Concept("please"))
  val installAnnotatedPhrase = AnnotatedPhrase("install", installConcept)
  val fireFoxAnnotatedPhrase = AnnotatedPhrase("FireFox", firefoxConcept)
  val pleaseInstallFFAnnotated = AnnotatedNarrative(List(pleaseAnnotatedPhrase, installAnnotatedPhrase, fireFoxAnnotatedPhrase),
    KnowledgeURI("pleaseInstallFF"))

  // User is missing Internet Explorer 8
  val user = AnnotatedPhrase("User")
  val is = AnnotatedPhrase("is")
  val missing = AnnotatedPhrase("missing")
  val internetExplorer8 = AnnotatedPhrase("Internet Explorer 8")
  val iHaveProblemWithPDF =  AnnotatedNarrative(List(user, is , missing, internetExplorer8), KnowledgeURI("iHaveProblemWithPDF"))

  def generateDirectInstructionNarrative = pleaseInstallFF

  def generateProblemDescriptionNarrative = iHaveProblemWithPDF

  def generateDomainModelConceptNetwork = domainModel

  /**
   * Generates AnnotatedNarrative the result of KBAnnotator.
   * @return
   */
  def generateDirectInstructionAnnotatedNarrative = pleaseInstallFFAnnotated
}
