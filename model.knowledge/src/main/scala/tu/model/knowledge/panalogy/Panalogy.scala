package tu.model.knowledge.panalogy

import tu.model.knowledge.frame.Frame
import tu.model.knowledge.{Probability, KnowledgeURI, MicroNeme, Resource}

/**
 * @author max
 *         date 2012-05-09
 *         time: 7:31 PM
 */

class Panalogy(var _resources: Map[MicroNeme, Frame[Resource]], _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(_resources: Map[MicroNeme, Frame[Resource]], _uri: KnowledgeURI) {
    this(_resources, _uri, new Probability)
  }

  def resources = _resources

  def resources_=(in:  Map[MicroNeme, Frame[Resource]]): Panalogy = {
    _resources = in
    this
  }

}
