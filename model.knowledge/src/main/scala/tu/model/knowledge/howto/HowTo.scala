package tu.model.knowledge.howto

import tu.model.knowledge.frame.Frame
import tu.model.knowledge.{Resource, Probability, KnowledgeURI, Tag}

/**
 * @author max
 *         date 2012-05-08
 *         time: 10:55 PM
 */

class HowTo(var _parameters: List[Frame[Resource]], var _tags: List[Tag], _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(_parameters: List[Frame[Resource]], _tags: List[Tag], _uri: KnowledgeURI) {
    this(_parameters, _tags, _uri, new Probability())
  }

  def parameters = _parameters

  def parameters_=(in: List[Frame[Resource]]): HowTo = {
    _parameters = in
    this
  }

  def tags = _tags

  def tags_=(in: List[Tag]): HowTo = {
    _tags = in
    this
  }

}
