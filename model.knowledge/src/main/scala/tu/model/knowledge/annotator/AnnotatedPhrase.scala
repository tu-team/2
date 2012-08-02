package tu.model.knowledge.annotator

import tu.model.knowledge.domain.Concept
import tu.model.knowledge.{Resource, KnowledgeURI, Probability}


/**
 * @author talanov max
 *         date 2012-06-04
 *         time: 10:32 PM
 */

case class AnnotatedPhrase(var _words: List[AnnotatedWord], var _concepts: List[Concept] = List[Concept](), _uri: KnowledgeURI, _probability: Probability = new Probability(), text: String = "")
  extends Resource(_uri, _probability) {

  def this(_words: List[AnnotatedWord], _uri: KnowledgeURI) = {
    this(_words, List[Concept](), _uri, new Probability())
  }

  def concepts = _concepts

  def concepts_=(in: List[Concept]): AnnotatedPhrase = {
    _concepts = in
    this
  }

  /**
   * concatenated phrase
   * @return concatenated phrase
   */
  def phrase:String={
    var ph=""
    words.foreach(b=>

      ph+= b.value.toLowerCase+" "

    )

    //remove last whitespace
    ph=ph.substring(0,ph.length-1)

    ph
  }

  def words = _words

  def words_=(in: List[AnnotatedWord])=_words=in

}

object AnnotatedPhrase {
  def apply(word: String): AnnotatedPhrase = {
    new AnnotatedPhrase(List(AnnotatedWord(word)), List[Concept](), KnowledgeURI(word + "Phrase"), new Probability(), word)
  }

  def apply(words: List[AnnotatedWord]): AnnotatedPhrase = {
    val wordsValues: String = words.foldLeft[String]("")(
      (a: String, i: AnnotatedWord) => {a + " " + i.value}
    )
    new AnnotatedPhrase(words, List[Concept](),KnowledgeURI(words.toString() + "Phrase"), new Probability(), wordsValues)
  }

  def apply(words: List[AnnotatedWord], text: String): AnnotatedPhrase = {
    new AnnotatedPhrase(words, List[Concept](),KnowledgeURI(words.toString() + "Phrase"), new Probability(), text)
  }

  def split(words: String, name: String): AnnotatedPhrase = {
    val wordsArray: Array[String] = words.split(" ")
    val wordsList: List[AnnotatedWord] = (wordsArray.map(x => {
      AnnotatedWord(x.trim, wordsArray.indexOf(x))
    })).toList
    new AnnotatedPhrase(wordsList, List[Concept](), KnowledgeURI(name), new Probability(), words)
  }

  def apply(words: List[AnnotatedWord], concepts: List[Concept]): AnnotatedPhrase = {
    new AnnotatedPhrase(words, concepts, KnowledgeURI(words.toString() + "Phrase"))
  }

  def apply(word: String, concepts: List[Concept]): AnnotatedPhrase = {
    val it = new AnnotatedPhrase(List(AnnotatedWord(word)), concepts, KnowledgeURI(word + "Phrase"), new Probability(), word)
    concepts.map((in: Concept) => {
      in.phrases = in.phrases + (it.uri -> it)
      in
    })
    it
  }

  def apply(word: String, concept: Concept): AnnotatedPhrase = {
    val it = new AnnotatedPhrase(List(AnnotatedWord(word)), List(concept), KnowledgeURI(word + "Phrase"), new Probability(), word)
    concept.phrases = concept.phrases + (it.uri -> it)
    it
  }

}
