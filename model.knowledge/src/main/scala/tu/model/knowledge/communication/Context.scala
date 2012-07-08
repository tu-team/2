package tu.model.knowledge.communication

import tu.model.knowledge.{Probability, Resource, KnowledgeURI, KLine}
import tu.model.knowledge.selector.SelectorRequest

/**
 * Stores contexts parameters.
 * @author max talanov
 *         date 2012-05-28
 *         time: 11:27 PM
 */

case class Context(var __frames: Map[KnowledgeURI, Resource], override val _uri: KnowledgeURI,
                   override val _probability: Probability = new Probability())
  extends KLine(__frames, _uri, _probability) {

  var lastResult:SelectorRequest = null
  var classificationResults : List[SelectorRequest] = Nil


  def classificationResultsAdd(w2t:SelectorRequest)
                              { classificationResults = w2t :: classificationResults }

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
    //if (returnContext.frames == null) returnContext.frames = Map.empty[KnowledgeURI, Resource];

    returnContext
  }

  def apply(resources: List[Resource], name: String): Context = {
    val uri = KnowledgeURI(name)
    val resourcesMap: Map[KnowledgeURI, Resource] = resources.map {
      t => (t.uri, t)
    }.toMap
    new Context(resourcesMap, uri)
  }

}