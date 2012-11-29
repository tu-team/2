/**
 * @author max talanov
 *         Date: 8/2/12
 *         Time: 3:52 AM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.slf4j.LoggerFactory
import relex.entity.EntityMaintainer
import relex.parser.{LGParser, LocalLGParser}
import relex.Sentence
import tu.coreservice.linkparser.{RelationExtractorKB, LinkParser}
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
    assert(true)
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

  test("Link parser should be stable") {
    val lp: LocalLGParser = new LocalLGParser
    val src = "Browser is an object.";
    val dst = "(S (NP Browser) (VP is (NP an object)) .)\n"

    for (i <- 1 until 1000) {
      log.debug("i={}", i)
      val sntc: relex.Sentence = lp.parse(src)
      log.debug(("FOUND " + sntc.getParses.size + " sentence(s)"))

      if (sntc.getParses.size > 0) {
        val sentence: relex.ParsedSentence = sntc.getParses.get(0)
        log.debug("ParsedSentence.getLinkString():\n{}", sentence.getLinkString)
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

  test("Recreated LinkParser should be stable") {
    val src = "Browser is an object.";
    val dst = "(S (NP Browser) (VP is (NP an object)) .)\n"
    for (i <- 1 until 1000) {
      val lp: LocalLGParser = new LocalLGParser
      log.debug("i={}", i)
      val sntc: relex.Sentence = lp.parse(src)
      log.debug(("FOUND " + sntc.getParses.size + " sentence(s)"))
      if (sntc.getParses.size > 0) {
        val sentence: relex.ParsedSentence = sntc.getParses.get(0)
        log.debug("ParsedSentence.getLinkString():\n{}", sentence.getLinkString)
        log.debug(sentence.getPhraseString)
        expect(dst)(sentence.getPhraseString)
      }
      else {
        log.debug("No parse found for sentence")
        assert(false)
      }
      lp.close
    }
  }

  /*

  test("Lisk Grammer should be stable") {
    org.linkgrammar.LinkGrammar.init()
    org.linkgrammar.LinkGrammar.parse("Browser is an object")
    log.debug(org.linkgrammar.LinkGrammar.getConstituentString)
    org.linkgrammar.LinkGrammar.close()

  }
  */

  final val DEFAULT_MAX_SENTENCE_LENGTH: Int = 1024

  private def parseSentence(_sentence: String, entityMaintainer: EntityMaintainer, parser: LGParser): Sentence = {

    var sentence = _sentence
    if (entityMaintainer != null) {
      entityMaintainer.convertSentence(sentence, null)
      sentence = entityMaintainer.getConvertedSentence
    }
    if (sentence == null) return null
    val orig_sentence = entityMaintainer.getOriginalSentence
    var sent: Sentence = null
    if (sentence.length < DEFAULT_MAX_SENTENCE_LENGTH) {
      sent = parser.parse(sentence)
    }
    else {
      System.err.println("Sentence too long!: " + sentence)
      sent = new Sentence()
    }
    sent.setSentence(orig_sentence)
    sent
  }


  test("Repetitive sentence parse should be stable") {
    val src = "Browser is an object."
    val dst = "(S (NP Browser) (VP is (NP an object)) .)\n"
    val entityMaintainer = new EntityMaintainer()
    for (i <- 1 until 100) {
      val lp: LocalLGParser = new LocalLGParser
      log.debug("i={}", i)
      val sntc: relex.Sentence = parseSentence(src, entityMaintainer, lp)
      log.debug(("FOUND " + sntc.getParses.size + " sentence(s)"))
      if (sntc.getParses.size > 0) {
        val sentence: relex.ParsedSentence = sntc.getParses.get(0)
        log.debug("ParsedSentence.getLinkString():\n{}", sentence.getLinkString)
        log info ("relexSentence={}", sentence)
        log.debug("sentence.getPhraseString()={}", sentence.getPhraseString)
        expect(dst)(sentence.getPhraseString)
        //todo check subject-object
      }
      else {
        log.debug("No parse found for sentence")
        assert(false)
      }
      lp.close()
    }
  }

  test("Test stable with RelationExtractorKB") {
    val src = "Browser is an object."
    val dst = "(S (NP Browser) (VP is (NP an object)) .)\n"

    var reKB = new RelationExtractorKB(false,List[AnnotatedSentence ](AnnotatedSentence(src,null)))

    for (i <- 1 until 100) {


      val sntc: relex.Sentence = reKB.processSentence(src)
      log.debug(("FOUND " + sntc.getParses.size + " sentence(s)"))
      if (sntc.getParses.size > 0) {
        val sentence: relex.ParsedSentence = sntc.getParses.get(0)
        log.debug("ParsedSentence.getLinkString():\n{}", sentence.getLinkString)
        log info ("relexSentence={}", sentence)
        log.debug("sentence.getPhraseString()={}", sentence.getPhraseString)
        expect(dst)(sentence.getPhraseString)
        //todo check subject-object
      }
      else {
        log.debug("No parse found for sentence")
        assert(false)
      }

    }
  }
}


