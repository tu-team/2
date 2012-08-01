package tu.model.knowledge.annotator

import tu.model.knowledge.{Resource, Probability, KnowledgeURI}
import tu.model.knowledge.domain.Concept

/**
 * @author alex toschev
 *         Time stamp: 6/29/12 11:25 AM
 */

case class AnnotatedSentence(var _phrases: List[AnnotatedPhrase], _uri: KnowledgeURI, _probability: Probability = new Probability(), text: String = "")
  extends Resource(_uri, _probability) {

  def this(_phrases: List[AnnotatedPhrase], _uri: KnowledgeURI) = {
    this(_phrases, _uri, new Probability())
  }

  def phrases = _phrases

  def concepts_=(in: List[AnnotatedPhrase]): AnnotatedSentence = {
    _phrases = in
    this
  }

  def concepts: List[Concept] = {
    val phrasesWithConcepts = phrases.filter(
      (p: AnnotatedPhrase) => {
        p.concepts.size > 0
      })
    val concepts: List[Concept] = phrasesWithConcepts.map {
      p: AnnotatedPhrase => p.concepts
    }.flatten
    concepts
  }

}

object AnnotatedSentence {
  def apply(phrases: List[AnnotatedPhrase]): AnnotatedSentence = {
    new AnnotatedSentence(phrases, KnowledgeURI("AnnotatedSentence"))
  }

  /**
   * Creates AnnotatedSentence based on specified text and AnnotatedPhrase
   * @param text of AnnotatedSentence
   * @param phrases list of AnnotatedPhrase
   * @return AnnotatedSentence
   */
  def apply(text: String, phrases: List[AnnotatedPhrase]): AnnotatedSentence = {
    new AnnotatedSentence(phrases, KnowledgeURI("AnnotatedSentence"), new Probability(), text)
  }
}

