package tu.dataservice.knowledgebaseserver


import tu.model.knowledge.annotator.AnnotatedPhrase
import tu.model.knowledge.howto.HowTo
import tu.model.knowledge.frame.Frame
import tu.model.knowledge.domain.{ConceptNetwork, ConceptTag, ConceptLink, Concept}
import tu.model.knowledge.{KnowledgeURI, Probability}
import tu.model.knowledge.critic.CriticModel

/**
 * @author alex toschev
 * @author max talanov
 *         Time stamp: 9/18/12 6:29 PM
 */

object Defaults {

  val CONCEPT = Concept("concept")
  val CONCEPT_LINK = ConceptLink(CONCEPT, CONCEPT, "conceptLink")
  val conceptPhrase = AnnotatedPhrase("concept", CONCEPT)
  val wordConcept = Concept.createSubConcept(CONCEPT, "word")
  val wordPhrase = AnnotatedPhrase("word", wordConcept)
  val subjectConcept = Concept.createSubConcept(CONCEPT, "subject")
  val subjectPhrase = AnnotatedPhrase("subject", subjectConcept)
  val objectConcept = Concept.createSubConcept(CONCEPT, "object")
  val objectPhrase = AnnotatedPhrase("object", objectConcept)
  val formOfPolitenessConcept = Concept.createSubConcept(CONCEPT, "FormOfPoliteness")
  val formOfPolitenessPhrase = AnnotatedPhrase("form of politeness", formOfPolitenessConcept)
  val actionConcept = Concept.createSubConcept(CONCEPT, "action")
  val actionPhrase = AnnotatedPhrase("action", actionConcept)
  val programConcept = Concept.createSubConcept(CONCEPT, "program")
  val programPhrase = AnnotatedPhrase("program", programConcept)
  val beConcept = Concept.createSubConcept(CONCEPT, "be")
  val hasConcept = Concept.createSubConcept(CONCEPT, "has")
  val consistConcept = Concept.createSubConcept(CONCEPT, "consistOf")

  /**
   * Links
   */
  val missLink = ConceptLink(userConcept, objectConcept, "miss")
  val hasNo = ConceptLink(subjectConcept, objectConcept, "hasNo")
  val appliedLink = ConceptLink(subjectConcept, objectConcept, "applied")

  val has = ConceptLink.createSubConceptLink(CONCEPT_LINK, subjectConcept, objectConcept, "has", new Probability(1.0, 1.0))
  val hasPhrase = AnnotatedPhrase("has", has)
  val beLink = ConceptLink.createSubConceptLink(CONCEPT_LINK, subjectConcept, objectConcept, "be")
  val isPhrase = AnnotatedPhrase("is", beLink)
  val generalisationLink = ConceptLink.createSubConceptLink(CONCEPT_LINK, subjectConcept, objectConcept, "generalisation")
  val isAPhrase = AnnotatedPhrase("is a", generalisationLink)
  val kindOfPhrase = AnnotatedPhrase("kind of", generalisationLink)
  val missPhrase = AnnotatedPhrase("miss", missLink )

  /**
   * Link reduction
   */
  val reduceLinks = Map(beConcept -> beLink, hasConcept -> has, consistConcept -> has )
  val objectLinkName = "obj"
  val subjectLinkName = "subj"
  val reductionConceptLinks = List(objectLinkName, subjectLinkName)

  /**
   * Containers
   */
  val concepts = List[Concept](CONCEPT, wordConcept, subjectConcept, objectConcept, formOfPolitenessConcept, actionConcept, programConcept, beConcept, hasConcept)
  val conceptLinks: List[ConceptLink] = List(CONCEPT_LINK, has, beLink, generalisationLink)
  val phrases: List[AnnotatedPhrase] = List(conceptPhrase, wordPhrase, subjectPhrase, objectPhrase, hasPhrase, isPhrase, isAPhrase, kindOfPhrase, missPhrase, formOfPolitenessPhrase)

  val softwareConcept = Concept.createSubConcept(objectConcept, "sofware")
  val browserConcept = Concept.createSubConcept(softwareConcept, "Browser")
  val internetExplorerConcept = Concept.createSubConcept(browserConcept, "Microsoft Internet Explorer")
  val versionConcept = Concept.createSubConcept(objectConcept, "version")
  val userConcept = Concept.createSubConcept(subjectConcept, "user")
  val addressConcept = Concept.createSubConcept(objectConcept, "address")
  val computerConcept = Concept.createSubConcept(objectConcept, "computer")
  val firefoxConcept = Concept.createSubConcept(browserConcept, "Firefox")
  val systemConcept = Concept.createSubConcept(objectConcept, "system")


  // lexical
  val tenseConcept = Concept("tense")
  val posConcept = Concept("pos")
  val formOfPoliteness = Concept("formOfPoliteness")
  val tenseLink = ConceptLink(subjectConcept, objectConcept, "tense")
  val posLink = ConceptLink(subjectConcept, objectConcept, "pos")

  /**
   * domain-s (ConceptNetworks
   */
  val domainModelConceptNetwork = ConceptNetwork(Defaults.concepts, Defaults.conceptLinks, KnowledgeURI("domainModel"))
  /**
   * HowTo-s
   */
  val installHowTo = new HowTo(List[Frame](Frame(CONCEPT)), List[ConceptTag](), KnowledgeURI("installHowTo"))
  val reinstallHowTo = new HowTo(List[Frame](Frame(CONCEPT)), List[ConceptTag](), KnowledgeURI("reinstallHowTo"))

  // actions
  val installConcept = Concept.createSubConcept(actionConcept, "install")
  val removeConcept = Concept.createSubConcept(actionConcept, "remove")
  val cleanConcept = Concept.createSubConcept(actionConcept, "clean")


  /**
   * Generates reinstall IE8 HowTo
   * @return HowTo
   */
  def generateReinstallIE8HowTo = reinstallIEHowTo

  /**
   * Generates install Firefox HowTo
   * @return  HowTo
   */
  def generateInstallFirefoxHowTo = installFirefoxHowTo

  val installFirefoxHowTo = HowTo.createInstance(installHowTo, List(Frame(firefoxConcept)))
  val reinstallIEHowTo = HowTo.createInstance(installHowTo, List(Frame(internetExplorerConcept)))

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

  // Please install Firefox simulation
  val installActionInst = Concept.createInstanceConcept(installConcept)
  val firefoxConceptInst = Concept.createInstanceConcept(firefoxConcept)
  val systemInstallFirefox = ConceptLink.createSubConceptLink(appliedLink, systemConcept, firefoxConceptInst, "systemInstallFirefox")
  val pleaseInstallFFSimulation = new ConceptNetwork(List[Concept](installActionInst, firefoxConceptInst), List[ConceptLink](systemInstallFirefox),
    KnowledgeURI("pleaseInstallFFSimulation"))

  val defaultSelfReflectiveCritics = List(CriticModel("tu.coreservice.action.critic.manager.DoNotUnderstandManager"))

}

