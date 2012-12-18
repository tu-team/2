import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.slf4j.LoggerFactory
import tu.extensions.Extensions
import tu.extensions.interfaces.Generalizer

/**
 * @author alex
 * Time stamp: 12/10/12 12:01 PM
 */
@RunWith(classOf[JUnitRunner])
class ExtensionsTest extends FunSuite {

  val log = LoggerFactory.getLogger(this.getClass)

  test("Load sample Generalizer") {
    assert(Extensions.load[Generalizer]!=null )
  }


}