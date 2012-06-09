package tu.model.knowledge.domain

import tu.model.knowledge.semanticnetwork.{SemanticNetworkLink, SemanticNetworkNode}
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.annotator.AnnotatedPhrase
import util.Random
import tu.model.knowledge._

/**
 * @author max
 *         date 2012-06-01
 *         time: 8:50 AM
 */

case class Concept(var _generalisations: TypedKLine[Concept],
                   var _specialisations: TypedKLine[Concept],
                   var _phrases: TypedKLine[AnnotatedPhrase],
                   override val _content: Resource,
                   override val _links: List[SemanticNetworkLink],
                   override val _uri: KnowledgeURI,
                   override val _probability: Probability)
  extends SemanticNetworkNode[Resource](_content, _links, _uri, _probability) {

  def this(_generalisations: TypedKLine[Concept], _specialisations: TypedKLine[Concept], _phrases: TypedKLine[AnnotatedPhrase], _content: Resource,
           _links: List[SemanticNetworkLink], _uri: KnowledgeURI) {
    this(_generalisations, _specialisations, _phrases, _content, _links, _uri, new Probability())
  }

  def phrases = _phrases

  def phrases_=(in: TypedKLine[AnnotatedPhrase]): Concept = {
    _phrases = in
    this
  }

  def generalisations = _generalisations

  def generalisations_=(in: TypedKLine[Concept]): Concept = {
    _generalisations = in
    this
  }

  def specialisations = _specialisations

  def specialisations_=(in: TypedKLine[Concept]): Concept = {
    _specialisations = in
    this
  }

}

object Concept {

  def apply(phrases: TypedKLine[AnnotatedPhrase], name: String): Concept = {
    new Concept(TypedKLine[Concept]("generalisation"), TypedKLine[Concept]("specialisation"),
      phrases, KnowledgeString(name, name), List[SemanticNetworkLink](), KnowledgeURI(name + "Concept"))
  }

  def apply(word: String, name: String): Concept = {
    new Concept(TypedKLine[Concept]("generalisation"),
      TypedKLine[Concept]("specialisation"),
      TypedKLine[AnnotatedPhrase]("phrases", AnnotatedPhrase(word)),
      KnowledgeString(name, name),
      List[SemanticNetworkLink](),
      KnowledgeURI(name + "Concept"))
  }

  def apply(name: String): Concept = {
    new Concept(TypedKLine[Concept]("generalisation"),
      TypedKLine[Concept]("specialisation"),
      TypedKLine[AnnotatedPhrase]("phrases"),
      KnowledgeString(name, name),
      List[SemanticNetworkLink](),
      KnowledgeURI(name + "Concept"))
  }

  def createSubConcept(parent: Concept, name: String): Concept = {
    val it = new Concept(TypedKLine("generalisations", parent), TypedKLine("specialisations"), TypedKLine("phrases"),
      KnowledgeString(name, name),
      List[SemanticNetworkLink](),
      KnowledgeURI(name + "Concept"))
    parent.specialisations = parent.specialisations + (it.uri -> it)
    it
  }

  def createInstanceConcept(parent: Concept): Concept = {
    val name = parent.uri.name + Random.nextString(Constant.INSTANCE_ID_LENGTH)
    val it = new Concept(TypedKLine("generalisations", parent), TypedKLine("specialisations"), TypedKLine("phrases"),
      KnowledgeString(name, name),
      List[SemanticNetworkLink](),
      KnowledgeURI(name + "Concept"))
    parent.specialisations = parent.specialisations + (it.uri -> it)
    it
  }
}