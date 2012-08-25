package tu.dataservice.knowledgebaseserver

import tu.model.knowledge.Resource

/**
 * @author ${user.name}
 */


trait KB {

  def save(resource:Resource): Boolean

  def save(child:Resource, parent:Resource, key:String = ""): Boolean

  def loadChild(key:String):Resource

  def loadChildrenList():List[Resource]

  def loadChildrenMap():Map[String,  Resource]

  def loadChild(parent:Resource, key:String):Resource

  def loadChildrenList(parent:Resource):List[Resource]

  def loadChildrenMap(parent:Resource):Map[String,  Resource]


}
