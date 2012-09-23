package tu.model.knowledge

import Constant.DEFAULT_LINK_NAME
/**
 * @author ${user.name}
 */

trait KB {

  //any objects stored with link from other (parent) object. If parent object not specified, then root node used insted one.
  //
  //to store simple object (node) into KnowledgeBase you should specify parent node, key of link ant type of link.
  //
  //to store complex object, you should store core of this object then store this components as specific links use this object as parent node
  //
  //to read one object, you should specify parent, key of relation and type of relation. first object with this key and type of relation will be read.
  //     if object has sub-objects, they should be read after
  //
  // to read set of objects you should specify only parent node and type of link. it is useful for read lists of sub-object.
  //     For example for store all generalisations of concept, used Constant.GENERALISATION_LINK_NAME
  //
  //
  //common way of using database:
  //
  //  to store
  //    Any object that should be stored, should contain "save(kb: KB, parent: KBNodeId, key: String, linkType: String)" method.
  //    If object has some children, it must invoke save-method and specify "this" as a parent argument.
  //    If object could be stored as child of root node, it should contain  "save(kb: KB, key: String, linkType: String)" method.
  //    Inside save-methods described above KB.saveResource method with or without "parent" parameter should be invoked.
  //
  //  to restore
  //    Object (helper) should contain load(kb: KB, parent: KBNodeId, key: String, linkType: String) method to load this node and its child nodes
  //
  //  KBNodeId should be initialised from Resource of from Long or from map[String String]

  //  after object is restored fr0om database it can be registered by "register(r:Resource, id:Long)" method where r - loaded resource, ID its ID in KB
  //
  //  If object has some simple fields, it should has constructor from Map[String, String] and export function which return appropriate Map[String, String]

  // TODO edit and remove objects

  def getIdFromMap(map:Map[String, String]) =  KB.getIdFromMap(map)

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

  /**
   * saves specified resource into KB as child of specified parent
   * @param child    resource to be saved
   * @param parent  parent of resource to be saved
   * @param nameOfLink name of link from parent to child/ Can be used to read specified child
   * @param linkType  is defined by child type (for example generalisation and specialisation, ConceptList)
   * @return          result of saving
   */
  def saveResource(child:Resource, parent:KBNodeId, nameOfLink:String, linkType:String = DEFAULT_LINK_NAME): Boolean = false

  def loadChild(parent:KBNodeId, key:String, linkType:String = DEFAULT_LINK_NAME):Map[String,  String]  = Map()

  def loadChildrenList(parent:KBNodeId, linkType:String = DEFAULT_LINK_NAME):List[Map[String,  String]] = List()

  def loadChildrenMap(parent:KBNodeId, linkType:String = DEFAULT_LINK_NAME):Map[String,  Map[String,  String]] = Map()


  //def loadSelf(parent:KBNodeId):Map[String,  String] = Map()

}


object KB {

  def getRootId() =  0L

  def getIdFromMap(map:Map[String, String]) =  map.get(Constant.KB_ID)
  match {
    case Some(x) => x.toLong
    case None => Constant.NO_KB_NODE
  }

}
