/**
 * @author max
 *         Date: 8/2/12
 *         Time: 3:52 AM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.coreservice.linkparser.LinkParser
import tu.coreservice.utilities.TestDataGenerator
import tu.model.knowledge.annotator.{AnnotatedPhrase, AnnotatedNarrative, AnnotatedSentence}
import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.frame.Frame
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.{KnowledgeURI, Resource}

@RunWith(classOf[JUnitRunner])
class LinkParserTest extends FunSuite {

  test("Ok") {
    assert(condition = true)
  }

  test("Lisk parser should be ok") {
    val lp = new LinkParser()
    lp.apply(createContext)
  }

  private def createContext: Context = {
    val testString = KnowledgeString("Please", "Please")
    val phrase: AnnotatedPhrase = AnnotatedPhrase("Please", TestDataGenerator.formOfPoliteness)
    val sentence: AnnotatedSentence = AnnotatedSentence(List(phrase))
    val narrative: AnnotatedNarrative = new AnnotatedNarrative(List[AnnotatedSentence](sentence), KnowledgeURI("TestNarrative"))
    val frame = Frame(Map[KnowledgeURI, Resource](narrative.uri -> narrative), KnowledgeURI("TestFrame"))
    val context = ContextHelper(List[Resource](), frame, "TestContext")
    context
  }


}