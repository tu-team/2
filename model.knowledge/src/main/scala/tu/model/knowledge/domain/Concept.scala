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

case class Concept(_generalisations: KLine, _specialisations: KLine, _phrases: List[AnnotatedPhrase], override val _content: Resource,
                   override val _links: List[SemanticNetworkLink], override val _uri: KnowledgeURI, override val _probability: Probability)
  extends SemanticNetworkNode[Resource](_content, _links, _uri, _probability) {

  def this(_generalisations: KLine, _specialisations: KLine, _phrases: List[AnnotatedPhrase], _content: Resource,
           _links: List[SemanticNetworkLink], _uri: KnowledgeURI) {
    this(_generalisations, _specialisations, _phrases, _content, _links, _uri, new Probability())
  }

  def phrases = _phrases

}

object Concept {

  def apply(phrases: List[AnnotatedPhrase], name: String): Concept = {
    new Concept(KLine("generalisation"), KLine("specialisation"), phrases, KnowledgeString(name, name), List[SemanticNetworkLink](), KnowledgeURI(name + "Concept"))
  }

  def apply(word: String, name: String): Concept =  {
    new Concept(KLine("generalisation"), KLine("specialisation"),
      List[AnnotatedPhrase](AnnotatedPhrase(word)),
      KnowledgeString(name, name),
      List[SemanticNetworkLink](),
      KnowledgeURI(name + "Concept"))
  }

  def apply(name: String): Concept = {
    new Concept(KLine("generalisation"), KLine("specialisation"), List[AnnotatedPhrase](), KnowledgeString(name, name), List[SemanticNetworkLink](), KnowledgeURI(name + "Concept"))
  }
}