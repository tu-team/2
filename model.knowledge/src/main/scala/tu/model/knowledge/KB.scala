package tu.model.knowledge


/**
 * @author ${user.name}
 */


trait KB {

  // should be implemented in real database server
  def saveResource(resource:Resource, key:String): Boolean = saveResource(resource, key, "defaultLink")

  def saveResource(resource:Resource, key:String, linkType:String): Boolean = false

  def saveResource(child:Resource, parent:Resource, key:String, linkType:String = "defaultLink"): Boolean = false

  def loadChild(key:String):Map[String,  String] = loadChild(key, "defaultLink")

  def loadChild(key:String, linkType:String):Map[String,  String] = Map()

  def loadChildrenList():List[Map[String,  String]] = loadChildrenList("defaultLink")

  def loadChildrenList(linkType:String):List[Map[String,  String]] = List()

  def loadChildrenMap():Map[String,  Map[String,  String]] = loadChildrenMap("defaultLink")

  def loadChildrenMap(linkType:String):Map[String,  Map[String,  String]] = Map()

  def loadChild(parent:Resource, key:String, linkType:String = "defaultLink"):Map[String,  String]  = Map()

  def loadChildrenList(parent:Resource, linkType:String = "defaultLink"):List[Map[String,  String]] = List()

  def loadChildrenMap(parent:Resource, linkType:String = "defaultLink"):Map[String,  Map[String,  String]] = Map()

}
