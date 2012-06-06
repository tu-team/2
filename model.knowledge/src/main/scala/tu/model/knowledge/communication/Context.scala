package tu.model.knowledge.communication

import tu.model.knowledge.{Probability, Resource, KnowledgeURI, KLine}


/**
 * @author max talanov
 *         date 2012-05-28
 *         time: 11:27 PM
 */

class Context(_frames: Map[KnowledgeURI, Resource], _uri: KnowledgeURI, _probability: Probability)
  extends KLine(_frames, _uri, _probability) {

}

/**
 * @author toscheva
 *         date 2012-06-06
 *         time: 11:27 PM
 */
object ContextHelper {

  /**
   * return initialized context to the user
   * @param ctx
   * @return
   */
  def initializeContext(ctx: Context): Context = {
    var returnContext = ctx;
    if (returnContext == null) returnContext = new Context(Map.empty[KnowledgeURI, Resource], new KnowledgeURI("empty", "empty", "1.0"), new Probability());
    //if (returnContext.frames == null) returnContext.frames = Map.empty[KnowledgeURI, Resource];

    return returnContext;
  }

}