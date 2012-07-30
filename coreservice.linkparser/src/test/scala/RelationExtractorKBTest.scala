/**
 * @author max talanov
 *         date 2012-07-29
 *         time: 10:21 AM
 */

import org.slf4j.LoggerFactory
import relex.output.{StanfordView, RawView}
import scala.collection.JavaConversions._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import relex.entity.EntityMaintainer
import relex.feature.FeatureNode
import tu.coreservice.linkparser.RelationExtractorKB
import tu.coreservice.utilities.TestDataGenerator
import tu.model.knowledge.annotator.{AnnotatedSentence, AnnotatedPhrase}
import tu.model.knowledge.communication.{Context, ContextHelper}
import tu.model.knowledge.frame.Frame
import tu.model.knowledge.{Resource, KnowledgeURI}
import tu.model.knowledge.primitive.KnowledgeString

@RunWith(classOf[JUnitRunner])
class RelationExtractorKBTest extends FunSuite {

  val log = LoggerFactory.getLogger(this.getClass)

  val sentence = "Please install Firefox"

  test("test Ok") {
    assert(condition = true)
  }

  test("RelationExtractorKB run must be ok") {
    //run relex and extract sentences
    val em: EntityMaintainer = new EntityMaintainer()
    val relExt = setup
    val relexSentence = relExt.processSentence(sentence, em)
    val tree = relexSentence.getParses.get(0).getPhraseTree
    val parse = relexSentence.getParses.get(0)
    //extract all sentences
    log info ("\n====\n")
    log info ("Dependency graph:\n")
    log info ("\n" + RawView.printZHeads(parse.getLeft()))
    log info ("\n======\n")
    log info ("Link string:\n")
    log info ("\n" + parse.getLinkString())
    log info ("\n======\n")
    log info ("Relations :\n")
    log info StanfordView.printRelations(parse, relExt.do_penn_tagging)
    log info ("\n======\n")
    log info ("Phrase string:\n")
    log info ("\n" + parse.getPhraseString())

    val testString = "[head $0[name <<install>>\n         tense <<imperative>>\n         links [_obj [name <<Firefox>>\n                      definite-FLAG <<T>>\n                      noun_number <<singular>>\n                      pos <<noun>>]\n                _advmod [name <<please>>\n                         inflection-TAG <<.e>>\n                         pos <<adv>>]]\n         inflection-TAG <<.v>>\n         pos <<verb>>]\n background $0]"
    expect(testString)(RawView.printZHeads(parse.getLeft()))

    val node = parse.getLeft
    val featureNames: java.util.Set[String] = node.getFeatureNames
    featureNames.map{
      fN: String => {
        val feature:FeatureNode = node.get(fN)
        log info fN
        log info feature.toString
      }
    }

    val phrases: List[AnnotatedPhrase] = tree.iterator().map(
      u => {
        val wordList = u.getWordList
        val phrs: List[AnnotatedPhrase] = wordList.map {
          w: FeatureNode => {
            //append word
            val phrase: AnnotatedPhrase = AnnotatedPhrase(w.get("orig_str").getValue)
            phrase
          }
        }.toList
        phrs
      }
    ).toList.flatten
    log info phrases.toString()
  }

  private def setup: RelationExtractorKB = {
    val re = new RelationExtractorKB(false, createContext)
    // -n 4
    re.setMaxParses(1)
    re.do_anaphora_resolution = true
    // -t
    re.do_tree_markup = true
    re.do_pre_entity_tagging = true
    re.do_post_entity_tagging = true
    re
  }

  private def createContext: Context = {
    val testString = KnowledgeString("Please", "Please")
    val phrase: AnnotatedPhrase = AnnotatedPhrase("Please", TestDataGenerator.formOfPoliteness)
    val sentence: AnnotatedSentence = AnnotatedSentence(List(phrase))
    val frame = Frame(Map[KnowledgeURI, Resource](sentence.uri -> sentence), KnowledgeURI("TestFrame"))
    val context = ContextHelper(List[Resource](), frame, "TestContext")
    context
  }
}
