package tu.model.knowledge.annotator

import tu.model.knowledge.{Resource, Probability, KnowledgeURI}
import tu.model.knowledge.domain.{ConceptNetwork, Concept}


/**
 * @author toschev alex, talanov max
 *         Date: 01.06.12
 *         Time: 19:30
 *
 */

case class AnnotatedNarrative(_sentences: List[AnnotatedSentence], _uri: KnowledgeURI, _probability: Probability = new Probability(), text: String = "")
  extends Resource(_uri, _probability) {

  def sentences: List[AnnotatedSentence] = _sentences // or Nil, but not null

  /**
   * Returns List[Concepts in current AnnotatedNarrative.
   * @return List[Concepts in current AnnotatedNarrative
   */
  def concepts: List[Concept] = {
    val sentencesWithConcepts = sentences.filter(
      (s: AnnotatedSentence) => {
        s.concepts.size > 0
      })
    val concepts: List[Concept] = sentencesWithConcepts.map {
      s: AnnotatedSentence => s.concepts
    }.flatten
    concepts
  }

  def conceptNetwork: ConceptNetwork = {
    val conceptNetworkConcepts = concepts
    val res = ConceptNetwork(conceptNetworkConcepts, uri.name + "ConceptNetwork")
    res
  }

}

object AnnotatedNarrative {
  def apply(phrases: List[AnnotatedPhrase], uri: KnowledgeURI): AnnotatedNarrative = {
    val sentence = AnnotatedSentence(phrases)
    new AnnotatedNarrative(List(sentence), uri)
  }

  def apply(text: String, phrases: List[AnnotatedPhrase], uri: KnowledgeURI): AnnotatedNarrative = {
    val sentence = AnnotatedSentence(phrases)
    new AnnotatedNarrative(List(sentence), uri, new Probability(), text)
  }
}
