package tu.model.knowledge.annotator

import tu.model.knowledge.{Resource, Probability, KnowledgeURI}
import tu.model.knowledge.domain.Concept


/**
 * @author toschev alex, talanov max
 *         Date: 01.06.12
 *         Time: 19:30
 *
 */

case class AnnotatedNarrative(_phrases: List[AnnotatedPhrase], _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Resource(_uri, _probability) {

  def phrases = _phrases

  /**
   * Returns List[Concepts in current AnnotatedNarrative.
   * @return List[Concepts in current AnnotatedNarrative
   */
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
