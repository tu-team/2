package tu.model.knowledge.annotator

import tu.model.knowledge.semanticnetwork.{SemanticNetwork, SemanticNetworkNode}
import tu.model.knowledge.{Resource, Probability, KnowledgeURI}

/**
 * @author max
 *         date 2012-06-03
 *         time: 12:15 PM
 */

case class AnnotatedSemanticNetwork(_words: List[SemanticNetworkNode[AnnotatedWord]], override val _uri: KnowledgeURI, override val _probability: Probability)
  extends SemanticNetwork(List[SemanticNetworkNode[Resource]](), _uri, _probability) {

  def this(_words: List[SemanticNetworkNode[AnnotatedWord]], _uri: KnowledgeURI) {
    this(_words, _uri, new Probability())
  }

  def words = _words

  override def rootNodes = _words.asInstanceOf[List[SemanticNetworkNode[Resource]]]
}
