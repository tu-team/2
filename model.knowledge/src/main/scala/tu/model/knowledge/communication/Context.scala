package tu.model.knowledge.communication

import tu.model.knowledge._
import tu.model.knowledge.selector.SelectorRequest

/**
 * Stores contexts parameters.
 * @author max talanov, adel chepkunov
 *         date 2012-05-28
 *         time: 11:27 PM
 */

case class Context(private val __frames: Map[KnowledgeURI, Resource], override val _uri: KnowledgeURI,
                   override val _probability: Probability = new Probability())
  extends KLine(__frames, _uri, _probability) {

  //TODO should be something like this: _frames.get(KnowledgeURI(Constant.LAST_RESULT_NAME))
  var bestClassificationResult: Option[SelectorRequest] = None
  var classificationResults: List[SelectorRequest] = Nil

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

  def merge(first: Context, second: Context): Context = {
    val res = new Context(first.frames ++ second.frames, KnowledgeURI(first.uri.name + "&" + second.uri.name))
    res
  }
}