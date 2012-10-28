/**
 * @author max
 *         date 2012-06-18
 *         time: 11:15 PM
 */

import collection.mutable.ListBuffer
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

@RunWith(classOf[JUnitRunner])
class TestDataGeneratorTest extends FunSuite {

  test("test list buffer") {
   var buf = new ListBuffer[String]()

    buf.append("test")

    buf.append("test2")

    assert(buf.contains("test"))
    assert(!buf.contains("tte"))
  }

}
