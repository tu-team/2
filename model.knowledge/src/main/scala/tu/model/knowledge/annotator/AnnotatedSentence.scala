package tu.model.knowledge.annotator

import tu.model.knowledge._
import domain.Concept
import scala.Some
import tu.exception.UnexpectedException

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
  }

  override def export:Map[String, String] = {
    super.export + Pair("text",  this.text)
  }

  override def save(kb: KB, parent: KBNodeId, key: String, linkType: String, saved: List[String] = Nil): Boolean = {

    val uri = this.uri.toString
    if (saved.contains(uri))
      return true
    val savedPlus:List[String] = uri :: saved

    var res = kb.saveResource(this, parent, key)

    for (x: Resource <- _phrases)
      res &= x.save(kb, this, x.uri.toString, Constant.PHRASES_LINK_NAME, savedPlus)
    res
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

  def load(kb: KB, parent: KBNodeId, key: String, linkType: String):AnnotatedSentence = {

    val selfMap = kb.loadChild(parent, key, linkType)
    if (selfMap.isEmpty) {
      //log.error("Concept not loaded for link {}/{} for {}", List(key, linkType, parentId.toString))
      throw new UnexpectedException("Concept not loaded for link " + key + "/" + linkType + " for " + parent.toString)
    }

    val ID = new KBNodeId(selfMap)

    val res = new AnnotatedSentence(
      kb.loadChildrenList(ID, Constant.PHRASES_LINK_NAME).map(new AnnotatedPhrase(_)),
      new KnowledgeURI(selfMap),
      new Probability(selfMap),
      selfMap.get("text") match {
        case Some(text) => text
        case None => ""
      }
    )

    KBMap.register(res, ID.ID)

    res
  }

}

