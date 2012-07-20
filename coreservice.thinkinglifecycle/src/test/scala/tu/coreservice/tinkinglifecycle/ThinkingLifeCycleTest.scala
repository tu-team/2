package tu.coreservice.tinkinglifecycle

/**
 * @author max talanov
 *         date 2012-07-09
 *         time: 10:19 AM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.slf4j.LoggerFactory
import scala.actors.Actor
import tu.model.knowledge.communication.Request
import tu.model.knowledge.KnowledgeURI
import tu.coreservice.thinkinglifecycle.ThinkingLifeCycleMinimal
import tu.model.knowledge.primitive.KnowledgeString

@RunWith(classOf[JUnitRunner])
class ThinkingLifeCycleTest extends FunSuite {

  val log = LoggerFactory.getLogger(this.getClass)

  test("test Ok") {
    assert(condition = true)
  }

  test("run comelete lifecycle with dummy way2think") {
    val requestText = "Please install Firefox"
    val r = new Request(KnowledgeString(requestText, "inputtext"), KnowledgeURI("testRequest"))
    val t = new ThinkingLifeCycleMinimal()
    val res = t(r)
    assert(res != null)
    log info res.toString
  }

  test("Please install Firefox case.") {
    val requestText = "Please install Firefox"
    val r = new Request(KnowledgeString(requestText, "inputtext"), KnowledgeURI("testRequest"))
    val t = new ThinkingLifeCycleMinimal()
    val res = t(r)
    assert(res != null)
    log info res.toString
  }

  test("start 5 paralel actors and join") {

    log.info("test started")

    Accumulator.start
    for (i <- (1 to 5)) {
      Accumulator ! Accumulate(i)
    }

    Accumulator !? Total match {
      case result: Int => log.info("result = {}", result.toString)
    }

    log.info("test completed")
  }

  test("run tree actons in parallel") {
    log.info("test started")
    val a1 = new A1()
    val a2 = new A2()
    val a3 = new A3()
    a1.start()
    a2.start()
    a3.start()

    a1 ! Accumulate(1)
    a2 ! Accumulate(2)
    a3 ! Accumulate(3)

    // closing accumulators
    a1 !? Total match {
      case result: String => println(result)
    }
    // closing accumulators
    a3 !? Total match {
      case result: String => println(result)
    }
    // closing accumulators
    a2 !? Total match {
      case result: String => println(result)
    }
  }

  class A extends Actor {

    val name = "A"

    def act() {
      log info("{} started", name)
      var sum = 0
      loop {
        react {
          case Accumulate(n) => {
            for (i <- (1 to 10)) {
              log info("count {} {}", name, i)
              sum = i
            }
          }
          case Total => reply(name + "done = " + sum); exit()
        }
      }
    }
  }

  class A1 extends A {
    override val name = "A1"
  }

  class A2 extends A {
    override val name = "A2"
  }

  class A3 extends A {
    override val name = "A3"
  }

  // case classes for immutable messages
  case class Accumulate(amount: Int)

  case class Reset

  case class Total

  // actor definition
  object Accumulator extends Actor {
    def act = {
      log info "Acummulatior started"
      var sum = 0
      loop {
        react {
          case Accumulate(n) => {
            sum += n
            log info("sum {} {}", sum, n)
          }
          case Reset => sum = 0
          case Total => reply(sum); exit
        }
      }
    }
  }

  // application
  object Accumulators extends Application {
    Accumulator.start
    for (i <- (1 to 100)) {
      Accumulator ! Accumulate(i)
    }

    Accumulator !? Total match {
      case result: Int => println(result)
    }
  }


}
