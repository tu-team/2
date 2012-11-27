/**
 * @author max
 *         Date: 8/2/12
 *         Time: 3:52 AM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.slf4j.LoggerFactory
import relex.parser.LocalLGParser
import tu.coreservice.linkparser.LinkParser
import tu.coreservice.utilities.TestDataGenerator
import tu.model.knowledge.annotator.{AnnotatedPhrase, AnnotatedNarrative, AnnotatedSentence}
import tu.model.knowledge.communication.{ContextHelper, ShortTermMemory}
import tu.model.knowledge.frame.Frame
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.{KnowledgeURI, Resource}
import org.linkgrammar.LinkGrammar

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

  private def createContext: ShortTermMemory = {
    val testString = KnowledgeString("Please", "Please")
    val please: AnnotatedPhrase = AnnotatedPhrase("Please", TestDataGenerator.formOfPoliteness)
    val install: AnnotatedPhrase = AnnotatedPhrase("install", TestDataGenerator.installActionInst)
    val fireFox: AnnotatedPhrase = AnnotatedPhrase("FireFox", TestDataGenerator.firefoxConceptInst)
    val sentence: AnnotatedSentence = AnnotatedSentence("Please install FireFox.", List(please, install, fireFox))
    val narrative: AnnotatedNarrative = new AnnotatedNarrative(List[AnnotatedSentence](sentence), KnowledgeURI("TestNarrative"))
    val context = ContextHelper(List[Resource](), narrative, "TestContext")
    context
  }

  test("Lisk parser should be stable") {
    val lp: LocalLGParser = new LocalLGParser
    val src = "Browser is an object.";
    val dst = "(S (NP Browser) (VP is (NP an object)) .)\n"

    for (i <- 1 until 1000)
    {
      log.debug("i={}", i)
      val sntc:relex.Sentence = lp.parse(src)
      log.debug(("FOUND " + sntc.getParses.size + " sentence(s)"))

      if (sntc.getParses.size > 0) {
        val sentence: relex.ParsedSentence = sntc.getParses.get(0)
        log.debug("ParsedSentence.getLinkString():\n" + sentence.getLinkString)
        log.debug(sentence.getPhraseString)
        expect(dst)(sentence.getPhraseString)
      }
      else {
        log.debug("No parse found for sentence")
        assert(false)
      }
    }

    lp.close
  }
  /*

  test("Lisk Grammer should be stable") {
    org.linkgrammar.LinkGrammar.init()
    org.linkgrammar.LinkGrammar.parse("Browser is an object")
    log.debug(org.linkgrammar.LinkGrammar.getConstituentString)
    org.linkgrammar.LinkGrammar.close()

  }
  */


}


