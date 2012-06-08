package tu.model.knowledge

/**
 * @author talanov max
 *         date 2012-06-08
 *         time: 10:51 PM
 */

case class TypedKLine[Type <: Resource](var _frames: Map[KnowledgeURI, Type], _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(_frames: Map[KnowledgeURI, Type], _uri: KnowledgeURI) = {
    this(_frames, _uri, new Probability())
  }

  def frames: Map[KnowledgeURI, Type] = _frames

  def frames_=(value: Map[KnowledgeURI, Type]): TypedKLine[Type] = {
    _frames = value
    this
  }

}

object TypedKLine {

  def apply[Type <: Resource](uri: KnowledgeURI): KLine = {
    new KLine(Map[KnowledgeURI, Type](), uri)
  }

  def apply[Type <: Resource](name: String): KLine = {
    new KLine(Map[KnowledgeURI, Type](), KnowledgeURI(name))
  }
}