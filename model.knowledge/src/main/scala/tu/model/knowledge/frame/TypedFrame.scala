package tu.model.knowledge.frame

import tu.model.knowledge.{Probability, KnowledgeURI, Resource}
import tu.model.knowledge.domain.Concept


/**
 * @author toschev alex, talanov max
 *         Date: 03.05.12
 *         Time: 12:29
 */

case class Frame(var __resources: Map[KnowledgeURI, Resource],
                 override val _uri: KnowledgeURI,
                 override val _probability: Probability = new Probability())
  extends TypedFrame[Resource](__resources, _uri, _probability) {
}

object Frame{
  val framePostfix = "Frame"
/**
 * Creates TypedFrame based on Resource list.
 * @param resource to store
 * @return TypedFrame[Resource]
 */
  def apply(resource: Resource): Frame = {
    new Frame(Map[KnowledgeURI, Resource](resource.uri -> resource), KnowledgeURI(resource.uri.name + framePostfix))
  }

  def apply(someList: List[Resource], uri: KnowledgeURI): Frame = {
    val conceptMap: Map[KnowledgeURI, Resource] = someList.map {
      t => (t.uri, t)
    }.toMap
    new Frame(conceptMap, uri)
  }


}

case class TypedFrame[Type <: Resource](var _resources: Map[KnowledgeURI, Type],
                                        _uri: KnowledgeURI,
                                        _probability: Probability = new Probability())
  extends Resource(_uri, _probability) {

  def this(_resources: Map[KnowledgeURI, Type],
           _uri: KnowledgeURI) {
    this(_resources: Map[KnowledgeURI, Type],
      _uri: KnowledgeURI,
      new Probability())
  }

  def resources = _resources

  def resources_=(in: Map[KnowledgeURI, Type]): TypedFrame[Type] = {
    _resources = in
    this
  }

  override def equals(that: Any) = {
    (that.isInstanceOf[TypedFrame[Resource]] && that.asInstanceOf[TypedFrame[Resource]].resources.equals(this.resources))
  }


}

object TypedFrame {
  val framePostfix = "TypedFrame"

  /**
   * Creates TypedFrame[Concept] based on concept.
   * @param concept concept to store
   * @return TypedFrame[Resource]
   */
  def apply(concept: Concept): TypedFrame[Resource] = {
    new TypedFrame[Resource](Map[KnowledgeURI, Concept](concept.uri -> concept), KnowledgeURI(concept.uri.name + framePostfix))
  }

  /**
   * Creates TypedFrame based on Resource list.
   * @param resource to store
   * @return TypedFrame[Resource]
   */
  def apply(resource: Resource): TypedFrame[Resource] = {
    new TypedFrame(Map[KnowledgeURI, Resource](resource.uri -> resource), KnowledgeURI(resource.uri.name + framePostfix))
  }

  /**
   * Creates TypedFrame of Concept List with specified URI.
   * @param conceptList to store
   * @param uri to assign
   * @return TypedFrame[Resource]
   */
  def apply(conceptList: List[Concept], uri: KnowledgeURI): TypedFrame[Resource] = {
    val conceptMap: Map[KnowledgeURI, Concept] = conceptList.map {
      t => (t.uri, t)
    }.toMap
    new TypedFrame(conceptMap, uri)
  }
}
