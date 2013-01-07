package tu.dataservice.knowledgebaseserver

import providers.N4JKB
import tu.model.knowledge.training.Goal
import tu.model.knowledge.way2think.{JoinWay2ThinkModel, Way2ThinkModel}
import tu.model.knowledge.action.ActionModel
import tu.model.knowledge.critic.CriticModel
import tu.model.knowledge._
import tu.model.knowledge.domain.{ConceptNetwork, Concept}
import tu.model.knowledge.annotator.AnnotatedPhrase
import tu.model.knowledge.howto.Solution
import tu.model.knowledge.Constant

/**
 * KBSever stub only for prototype purposes.
 * @author max talanov
 *         date 2012-07-06
 *         time: 1:58 PM
 */

object KBAdapter {

  var kb = N4JKB

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
        ),
      Goal("Train") ->
        List[Way2ThinkModel](Way2ThinkModel("tu.coreservice.splitter.PreliminarySplitter"),
          Way2ThinkModel("tu.coreservice.annotator.KBAnnotatorImpl"),
          Way2ThinkModel("tu.coreservice.linkparser.LinkParser"),
          Way2ThinkModel("tu.coreservice.action.way2think.correlation.CorrelationWay2Think")
        ),
      Goal("Cry4Help") ->
        List[Way2ThinkModel](Way2ThinkModel("tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think")
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
    Goal("Train") ->
      List[Way2ThinkModel](Way2ThinkModel("tu.coreservice.splitter.PreliminarySplitter"),
        Way2ThinkModel("tu.coreservice.annotator.KBAnnotatorImpl"),
        Way2ThinkModel("tu.coreservice.linkparser.LinkParser"),
        Way2ThinkModel("tu.coreservice.action.way2think.correlation.CorrelationWay2Think")
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


  def annotations: Map[String, AnnotatedPhrase] = Defaults.phrases.map(
    (phrase: AnnotatedPhrase) => {
      phrase.text -> phrase
    }
  ).toMap

  val uri = new KnowledgeURI("namespace", "name", "revision")
  val probability = new Probability


  //@deprecated
  //def domainModel(): ConceptNetwork = someModel(Constant.domainName)

  //def domainModel(name: String) = someModel(name)

  def domainModel(uri: KnowledgeURI) = someModel(uri)

  //@deprecated
  //def simulationModel(): ConceptNetwork = someModel(Constant.simulationName)

  //def simulationModel(name: String): ConceptNetwork = someModel(name)

  def simulationModel(uri: KnowledgeURI): ConceptNetwork = someModel(uri)

  //@deprecated
  //def reformulationModel(): ConceptNetwork = someModel(Constant.reformulationName)

  //def reformulationModel(name: String): ConceptNetwork = someModel(name)

  def reformulationModel(uri: KnowledgeURI): ConceptNetwork = someModel(uri)

  private def someModel(modelName: KnowledgeURI): ConceptNetwork = {
    try {
      ConceptNetwork.load(kb, KBNodeId(0), modelName.uri().get.toString, Constant.DEFAULT_LINK_NAME)
    }
    catch {
      case _ =>
        val res: ConceptNetwork = Defaults.domainModelConceptNetwork
        res.save(kb, KBNodeId(0), modelName.uri().get.toString, Constant.DEFAULT_LINK_NAME)
        res
    }

  }


  def solutions(): List[SolvedIssue] = {
    val res: List[SolvedIssue] = kb.loadChildrenList(Constant.solutionsName).map(x => SolvedIssue.load(kb, x))
    if (res.isEmpty) {
      //save solutions
      getDefaultSolutions
    } else {
      res
    }
  }

  def solutionsAdd(item: SolvedIssue): List[SolvedIssue] = {
    item.save(kb, KBNodeId(KB.getRootId()), item.uri.toString, Constant.solutionsName)
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
    List(new SolvedIssue(Defaults.pleaseInstallFFSimulation, s, uri, new Probability), getTestSolvedIssue2, getTestSolvedIssue3)
  }

  /** *
    * Gets annotations according to specified word
    * @param word to find annotations
    * @return annotated phrase by word (for example get rid off)
    */
  def getAnnotationByWord(word: String): Option[AnnotatedPhrase] = {

    var resources = kb.loadChildrenList(Constant.savedAnnotations).map(x => AnnotatedPhrase.load(kb, x))

    if (resources.isEmpty) {
      resources = Defaults.phrases
    }

    val phrases: Iterable[AnnotatedPhrase] = resources.toList.filter {
      g: AnnotatedPhrase => {
        g.toString.equals(word)
      }
    }
    if (phrases.size > 0) {
      Option(phrases.head)
    } else {
      None
    }
  }

  def getReflectiveCritics: List[CriticModel] = {
    var list = kb.loadChildrenList(Constant.selfReflectiveCritics).map(x => CriticModel.load(kb, x))

    if (list.isEmpty) {
      //save list to db and return
      list = Defaults.defaultSelfReflectiveCritics
    }

    list
  }

  //object Defaults moved to InitialData file
}
