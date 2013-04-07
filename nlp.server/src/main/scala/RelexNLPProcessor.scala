package tu.nlp.server

import org.slf4j.LoggerFactory
import relex.corpus.{DocSplitterFactory, DocSplitter}
import relex.entity.EntityMaintainer
import relex.output.OpenCogScheme
import relex.RelationExtractor
import tu.model.knowledge.annotator.AnnotatedSentence
import collection.JavaConversions._

/**
 *
 * @author Alexander Toschev
 *         Date: 12/1/12
 *         Time: 1:09 PM
 */
//TODO: use apache common pool
object RelexNLPProcessor extends NLPProcessor {
  val log = LoggerFactory.getLogger(this.getClass)
  val em: EntityMaintainer = new EntityMaintainer()

  val ds: DocSplitter = DocSplitterFactory.create()

  private val extractorDefault: RelationExtractorKB = {
    log.debug("SETUP Default extractor")
    // relex.RelationExtractor -n 4 -l -t -f -r -a
    val re = new RelationExtractorKB(false, List(), false)
    // -n 4
    re.setMaxParses(1)
    // -l -f -a
    val opencog: OpenCogScheme = new OpenCogScheme()
    opencog.setShowLinkage(true)
    opencog.setShowFrames(true)
    re.do_anaphora_resolution = true
    opencog.setShowAnaphora(true)
    // -t
    re.do_tree_markup = true
    re.do_pre_entity_tagging = true
    re.do_post_entity_tagging = true
    re
  }

  private val extractorWithCorrection: RelationExtractorKB = {
    log.debug("SETUP Correction extractor")
    val re = new RelationExtractorKB(false, List(), true)
    // -n 4
    re.setMaxParses(1)
    // -l -f -a
    val opencog: OpenCogScheme = new OpenCogScheme()
    opencog.setShowLinkage(true)
    opencog.setShowFrames(true)
    re.do_anaphora_resolution = true
    opencog.setShowAnaphora(true)
    // -t
    re.do_tree_markup = true
    re.do_pre_entity_tagging = true
    re.do_post_entity_tagging = true
    re
  }

  /**
   * Process sentence using default processing without correction
   * @param sent sentence string
   * @return relex.Sentence
   */
  def processSentence(sent: String) = {
    extractorDefault.synchronized {
      extractorDefault.processSentence(sent, em)
    }
  }

  /**
   * process sentence using corrections
   * @param sent
   * @param sentences
   * @return
   */
  def processSentence(sent: String, sentences: List[AnnotatedSentence]) = {
    extractorWithCorrection.synchronized {
      extractorWithCorrection.setSentences(sentences)
      extractorWithCorrection.processSentence(sent, em)
    }
  }

  /**
   * split sentences from text
   * @param txt Source text
   */
  def splitSentences(txt: String) = {
    ds.synchronized {
      ds.split(txt).toList
    }
  }


}
