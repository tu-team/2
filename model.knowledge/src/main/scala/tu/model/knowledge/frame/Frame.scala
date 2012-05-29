package tu.model.knowledge.frame

import tu.model.knowledge.{Probability, KnowledgeURI, Resource}


/**
 * @author toscheva, talanovm
 *         Date: 03.05.12
 *         Time: 12:29

 */

case class Frame[Type<:Resource](var _resources: Map[KnowledgeURI, Type], _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(_resources: Map[KnowledgeURI, Type], _uri: KnowledgeURI) {
    this(_resources: Map[KnowledgeURI, Type], _uri: KnowledgeURI, new Probability())
  }

  def resources = _resources

  def resources_= (in: Map[KnowledgeURI, Type]): Frame[Type] = {
    _resources = in
    this
  }

  override def equals(that: Any) = {
    (that.isInstanceOf[Frame[Resource]] && that.asInstanceOf[Frame[Resource]].resources.equals(this.resources))
  }
}
