package tu.model.knowledge

import Constant.DEFAULT_LINK_NAME
/**
 * @author ${user.name}
 */


trait KB {

  //any objects stored with link from other (parent) object. If parent object not applied, then root node used insted one.
  //
  //for store simple object (node) into KnowledgeBase you should specify parent node, key of link ant type of link.
  //
  //for store complex object, you should store core of this object then store this components as specific links use this object as parent node
  //
  //for read one object, you should specify parent, key of relation and type of relation. first object with this key and type of relation will be read.
  //     if object has sub-objects, they should be read after
  //
  // for read set of objects you should specify only parent node and type of link. it is useful for read lists of sub-object.
  // For example for store all generalisations of concept, used Constant.GENERALISATION_LINK_NAME
  //
  //
  //common way of using database:
  //
  //  for store
  //    object should contain "save(kb: KB, parent: Resource, key: String, linkType: String)" method for any object can be save own childs. parent value is this for this case
  //    if object should stored into root node, it should contain  "save(kb: KB, key: String, linkType: String)" method also
  //    inside this methods KB.saveResource with or without "parent" parameter should be called
  //
  //  for restore
  //    object (helper) should contain load(kb: KB, parent: Resource, key: String, linkType: String) method for load this node and they child nodes
  //                                 and maybe load(kb: KB, parentId: Long, key: String, linkType: String) method for load this node and they child nodes
  //    second method (with ParentId) need for load children if object not created yet: complex object as child of other complex object
  //
  //  if object has some simple fields, it should has constructor from Map[String, String] and export function which return appropriate Map[String, String]


  def getIdFromMap(map:Map[String, String]) =  map.get("KB_ID")
  match {
    case Some(x) => x.toLong
    case None => Constant.NO_KB_NODE
  }

  // methods above should be implemented in real database server

  // with root as parent
  def saveResource(resource:Resource, key:String): Boolean = saveResource(resource, key, DEFAULT_LINK_NAME)

  def saveResource(resource:Resource, key:String, linkType:String): Boolean = false

  def loadChild(key:String):Map[String,  String] = loadChild(key, DEFAULT_LINK_NAME)

  def loadChild(key:String, linkType:String):Map[String,  String] = Map()

  def loadChildrenList():List[Map[String,  String]] = loadChildrenList(DEFAULT_LINK_NAME)

  def loadChildrenList(linkType:String):List[Map[String,  String]] = List()

  def loadChildrenMap():Map[String,  Map[String,  String]] = loadChildrenMap(DEFAULT_LINK_NAME)

  def loadChildrenMap(linkType:String):Map[String,  Map[String,  String]] = Map()


  // with Resource as parent
  def saveResource(child:Resource, parent:Resource, key:String, linkType:String = DEFAULT_LINK_NAME): Boolean = false

  def loadChild(parent:Resource, key:String, linkType:String = DEFAULT_LINK_NAME):Map[String,  String]  = Map()

  def loadChildrenList(parent:Resource, linkType:String = DEFAULT_LINK_NAME):List[Map[String,  String]] = List()

  def loadChildrenMap(parent:Resource, linkType:String = DEFAULT_LINK_NAME):Map[String,  Map[String,  String]] = Map()


  // with ID as parent
  def saveResource(child:Resource, parentId:Long, key:String, linkType:String): Boolean = false

  def loadChild(parentId:Long, key:String, linkType:String):Map[String,  String]  = Map()

  def loadChildrenList(parentId:Long, linkType:String):List[Map[String,  String]] = List()

  def loadChildrenMap(parentId:Long, linkType:String):Map[String,  Map[String,  String]] = Map()

}
