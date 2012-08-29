package tu.model.knowledge.annotator

import tu.model.knowledge._
import domain.Concept
import scala.Some

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

  def this(map: Map[String, String]) = {
    this(
      List[AnnotatedPhrase](),
      new KnowledgeURI(map),
      new Probability(map),
      map.get("text") match {
        case Some(text) => text
        case None => ""
      }
    )
    // TODO add loadLinks here
  }

  /* TODO: new AnnotatedPhrase()
  override def loadLinks(kb: KB): List[AnnotatedPhrase] = {
    val list = kb.loadChildrenList(this, Constant.PHRASES_LINK_NAME)
    list.map {
      x: Map[String, String] => {
        new AnnotatedPhrase(x)
      }
    }
  }
  */
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

  /**
   * Creates AnnotatedSentence based on specified text and AnnotatedPhrase
   * @param text of AnnotatedSentence
   * @param phrases list of AnnotatedPhrase
   * @param uri KnowledgeURI of sentence
   * @return AnnotatedSentence
   */
  def apply(text: String, phrases: List[AnnotatedPhrase], uri: KnowledgeURI): AnnotatedSentence = {
    new AnnotatedSentence(phrases, uri, new Probability(), text)
  }
}

