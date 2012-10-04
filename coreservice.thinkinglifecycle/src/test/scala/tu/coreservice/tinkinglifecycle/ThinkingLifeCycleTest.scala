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
import tu.model.knowledge.communication.{TrainingRequest, Request}
import tu.model.knowledge.{Constant, KnowledgeURI}
import tu.coreservice.thinkinglifecycle.ThinkingLifeCycleMinimal
import tu.model.knowledge.primitive.KnowledgeString

@RunWith(classOf[JUnitRunner])
class ThinkingLifeCycleTest extends FunSuite {

  val log = LoggerFactory.getLogger(this.getClass)

  test("test Ok") {
    assert(true)
  }

  test("tense is kind of concept.") {
    val requestText = "Tense is kind of concept."
    val r = new TrainingRequest(KnowledgeString(requestText, Constant.INPUT_TEXT), KnowledgeURI("testTrainingRequest"))
    val t = new ThinkingLifeCycleMinimal()
    val res = t.apply(r)
    assert(res != null)
    log info res.toString
  }

  test("run comelete lifecycle with dummy way2think") {
    val requestText = "Please install Firefox."
    val r = new Request(KnowledgeString(requestText, "inputtext"), KnowledgeURI("testRequest"))
    val t = new ThinkingLifeCycleMinimal()
    val res = t(r)
    assert(res != null)
    log info res.toString
  }

  test("Please install Firefox case.") {
    val requestText = "Please install Firefox."
    val r = new Request(KnowledgeString(requestText, "inputtext"), KnowledgeURI("testRequest"))
    val t = new ThinkingLifeCycleMinimal()
    val res = t(r)
    assert(res != null)
    log info res.toString
  }

  test("User miss Internet Explorer 8.") {
    val requestText = "User miss Internet Explorer 8."
    val r = new Request(KnowledgeString(requestText, "inputtext"), KnowledgeURI("testRequest"))
    val t = new ThinkingLifeCycleMinimal()
    val res = t(r)
    assert(res != null)
    log info res.toString
  }

  test("Training Chrome is a browser") {
    val requestText = "Chrome is a browser."
    val r = new TrainingRequest(KnowledgeString(requestText, Constant.INPUT_TEXT), KnowledgeURI("testTrainingRequest"))
    val t = new ThinkingLifeCycleMinimal()
    val res = t.apply(r)
    assert(res != null)
    log info res.toString
  }

  test("Training please is a form of politeness") {
    val requestText = "Please is a form of politeness."
    val r = new TrainingRequest(KnowledgeString(requestText, Constant.INPUT_TEXT), KnowledgeURI("testTrainingRequest"))
    val t = new ThinkingLifeCycleMinimal()
    val res = t.apply(r)
    assert(res != null)
    log info res.toString
  }



  def testCycle(input: String) {
    val requestText = input
    val r = new Request(KnowledgeString(requestText, "inputtext"), KnowledgeURI("testRequest"))
    val t = new ThinkingLifeCycleMinimal()
    val res = t(r)
    assert(res != null)
    log info res.toString
  }

  //TODO
  test("User needs document portal uppdate") {
    testCycle("User needs document portal uppdate")
  }

  //TODO
  test("Add new alias") {
    testCycle("Add new alias Host name on host that alias is wanted to: hrportal.lalala.biz IP adress on host that alias is wanted to: 322.223.333.22 Wanted Alias:    webadviser.lalala.net")
  }
  //TODO
  test("Outlook Web Access '") {
    testCycle("Outlook Web Access (CCC) - 403 - Forbidden: Access is denied'C:\\WINDOWS\\system32\\LALA\\Cache\\A0000B72.1.System\\install.vbs'")
  }
  //TODO
  test("PP2C - Cisco IP communicator.") {
    testCycle("PP2C - Cisco IP communicator. Please see if you can fix the problem with the ip phone, it's stuck on configuring ip + sometimes Server error rejected: Security etc.")
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
