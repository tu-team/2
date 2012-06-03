package tu.model.knowledge.domain

import tu.model.knowledge.semanticnetwork.{SemanticNetworkLink, SemanticNetworkNode}
import tu.model.knowledge.{KLine, KnowledgeURI, Probability, Resource}
import tu.model.knowledge.primitive.KnowledgeString

/**
 * @author max
 *         date 2012-06-01
 *         time: 8:50 AM
 */

case class Concept(_generalisations: KLine, _specialisations: KLine, _words: KLine, _content: Resource,
                   _links: List[SemanticNetworkLink], _uri: KnowledgeURI, _probability: Probability)
  extends SemanticNetworkNode[Resource](_content, _links, _uri, _probability) {

  def this(_generalisations: KLine, _specialisations: KLine, _words: KLine, _content: Resource,
           _links: List[SemanticNetworkLink], _uri: KnowledgeURI) {
    this(_generalisations: KLine, _specialisations: KLine, _words: KLine, _content: Resource,
      _links: List[SemanticNetworkLink], _uri: KnowledgeURI, new Probability())
  }

}

object Concept {

  def apply(words: KLine, content: String): Concept = {
    new Concept(KLine("generalisation"), KLine("specialisation"), words, KnowledgeString(content, content), List[SemanticNetworkLink](), KnowledgeURI(content + "Concept"))
  }
}