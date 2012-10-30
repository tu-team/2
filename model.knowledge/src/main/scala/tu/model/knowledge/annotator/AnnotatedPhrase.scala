package tu.model.knowledge.annotator

import tu.model.knowledge._
import domain.{ConceptLink, Concept}
import scala.Some
import tu.exception.UnexpectedException
import tu.model.knowledge.KBMap._
import collection.mutable.ListBuffer


/**
 * @author talanov max
 *         date 2012-06-04
 *         time: 10:32 PM
 */

case class AnnotatedPhrase(var _phrases: List[AnnotatedPhrase],
                           var _concepts: List[Concept] = List[Concept](),
                           var _links: List[ConceptLink] = List[ConceptLink](),
                           _uri: KnowledgeURI,
                           _probability: Probability = new Probability(),
                           text: String = "",
                           index: Double = 0)
  extends Resource(_uri, _probability) {

  def this(_phrases: List[AnnotatedPhrase], _uri: KnowledgeURI) = {
    this(_phrases, List[Concept](), List[ConceptLink](), _uri, new Probability(), "", 0)
  }

  def this() = {
    this(List[AnnotatedPhrase](), KnowledgeURI("Phrase"))
  }

  def this(map: Map[String, String]) = {
    this(
      List[AnnotatedPhrase](),
      List[Concept](),
      List[ConceptLink](),
      new KnowledgeURI(map),
      new Probability(map),
      map.get("text") match {
        case Some(aText) => aText
        case None => ""
      },
      map.get("index") match {
        case Some(aText) => aText.toDouble
        case None => 0
      }
    )
  }

  override def export: Map[String, String] = {
    Map("text" -> this.text, "index" -> this.index.toString) ++ super.export
  }

  def concepts = _concepts

  def concepts_=(in: List[Concept]): AnnotatedPhrase = {
    _concepts = in
    this
  }

  def conceptsAdd(in: Concept): AnnotatedPhrase = {
    _concepts = concepts ::: List(in)
    this
  }

  var _sentenceIndex: Double = index

  def sentenceIndex = _sentenceIndex

  def sentenceIndex_=(in: Double) {
    _sentenceIndex = in
  }

  /**
   * concatenated phrase
   * @return concatenated phrase
   */
  def phrase: String = {
    var ph = ""
    if (_phrases.length <= 0) {
      ph = text
    }
    else {
      _phrases.foreach(b =>
        ph += b.text.toLowerCase + " "
      )
      //remove last whitespace
      if (ph.length > 0) {
        ph = ph.substring(0, ph.length - 1)
      }
    }
    ph
  }

  override def toString: String = {
    phrase
  }

  def phrases = _phrases

  def phrases_=(in: List[AnnotatedPhrase]) {
    _phrases = in
  }

  def findPhrase(word: String): Option[AnnotatedPhrase] = {

    if (this.toString.toLowerCase == word.toLowerCase) {
      Some(this)
    }
    else {
      if (phrases.size > 0) {
        phrases.find {
          ph: AnnotatedPhrase => {
            ph.findPhrase(word) match {
              case Some(p: AnnotatedPhrase) => true
              case None => false
            }
          }
        } match {
          case Some(ph: AnnotatedPhrase) => Some(ph)
          case None => {
            if (this.text.toLowerCase == word.toLowerCase) {
              Some(this)
            } else {
              None
            }
          }
        }
      }
      else {
        None
      }
    }
  }

  override def save(kb: KB, parent: KBNodeId, key: String, linkType: String, saved: ListBuffer[String] = new ListBuffer[String]()): Boolean = {

    val uri = this.uri.toString
    if (saved.contains(uri))
    {
      //only create link
      kb.createLink(parent,this,linkType)
      return true
    }
    saved.append(uri)

    var res = kb.saveResource(this, parent, key, linkType)
    for (x: Resource <- _phrases)
      res &= x.save(kb, this, x.uri.toString, Constant.PHRASES_LINK_NAME, saved)
    for (x: Resource <- _concepts)
      res &= x.save(kb, this, x.uri.toString, Constant.CONCEPT_LINK_NAME, saved)

    res
  }
}

//TODO: For all apply with string parameter generate unique ID for newly created object
object AnnotatedPhrase {
  //TODO: for example here
  def apply(word: String): AnnotatedPhrase = {
    new AnnotatedPhrase(List[AnnotatedPhrase](), List[Concept](), List[ConceptLink](),
      KnowledgeURI(word + "EmptyPhrase"), new Probability(), word, 0)
  }

  def apply(word: String, index: Double): AnnotatedPhrase = {
    new AnnotatedPhrase(List[AnnotatedPhrase](), List[Concept](), List[ConceptLink](),
      KnowledgeURI(word + "Phrase"), new Probability(), word, index)
  }

  def apply(words: List[AnnotatedPhrase], index: Double): AnnotatedPhrase = {
    val wordsValues: String = words.foldLeft[String]("")(
      (a: String, i: AnnotatedPhrase) => {
        a + " " + i.text
      }
    )
    new AnnotatedPhrase(words, List[Concept](), List[ConceptLink](),
      KnowledgeURI(words.toString() + "Phrase"), new Probability(), wordsValues, index)
  }

  def apply(words: List[AnnotatedPhrase]): AnnotatedPhrase = {
    apply(words: List[AnnotatedPhrase], 0.0): AnnotatedPhrase
  }

  def apply(words: List[AnnotatedPhrase], text: String): AnnotatedPhrase = {
    new AnnotatedPhrase(words, List[Concept](), List[ConceptLink](),
      KnowledgeURI(words.toString() + "Phrase"), new Probability(), text, 0)
  }

  def split(words: String, name: String): AnnotatedPhrase = {
    val wordsArray: Array[String] = words.split(" ")
    val wordsList: List[AnnotatedPhrase] = (wordsArray.map(x => {
      AnnotatedPhrase(x.trim)
    })).toList
    new AnnotatedPhrase(wordsList, List[Concept](), List[ConceptLink](),
      KnowledgeURI(name), new Probability(), words, 0)
  }

  def apply(words: List[AnnotatedPhrase], concepts: List[Concept]): AnnotatedPhrase = {
    new AnnotatedPhrase(words, concepts, List[ConceptLink](), KnowledgeURI(words.toString() + "Phrase"))
  }

  def apply(words: List[AnnotatedPhrase], conceptLink: ConceptLink): AnnotatedPhrase = {
    new AnnotatedPhrase(words, List[Concept](), List(conceptLink), KnowledgeURI(words.toString() + "Phrase"))
  }

  def apply(words: List[AnnotatedPhrase], concepts: List[Concept], conceptLinks: List[ConceptLink]): AnnotatedPhrase = {
    new AnnotatedPhrase(words, concepts, conceptLinks, KnowledgeURI(words.toString() + "Phrase"))
  }

  def apply(word: String, concepts: List[Concept]): AnnotatedPhrase = {
    val it = new AnnotatedPhrase(List(AnnotatedPhrase(word)), concepts, List[ConceptLink](),
      KnowledgeURI(word + "Phrase"), new Probability(), word)
    concepts.map((in: Concept) => {
      in.phrases = in.phrases + (it.uri -> it)
      in
    })
    it
  }

  def apply(words: List[String], concept: Concept): AnnotatedPhrase = {
    val it = new AnnotatedPhrase(words.map(w => AnnotatedPhrase(w)), List(concept), List[ConceptLink](),
      KnowledgeURI(words + "Phrase"))
    concept.phrases = concept.phrases + (it.uri -> it)
    it
  }

  def apply(word: String, concept: Concept): AnnotatedPhrase = {
    val it = new AnnotatedPhrase(List(AnnotatedPhrase(word)), List(concept), List[ConceptLink](),
      KnowledgeURI(word + "Phrase"), new Probability(), word)
    concept.phrases = concept.phrases + (it.uri -> it)
    it
  }

  def apply(word: String, conceptLink: ConceptLink): AnnotatedPhrase = {
    val it = new AnnotatedPhrase(List(AnnotatedPhrase(word)), List[Concept](), List[ConceptLink](conceptLink),
      KnowledgeURI(word + "Phrase"), new Probability(), word)
    conceptLink.phrases = conceptLink.phrases + (it.uri -> it)
    it
  }


  /**
   * load from primary storage
   * @param kb KB to load.
   * @param selfMap map to load.
   * @return loaded AnnotatedPhrase
   */
  def load(kb: KB, selfMap: Map[String, String]): AnnotatedPhrase = {
    val ID = new KBNodeId(selfMap)
    subload(kb, selfMap, ID)
  }


  private def subload(kb: KB, selfMap: Map[String, String], ID: KBNodeId): AnnotatedPhrase = {


    val loadedConcepts: List[Concept] = kb.loadChildrenList(ID, Constant.CONCEPT_LINK_NAME).map(new Concept(_))
    val loadedLinks: List[ConceptLink] = loadedConcepts.map {
      c: Concept => {
        c.links
      }
    }.flatten

    val subPhrases = kb.loadChildrenList(ID, Constant.PHRASES_LINK_NAME).map(load(kb, _))
    /*
    val subPhrases =
      if (subPhrasesRaw.size == 1)
        subPhrasesRaw.map(new AnnotatedPhrase(_))
      else
        subPhrasesRaw.map(load(kb, _))
        */
    val res = new AnnotatedPhrase(
      subPhrases,
      loadedConcepts,
      loadedLinks,
      new KnowledgeURI(selfMap),
      new Probability(selfMap),
      selfMap.get("text") match {
        case Some(text) => text
        case None => ""
      },
      selfMap.get("index") match {
        case Some(text) => text.toDouble
        case None => 0
      }
    )

    KBMap.register(res, ID.ID)

    res
  }


  def load(kb: KB, parent: KBNodeId, key: String, linkType: String): AnnotatedPhrase = {
    //    apply("dummy phrase from ID-parent")

    val selfMap = kb.loadChild(parent, key, linkType)
    if (selfMap.isEmpty) {
      //log.error("Concept not loaded for link {}/{} for {}", List(key, linkType, parentId.toString))
      throw new UnexpectedException("Concept not loaded for link " + key + "/" + linkType + " for " + parent.toString)
    }

    val ID = new KBNodeId(selfMap)
    subload(kb, selfMap, ID)
  }


}
