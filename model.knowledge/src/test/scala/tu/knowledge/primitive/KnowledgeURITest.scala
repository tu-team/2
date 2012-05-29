package tu.knowledge.primitive

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.KnowledgeURI

/**
 * @author max
 *         date 2012-05-01
 *         time: 7:35 PM
 */

@RunWith(classOf[JUnitRunner])
class KnowledgeURITest extends FunSuite {

  val namespace = "testNamespace"
  val name = "name"
  val revision = "rev"

  test("test Ok") {
    assert(true)
  }

  test("KnowledgeURI contain namespace, name, revision") {
    val uri = new KnowledgeURI(namespace, name, revision)
    assert(uri.namespace == namespace)
    assert(uri.name == name)
    assert(uri.revision() == revision)
  }

}
