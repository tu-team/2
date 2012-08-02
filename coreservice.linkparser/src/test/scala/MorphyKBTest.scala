/**
 * @author max talanov
 * Date: 7/27/12
 * Time: 1:46 AM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.slf4j.LoggerFactory
import tu.coreservice.linkparser.MorphyKB
import tu.coreservice.utilities.TestDataGenerator
import tu.model.knowledge.annotator.{AnnotatedSentence, AnnotatedPhrase}
import tu.model.knowledge.communication.ContextHelper
import tu.model.knowledge.frame.Frame
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.{KnowledgeURI, Resource}

@RunWith(classOf[JUnitRunner])
class MorphyKBTest extends FunSuite {

  val log = LoggerFactory.getLogger(this.getClass)

  test("Ok") {
    assert(condition = true)
  }

  test("Run MorphyKB") {
    val testString = KnowledgeString("Please", "Please")
    val phrase: AnnotatedPhrase = AnnotatedPhrase("Please", TestDataGenerator.formOfPoliteness)
    val sentence: AnnotatedSentence = AnnotatedSentence(List(phrase))
    val frame = Frame(Map[KnowledgeURI, Resource](sentence.uri -> sentence), KnowledgeURI("TestFrame"))
    val context = ContextHelper(List[Resource](), frame, "TestContext")
    val morphy = new MorphyKB(List(sentence))
    val res = morphy.morph("Please")
    log info (res.getFeatures.toString)
    assert(res != null)
    expect("adv")(res.getFeatures.get("adv").get("type").getValue())
  }
}