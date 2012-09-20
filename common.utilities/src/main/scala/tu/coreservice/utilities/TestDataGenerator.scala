package tu.coreservice.utilities

import tu.model.knowledge.annotator.{AnnotatedNarrative, AnnotatedPhrase}
import tu.model.knowledge.{KnowledgeURI, Probability}
import tu.model.knowledge.howto.HowTo
import tu.model.knowledge.frame.Frame
import tu.model.knowledge.domain._
import tu.coreservice.utilities.TestDataGenerator._

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

  /* will be hardcoded
   */
  val CONCEPT = Concept("concept")
  val CONCEPT_LINK = ConceptLink(CONCEPT, CONCEPT, "conceptLink")
  val wordConcept = Concept("word")
  val subjectConcept = Concept.createSubConcept(CONCEPT, "subject")
  val objectConcept = Concept.createSubConcept(CONCEPT, "object")
  val has = ConceptLink.createSubConceptLink(CONCEPT_LINK, subjectConcept, objectConcept, "has", new Probability(1.0, 1.0))
  val isLink = ConceptLink.createSubConceptLink(CONCEPT_LINK, subjectConcept, objectConcept, "is")

  //for Perl - below

  /**
   * concepts
   */
  val tenseConcept = Concept("tense")
  val posConcept = Concept("pos")
  val systemConcept = Concept.createSubConcept(objectConcept, "system")
  val userConcept = Concept.createSubConcept(subjectConcept, "user")
  val computerConcept = Concept.createSubConcept(objectConcept, "computer")
  val deviceConcept = Concept.createSubConcept(objectConcept, "device")
  val softwareConcept = Concept.createSubConcept(objectConcept, "sofware")
  val versionConcept = Concept.createSubConcept(objectConcept, "version")
  val photoShopConcept = Concept.createSubConcept(softwareConcept, "Adobe Photoshop")
  val browserConcept = Concept.createSubConcept(softwareConcept, "Browser")
  val firefoxConcept = Concept.createSubConcept(browserConcept, "Mozilla Firefox")
  val internetExplorerConcept = Concept.createSubConcept(browserConcept, "Microsoft Internet Explorer")
  val networkConcept = Concept.createSubConcept(objectConcept, "network")
  val addressConcept = Concept.createSubConcept(objectConcept, "address")
  val internetConcept = Concept.createSubConcept(networkConcept, "internet")
  val sharedResourcesConcept = Concept.createSubConcept(objectConcept, "sharedResources")
  val sharedDiskConcept = Concept.createSubConcept(sharedResourcesConcept, "sharedDisk")
  val accountConcept = Concept.createSubConcept(objectConcept, "account")


  // axillary
  val formOfPoliteness = Concept("formOfPoliteness")

  // actions
  val actionConcept = Concept("action")
  val installConcept = Concept.createSubConcept(actionConcept, "install")
  val removeConcept = Concept.createSubConcept(actionConcept, "remove")
  val cleanConcept = Concept.createSubConcept(actionConcept, "clean")

  // problems
  val problemConcept = Concept.createSubConcept(objectConcept, "problem")
  val lackConcept = Concept.createSubConcept(problemConcept, "lack")

  /**
   * Desired states
   */
  val desireConcept = Concept("desire")
  val shouldConcept = Concept.createSubConcept(desireConcept, "should")
  val shouldHaveConcept = Concept.createSubConcept(shouldConcept, "shouldHave")

  var concepts = List[Concept](systemConcept, subjectConcept, objectConcept, userConcept, computerConcept, softwareConcept,
    photoShopConcept, browserConcept,
    firefoxConcept, internetExplorerConcept,
    networkConcept, internetConcept, sharedResourcesConcept,
    formOfPoliteness,
    sharedDiskConcept, accountConcept, actionConcept, versionConcept,
    actionConcept, installConcept, removeConcept, cleanConcept)

  var simulationConcepts = List[Concept](systemConcept, subjectConcept, objectConcept, userConcept, computerConcept, softwareConcept,
    photoShopConcept, browserConcept,
    firefoxConcept, internetExplorerConcept,
    networkConcept, internetConcept, sharedResourcesConcept,
    sharedDiskConcept, accountConcept, actionConcept, versionConcept,
    actionConcept, installConcept, removeConcept, cleanConcept)


  var reformulationConcepts = List[Concept](systemConcept, subjectConcept, objectConcept, userConcept, computerConcept, softwareConcept,
    photoShopConcept, browserConcept,
    firefoxConcept, internetExplorerConcept,
    networkConcept, internetConcept, sharedResourcesConcept,
    sharedDiskConcept, accountConcept, actionConcept, versionConcept,
    actionConcept, installConcept, removeConcept, cleanConcept)

  /**
   * links
   */
  /**
   * has
   */
  val hasComputer = ConceptLink.createSubConceptLink(has, userConcept, computerConcept, "hasComputer", new Probability(1.0, 1.0))
  val userComputerLinkedPair = ConceptLink.likConcepts(hasComputer, userConcept, computerConcept)
  val hasSoftware = ConceptLink.createSubConceptLink(has, computerConcept, softwareConcept, "hasSoftware", new Probability(1.0, 0.9))
  val computerSoftwareLinkedPair = ConceptLink.likConcepts(hasSoftware, computerConcept, softwareConcept)
  val hasAccount = ConceptLink.createSubConceptLink(has, userConcept, accountConcept, "hasAccount", new Probability(1.0, 0.9))
  val userAccountLinkedPair = ConceptLink.likConcepts(has, userConcept, accountConcept)
  val hasVersion = ConceptLink.createSubConceptLink(has, softwareConcept, versionConcept, "hasVersion", new Probability(1.0, 0.9))
  val hasVersionLinkedPair = ConceptLink.likConcepts(has, softwareConcept, versionConcept)
  /**
   * uses
   */
  val isUsedFor = ConceptLink(subjectConcept, objectConcept, "isUsedFor")
  val browserIsUsedForInternet = ConceptLink.createSubConceptLink(isUsedFor, browserConcept, internetConcept, "browserIsUsedForInternet")

  val appliedLink = ConceptLink(subjectConcept, objectConcept, "applied")
  val missLink = ConceptLink(userConcept, objectConcept, "miss")
  val hasNo = ConceptLink(subjectConcept, objectConcept, "hasNo")
  val hasProblemWith = ConceptLink(subjectConcept, objectConcept, "hasProblemWith")

  val tenseLink = ConceptLink(subjectConcept, objectConcept, "tense")
  val posLink = ConceptLink(subjectConcept, objectConcept, "pos")

  // actions
  val actionLink = ConceptLink(subjectConcept, objectConcept, "actionLink")
  val installLink = ConceptLink.createSubConceptLink(actionLink, subjectConcept, softwareConcept, "install")
  val removeConceptLink = ConceptLink.createSubConceptLink(actionLink, subjectConcept, softwareConcept, "remove")
  val cleanConceptLink = ConceptLink.createSubConceptLink(actionLink, subjectConcept, deviceConcept, "clean")

  var conceptLinks: List[ConceptLink] = List(has, hasComputer, hasSoftware, hasAccount, hasVersion, isLink, appliedLink,
    actionLink, installLink, cleanConceptLink, removeConceptLink, cleanConceptLink, tenseLink, posLink)

  var simulationConceptLinks: List[ConceptLink] = List(has, hasComputer, hasSoftware, hasAccount, hasVersion, isLink, appliedLink,
    actionLink, installLink, cleanConceptLink, removeConceptLink, cleanConceptLink)

  var reformulationConceptLinks: List[ConceptLink] = List(has, hasComputer, hasSoftware, hasAccount, hasVersion, isLink, appliedLink,
    actionLink, installLink, cleanConceptLink, removeConceptLink, cleanConceptLink)

  /**
   * Domain model concept network
   */
  val domainModel = ConceptNetwork(concepts, conceptLinks, KnowledgeURI("domainModel"))

  /**
   * Simulation model concept network
   */
  val simulationModel = ConceptNetwork(simulationConcepts, simulationConceptLinks, KnowledgeURI("simulationModel"))

  /**
   * Reformulation model concept network
   */
  val reformulationModel = ConceptNetwork(reformulationConcepts, reformulationConceptLinks, KnowledgeURI("simulationModel"))

  /**
   * sentences
   */
  val userPhrase = AnnotatedPhrase("user", userConcept)
  val customerPhrase = AnnotatedPhrase("customer", userConcept)
  val notebookPhrase = AnnotatedPhrase("notebook", computerConcept)
  val laptopPhrase = AnnotatedPhrase("laptop", computerConcept)
  val desktopPhrase = AnnotatedPhrase("desktop", computerConcept)
  val computerPhrase = AnnotatedPhrase("computer", computerConcept)

  /**
   * HowTo-s
   */
  val installHowTo = new HowTo(List[Frame](Frame(objectConcept)), List[ConceptTag](), KnowledgeURI("installHowTo"))
  val reinstallHowTo = new HowTo(List[Frame](Frame(objectConcept)), List[ConceptTag](), KnowledgeURI("reinstallHowTo"))

  /**
   * Test sentences
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
  val pleaseInstallFFSimulation = new ConceptNetwork(List[Concept](installActionInst, firefoxConceptInst), List[ConceptLink](systemInstallFirefox),
    KnowledgeURI("pleaseInstallFFSimulation"))

  // User miss Internet Explorer 8
  val user = AnnotatedPhrase("User")
  val missing = AnnotatedPhrase("miss")
  val internetExplorer8 = AnnotatedPhrase("Internet Explorer 8")
  val iHaveProblemWithIE8 = AnnotatedNarrative(List(user, missing, internetExplorer8), KnowledgeURI("iHaveProblemWithIE8"))

  // User miss Internet Explorer 8 annotated
  val userAnnotatedPhrase = AnnotatedPhrase("User", userConcept)
  val missingAnnotatedPhrase = AnnotatedPhrase("miss", lackConcept)
  val internetExplorer8AnnotatedPhrase = AnnotatedPhrase("Internet Explorer", internetExplorerConcept)
  val iHaveProblemWithIE8Annotated = AnnotatedNarrative(List(userAnnotatedPhrase,
    missingAnnotatedPhrase, internetExplorer8AnnotatedPhrase), KnowledgeURI("iHaveProblemWithIE8"))

  // User miss Internet Explorer 8 annotated
  val shouldHaveAnnotatedPhrase = AnnotatedPhrase("should have", shouldHaveConcept)
  val iHaveProblemWithIE8IShouldHaveIE8 = AnnotatedNarrative(List(userAnnotatedPhrase,
    missingAnnotatedPhrase, internetExplorer8AnnotatedPhrase,
    shouldHaveAnnotatedPhrase, internetExplorer8AnnotatedPhrase), KnowledgeURI("iHaveProblemWithIE8IShouldHaveIE8"))

  // User miss Internet Explorer 8 annotated
  val lackLink = ConceptLink(lackConcept, internetExplorerConcept, "lackLink")
  val internetExplorer8AnnotatedPhraseAmbiguous = AnnotatedPhrase("Internet Explorer 8", List(objectConcept, internetExplorerConcept))
  val iHaveProblemWithIE8AnnotatedAmbiguous = AnnotatedNarrative(List(userAnnotatedPhrase,
    missingAnnotatedPhrase, internetExplorer8AnnotatedPhraseAmbiguous), KnowledgeURI("iHaveProblemWithIE8"))

  val phrases: List[AnnotatedPhrase] = pleaseInstallFFAnnotated.sentences.map(s => s.phrases).flatten ::: iHaveProblemWithIE8IShouldHaveIE8.sentences.map(s => s.phrases).flatten

  // User miss Internet Explorer 8 simulated
  val userInst = Concept.createInstanceConcept(userConcept)
  val internetExplorerInst = Concept.createInstanceConcept(internetExplorerConcept)
  val versionInst = Concept.createInstanceConcept(versionConcept, "8")
  val userMissInternetExplorer = ConceptLink.createInstanceConceptLink(missLink, userInst, internetExplorerInst)
  val internetExplorerHasVersion = ConceptLink.createInstanceConceptLink(has, internetExplorerInst, versionInst)
  val iHaveProblemWithIE8Simulation = new ConceptNetwork(List[Concept](userInst, internetExplorerInst, versionInst),
    List[ConceptLink](userMissInternetExplorer, internetExplorerHasVersion), KnowledgeURI("iHaveProblemWithIE8Simulation"))

  // User miss Internet Explorer 8 reformulated
  val userInstRef = Concept.createInstanceConcept(userConcept)
  val computerInstRef = Concept.createInstanceConcept(computerConcept)
  val userHasComputerInst = ConceptLink.createInstanceConceptLink(has, userInstRef, computerInstRef)
  val addressInstRef = Concept.createInstanceConcept(addressConcept, "someAddress")
  val computerHasAddressRef = ConceptLink.createInstanceConceptLink(has, computerInstRef, addressInstRef)
  val internetExplorerInstRef = Concept.createInstanceConcept(internetExplorerConcept)
  val computerHasNoInternetExplorerInstRef = ConceptLink.createInstanceConceptLink(hasNo, computerInstRef, internetExplorerInstRef)
  val internetExplorerHasVersionInstRef = ConceptLink.createInstanceConceptLink(has, internetExplorerInstRef, versionInst)
  val iHaveProblemWithIE8Reformulation = new ConceptNetwork(List[Concept](userInstRef, computerInstRef, addressInstRef, internetExplorerInstRef),
    List[ConceptLink](userHasComputerInst, computerHasAddressRef, computerHasNoInternetExplorerInstRef, internetExplorerHasVersionInstRef), KnowledgeURI("iHaveProblemWithIE8Reformulation"))

  val iHaveProblemWithIE8ReformulationTest = new ConceptNetwork(List[Concept](userConcept, computerConcept, addressConcept, internetExplorerConcept),
    List[ConceptLink](), KnowledgeURI("iHaveProblemWithIE8Reformulation"))

  val installFirefoxHowTo = HowTo.createInstance(installHowTo, List(Frame(firefoxConcept)))
  val reinstallIEHowTo = HowTo.createInstance(installHowTo, List(Frame(internetExplorerConcept)))


  def generateDirectInstructionNarrative = pleaseInstallFF

  def generateProblemDescriptionNarrative = iHaveProblemWithIE8

  def generateDomainModelConceptNetwork = domainModel

  def generateSimulationModelConceptNetwork = simulationModel

  def generateReformulationModelConceptNetwork = reformulationModel

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
   * Generates AnnotatedNarrative with desired state the result of KBAnnotator.
   * @return AnnotatedNarrative
   */
  def generateProblemDescriptionWithDesiredStateAnnotatedNarrative = iHaveProblemWithIE8Annotated

  /**
   * Generates AnnotatedNarrative with ambiguous sentences(that references several concepts)
   * @return
   */
  def generateProblemDescriptionAnnotatedNarrativeAmbiguous = iHaveProblemWithIE8AnnotatedAmbiguous

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

  /**
   * Generates ConceptNetwork the result of Reformulation.
   * @return ConceptNetwork
   */
  def generateProblemDescriptionReformulation = iHaveProblemWithIE8Reformulation

  /**
   * Generates Test ConceptNetwork for Reformulation.
   * @return ConceptNetwork.
   */
  def generateProblemDescriptionReformulationTest = iHaveProblemWithIE8ReformulationTest

  /**
   * Generates install Firefox HowTo
   * @return  HowTo
   */
  def generateInstallFirefoxHowTo = installFirefoxHowTo

  /**
   * Generates reinstall IE8 HowTo
   * @return HowTo
   */
  def generateReinstallIE8HowTo = reinstallIEHowTo

}
