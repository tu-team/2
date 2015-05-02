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
import tu.model.knowledge.communication.{ShortTermMemory, TrainingRequest, Request}
import tu.coreservice.thinkinglifecycle.ThinkingLifeCycleMinimal
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.domain.{Concept, ConceptNetwork}
import tu.model.knowledge.{Constant, KnowledgeURI}
import tu.dataservice.memory.LongTermMemory

@RunWith(classOf[JUnitRunner])
class ThinkingLifeCycleTest extends FunSuite {

  val log = LoggerFactory.getLogger(this.getClass)

  test("test Ok") {
    assert(true)
  }

  test("tense is kind of concept.") {
    val requestText = "Tense is kind of concept."
    val r = new TrainingRequest(KnowledgeString(requestText, Constant.INPUT_TEXT), KnowledgeURI("testTrainingRequest"), KnowledgeURI(Constant.domainName))
    val t = new ThinkingLifeCycleMinimal()
    val res = t.apply(r)
    assert(res != null)
    log debug res.toString
  }

  test("run complete lifecycle with dummy way2think") {
    val requestText = "Please install Firefox."
    val r = new Request(KnowledgeString(requestText, "inputtext"), KnowledgeURI("testRequest"), KnowledgeURI(Constant.domainName))
    val t = new ThinkingLifeCycleMinimal()
    val res = t(r)
    assert(res != null)
    log debug res.toString
  }


  /*test("Please install Firefox Cry4Help.") {
    val requestText = "Please install Firefox."
    val r = new Request(KnowledgeString(requestText, "inputtext"), KnowledgeURI("testRequest"), KnowledgeURI(Constant.domainName))
    val t = new ThinkingLifeCycleMinimal()
    val res = t(r)
    assert(res != null)
    log debug res.toString
  }*/

  test("Please install Firefox case.") {
    // training
    BrowserTraining
    InstallTraining
    PleaseTraining
    FirefoxTraining

    log info ("Training is over")

    val requestText = "Please install Firefox."
    val r = new Request(KnowledgeString(requestText, "inputtext"), KnowledgeURI("testRequest"), KnowledgeURI(Constant.domainName))
    val t = new ThinkingLifeCycleMinimal()
    val res = t(r)
    assert(res != null)
    log debug res.toString
  }

  private def BrowserTraining {
    val requestText = "Browser is an object."
    val r = new TrainingRequest(KnowledgeString(requestText, Constant.INPUT_TEXT), KnowledgeURI("browserTrainingRequest"), KnowledgeURI(Constant.domainName))
    val t = new ThinkingLifeCycleMinimal()
    val res = t.apply(r)
    res
  }

  private def FirefoxTraining {
    val requestText = "Firefox is a browser."
    val r = new TrainingRequest(KnowledgeString(requestText, Constant.INPUT_TEXT), KnowledgeURI("firefoxTrainingRequest"), KnowledgeURI(Constant.domainName))
    val t = new ThinkingLifeCycleMinimal()
    val res = t.apply(r)
    res
  }

  private def InstallTraining: ShortTermMemory = {
    val requestText = "Install is an action."
    val r = new TrainingRequest(KnowledgeString(requestText, Constant.INPUT_TEXT), KnowledgeURI("installTrainingRequest"), KnowledgeURI(Constant.domainName))
    val t = new ThinkingLifeCycleMinimal()
    val res = t.apply(r)
    res
  }

  private def PleaseTraining {
    val requestText = "Please is a form of politeness."
    val r = new TrainingRequest(KnowledgeString(requestText, Constant.INPUT_TEXT), KnowledgeURI("actionTrainingRequest"), KnowledgeURI(Constant.domainName))
    val t = new ThinkingLifeCycleMinimal()
    val res = t.apply(r)
    res
  }

  test("User miss Internet Explorer 8.") {
    val requestText = "User miss Internet Explorer 8."
    val r = new Request(KnowledgeString(requestText, "inputtext"), KnowledgeURI("testRequest"), KnowledgeURI(Constant.domainName))
    val t = new ThinkingLifeCycleMinimal()
    val res = t(r)
    assert(res != null)
    log debug res.toString
  }

  test("Training Chrome is a browser") {
    val requestText = "Chrome is a browser."
    val r = new TrainingRequest(KnowledgeString(requestText, Constant.INPUT_TEXT), KnowledgeURI("testTrainingRequest"), KnowledgeURI(Constant.domainName))
    val t = new ThinkingLifeCycleMinimal()
    val res = t.apply(r)
    assert(res != null)
    log debug res.toString
  }

  test("List all phrases from KBServer"){
    val dmModel = LongTermMemory.domainModel(KnowledgeURI(Constant.defaultDomainName))
    log debug  "Concept with phrases"
    dmModel.nodes.foreach(m=>{
       log.debug( m.phrases.frames.map(p => p._2).toString() )
    })

    log debug  "All concepts"
    dmModel.nodes.foreach(m=>{
      log.debug( m.toString() )
    })

  }

  test("Training please is a form of politeness") {
    val requestText = "Please is a form of politeness."
    val r = new TrainingRequest(KnowledgeString(requestText, Constant.INPUT_TEXT), KnowledgeURI("testTrainingRequest"), KnowledgeURI(Constant.domainName))
    val t = new ThinkingLifeCycleMinimal()
    val res = t.apply(r)
    assert(res != null)
    assert(res.notUnderstoodConcepts == Nil)
    assert(res.notUnderstoodPhrases == Nil)
    assert(res.simulationModel match {
      case Some(x: ConceptNetwork)
      => {
        x.nodes.find((k: Concept) => k.toString.startsWith("please")) match {
          case None => false
          case Some(c: Concept) => true
        }
      }
      case None =>
        false
    }
    )
    log debug res.toString
  }

  test("Training: Browser is an object") {
    val requestText = "Browser is an object."
    val r = new TrainingRequest(KnowledgeString(requestText, Constant.INPUT_TEXT), KnowledgeURI("testTrainingRequest"), KnowledgeURI(Constant.domainName))
    val t = new ThinkingLifeCycleMinimal()
    val res = t.apply(r)
    assert(res != null)
    assert(res.notUnderstoodConcepts == Nil)
    assert(res.notUnderstoodPhrases == Nil)
    assert(res.simulationModel match {
      case Some(x: ConceptNetwork)
      => {
        x.nodes.find((k: Concept) => k.toString.startsWith("Browser")) match {
          case None => false
          case Some(c: Concept) => true
        }
      }
      case None =>
        false
    }
    )
    log debug res.toString
  }

  test("Training: install is an action") {
    val res = InstallTraining
    assert(res != null)
    assert(res.notUnderstoodConcepts == Nil)
    assert(res.notUnderstoodPhrases == Nil)
    assert(res.simulationModel match {
      case Some(x: ConceptNetwork)
      => {
        x.nodes.find((k: Concept) => k.toString.startsWith("install")) match {
          case None => false
          case Some(c: Concept) => true
        }
      }
      case None =>
        false
    }
    )
    log debug res.toString
  }

  def testCycle(input: String) {
    val requestText = input
    val r = new Request(KnowledgeString(requestText, "inputtext"), KnowledgeURI("testRequest"), KnowledgeURI(Constant.domainName))
    val t = new ThinkingLifeCycleMinimal()
    val res = t(r)
    assert(res != null)
    log debug res.toString
  }

  //TODO
  /* test("User needs document portal update") {
    testCycle("User needs document portal update")
  }*/

  //TODO
  /*test("Add new alias") {
    testCycle("Add new alias Host name on host that alias is wanted to: hrportal.lalala.biz IP adress on host that alias is wanted to: 322.223.333.22 Wanted Alias:    webadviser.lalala.net")
  }*/

  //TODO
  /* test("Outlook Web Access '") {
    testCycle("Outlook Web Access (CCC) - 403 - Forbidden: Access is denied'C:\\WINDOWS\\system32\\LALA\\Cache\\A0000B72.1.System\\install.vbs'")
  }*/

  //TODO
  /*test("PP2C - Cisco IP communicator.") {
    testCycle("PP2C - Cisco IP communicator. Please see if you can fix the problem with the ip phone, it's stuck on configuring ip + sometimes Server error rejected: Security etc.")
  }*/




}
