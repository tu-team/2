package tu.model.knowledge.selector

import tu.model.knowledge.{Probability, KnowledgeURI, Resource}

/**
 * Simple implementation of selector request as getter for an resource by KnowledgeURI.
 * To be replaced by storage request in next release.
 * @author max talanov
 *         date 2012-06-28
 *         time: 5:34 PM
 */

case class SelectorRequest(resourceURIList: List[KnowledgeURI], _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability){

  def this(_resourceURI: KnowledgeURI, _uri: KnowledgeURI, _probability: Probability = new Probability()) =
  this (List(_resourceURI), _uri, _probability)

}
