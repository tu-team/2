package tu.model.knowledge.semanticnetwork

import tu.model.knowledge._
import scala.{None, Some}


/**
 * Stores SemanticNetwork nodes and it's parameters.
 * @author talanov max
 *         date 2012-05-02
 *         time: 10:53 PM
 */

case class SemanticNetworkNode[Type<:Resource](_content: Type, var _links: List[SemanticNetworkLink], _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(_content: Type, _links: List[SemanticNetworkLink], _uri: KnowledgeURI) = {
    this(_content, _links, _uri, new Probability())
  }

  def content = _content

  def links = _links

  def links_=(in: List[SemanticNetworkLink]): SemanticNetworkNode[Type] = {
    _links = in
    this
  }

  //override def save(kb:KB, parent:KBNodeId, key:String):Boolean =
  //{
  //      super.save(kb,parent,key);
  //}

}
