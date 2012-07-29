/**
 * @author max
 *         date 2012-07-29
 *         time: 10:21 AM
 */

import scala.collection.JavaConversions._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import relex.entity.EntityMaintainer
import relex.feature.FeatureNode
import tu.coreservice.linkparser.RelationExtractorKB
import tu.model.knowledge.annotator.AnnotatedPhrase

@RunWith(classOf[JUnitRunner])
class RelationExtractorKBTest extends FunSuite {

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
    //extract all sentences
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
  }

  private def setup: RelationExtractorKB = {
    val re = new RelationExtractorKB(false)
    // -n 4
    re.setMaxParses(1)
    re.do_anaphora_resolution = true
    // -t
    re.do_tree_markup = true
    re.do_pre_entity_tagging = true
    re.do_post_entity_tagging = true
    re
  }
}
