package tu.model.knowledge.annotator

import tu.model.knowledge.{Probability, KnowledgeURI}
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.domain.Concept

/**
 * Stores word and it's concepts.
 * @author max
 *         date 2012-05-31
 *         time: 11:32 PM
 */

case class AnnotatedWord(var _concepts: List[Concept], val _value: String, val _uri: KnowledgeURI,val _probability: Probability = new Probability(), position: Int = 0)
  extends KnowledgeString(_value, _uri, _probability) {

  def this(_concepts: List[Concept], _value: String, _uri: KnowledgeURI) {
    this(_concepts, _value, _uri, new Probability)
  }

  def concepts = _concepts
  def concepts_=(in: List[Concept]): AnnotatedWord = {
    _concepts = in
    this
  }
}

object AnnotatedWord {

  @deprecated
  def apply(name: String):AnnotatedWord = {
    val uri: KnowledgeURI = KnowledgeURI(name)
    new AnnotatedWord(List[Concept](), name, uri, new Probability(), 0)
  }

  def apply(name: String, position: Int):AnnotatedWord = {
    val uri: KnowledgeURI = KnowledgeURI(name)
    new AnnotatedWord(List[Concept](), name, uri, new Probability(), position)
  }

  @deprecated
  def apply(concepts: List[Concept], name: String): AnnotatedWord = {
    val uri: KnowledgeURI = KnowledgeURI(name)
    new AnnotatedWord(concepts, name, uri)
  }

  def apply(concepts: List[Concept], name: String, position: Int): AnnotatedWord = {
    val uri: KnowledgeURI = KnowledgeURI(name)
    new AnnotatedWord(concepts, name, uri, new Probability())
  }
}
