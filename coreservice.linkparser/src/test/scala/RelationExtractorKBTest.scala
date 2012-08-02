/**
 * @author max talanov
 *         date 2012-07-29
 *         time: 10:21 AM
 */

import java.util
import org.slf4j.LoggerFactory
import relex.output.{StanfordView, RawView}
import scala.collection.JavaConversions._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import relex.entity.EntityMaintainer
import relex.feature.{FeatureNameFilter, FeatureNode}
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

    log info ("\n======\n")
    log info "Print rec"
    val node: FeatureNode = new FeatureNode()
    node.set("head", parse.getLeft.get("head"))
    node.set("background", parse.getLeft.get("background"))
    printRec(node.get("head"))
    log info ("\n======\n")

    /*
    val featureNames: java.util.Set[String] = node.getFeatureNames
    featureNames.map{
      fN: String => {
        try {
          val feature:FeatureNode = node.get(fN)
          log info "name=" + feature.get("name").getValue
          log info "tense= " + feature.get("tense").getValue
          log info "links=" + feature.get("links").toString(getZHeadsFilter)
          log info "fature name= " + fN
          log info "value=" + feature.get(fN)

        } catch {
          case e: RuntimeException => {
            log error e.getMessage
          }
        }
      }
    }
    */

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

  def printRec(feature: FeatureNode) {
    val leftWall = "LEFT-WALL"
    try {

      if (feature.get("orig_str") != null) log info "orig_str" + feature.get("orig_str").getValue

      if (feature.get("_subj") != null) {
        log info "_subj ("
        printRec(feature.get("_subj"))
        log info ")"
      }
      if (feature.get("_obj") != null) {
        log info "_obj=("
        printRec(feature.get("_obj"))
        log info ")"
      }
      if (feature.get("_iobj") != null) {
        log info "_iobj=("
        printRec(feature.get("_iobj"))
        log info ")"
      }
      if (feature.get("_advmod") != null) {
        log info "_advmod=("
        printRec(feature.get("_advmod"))
        log info ")"
      }

      if (feature.get("name") != null) log info "name=" + feature.get("name").getValue
      if (feature.get("tense") != null) log info "tense=" + feature.get("tense").getValue
      if (feature.get("PREP-FLAG") != null) log info "PREP-FLAG=" + feature.get("PREP-FLAG").getValue
      if (feature.get("pos") != null) log info "pos=" + feature.get("pos").getValue

      if (feature.get("links") != null) {
        // log info "links=" + feature.get("links").toString(getZHeadsFilter)
        log info "==> {"
        printRec(feature.get("links"))
        log info "}"
      }
      val next = feature.get("NEXT")
      if (next != null) {
        log info "=>"
        printRec(next)
      }

      // if (feature.get("head") != null) log info "head=" + feature.get("head").getValue
      // if (feature.get("background") != null) log info "background=" + feature.get("background").getValue
      // if (feature.get("words") != null) log info "words=" + feature.get("words").getValue
    } catch {
      case e: RuntimeException => {
        log error e.getMessage
      }
    }
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

  def getZHeadsFilter: FeatureNameFilter = {
    val ignores: util.HashSet[String] = new util.HashSet[String]
    ignores.add("nameSource")
    ignores.add("syn_location")
    ignores.add("SIG")
    ignores.add("linkR0")
    ignores.add("linkR1")
    ignores.add("linkR2")
    ignores.add("linkR3")
    ignores.add("linkR4")
    ignores.add("linkR5")
    ignores.add("linkR6")
    ignores.add("linkR7")
    ignores.add("linkL0")
    ignores.add("linkL1")
    ignores.add("linkL2")
    ignores.add("linkL3")
    ignores.add("linkL4")
    ignores.add("linkL5")
    ignores.add("linkL6")
    ignores.add("linkL7")
    ignores.add("first_verb")
    ignores.add("HEAD-FLAG")
    ignores.add("POS")
    ignores.add("head-word")
    ignores.add("morph")
    ignores.add("num")
    ignores.add("num_left_links")
    ignores.add("num_right_links")
    ignores.add("str")
    ignores.add("ref")
    ignores.add("subj")
    ignores.add("obj")
    ignores.add("iobj")
    ignores.add("this")
    ignores.add("wall")
    ignores.add("COMP-FLAG")
    ignores.add("VTAlg_flag")
    ignores.add("comparative-name")
    ignores.add("comparative-nameSource")
    ignores.add("comparative-obj")
    ignores.add("comparative-obj-name")
    ignores.add("comparative-obj-nameSource")
    ignores.add("comp-obj-copy")

    val order: util.ArrayList[String] = new util.ArrayList[String]
    order.add("_subj")
    order.add("_obj")
    order.add("_iobj")
    order.add("name")
    order.add("tense")
    order.add("PREP-FLAG")
    order.add("links")
    order.add("")
    order.add("head")
    order.add("background")
    order.add("words")
    new FeatureNameFilter(ignores, order)
  }
}
