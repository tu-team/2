package tu.coreservice.action.way2think.cry4help

import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.{KnowledgeURI, Resource}
import tu.model.knowledge.primitive.KnowledgeString
import tu.exception.UnexpectedException
import org.slf4j.LoggerFactory

/**
 * @author max talanov
 *         date 2012-06-25
 *         time: 4:25 PM
 */

class Cry4HelpWay2Think(var _inputContext: Context, _uri: KnowledgeURI)
  extends Way2Think(_uri) {

  val log = LoggerFactory.getLogger(this.getClass)

  def this() = {
    this(ContextHelper.apply(List[Resource](KnowledgeString("", "Cry4HelpMessage")), ""), KnowledgeURI("Cry4HelpMessage"))
  }

  /**
   * Way2Think interface.
   * @param inputContext Context of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: Context): Context = {
    this._inputContext = inputContext
    //TODO get message from last error
    inputContext.lastError match {
      case Some(error: Error) => {
        //TODO run console adapter
        inputContext
      }
      case None => {
        log error "$LastError_is_empty"
        throw new UnexpectedException("$LastError_is_empty")
      }
    }
  }

  def start() = false

  def stop() = false

  override def toString(): String = {
    this.getClass.getName + ":" + _inputContext.toString()
  }
}

object Cry4HelpWay2Think {
  def apply(message: String): Cry4HelpWay2Think = {
    val res = List[Resource](KnowledgeString(message, "Cry4HelpMessage"))
    val context = ContextHelper.apply(res, message)
    val cry4helpWay2Think = new Cry4HelpWay2Think()
    cry4helpWay2Think.apply(context)
    cry4helpWay2Think
  }
}
