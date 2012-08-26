package tu.dataservice.knowledgebaseserver

import org.neo4j.kernel.EmbeddedGraphDatabase
import tu.model.knowledge.training.Goal
import tu.model.knowledge.Resource
import org.neo4j.graphdb.index.Index
import collection.immutable.HashMap
import org.neo4j.graphdb._


class RelationType(_name:String) extends RelationshipType
{
  def name():String = {_name}
}


object N4JKB extends KB {
  val defaultFilename = java.lang.System.getProperty("user.home") + "/tu_kb"
  val keyField = "key"
  private var inited = false
  private var _GraphDb: EmbeddedGraphDatabase = _
  private var _nodeIndex: IndexedSeq[Node] = _

  private val defaultRelationType = new RelationType("defaultRelationType")

  def apply(): EmbeddedGraphDatabase = {
    if (!inited) {
      _GraphDb = new EmbeddedGraphDatabase(defaultFilename)
      ShutdownHook(_GraphDb.shutdown())
      //TODO: _nodeIndex = _GraphDb.index().forNodes( "nodes" );
      inited = true
    }

    _GraphDb
  }

  override def saveResource(resource:Resource, key:String):Boolean = {saveResource (resource, _GraphDb.getReferenceNode, key) }

  override def loadChild(key:String):Map[String,  String] = loadChild(_GraphDb.getReferenceNode, key)

  override def loadChildrenList():List[Map[String,  String]] = loadChildrenList(_GraphDb.getReferenceNode)

  override def loadChildrenMap():Map[String,  Map[String,  String]] = loadChildrenMap(_GraphDb.getReferenceNode)


  override def saveResource(child:Resource, parent:Resource, key:String):Boolean = {saveResource (child, getNodeByResource(parent), key)}

  override def loadChild(parent:Resource, key:String):Map[String,  String] = loadChild(getNodeByResource(parent), key)

  override def loadChildrenList(parent:Resource):List[Map[String,  String]] = loadChildrenList(getNodeByResource(parent))

  override def loadChildrenMap(parent:Resource):Map[String,  Map[String,  String]] = loadChildrenMap(getNodeByResource(parent))


  private def getNodeByResource( resource:Resource) : Node = {_GraphDb.getReferenceNode} //TODO - get by uri


  private def saveResource(child:Resource, parentNode:Node, key:String):Boolean = {
    var ok = false
    val tx:Transaction = N4JKB().beginTx();
    try
    {

      val childNode = N4JKB().createNode();
      for ((x, y) <- child.export)
          childNode.setProperty( x, y );
      val relationship = parentNode.createRelationshipTo( childNode , defaultRelationType );
      relationship.setProperty( "key", key );

      tx.success();
      ok = true
    }
    finally
    {
      tx.finish();
    }
    ok
  }

  private def loadChild(parent:Node, key:String):Map[String,  String] = Nil.toMap[String,  String]

  private def loadChildrenList(parent:Node):List[Map[String,  String]] = Nil

  private def loadChildrenMap(parent:Node):Map[String,  Map[String,  String]] ={
    var res = new HashMap[String,  Map[String,  String]]
    //TODO use кошерный синтаксис
    val i = parent.getRelationships.iterator()
    while (i.hasNext) //(x:Relationship <- parent.getRelationships.iterator())
    {
      val relationship:Relationship = i.next()
      var values = new HashMap[String,  String]
      val node:Node = relationship.getEndNode
      val j = node.getPropertyKeys.iterator()
      while(j.hasNext)
      {
        val key:String = j.next()
        values += key -> node.getProperty(key).toString
      }
      res += relationship.getProperty("key").toString -> values
    }
    res
  }



  private var goalIndex:Index[Node] = _GraphDb.index().forNodes( Goal.getClass.getName )

  def init(): Boolean =
  {
    //_GraphDb.getReferenceNode.createRelationshipTo(usersReferenceNode, RelTypes.USERS_REFERENCE );

    if (goalIndex.ensuring(true) != null)
    {
      addIndexedNode(Goal("ProcessIncident"), goalIndex)
    }

    true
  }

  def goals = {goalIndex.ensuring(true)}
    //List(Goal("ProcessIncident"), Goal("ClassifyIncident"), Goal("GetMostProbableAction"), Goal("SearchSolution"))

  private def addIndexedNode(resource:Resource,  index:Index[Node]):Option[Node] = addIndexedNode(resource, resource.uri.name, index)

  private def addIndexedNode(resource:Resource, key:String,  index:Index[Node]):Option[Node] =
  {
    val tx:Transaction = _GraphDb.beginTx()
    try{
      var node:Node = _GraphDb.createNode()
      node.setProperty("type", resource.getClass.getName)
      node.setProperty("name", resource.uri.name)
      index.add( node, "key", key );
      tx.success()
      return Option(node)
    }
    finally
    {tx.finish()}
    return None
  }

  def addIndexedNode(key: String): Node = {
    var node: Node = _GraphDb.createNode();
    node.setProperty(keyField, key);
    //TODO: _nodeIndex.add( node, keyField , key );
    node;
  }

}





/**
 * from https://issues.scala-lang.org/browse/SI-4200?page=com.atlassian.jira.plugin.system.issuetabpanels:all-tabpanel
 * by Christian Krause
 */

/**
 * A virtual machine shutdown hook.
 *
 * Registered shutdown hooks are executed when the virtual machine
 * terminates normally. For more information read the documentation
 * of Runtime. Constructor creates an unregistered instance
 * @param name the shutdown hook's name (for e.g. logging purposes)
 * @param body code to execute when the shutdown hook is executed
 */
final class ShutdownHook(
                          val name: String,
                          private[this] val body: => Unit) {

  def this(body: => Unit) = this("", body)

  private[this] val hook = new Thread(new Runnable() { def run { body } }, name)

  /** Registers this as a virtual machine shutdown hook. */
  def register() = {
    Runtime.getRuntime().addShutdownHook(hook)
    this
  }

  /** Deregisters this from the virtual machine. */
  def deregister() = Runtime.getRuntime.removeShutdownHook(hook)

  override def toString = "ShutdownHook("+name+")"
}

/** Factory for ShutdownHook instances. */
final object ShutdownHook {
  /** Creates a registered instance. */
  def apply(name: String, body: => Unit) = new ShutdownHook(name, body).register()

  /** Creates a registered instance. */
  def apply(body: => Unit) = new ShutdownHook("", body).register()
}
