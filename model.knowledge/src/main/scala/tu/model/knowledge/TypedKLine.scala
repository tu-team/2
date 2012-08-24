package tu.model.knowledge

import tu.exception.UnexpectedException

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

  //TODO correct this
  /*def this(map: Map[String, String]) = {
    val typeString = map.get("type") match {
      case Some(x) => x
      case None => throw new UnexpectedException("$Type_not_specified")
    }
    val frames = map.get("frames") match {
      case Some(x) => {
        x
      }
      case None => Map[KnowledgeURI, Resource]()
    }
    this(Map[KnowledgeURI, Resource](), new KnowledgeURI(map), new Probability(map))
  }*/
}

object TypedKLine {

  //TODO correct this
  def translateStringMap[Type <: Resource](stringMap: String, typeString: String): Map[KnowledgeURI, Type] = {
    val res = Map.empty[KnowledgeURI, Type]
    val listPairsString: List[String] = stringMap.replaceFirst("Map\(", "").replace(")", "").split(",").toList
    val listPairs: List[Pair[String, String]] = listPairsString.map {
      x: String => {
        val twoString = x.split("=>").toList
        if (twoString.size > 1) {
          (twoString(0), twoString(1))
        } else {
          throw new UnexpectedException("$Invalid_map")
        }
      }
    }
    /*listPairs.map {

    }*/
    null
  }

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