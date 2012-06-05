package tu.model.knowledge.annotator

import tu.model.knowledge.domain.Concept
import tu.model.knowledge.{Resource, KnowledgeURI, Probability}
import scala.collection.JavaConversions._


/**
 * @author talanov max
 *         date 2012-06-04
 *         time: 10:32 PM
 */

case class AnnotatedPhrase(var _concepts: List[Concept], _words: List[AnnotatedWord], _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(_words: List[AnnotatedWord], _uri: KnowledgeURI) = {
    this(List[Concept](), _words, _uri, new Probability())
  }

}

object AnnotatedPhrase {
  def apply(word: String): AnnotatedPhrase = {
    new AnnotatedPhrase(List(AnnotatedWord(word)), KnowledgeURI(word + "Phrase"))
  }

  def apply(words: List[AnnotatedWord]): AnnotatedPhrase = {
    new AnnotatedPhrase(words, KnowledgeURI(words.toString() + "Phrase"))
  }

  def split(words:String, name: String): AnnotatedPhrase = {
    val wordsArray: Array[String] = words.split(" ")
    val wordsList: List[AnnotatedWord] = (wordsArray.map(x => { AnnotatedWord(x.trim) })).toList
    new AnnotatedPhrase(wordsList, KnowledgeURI(name))
  }
}
