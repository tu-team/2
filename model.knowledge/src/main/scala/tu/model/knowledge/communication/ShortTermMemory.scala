package tu.model.knowledge.communication

import tu.model.knowledge._
import annotator.AnnotatedPhrase
import domain.{Concept, ConceptNetwork}
import selector.SelectorRequest
import training.Goal

/**
 * Stores contexts parameters.
 * @author max talanov
 * @author adel chepkunov
 *         date 2012-05-28
 *         time: 11:27 PM
 */

case class ShortTermMemory(__frames: Map[KnowledgeURI, Resource], override val _uri: KnowledgeURI,
                   override val _probability: Probability = new Probability())
  extends KLine(__frames, _uri, _probability) {

  var bestClassificationResult: Option[SelectorRequest] = None
  var _lastResult: Option[Resource] = None
  var classificationResults: List[SelectorRequest] = Nil
  var checkedClassificationResults: List[SelectorRequest] = Nil
  var _domainModel: Option[ConceptNetwork] = None
  var _simulationModel: Option[ConceptNetwork] = None
  var _reformulationModel: Option[ConceptNetwork] = None
  var _solutions: List[SolvedIssue]=Nil
  var _errors: List[Error] = List[Error]()
  var _lastError: Option[Error] = None
  var _userResponse: Option[Response] = None
  var _nextGoal: Option[Goal] = None
  var _simulationResult: Option[ConceptNetwork] = None
  var _notUnderstoodConcepts: List[Concept] = List[Concept]()
  var _notUnderstoodPhrases: List[AnnotatedPhrase] = List[AnnotatedPhrase]()

  def notUnderstoodPhrases = _notUnderstoodPhrases

  def notUnderstoodPhrases_=(in: List[AnnotatedPhrase]): ShortTermMemory = {
    _notUnderstoodPhrases = in
    this
  }

  def notUnderstoodConcepts = _notUnderstoodConcepts

  def notUnderstoodConcepts_=(in: List[Concept]): ShortTermMemory = {
    _notUnderstoodConcepts = in
    this
  }

  def simulationResult = _simulationResult

  def simulationResult_=(in: ConceptNetwork): ShortTermMemory = {
    _simulationResult = Some(in)
    this
  }

  def simulationResult_=(in: Option[ConceptNetwork]): ShortTermMemory = {
    _simulationResult = in
    this
  }

  def nextGoal = _nextGoal

  def nextGoal_=(g: Option[Goal]): ShortTermMemory = {
    this._nextGoal = g
    this
  }

  def nextGoal_=(g: Goal): ShortTermMemory = {
    this._nextGoal = Some(g)
    this
  }

  def userResponse: Option[Response] = _userResponse

  def userResponse_=(r: Response): ShortTermMemory = {
    _userResponse = Some(r)
    this
  }

  def userResponse_=(r: Option[Response]): ShortTermMemory = {
    _userResponse = r
    this
  }

  def errors: List[Error] = _errors

  def errors_=(e: Error) {
    _errors = List[Error](e)
  }

  def errors_=(e: List[Error]) {
    _errors = e
  }

  def lastError: Option[Error] = _lastError

  def lastError_=(e: Error) {
    _lastError = Some(e)
  }

  def lastError_=(e: Option[Error]) {
    _lastError = e
  }

  def lastResult = _lastResult

  def lastResult_=(in: Option[Resource]): ShortTermMemory = {
    _lastResult = in
    in match {
      case Some(sr: SelectorRequest) => {
        if (!classificationResults.exists(p => p == sr)) {
          classificationResultsAdd(sr)
          _frames += (sr.uri -> sr)
        }
      }
      case Some(r: Resource) => {
        _frames += (r.uri -> r)
      }
      case None => {
        // Do nothing
      }
    }
    this
  }

  def domainModel = _domainModel

  def domainModel_=(aDomainModel: ConceptNetwork): ShortTermMemory = {
    this._domainModel = Some(aDomainModel)
    this
  }

  def domainModel_=(aDomainModel: Option[ConceptNetwork]): ShortTermMemory = {
    this._domainModel = aDomainModel
    this
  }

  def simulationModel: Option[ConceptNetwork] = _simulationModel

  def simulationModel_=(aModel: ConceptNetwork): ShortTermMemory = {
    this._simulationModel = Some(aModel)
    this
  }

  def simulationModel_=(aDomainModel: Option[ConceptNetwork]): ShortTermMemory = {
    this._simulationModel = aDomainModel
    this
  }

  def solutions: List[SolvedIssue] = _solutions

  def solutions_=(aSln: List[SolvedIssue]): ShortTermMemory = {
    this._solutions = aSln
    this
  }

  def reformulationModel = _reformulationModel

  def reformulationModel_=(aModel: ConceptNetwork): ShortTermMemory = {
    this._reformulationModel = Some(aModel)
    this
  }

  def reformulationModel_=(aDomainModel: Option[ConceptNetwork]): ShortTermMemory = {
    this._reformulationModel = aDomainModel
    this
  }

  def classificationResultsAdd(w2t: SelectorRequest) {
    classificationResults = w2t :: classificationResults
  }

  def findByName(name: String): Option[Resource] = {
    val filteredFrames: List[KnowledgeURI] = _frames.keys.filter(
      (uri: KnowledgeURI) => uri.name == name
    ).toList
    if (filteredFrames.size > 0) {
      _frames.get(filteredFrames.head)
    } else {
      None
    }
  }

  override def toString: String = {
    this.getClass.getName + ":" + uri.toString + "" + "[" + frames.toString() + "][" + lastResult + "] @" + probability.toString
  }
}

/**
 * @author toschev alex
 *         date 2012-06-06
 *         time: 11:27 PM
 */
object ContextHelper {

  /**
   * return initialized context to the user
   * @param ctx ShortTermMemory to initialize
   * @return
   */
  def initializeContext(ctx: ShortTermMemory): ShortTermMemory = {
    var returnContext = ctx
    if (returnContext == null) returnContext = new ShortTermMemory(Map.empty[KnowledgeURI, Resource], new KnowledgeURI("empty", "empty", "1.0"), new Probability())
    returnContext
  }

  /**
   * Creates ShortTermMemory based on List of Resource and name specified. Name is used as base for URI.
   * @param resources List of Resource used to create ShortTermMemory.
   * @param name URI.name
   * @return ShortTermMemory
   */
  def apply(resources: List[Resource], name: String): ShortTermMemory = {
    val uri = KnowledgeURI(name)
    val resourcesMap: Map[KnowledgeURI, Resource] = resources.map {
      t => (t.uri, t)
    }.toMap
    new ShortTermMemory(resourcesMap, uri)
  }

  /**
   * Creates ShortTermMemory based on List of Resource and name specified. Name is used as base for URI.
   * @param resources List of Resource used to create ShortTermMemory.
   * @param classificationResults List of classification results
   * @param name URI.name
   * @return ShortTermMemory
   */
  def apply(resources: List[Resource], classificationResults: List[SelectorRequest], name: String): ShortTermMemory = {
    val uri = KnowledgeURI(name)
    val resourcesMap: Map[KnowledgeURI, Resource] = resources.map {
      t => (t.uri, t)
    }.toMap
    val res = new ShortTermMemory(resourcesMap, uri)
    res.classificationResults = classificationResults
    res
  }

  /**
   * Creates ShortTermMemory based on List of Resource and name specified and lastResult resource. Name is used as base for URI.
   * @param resources List of Resource used to create ShortTermMemory.
   * @param lastResult Resource is last result.
   * @param name URI.name
   * @return ShortTermMemory
   */
  def apply(resources: List[Resource], lastResult: Resource, name: String): ShortTermMemory = {
    val uri = KnowledgeURI(name)
    val resourcesMap: Map[KnowledgeURI, Resource] = resources.map {
      t => (t.uri, t)
    }.toMap
    val res = new ShortTermMemory(resourcesMap, uri)
    res.lastResult = Some(lastResult)
    res
  }

  def createContext(resourcesMap: Map[KnowledgeURI, Resource], classificationResults: List[SelectorRequest], name: String): ShortTermMemory = {
    val uri = KnowledgeURI(name)
    val res = new ShortTermMemory(resourcesMap, uri)
    res.classificationResults = classificationResults
    res
  }

  /**
   * Merges fist and second contexts: their frames, classificationResults and names.
   * @param first context to merge
   * @param second context to merge.
   * @return merged ShortTermMemory
   */
  def merge(first: ShortTermMemory, second: ShortTermMemory): ShortTermMemory = {
    val res = ContextHelper.createContext(first.frames ++ second.frames, mergeList(first.classificationResults, second.classificationResults),
      first.uri.name + "&" + second.uri.name)
    res.checkedClassificationResults = mergeList(first.checkedClassificationResults, second.checkedClassificationResults)
    res
  }

  /**
   * Merges context folding them to left.
   * @param contexts List[ShortTermMemory] to merge.
   * @return merged ShortTermMemory
   */
  def merge(contexts: List[ShortTermMemory]): ShortTermMemory = {
    if (contexts.size > 0) {
      val res: ShortTermMemory = contexts.reduceLeft((i: ShortTermMemory, s: ShortTermMemory) => ContextHelper.merge(i, s))
      res
    } else {
      ContextHelper.apply(List[Resource](), "AnonymousContext")
    }
  }

  def mergeList(x: List[SelectorRequest], y: List[SelectorRequest]): List[SelectorRequest] = {
    x.filter(p => !y.exists(q => p == q)) ::: y
  }

  def mergeLast(contexts: List[ShortTermMemory]): ShortTermMemory = {
    if (contexts.size > 0) {
      val res = merge(contexts)
      res.lastResult = contexts.last.lastResult
      res.domainModel = contexts.last.domainModel
      res.simulationModel = contexts.last.simulationModel
      res.reformulationModel = contexts.last.reformulationModel
    } else {
      ContextHelper.apply(List[Resource](), "AnonymousContext")
    }
  }

  def mergeFirstAndLastResult(contexts: List[ShortTermMemory]): ShortTermMemory = {
    if (contexts.size > 0) {
      val res = merge(contexts)
      res.lastResult = contexts.last.lastResult
      res.domainModel = contexts.head.domainModel
      res.simulationModel = contexts.head.simulationModel
      res.reformulationModel = contexts.head.reformulationModel
      res.userResponse = contexts.head.userResponse
    } else {
      ContextHelper.apply(List[Resource](), "AnonymousContext")
    }
  }
}
