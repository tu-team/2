package tu.model.knowledge.semanticnetwork

import tu.model.knowledge.{Resource, Probability, KnowledgeURI, Link}


/**
 * @author max talanov
 *         date 2012-05-02
 *         time: 10:40 PM
 */

class SemanticNetworkLink(_source: SemanticNetworkNode[Resource], _destination: SemanticNetworkNode[Resource], _uri: KnowledgeURI, _probability: Probability)
  extends Link[SemanticNetworkNode[Resource]](_source, _destination, _uri, _probability) {

  def this(_source: SemanticNetworkNode[Resource], _destination: SemanticNetworkNode[Resource], _uri: KnowledgeURI) = {
    this(_source, _destination, _uri: KnowledgeURI, new Probability())
  }
}
