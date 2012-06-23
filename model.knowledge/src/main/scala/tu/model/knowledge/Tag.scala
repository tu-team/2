package tu.model.knowledge

import primitive.KnowledgeString
import semanticnetwork.{SemanticNetworkLink, SemanticNetworkNode}

/**
 * @author talanovm
 *         date 2012-05-08
 *         time: 10:27 PM
 */

case class Tag(override val _content: KLine, var __links: List[SemanticNetworkLink], override val _uri: KnowledgeURI, override val _probability: Probability)
  extends SemanticNetworkNode[KLine](_content, __links, _uri, _probability) {

  def this(_content: KLine, _links: List[SemanticNetworkLink], _uri: KnowledgeURI) {
    this(_content, _links, _uri, new Probability)
  }

  override def links = __links
}