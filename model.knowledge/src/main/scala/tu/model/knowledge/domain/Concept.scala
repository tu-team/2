package tu.model.knowledge.domain

import tu.model.knowledge.semanticnetwork.{SemanticNetworkLink, SemanticNetworkNode}
import tu.model.knowledge.{KLine, KnowledgeURI, Probability, Resource}
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.annotator.{AnnotatedWord, AnnotatedPhrase}

/**
 * @author max
 *         date 2012-06-01
 *         time: 8:50 AM
 */

case class Concept(_generalisations: KLine, _specialisations: KLine, _phrases: KLine, _content: Resource,
                   _links: List[SemanticNetworkLink], _uri: KnowledgeURI, _probability: Probability)
  extends SemanticNetworkNode[Resource](_content, _links, _uri, _probability) {

  def this(_generalisations: KLine, _specialisations: KLine, _phrases: KLine, _content: Resource,
           _links: List[SemanticNetworkLink], _uri: KnowledgeURI) {
    this(_generalisations: KLine, _specialisations: KLine, _phrases: KLine, _content: Resource,
      _links: List[SemanticNetworkLink], _uri: KnowledgeURI, new Probability())
  }

  def phrases = _phrases

}

object Concept {

  def apply(phrases: KLine, content: String): Concept = {
    new Concept(KLine("generalisation"), KLine("specialisation"), phrases, KnowledgeString(content, content), List[SemanticNetworkLink](), KnowledgeURI(content + "Concept"))
  }

  def apply(word: String, content: String): Concept =  {
    new Concept(KLine("generalisation"), KLine("specialisation"),
      new KLine(Map[KnowledgeURI, Resource](KnowledgeURI(word + "Phrase") -> AnnotatedPhrase(word)), KnowledgeURI(word + "KLine")),
      KnowledgeString(content, content), List[SemanticNetworkLink](), KnowledgeURI(content + "Concept"))
  }
}