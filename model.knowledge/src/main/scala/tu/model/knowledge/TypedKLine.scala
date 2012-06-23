package tu.model.knowledge


/**
 * Stores typed KLine
 * @author talanov max
 *         date 2012-06-08
 *         time: 10:51 PM
 * @see KLine
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

  def +(in: Pair[KnowledgeURI, Type]): TypedKLine[Type] = {
    _frames = _frames + in
    this
  }

}

object TypedKLine {

  def apply[Type <: Resource](uri: KnowledgeURI): TypedKLine[Type] = {
    new TypedKLine(Map[KnowledgeURI, Type](), uri)
  }

  def apply[Type <: Resource](name: String): TypedKLine[Type] = {
    new TypedKLine(Map[KnowledgeURI, Type](), KnowledgeURI(name + "TypedKLine"))
  }

  def apply[Type <: Resource](name: String, entity: Type): TypedKLine[Type] = {
    new TypedKLine(Map[KnowledgeURI, Type](entity.uri -> entity), KnowledgeURI(name + "TypedKLine"))
  }

  def apply[Type <: Resource](name: String, entities: Map[KnowledgeURI, Type]): TypedKLine[Type] = {
    new TypedKLine(entities, KnowledgeURI(name + "TypedKLine"))
  }
}