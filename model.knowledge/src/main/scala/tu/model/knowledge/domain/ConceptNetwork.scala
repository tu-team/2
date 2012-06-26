package tu.model.knowledge.domain

import tu.model.knowledge.semanticnetwork.SemanticNetwork
import tu.model.knowledge.{Probability, KnowledgeURI}

/**
 * @author max
 *         date 2012-06-17
 *         time: 10:41 PM
 */

case class ConceptNetwork(_nodes: List[Concept] = List[Concept](),
                          _links: List[ConceptLink] = List[ConceptLink](),
                          override val _uri: KnowledgeURI,
                          override val _probability: Probability = new Probability())
  extends SemanticNetwork(_nodes, _uri, _probability) {

  def this(uri: KnowledgeURI) = {
    this(List[Concept](), List[ConceptLink](), uri)
  }

  def nodes = _nodes

  def links = _links

  override def rootNodes = _nodes

}
object ConceptNetwork {

  def apply (nodes: List[Concept], links: List[ConceptLink], name: String) = {
    val uri = KnowledgeURI(name)
    new ConceptNetwork(nodes, links, uri)
  }
}