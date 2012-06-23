package tu.model.knowledge.semanticnetwork

import tu.model.knowledge.{Resource, Probability, KnowledgeURI}


/**
 * SemanticNetwork elements container.
 * @author max talanov
 *         date 2012-05-02
 *         time: 10:59 PM
 */

case class SemanticNetwork(_rootNodes: List[SemanticNetworkNode[Resource]], _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(_rootNodes: List[SemanticNetworkNode[Resource]], _uri: KnowledgeURI) = {
    this(_rootNodes: List[SemanticNetworkNode[Resource]], _uri: KnowledgeURI, new Probability)
  }

  def rootNodes: List[SemanticNetworkNode[Resource]] = _rootNodes

}
