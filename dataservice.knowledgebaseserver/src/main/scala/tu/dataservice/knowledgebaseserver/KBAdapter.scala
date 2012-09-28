package tu.dataservice.knowledgebaseserver

import tu.coreservice.utilities.TestDataGenerator
import tu.model.knowledge.training.Goal
import tu.model.knowledge.way2think.{JoinWay2ThinkModel, Way2ThinkModel}
import tu.model.knowledge.action.ActionModel
import tu.model.knowledge.critic.CriticModel
import tu.model.knowledge._
import domain.{ConceptTag, ConceptLink, ConceptNetwork, Concept}
import annotator.AnnotatedPhrase
import frame.Frame
import howto.{HowTo, Solution}

/**
 * KBSever stub only for prototype purposes.
 * @author max talanov
 *         date 2012-07-06
 *         time: 1:58 PM
 */

object KBAdapter {

  val solutionsName = "stored_solutions_name"
  val goalsName = "goals_name"
  val domainName = "domain_name"

  val selfReflectiveCritics = "selfReflectiveCritics"

  var kb = N4JKB

  def model = TestDataGenerator.generateDomainModelConceptNetwork

  private def goalResourceMap =
    Map[Goal, List[ActionModel]](
      Goal("ProcessIncident") ->
        List[Way2ThinkModel](Way2ThinkModel("tu.coreservice.splitter.PreliminarySplitter"),
          Way2ThinkModel("tu.coreservice.annotator.KBAnnotatorImpl"),
          Way2ThinkModel("tu.coreservice.linkparser.LinkParser")
        ),
      Goal("ClassifyIncident") ->
        List[JoinWay2ThinkModel](JoinWay2ThinkModel(
          List[CriticModel](CriticModel("tu.coreservice.action.critic.analyser.DirectInstructionAnalyserCritic"),
            CriticModel("tu.coreservice.action.critic.analyser.ProblemDescriptionAnalyserCritic"),
            CriticModel("tu.coreservice.action.critic.analyser.ProblemDescriptionWithDesiredStateAnalyserCritic")
          ), "tu.model.knowledge.way2think.JoinWay2ThinkModel")
        ),
      Goal("FormalizeDirectInstruction") ->
        List[Way2ThinkModel](Way2ThinkModel("tu.coreservice.action.way2think.simulation.SimulationWay2Think")),
      Goal("FormalizeProblemDescription") ->
        List[Way2ThinkModel](Way2ThinkModel("tu.coreservice.action.way2think.simulation.SimulationWay2Think"),
          Way2ThinkModel("tu.coreservice.action.way2think.reformulation.ReformulationWay2Think")),
      Goal("GetMostProbableAction") ->
        List[Way2ThinkModel](Way2ThinkModel("tu.coreservice.action.way2think.FindMostProbableAction")
        ),
      Goal("SearchSolution") ->
        List[Way2ThinkModel](Way2ThinkModel("tu.coreservice.action.way2think.SearchSolution")
        ),
      Goal("ProcessResponse") ->
        List[Way2ThinkModel](Way2ThinkModel("tu.coreservice.splitter.PreliminarySplitter"),
          Way2ThinkModel("tu.coreservice.annotator.KBAnnotatorImpl"),
          Way2ThinkModel("tu.coreservice.linkparser.LinkParser"),
          Way2ThinkModel("tu.coreservice.action.way2think.simulation.CorrelationWay2Think")
        )
    )

  private def resources = goalResourceMap.values

  /**
   * Gets Map of URI -> Resource of all registered Way2ThinkModel, CriticModel, JoinWay2ThinkModel
   * @return Map[KnowledgeURI, Resource]
   */
  private def uriResourcesMap: Map[KnowledgeURI, Resource] = {
    val res: Map[KnowledgeURI, Resource] = goalResourceMap.values.flatten.map {
      r: Resource => {
        Pair(r.uri, r)
      }
    }.toMap
    res
  }

  /**
   * Gets Map of String -> Resource of all registered Way2ThinkModel, CriticModel, JoinWay2ThinkModel
   * @return Map[String, Resource]
   */
  def stringResourcesMap: Map[String, Resource] = {
    val res: Map[String, Resource] = goalResourceMap.values.flatten.map {
      r: Resource => {
        Pair(r.uri.name, r)
      }
    }.toMap
    res
  }

  def workflow = List(Goal("ProcessIncident"), Goal("ClassifyIncident"), Goal("GetMostProbableAction"), Goal("SearchSolution"))

  def trainingGoal = Map[Goal, List[ActionModel]](
    Goal("ProcessIncident") ->
      List[Way2ThinkModel](Way2ThinkModel("tu.coreservice.splitter.PreliminarySplitter"),
        Way2ThinkModel("tu.coreservice.annotator.KBAnnotatorImpl"),
        Way2ThinkModel("tu.coreservice.linkparser.LinkParser"),
        Way2ThinkModel("tu.coreservice.action.way2think.simulation.CorrelationWay2Think")
      )
  )

  def getByGoalName(name: String): Option[List[ActionModel]] = {
    val resources = this.goalResourceMap
    val keys: Iterable[Goal] = resources.keys.filter {
      g: Goal => {
        g.uri.name.equals(name)
      }
    }
    if (keys.size > 0) {
      resources.get(keys.head)
    } else {
      None
    }
  }

  @deprecated
  def annotationsDep = Map[String, AnnotatedPhrase](
    "Please" ->
      AnnotatedPhrase.apply("Please", Concept.apply("formOfPoliteness")),
    TestDataGenerator.fireFoxAnnotatedPhrase.text ->
      TestDataGenerator.fireFoxAnnotatedPhrase,
    TestDataGenerator.installAnnotatedPhrase.text ->
      TestDataGenerator.installAnnotatedPhrase
  )

  def annotations: Map[String, AnnotatedPhrase] = getPhrases.map(
    (phrase: AnnotatedPhrase) => {
      phrase.text -> phrase
    }
  ).toMap

  val uri = new KnowledgeURI("namespace", "name", "revision")
  val probability = new Probability


  def domainModel(): ConceptNetwork = {
    try {
      val res: ConceptNetwork = ConceptNetwork.load(kb, KBNodeId(0), domainName, Constant.DEFAULT_LINK_NAME)
      res
    }
    catch {
      case _ => getDefaultDomainConceptNetwork
    }

    //TODO: store and update it

  }

  def getDefaultDomain: Pair[ConceptNetwork, List[AnnotatedPhrase]] = {

    (ConceptNetwork(Defaults.concepts, Defaults.conceptLinks, KnowledgeURI("domainModel")), Defaults.phrases)
  }

  def getDefaultDomainConceptNetwork: ConceptNetwork = {
    this.getDefaultDomain._1
  }

  def getPhrases: List[AnnotatedPhrase] = {
    this.getDefaultDomain._2
  }

  def solutions(): List[SolvedIssue] = {
    val res: List[SolvedIssue] = kb.loadChildrenList(solutionsName).map(x => SolvedIssue.load(kb, x))
    if (res.isEmpty) {
      //save solutions
      getDefaultSolutions
    }
    res
  }

  def solutionsAdd(item: SolvedIssue): List[SolvedIssue] = {
    item.save(kb, KBNodeId(KB.getRootId()), item.uri.toString, solutionsName)
    solutions()
  }

  private def getDefaultSolutions: List[SolvedIssue] = {
    val in_uri = new KnowledgeURI("namespace", "name", "revision")
    def getTestSolvedIssue2: SolvedIssue = {

      val s = new Solution(List(Defaults.generateReinstallIE8HowTo), in_uri)
      new SolvedIssue(Defaults.iHaveProblemWithIE8Simulation, s, in_uri, probability)
    }

    def getTestSolvedIssue3: SolvedIssue = {

      val s = new Solution(List(Defaults.generateReinstallIE8HowTo), in_uri)
      new SolvedIssue(Defaults.iHaveProblemWithIE8Reformulation, s, in_uri, probability)
    }

    val uri = new KnowledgeURI("namespace", "name", "revision")

    val s = new Solution(List(Defaults.generateInstallFirefoxHowTo), uri)
    List(new SolvedIssue(TestDataGenerator.pleaseInstallFFSimulation, s, uri, new Probability), getTestSolvedIssue2, getTestSolvedIssue3)
  }

  /** *
    * Gets annotations according to specified word
    * @param word to find annotations
    * @return annotated phrase by word (for example get rid off)
    */
  def getAnnotationByWord(word: String): Option[AnnotatedPhrase] = {

    val resources = this.annotations
    val keys: Iterable[String] = resources.keys.filter {
      g: String => {
        g.toLowerCase.equals(word.toLowerCase)
      }
    }
    if (keys.size > 0) {
      resources.get(keys.head)
    } else {
      None
    }
  }

  def getReflectiveCritics: List[CriticModel] = {
    // kb.loadChildrenList(selfReflectiveCritics).map(x => CriticModel.load(kb, x))
    List[CriticModel](CriticModel("tu.coreservice.action.critic.manager.DoNotUnderstandManager")
    )
  }

  object Defaults {

    val CONCEPT = Concept("concept")
    val CONCEPT_LINK = ConceptLink(CONCEPT, CONCEPT, "conceptLink")
    val conceptPhrase = AnnotatedPhrase("concept", CONCEPT)
    val wordConcept = Concept.createSubConcept(CONCEPT, "word")
    val wordPhrase = AnnotatedPhrase("word", CONCEPT)
    val subjectConcept = Concept.createSubConcept(CONCEPT, "subject")
    val subjectPhrase = AnnotatedPhrase("subject", CONCEPT)
    val objectConcept = Concept.createSubConcept(CONCEPT, "object")
    val objectPhrase = AnnotatedPhrase("object", CONCEPT)
    val has = ConceptLink.createSubConceptLink(CONCEPT_LINK, subjectConcept, objectConcept, "has", new Probability(1.0, 1.0))
    val hasPhrase = AnnotatedPhrase("has", has)
    val isLink = ConceptLink.createSubConceptLink(CONCEPT_LINK, subjectConcept, objectConcept, "is")
    val isPhrase = AnnotatedPhrase("is", isLink)
    val generalisationLink = ConceptLink.createSubConceptLink(CONCEPT_LINK, subjectConcept, objectConcept, "generalisation")
    val isAPhrase = AnnotatedPhrase("is a", generalisationLink)
    val kindOfPhrase = AnnotatedPhrase("kind of", generalisationLink)
    val concepts = List[Concept](CONCEPT, wordConcept, subjectConcept, objectConcept)
    val conceptLinks: List[ConceptLink] = List(CONCEPT_LINK, has, isLink, generalisationLink)
    val phrases: List[AnnotatedPhrase] = List(conceptPhrase, wordPhrase, subjectPhrase, objectPhrase, hasPhrase, isPhrase, isAPhrase, kindOfPhrase)

    val softwareConcept = Concept.createSubConcept(objectConcept, "sofware")
    val browserConcept = Concept.createSubConcept(softwareConcept, "Browser")
    val internetExplorerConcept = Concept.createSubConcept(browserConcept, "Microsoft Internet Explorer")
    val versionConcept = Concept.createSubConcept(objectConcept, "version")
    val userConcept = Concept.createSubConcept(subjectConcept, "user")
    val addressConcept = Concept.createSubConcept(objectConcept, "address")
    val computerConcept = Concept.createSubConcept(objectConcept, "computer")
    val firefoxConcept = Concept.createSubConcept(browserConcept, "Mozilla Firefox")
    val systemConcept = Concept.createSubConcept(objectConcept, "system")

    /**
     * Links
     */
    val missLink = ConceptLink(userConcept, objectConcept, "miss")
    val hasNo = ConceptLink(subjectConcept, objectConcept, "hasNo")
    val appliedLink = ConceptLink(subjectConcept, objectConcept, "applied")

    /**
     * HowTo-s
     */
    val installHowTo = new HowTo(List[Frame](Frame(CONCEPT)), List[ConceptTag](), KnowledgeURI("installHowTo"))
    val reinstallHowTo = new HowTo(List[Frame](Frame(CONCEPT)), List[ConceptTag](), KnowledgeURI("reinstallHowTo"))

    // actions
    val actionConcept = Concept("action")
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
  }

}
