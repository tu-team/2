package tu.model.knowledge.howto

import tu.model.knowledge.frame.TypedFrame
import tu.model.knowledge._
import domain.{ConceptTag, Concept}
import util.Random
import tu.model.knowledge.Tag

/**
 * Stores HowTo and it's parameters.
 * @author max talanov
 *         date 2012-05-08
 *         time: 10:55 PM
 */

case class HowTo(var _parameters: List[TypedFrame[Resource]],
                 var _tags: List[ConceptTag],
                 _uri: KnowledgeURI,
                 _probability: Probability = new Probability())
  extends Resource(_uri, _probability) {

  def this(_parameters: List[TypedFrame[Resource]], _tags: List[ConceptTag], _uri: KnowledgeURI) {
    this(_parameters, _tags, _uri, new Probability())
  }

  def parameters = _parameters

  def parameters_=(in: List[TypedFrame[Resource]]): HowTo = {
    _parameters = in
    this
  }

  def tags = _tags

  def tags_=(in: List[ConceptTag]): HowTo = {
    _tags = in
    this
  }

}

object HowTo {

  val howToPostfix = "HowTo"

  /**
   * Creates instance of HowTo based on parent HowTo and parameters
   * @param parent super HowTo
   * @param parameters Resources List
   * @return HowTo instance
   */
  def createInstance(parent: HowTo, parameters: List[TypedFrame[Resource]]): HowTo = {
    val name = parent.uri.name + "&ID=" + Random.nextInt(Constant.INSTANCE_ID_LENGTH)
    val it = new HowTo(parameters, List[ConceptTag](), KnowledgeURI(name + howToPostfix))
    it
  }

  def crateInstance(parent: HowTo, parameters: List[Concept]): HowTo = {
    val name = parent.uri.name + "&ID=" + Random.nextInt(Constant.INSTANCE_ID_LENGTH)

    val frames: List[TypedFrame[Resource]] = parameters.map(c => {
      TypedFrame(c)
    })
    val it = new HowTo(frames, List[ConceptTag](), KnowledgeURI(name + howToPostfix))
    it
  }
}