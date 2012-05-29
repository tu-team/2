package tu.model.knowledge.communication

import tu.model.knowledge.{Probability, Resource, KnowledgeURI, KLine}


/**
 * @author max talanov
 *         date 2012-05-28
 *         time: 11:27 PM
 */

class Context(_frames: Map[KnowledgeURI, Resource], _uri: KnowledgeURI, _probability: Probability)
  extends KLine(_frames, _uri, _probability){

}
