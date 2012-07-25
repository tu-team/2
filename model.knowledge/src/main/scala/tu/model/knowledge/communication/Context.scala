package tu.model.knowledge.communication

import tu.model.knowledge._
import domain.ConceptNetwork
import selector.SelectorRequest

/**
 * Stores contexts parameters.
 * @author max talanov, adel chepkunov
 *         date 2012-05-28
 *         time: 11:27 PM
 */

case class Context(__frames: Map[KnowledgeURI, Resource], override val _uri: KnowledgeURI,
                   override val _probability: Probability = new Probability())
  extends KLine(__frames, _uri, _probability) {


  var bestClassificationResult: Option[SelectorRequest] = None
  var _lastResult: Option[Resource] = None
  var classificationResults: List[SelectorRequest] = Nil
  var checkedClassificationResults: List[SelectorRequest] = Nil
  var _domainModel: Option[ConceptNetwork] = None

  def lastResult = _lastResult

  def lastResult_=(in: Option[Resource]): Context = {
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

  def domainModel_=(aDomainModel: ConceptNetwork): Context = {
    this._domainModel = Some(aDomainModel)
    this
  }

  def classificationResultsAdd(w2t: SelectorRequest) {
    classificationResults = w2t :: classificationResults
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
   * @param ctx Context to initialize
   * @return
   */
  def initializeContext(ctx: Context): Context = {
    var returnContext = ctx
    if (returnContext == null) returnContext = new Context(Map.empty[KnowledgeURI, Resource], new KnowledgeURI("empty", "empty", "1.0"), new Probability())
    returnContext
  }

  /**
   * Creates Context based on List of Resource and name specified. Name is used as base for URI.
   * @param resources List of Resource used to create Context.
   * @param name URI.name
   * @return Context
   */
  def apply(resources: List[Resource], name: String): Context = {
    val uri = KnowledgeURI(name)
    val resourcesMap: Map[KnowledgeURI, Resource] = resources.map {
      t => (t.uri, t)
    }.toMap
    new Context(resourcesMap, uri)
  }

  /**
   * Creates Context based on List of Resource and name specified. Name is used as base for URI.
   * @param resources List of Resource used to create Context.
   * @param classificationResults List of classification results
   * @param name URI.name
   * @return Context
   */
  def apply(resources: List[Resource], classificationResults: List[SelectorRequest], name: String): Context = {
    val uri = KnowledgeURI(name)
    val resourcesMap: Map[KnowledgeURI, Resource] = resources.map {
      t => (t.uri, t)
    }.toMap
    val res = new Context(resourcesMap, uri)
    res.classificationResults = classificationResults
    res
  }

  /**
   * Creates Context based on List of Resource and name specified and lastResult resource. Name is used as base for URI.
   * @param resources List of Resource used to create Context.
   * @param lastResult Resource is last result.
   * @param name URI.name
   * @return Context
   */
  def apply(resources: List[Resource], lastResult: Resource, name: String): Context = {
    val uri = KnowledgeURI(name)
    val resourcesMap: Map[KnowledgeURI, Resource] = resources.map {
      t => (t.uri, t)
    }.toMap
    val res = new Context(resourcesMap, uri)
    res.lastResult = Some(lastResult)
    res
  }

  def createContext(resourcesMap: Map[KnowledgeURI, Resource], classificationResults: List[SelectorRequest], name: String): Context = {
    val uri = KnowledgeURI(name)
    val res = new Context(resourcesMap, uri)
    res.classificationResults = classificationResults
    res
  }

  /**
   * Merges fist and second contexts: their frames, classificationResults and names.
   * @param first context to merge
   * @param second context to merge.
   * @return merged Context
   */
  def merge(first: Context, second: Context): Context = {
    val res = ContextHelper.createContext(first.frames ++ second.frames, mergeList(first.classificationResults, second.classificationResults),
      first.uri.name + "&" + second.uri.name)
    res.checkedClassificationResults = mergeList(first.checkedClassificationResults, second.checkedClassificationResults)
    res
  }

  /**
   * Merges context folding them to left.
   * @param contexts List[Context] to merge.
   * @return merged Context
   */
  def merge(contexts: List[Context]): Context = {
    val res: Context = contexts.reduceLeft((i: Context, s: Context) => ContextHelper.merge(i, s))
    res
  }

  def mergeList(x: List[SelectorRequest], y: List[SelectorRequest]): List[SelectorRequest] = {
    x.filter(p => !y.exists(q => p == q)) ::: y
  }

  def mergeLast(contexts: List[Context]): Context = {
    val res = merge(contexts)
    res.lastResult = contexts.last.lastResult
  }
}
