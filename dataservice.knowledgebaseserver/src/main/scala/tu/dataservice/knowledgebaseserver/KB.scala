package tu.dataservice.knowledgebaseserver

import tu.model.knowledge.Resource

/**
 * @author ${user.name}
 */


trait KB {

  def saveResource(resource:Resource, key:String): Boolean = false

  def saveResource(child:Resource, parent:Resource, key:String): Boolean = false

  def loadChild(key:String):Map[String,  String] = Map()

  def loadChildrenList():List[Map[String,  String]] = List()

  def loadChildrenMap():Map[String,  Map[String,  String]] = Map()

  def loadChild(parent:Resource, key:String):Map[String,  String]  = Map()

  def loadChildrenList(parent:Resource):List[Map[String,  String]] = List()

  def loadChildrenMap(parent:Resource):Map[String,  Map[String,  String]] = Map()


}
