package tu.dataservice.knowledgebaseserver

import tu.coreservice.utilities.TestDataGenerator
import tu.model.knowledge.training.Goal
import tu.model.knowledge.way2think.{JoinWay2ThinkModel, Way2ThinkModel}
import tu.model.knowledge.action.ActionModel
import tu.model.knowledge.critic.CriticModel
import tu.model.knowledge._
import domain.ConceptLink._
import domain.{ConceptLink, ConceptNetwork, Concept}
import tu.model.knowledge.annotator.AnnotatedPhrase
import tu.model.knowledge.howto.Solution

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

  def annotations: Map[String, AnnotatedPhrase] = TestDataGenerator.phrases.map(
    (phrase: AnnotatedPhrase) => {
      phrase.text -> phrase
    }
  ).toMap

  val uri = new KnowledgeURI("namespace", "name", "revision")
  val probability = new Probability


  def domainModel(): ConceptNetwork = {
    try{
      val res:ConceptNetwork = ConceptNetwork.load(kb, KBNodeId(0), domainName, Constant.DEFAULT_LINK_NAME)
      res
    }
    catch
    {
      case _ => getDefaultDomain()
    }

    //TODO: store and update it

  }

  def getDefaultDomain():ConceptNetwork =
  {
    val CONCEPT = Concept("concept")
    val CONCEPT_LINK = ConceptLink(CONCEPT, CONCEPT, "conceptLink")
    val wordConcept = Concept.createSubConcept(CONCEPT, "word")
    val subjectConcept = Concept.createSubConcept(CONCEPT, "subject")
    val objectConcept = Concept.createSubConcept(CONCEPT, "object")
    val has = ConceptLink.createSubConceptLink(CONCEPT_LINK, subjectConcept, objectConcept, "has", new Probability(1.0, 1.0))
    val isLink = ConceptLink.createSubConceptLink(CONCEPT_LINK, subjectConcept, objectConcept, "is")

    val concepts = List[Concept](CONCEPT, wordConcept, subjectConcept, objectConcept)

    val conceptLinks: List[ConceptLink] = List(CONCEPT_LINK, has, isLink)

    ConceptNetwork(concepts, conceptLinks, KnowledgeURI("domainModel"))

  }

  def solutions(): List[SolvedIssue] = {

    val res: List[SolvedIssue] = kb.loadChildrenList(solutionsName).map(x => SolvedIssue.load(kb, x))

    if (res.isEmpty)
      //save solutions

      getDefaultSolutions()

    res
  }

  def solutionsAdd(item: SolvedIssue): List[SolvedIssue] = {
    item.save(kb, KBNodeId(KB.getRootId()), item.uri.toString, solutionsName)
    solutions()
  }

  private def getDefaultSolutions(): List[SolvedIssue] = {
    val in_uri = new KnowledgeURI("namespace", "name", "revision")
    def getTestSolvedIssue2(): SolvedIssue = {

      val s = new Solution(List(TestDataGenerator.generateReinstallIE8HowTo), in_uri)
      new SolvedIssue(TestDataGenerator.iHaveProblemWithIE8Simulation, s, in_uri, probability)
    }

    def getTestSolvedIssue3(): SolvedIssue = {

      val s = new Solution(List(TestDataGenerator.generateReinstallIE8HowTo), in_uri)
      new SolvedIssue(TestDataGenerator.iHaveProblemWithIE8Reformulation, s, in_uri, probability)
    }

    val uri = new KnowledgeURI("namespace", "name", "revision")

    val s = new Solution(List(TestDataGenerator.generateInstallFirefoxHowTo), uri)
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

  def getReflectiveCritics(): List[CriticModel] = {
    List[CriticModel](CriticModel("tu.coreservice.action.critic.manager.DoNotUnderstandManager")
    )
  }

}
