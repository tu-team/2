package tu.model.knowledge.frame

import tu.model.knowledge.{Probability, KnowledgeURI, Resource}
import tu.model.knowledge.domain.Concept


/**
 * @author toscheva, talanovm
 *         Date: 03.05.12
 *         Time: 12:29

 */

case class Frame[Type <: Resource](var _resources: Map[KnowledgeURI, Type], _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Resource(_uri, _probability) {

  def this(_resources: Map[KnowledgeURI, Type], _uri: KnowledgeURI) {
    this(_resources: Map[KnowledgeURI, Type], _uri: KnowledgeURI, new Probability())
  }

  def resources = _resources

  def resources_=(in: Map[KnowledgeURI, Type]): Frame[Type] = {
    _resources = in
    this
  }

  override def equals(that: Any) = {
    (that.isInstanceOf[Frame[Resource]] && that.asInstanceOf[Frame[Resource]].resources.equals(this.resources))
  }
}

object Frame {
  val framePostfix = "Frame"

  /**
   * Creates Frame[Concept] based on concept.
   * @param concept concept to store
   * @return Frame[Resource]
   */
  def apply(concept: Concept): Frame[Resource] = {
    new Frame[Resource](Map[KnowledgeURI, Concept](concept.uri -> concept), KnowledgeURI(concept.uri.name + framePostfix))
  }

  /**
   * Creates Frame based on Resource list.
   * @param resource to store
   * @return Frame[Resource]
   */
  def apply(resource: Resource): Frame[Resource] = {
    new Frame(Map[KnowledgeURI, Resource](resource.uri -> resource), KnowledgeURI(resource.uri.name + framePostfix))
  }

  /**
   * Creates Frame of Concept List with specified URI.
   * @param conceptList to store
   * @param uri to assign
   * @return Frame[Resource]
   */
  def apply(conceptList: List[Concept], uri: KnowledgeURI): Frame[Resource] = {
    val conceptMap: Map[KnowledgeURI, Concept] = conceptList.map {
      t => (t.uri, t)
    }.toMap
    new Frame(conceptMap, uri)
  }
}
