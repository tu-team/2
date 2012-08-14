package tu.model.knowledge.annotator

import tu.model.knowledge.domain.Concept
import tu.model.knowledge.{Resource, KnowledgeURI, Probability}


/**
 * @author talanov max
 *         date 2012-06-04
 *         time: 10:32 PM
 */

//TODO add AnnotatedPhrases list.
case class AnnotatedPhrase(var _phrases: List[AnnotatedPhrase], var _concepts: List[Concept] = List[Concept](), _uri: KnowledgeURI, _probability: Probability = new Probability(), text: String = "")
  extends Resource(_uri, _probability) {

  def this(_phrases: List[AnnotatedPhrase], _uri: KnowledgeURI) = {
    this(_phrases, List[Concept](), _uri, new Probability())
  }

  def this() = {
    this(List[AnnotatedPhrase](), KnowledgeURI("Phrase"))
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
  def phrase: String = {
    var ph = ""
    _phrases.foreach(b =>

      ph += b.text.toLowerCase + " "

    )

    //remove last whitespace
    if (ph.length > 0) {
      ph = ph.substring(0, ph.length - 1)
    }

    this.text + " " + ph
  }

  override def toString(): String = {
    return phrase
  }

  def phrases = _phrases

  def phrases_=(in: List[AnnotatedPhrase]) = _phrases = in

  def findPhrase(word: String): Option[String] = {
    if (phrases.size > 0) {
      phrases.find {
        ph: AnnotatedPhrase => {
          ph.findPhrase(word) match {
            case Some(str: String) => true
            case None => false
          }
        }
      } match {
        case Some(ph: AnnotatedPhrase) => Some(ph.text)
        case None => {
          if (this.text == word) {
            Some(this.text)
          } else {
            None
          }
        }
      }
    } else {
      if (this.text == word) {
        Some(this.text)
      } else {
        None
      }
    }
  }
}

object AnnotatedPhrase {
  def apply(word: String): AnnotatedPhrase = {
    new AnnotatedPhrase(List[AnnotatedPhrase](), List[Concept](), KnowledgeURI(word + "Phrase"), new Probability(), word)
  }

  def apply(words: List[AnnotatedPhrase]): AnnotatedPhrase = {
    val wordsValues: String = words.foldLeft[String]("")(
      (a: String, i: AnnotatedPhrase) => {
        a + " " + i.text
      }
    )
    new AnnotatedPhrase(words, List[Concept](), KnowledgeURI(words.toString() + "Phrase"), new Probability(), wordsValues)
  }

  def apply(words: List[AnnotatedPhrase], text: String): AnnotatedPhrase = {
    new AnnotatedPhrase(words, List[Concept](), KnowledgeURI(words.toString() + "Phrase"), new Probability(), text)
  }

  def split(words: String, name: String): AnnotatedPhrase = {
    val wordsArray: Array[String] = words.split(" ")
    val wordsList: List[AnnotatedPhrase] = (wordsArray.map(x => {
      AnnotatedPhrase(x.trim)
    })).toList
    new AnnotatedPhrase(wordsList, List[Concept](), KnowledgeURI(name), new Probability(), words)
  }

  def apply(words: List[AnnotatedPhrase], concepts: List[Concept]): AnnotatedPhrase = {
    new AnnotatedPhrase(words, concepts, KnowledgeURI(words.toString() + "Phrase"))
  }

  def apply(word: String, concepts: List[Concept]): AnnotatedPhrase = {
    val it = new AnnotatedPhrase(List(AnnotatedPhrase(word)), concepts, KnowledgeURI(word + "Phrase"), new Probability(), word)
    concepts.map((in: Concept) => {
      in.phrases = in.phrases + (it.uri -> it)
      in
    })
    it
  }

  def apply(word: String, concept: Concept): AnnotatedPhrase = {
    val it = new AnnotatedPhrase(List(AnnotatedPhrase(word)), List(concept), KnowledgeURI(word + "Phrase"), new Probability(), word)
    concept.phrases = concept.phrases + (it.uri -> it)
    it
  }
}
