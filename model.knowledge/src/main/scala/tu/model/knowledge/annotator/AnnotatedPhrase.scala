package tu.model.knowledge.annotator

import tu.model.knowledge.domain.Concept
import tu.model.knowledge.{Resource, KnowledgeURI, Probability}


/**
 * @author talanov max
 *         date 2012-06-04
 *         time: 10:32 PM
 */

case class AnnotatedPhrase(_words: List[AnnotatedWord], var _concepts: List[Concept], _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Resource(_uri, _probability) {

  def this(_words: List[AnnotatedWord], _uri: KnowledgeURI) = {
    this(_words, List[Concept](), _uri, new Probability())
  }

  def concepts = _concepts

  def concepts_=(in: List[Concept]): AnnotatedPhrase = {
    _concepts = in
    this
  }

  def words = _words

}

object AnnotatedPhrase {
  def apply(word: String): AnnotatedPhrase = {
    new AnnotatedPhrase(List(AnnotatedWord(word)), KnowledgeURI(word + "Phrase"))
  }

  def apply(words: List[AnnotatedWord]): AnnotatedPhrase = {
    new AnnotatedPhrase(words, KnowledgeURI(words.toString() + "Phrase"))
  }

  def split(words: String, name: String): AnnotatedPhrase = {
    val wordsArray: Array[String] = words.split(" ")
    val wordsList: List[AnnotatedWord] = (wordsArray.map(x => {
      AnnotatedWord(x.trim)
    })).toList
    new AnnotatedPhrase(wordsList, KnowledgeURI(name))
  }

  def apply(words: List[AnnotatedWord], concepts: List[Concept]): AnnotatedPhrase = {
    new AnnotatedPhrase(words, concepts, KnowledgeURI(words.toString() + "Phrase"))
  }

  def apply(word: String, concepts: List[Concept]): AnnotatedPhrase = {
    val it = new AnnotatedPhrase(List(AnnotatedWord(word)), concepts, KnowledgeURI(word + "Phrase"))
    concepts.map((in: Concept) => {
      in.phrases = in.phrases + (it.uri -> it)
      in
    })
    it
  }

  def apply(word: String, concept: Concept): AnnotatedPhrase = {
    val it = new AnnotatedPhrase(List(AnnotatedWord(word)), List(concept), KnowledgeURI(word + "Phrase"))
    concept.phrases = concept.phrases + (it.uri -> it)
    it
  }

}
