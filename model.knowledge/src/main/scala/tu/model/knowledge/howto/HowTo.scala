package tu.model.knowledge.howto

import tu.model.knowledge.frame.Frame
import tu.model.knowledge._
import tu.model.knowledge.annotator.AnnotatedPhrase
import domain.{Concept, ConceptLink}
import tu.model.knowledge.primitive.KnowledgeString
import util.Random
import tu.model.knowledge.Tag

/**
 * Stores HowTo and it's parameters.
 * @author max talanov
 *         date 2012-05-08
 *         time: 10:55 PM
 */

case class HowTo(var _parameters: List[Frame[Resource]], var _tags: List[Tag], _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Resource(_uri, _probability) {

  def this(_parameters: List[Frame[Concept]], _tags: List[Tag], _uri: KnowledgeURI) {
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

object HowTo {

  val howToPostfix = "HowTo"

  /**
   * Creates instance of HowTo based on parent HowTo and parameters
   * @param parent
   * @param parameters
   * @return HowTo instance
   */
  def createInstance(parent: HowTo, parameters: List[Frame[Concept]]): HowTo = {
    val name = parent.uri.name + Random.nextString(Constant.INSTANCE_ID_LENGTH)
    val it = new HowTo(parameters, List[Tag](), KnowledgeURI(name + howToPostfix))
    it
  }
}