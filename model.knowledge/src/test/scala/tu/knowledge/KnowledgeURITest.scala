package tu.knowledge

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.KnowledgeURI

/**
 * @author alex
 * Time stamp: 10/30/12 5:55 PM
 */
@RunWith(classOf[JUnitRunner])
class KnowledgeURITest extends FunSuite {

  test("Test KnowledgeURI decoding/encoding") {
    var uri  = KnowledgeURI("test1")

    var fromSer = KnowledgeURI(uri.toString,true)


    assert(uri.name==fromSer.name && uri.namespace==fromSer.namespace && uri.uid==fromSer.uid && uri.revision==fromSer.revision)
  }

  test("Test KnowledgeURI decoding/encoding with whitespaces") {
    var uri  = KnowledgeURI("test1 with whitespace")

    var fromSer = KnowledgeURI(uri.toString,true)


    assert(uri.name==fromSer.name && uri.namespace==fromSer.namespace && uri.uid==fromSer.uid && uri.revision==fromSer.revision)
  }
}
