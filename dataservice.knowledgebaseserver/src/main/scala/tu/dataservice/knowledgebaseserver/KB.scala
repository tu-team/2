package tu.dataservice.knowledgebaseserver

import tu.model.knowledge.Resource

/**
 * @author ${user.name}
 */


trait KB {

  def save(resource:Resource): Boolean

  def save(child:Resource, parent:Resource, key:String = ""): Boolean

  def loadChild(key:String):Map[String,  String]

  def loadChildrenList():List[Map[String,  String]]

  def loadChildrenMap():Map[String,  Map[String,  String]]

  def loadChild(parent:Resource, key:String):Map[String,  String]

  def loadChildrenList(parent:Resource):List[Map[String,  String]]

  def loadChildrenMap(parent:Resource):Map[String,  Map[String,  String]]


}
