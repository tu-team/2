/**
 * @author max talanov
 * Date: 7/27/12
 * Time: 1:46 AM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.coreservice.linkparser.MorphyKB
import tu.model.knowledge.communication.ContextHelper
import tu.model.knowledge.frame.Frame
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.{KnowledgeURI, Resource}

@RunWith(classOf[JUnitRunner])
class MorphyKBTest extends FunSuite {
  test("Ok") {
    assert(condition = true)
  }

  test("Run MorphyKB") {
    val testString = KnowledgeString("Please", "Please")
    val frame = Frame(Map[KnowledgeURI, Resource](testString.uri -> testString), KnowledgeURI("TestFrame"))
    val context = ContextHelper(List[Resource](), frame, "TestContext")
    val morphy = new MorphyKB(context)
    val res = morphy.morph("Please")
  }
}