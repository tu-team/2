package tu.coreservice.utilities

import tu.model.knowledge.domain.{ConceptNetwork, ConceptLink, Concept}
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
  val systemConcept = Concept("system")
  val userConcept = Concept("user")
  val computerConcept = Concept("computer")
  val softwareConcept = Concept("sofware")
  val versionConcept = Concept("version")
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
  val lackConcept = Concept.createSubConcept(actionConcept, "lack")

  var concepts = List[Concept](systemConcept, subjectConcept, objectConcept, userConcept, computerConcept, softwareConcept, photoShopConcept, browserConcept,
    firefoxConcept, internetExplorerConcept, networkConcept, internetConcept, sharedResourcesConcept, sharedDiskConcept, accountConcept, actionConcept,
    installConcept, removeConcept, cleanConcept, versionConcept)

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
  val hasVersion = ConceptLink.createSubConceptLink(has, softwareConcept, versionConcept, "hasVersion", new Probability(1.0, 0.9))
  val userAccountLinkedPair = ConceptLink.likConcepts(has, softwareConcept, versionConcept)
  /**
   * uses
   */
  val isUsedFor = ConceptLink(subjectConcept, objectConcept, "isUsedFor")
  val browserIsUsedForInternet = ConceptLink.createSubConceptLink(isUsedFor, browserConcept, internetConcept, "browserIsUsedForInternet")

  val isLink = ConceptLink(subjectConcept, objectConcept, "is")
  val appliedLink = ConceptLink(subjectConcept, objectConcept, "applied")
  val missLink = ConceptLink(userConcept, objectConcept, "miss")

  var conceptLinks: List[ConceptLink] = List(has, hasComputer, hasSoftware, hasAccount, hasVersion, isLink, appliedLink)

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

  // Please install Firefox simulation
  val installActionInst = Concept.createInstanceConcept(installConcept)
  val firefoxConceptInst = Concept.createInstanceConcept(firefoxConcept)
  val systemInstallFirefox = ConceptLink.createSubConceptLink(appliedLink, systemConcept, firefoxConceptInst, "systemInstallFirefox")
  val pleaseInstallFFSimulation = ConceptNetwork(List[Concept](installActionInst, firefoxConceptInst), List[ConceptLink](systemInstallFirefox),
    KnowledgeURI("pleaseInstallFFSimulation"))

  // User miss Internet Explorer 8
  val user = AnnotatedPhrase("User")
  val missing = AnnotatedPhrase("miss")
  val internetExplorer8 = AnnotatedPhrase("Internet Explorer 8")
  val iHaveProblemWithIE8 = AnnotatedNarrative(List(user, missing, internetExplorer8), KnowledgeURI("iHaveProblemWithIE8"))

  // User miss Internet Explorer 8 annotated
  val userAnnotatedPhrase = AnnotatedPhrase("User", userConcept)
  val missingAnnotatedPhrase = AnnotatedPhrase("miss", lackConcept)
  val internetExplorer8AnnotatedPhrase = AnnotatedPhrase("Internet Explorer 8", internetExplorerConcept)
  val iHaveProblemWithIE8Annotated = AnnotatedNarrative(List(user, missing, internetExplorer8), KnowledgeURI("iHaveProblemWithIE8"))

  // User miss Internet Explorer 8 annotated
  val userInst = Concept.createInstanceConcept(userConcept)
  val internetExplorerInst = Concept.createInstanceConcept(internetExplorerConcept)
  val versionInst = Concept.createInstanceConcept(versionConcept, "8")
  val userMissInternetExplorer = ConceptLink.createInstanceConceptLink(missLink, userInst, internetExplorerInst)
  val internetExplorerHasVersion = ConceptLink.createInstanceConceptLink(has, internetExplorerInst, versionInst)
  val iHaveProblemWithIE8Simulation = ConceptNetwork(List[Concept](userInst, internetExplorerInst, versionInst),
  List[ConceptLink](userMissInternetExplorer, internetExplorerHasVersion), KnowledgeURI("iHaveProblemWithIE8Simulation"))


  def generateDirectInstructionNarrative = pleaseInstallFF

  def generateProblemDescriptionNarrative = iHaveProblemWithIE8

  def generateDomainModelConceptNetwork = domainModel

  /**
   * Generates AnnotatedNarrative the result of KBAnnotator.
   * @return AnnotatedNarrative
   */
  def generateDirectInstructionAnnotatedNarrative = pleaseInstallFFAnnotated

  /**
   * Generates AnnotatedNarrative the result of KBAnnotator.
   * @return AnnotatedNarrative
   */
  def generateProblemDescriptionAnnotatedNarrative = iHaveProblemWithIE8Annotated

  /**
   * Generates ConceptNetwork the result of Simulation.
   * @return ConceptNetwork
   */
  def generateDirectInstructionSimulation = pleaseInstallFFSimulation

  /**
   * Generates ConceptNetwork the result of Simulation.
   * @return ConceptNetwork
   */
  def generateProblemDescriptionSimulation = iHaveProblemWithIE8Simulation
}
