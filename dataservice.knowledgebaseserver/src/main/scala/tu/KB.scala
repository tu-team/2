package tu

import org.neo4j.kernel.EmbeddedGraphDatabase
import org.neo4j.graphdb.{Node, GraphDatabaseService}

/**
 * @author ${user.name}
 */
object KB {
  
  def foo(x : Array[String]) = x.foldLeft("")((a,b) => a + b)
  
  def main(args : Array[String]) {
    println( "Hello World!" )
    println("concat arguments = " + foo(args))
  }

}


object N4JKB {
  val defaultFilename = java.lang.System.getProperty("user.home") + "/mentakb"
  val keyField = "key"
  private var inited = false
  private var _GraphDb:EmbeddedGraphDatabase = _;
  private var _nodeIndex:IndexedSeq[Node] = _

  def apply():EmbeddedGraphDatabase =
  {
    if (!inited)
    {
      _GraphDb = new EmbeddedGraphDatabase( defaultFilename )
      ShutdownHook(_GraphDb.shutdown())
      //TODO: _nodeIndex = _GraphDb.index().forNodes( "nodes" );
      inited = true
    }

    _GraphDb
  }

  def addIndexedNode(key:String):Node =
  {
    var node:Node = _GraphDb.createNode();
    node.setProperty(keyField , key );
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
 * of [[Runtime]].
 *
 * @constructor creates an unregistered instance
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

/** Factory for [[ShutdownHook]] instances. */
final object ShutdownHook {
  /** Creates a registered instance. */
  def apply(name: String, body: => Unit) = new ShutdownHook(name, body).register()

  /** Creates a registered instance. */
  def apply(body: => Unit) = new ShutdownHook("", body).register()
}
