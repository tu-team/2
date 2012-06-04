package tu.model.knowledge.annotator

import tu.model.knowledge.{Probability, KnowledgeURI}
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.domain.Concept

/**
 * @author max
 *         date 2012-05-31
 *         time: 11:32 PM
 */

case class AnnotatedWord(var _concepts: List[Concept], _value: String, _uri: KnowledgeURI, _probability: Probability)
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

  def apply(name: String):AnnotatedWord = {
    val uri: KnowledgeURI = KnowledgeURI(name)
    new AnnotatedWord(List[Concept](), name, uri)
  }

  def apply(concepts: List[Concept], name: String): AnnotatedWord = {
    val uri: KnowledgeURI = KnowledgeURI(name)
    new AnnotatedWord(concepts, name, uri)
  }
}
