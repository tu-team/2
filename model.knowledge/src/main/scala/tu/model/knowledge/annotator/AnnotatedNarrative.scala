package tu.model.knowledge.annotator

import tu.model.knowledge._
import tu.model.knowledge.domain.{ConceptNetwork, Concept}
import scala.Some
import tu.exception.UnexpectedException
import tu.model.knowledge.KBMap._
import collection.mutable.ListBuffer


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

  override def save(kb: KB, parent: KBNodeId, key: String, linkType: String, saved: ListBuffer[String] = new ListBuffer[String]()): Boolean = {

    val uri = this.uri.toString
    if (saved.contains(uri))
    {
      kb.createLink(parent,this,linkType)
      return true
    }
      saved.append(uri)

    var res = kb.saveResource(this, parent, key, linkType)
    for (x: Resource <- sentences)
      res &= x.save(kb, this, x.uri.toString, Constant.PHRASES_LINK_NAME, saved)

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


  def load(kb: KB, parent: KBNodeId, key: String, linkType: String):AnnotatedNarrative = {

    val selfMap = kb.loadChild(parent, key, linkType)
    if (selfMap.isEmpty) {
      //log.error("Concept not loaded for link {}/{} for {}", List(key, linkType, parentId.toString))
      throw new UnexpectedException("Concept not loaded for link " + key + "/" + linkType + " for " + parent.toString)
    }

    val ID = new KBNodeId(selfMap)

    val res = new AnnotatedNarrative(
        kb.loadChildrenList(ID, Constant.PHRASES_LINK_NAME).map(new AnnotatedSentence(_)),
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
