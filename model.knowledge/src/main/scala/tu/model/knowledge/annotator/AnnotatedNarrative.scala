package tu.model.knowledge.annotator

import tu.model.knowledge._
import tu.model.knowledge.domain.{ConceptNetwork, Concept}
import scala.Some


/**
 * @author toschev alex, talanov max
 *         Date: 01.06.12
 *         Time: 19:30
 *
 */

case class AnnotatedNarrative(_sentences: List[AnnotatedSentence], _uri: KnowledgeURI, _probability: Probability = new Probability(), text: String = "")
  extends Resource(_uri, _probability) {

  def sentences: List[AnnotatedSentence] = _sentences

  def this(map: Map[String, String]) = {
    this(
      List[AnnotatedSentence](),
      new KnowledgeURI(map),
      new Probability(map),
      map.get("text") match {
        case Some(text) => text
        case None => ""
      }
    )
  }

  /*TODO: move to object
  override def loadLinks(kb: KB): List[AnnotatedSentence] = {
    val list = kb.loadChildrenList(this, Constant.SENTENCES_LINK_NAME)
    list.map {
      x: Map[String, String] => {
        new AnnotatedSentence(x)
      }
    }
  }
  */

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

  override def save(kb: KB, parent: Resource, key: String, linkType: String, saved: List[String] = Nil): Boolean = {

    val uri = this.uri.toString
    if (saved.contains(uri))
      return true
    val savedPlus:List[String] = uri :: saved

    var res = kb.saveResource(this, parent, key)
    for (x: Resource <- sentences)
      res &= x.save(kb, this, x.uri.toString, Constant.GENERALISATION_LINK_NAME, savedPlus)

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
