package tu.model.knowledge.semanticnetwork

import tu.model.knowledge.{Probability, KnowledgeURI, Resource}


/**
 * @author talanov max
 *         date 2012-05-02
 *         time: 10:53 PM
 */

case class SemanticNetworkNode[Type<:Resource](_content: Type, _links: List[SemanticNetworkLink], _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(_content: Type, _links: List[SemanticNetworkLink], _uri: KnowledgeURI) = {
    this(_content, _links, _uri, new Probability())
  }

  def content = _content

  def links = _links

}
