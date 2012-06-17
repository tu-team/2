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
  val subject = Concept("subject")
  val objectConcept = Concept("object")
  val userConcept = Concept("user")
  val computerConcept = Concept("computer")
  val softwareConcept = Concept("sofware")
  val photoShop = Concept.createSubConcept(softwareConcept, "Adobe Photoshop")
  val browser = Concept.createSubConcept(softwareConcept, "Browser")
  val firefox = Concept.createSubConcept(browser, "Mozilla Firefox")
  val internetExplorer = Concept.createSubConcept(browser, "Microsoft Internet Explorer")
  val network = Concept("network")
  val internet = Concept.createSubConcept(network, "internet")
  val sharedResources = Concept("sharedResources")
  val sharedDisk = Concept.createSubConcept(sharedResources, "sharedDisk")
  val account = Concept("account")

  // actions
  val action = Concept("action")
  val installConcept = Concept.createSubConcept(action, "install")
  val remove = Concept.createSubConcept(action, "remove")
  val clean = Concept.createSubConcept(action, "clean")

  var concepts = List[Concept](subject, objectConcept, userConcept, computerConcept, softwareConcept, photoShop, browser,
    firefox, internetExplorer, network, internet, sharedResources, sharedDisk, account, action, installConcept, remove, clean)

  /**
   * links
   */
  /**
   * has
   */
  val has = ConceptLink(subject, objectConcept, "has")
  val hasComputer = ConceptLink.createSubConceptLink(has, userConcept, computerConcept, "hasComputer", new Probability(1.0, 1.0))
  val userComputerLinkedPair = ConceptLink.likConcepts(hasComputer, userConcept, computerConcept)
  val hasSoftware = ConceptLink.createSubConceptLink(has, computerConcept, softwareConcept, "hasSoftware", new Probability(1.0, 0.9))
  val computerSoftwareLinkedPair = ConceptLink.likConcepts(hasSoftware, computerConcept, softwareConcept)
  val hasAccount = ConceptLink.createSubConceptLink(has, userConcept, account, "hasAccount", new Probability(1.0, 0.9))
  val userAccountLinkedPair = ConceptLink.likConcepts(has, userConcept, account)
  /**
   * uses
   */
  val isUsedFor = ConceptLink(subject, objectConcept, "isUsedFor")
  val browserIsUsedForInternet = ConceptLink.createSubConceptLink(isUsedFor, browser, internet, "BrowserIsUsedForInternet")

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

  // User is missing Internet Explorer 8
  val user = AnnotatedPhrase("User")
  val is = AnnotatedPhrase("is")
  val missing = AnnotatedPhrase("missing")
  val internetExplorer8 = AnnotatedPhrase("Internet Explorer 8")
  val iHaveProblemWithPDF =  AnnotatedNarrative(List(user, is , missing, internetExplorer8), KnowledgeURI("iHaveProblemWithPDF"))

  def generateDirectInstructionNarrative = pleaseInstallFF

  def generateProblemDescriptionNarrative = iHaveProblemWithPDF

  def generateDomainModelConceptNetwork = domainModel
}
