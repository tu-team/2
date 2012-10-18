/**
 * @author max
 *         Date: 8/2/12
 *         Time: 3:52 AM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.slf4j.LoggerFactory
import tu.coreservice.linkparser.LinkParser
import tu.coreservice.utilities.TestDataGenerator
import tu.model.knowledge.annotator.{AnnotatedPhrase, AnnotatedNarrative, AnnotatedSentence}
import tu.model.knowledge.communication.{ContextHelper, ShortTermMemory}
import tu.model.knowledge.frame.Frame
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.{KnowledgeURI, Resource}

@RunWith(classOf[JUnitRunner])
class LinkParserTest extends FunSuite {

  val log = LoggerFactory.getLogger(this.getClass)

  test("Ok") {
    assert(condition = true)
  }

  test("Lisk parser should be ok") {
    val lp = new LinkParser()
    val res = lp.apply(createContext)
    log info res.toString
    assert(res != null)
  }

  private def createContext: Context = {
    val testString = KnowledgeString("Please", "Please")
    val please: AnnotatedPhrase = AnnotatedPhrase("Please", TestDataGenerator.formOfPoliteness)
    val install: AnnotatedPhrase = AnnotatedPhrase("install", TestDataGenerator.installActionInst)
    val fireFox: AnnotatedPhrase = AnnotatedPhrase("FireFox", TestDataGenerator.firefoxConceptInst)
    val sentence: AnnotatedSentence = AnnotatedSentence("Please install FireFox.", List(please, install, fireFox))
    val narrative: AnnotatedNarrative = new AnnotatedNarrative(List[AnnotatedSentence](sentence), KnowledgeURI("TestNarrative"))
    val context = ContextHelper(List[Resource](), narrative, "TestContext")
    context
  }


}