package tu.model.knowledge

import Constant.DEFAULT_LINK_NAME
/**
 * @author ${user.name}
 */


trait KB {

  // should be implemented in real database server
  def saveResource(resource:Resource, key:String): Boolean = saveResource(resource, key, DEFAULT_LINK_NAME)

  def saveResource(resource:Resource, key:String, linkType:String): Boolean = false

  def saveResource(child:Resource, parent:Resource, key:String, linkType:String = DEFAULT_LINK_NAME): Boolean = false

  def loadChild(key:String):Map[String,  String] = loadChild(key, DEFAULT_LINK_NAME)

  def loadChild(key:String, linkType:String):Map[String,  String] = Map()

  def loadChildrenList():List[Map[String,  String]] = loadChildrenList(DEFAULT_LINK_NAME)

  def loadChildrenList(linkType:String):List[Map[String,  String]] = List()

  def loadChildrenMap():Map[String,  Map[String,  String]] = loadChildrenMap(DEFAULT_LINK_NAME)

  def loadChildrenMap(linkType:String):Map[String,  Map[String,  String]] = Map()

  def loadChild(parent:Resource, key:String, linkType:String = DEFAULT_LINK_NAME):Map[String,  String]  = Map()

  def loadChildrenList(parent:Resource, linkType:String = DEFAULT_LINK_NAME):List[Map[String,  String]] = List()

  def loadChildrenMap(parent:Resource, linkType:String = DEFAULT_LINK_NAME):Map[String,  Map[String,  String]] = Map()

}
