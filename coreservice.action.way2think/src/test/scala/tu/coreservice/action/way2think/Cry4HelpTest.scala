package tu.coreservice.action.way2think

import cry4help.Cry4HelpWay2Think
import org.scalatest.FunSuite
import org.scalatest.Assertions._
import tu.model.knowledge.domain.Concept
import tu.model.knowledge.{Probability, KnowledgeURI, Resource}
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.selector.SelectorRequest
import tu.model.knowledge.communication.{ShortTermMemory, ContextHelper}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import tu.coreservice.utilities.URIHelper

/**.
 * @author alexander toshchev
 *         Date: 30.06.12
 *         Time: 12:05
 */

@RunWith(classOf[JUnitRunner])
class Cry4HelpTest extends FunSuite {

  test("Read response from user") {
    var cry4help = new Cry4HelpWay2Think()
    var ctx = ShortTermMemory(Map[KnowledgeURI,Resource](),new KnowledgeURI("none","none","None"))

    ctx.lastError = new Error("Simple error occured")

    //TODO: create fake console input
    //cry4help.apply(ctx)
  }



}