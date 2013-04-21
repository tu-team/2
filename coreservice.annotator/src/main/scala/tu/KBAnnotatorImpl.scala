package tu.coreservice.annotator

import org.slf4j.LoggerFactory
import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.communication.{ContextHelper, ShortTermMemory}
import tu.providers.AnnotatorRegistry
import tu.model.knowledge.annotator.{AnnotatedNarrative, AnnotatedPhrase}
import tu.coreservice.utilities.URIHelper
import tu.model.knowledge.domain.Concept

/**
 * Simple KBAnnotator implementation.
 * @author toschev alex, talanov max
 *         Date: 15.06.12
 *         Time: 18:26
 */

class KBAnnotatorImpl extends Way2Think {

  val log = LoggerFactory.getLogger(this.getClass)

  /**
   * Annotate split Sentence with KB Concepts.
   * @param inputContext ShortTermMemory of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: ShortTermMemory): ShortTermMemory = {
    //we have output context from splitter
    //splitter return annotated sentences array

    val sentences = inputContext.frames.filter(p => p._1.name.contains(URIHelper.splitterMark()))
    val extractedNarratives = sentences.map(b => b._2.asInstanceOf[AnnotatedNarrative])
    //trying to annotate sentences
    //val localAnnotator = AnnotatorRegistry.getLocalAnnotator()

    /**
     * Check KB for the phrase concept.
     * @param phrase to search.
     * @return Concept of the phrase.
     */
    def checkLocalKB(phrase: String): Option[Concept] = {
      //check in local context
      //check content and specialisation
      val dmModel = inputContext.domainModel.get.nodes
      def checkList(lst: List[Concept]): Option[Concept] = {
        if (lst == null || lst.isEmpty) return None
        lst.foreach(n => {
          log trace "Check " + n.uri.name
          val currentText=n.phrases.frames.map(p => p._2.toString.toLowerCase.trim)
          if (currentText.count(p=>p.equals(phrase.toLowerCase.trim)) >0 || n.content.toString.toLowerCase.equals(phrase.toLowerCase))
            return Some(n)
          else if (n.specialisations != null && n.specialisations.frames.size > 0) {
            val lstRes = checkList(n.specialisations.frames.map(s => s._2).toList)
            if (!lstRes.isEmpty) return lstRes
          }
        })
        None
      }
      checkList(dmModel)
    }


    def annotatePhrase(ph: AnnotatedPhrase): Boolean = {
      var result = false
      var annotationFound = checkLocalKB(ph.phrase)
      log trace ("found annatations={}", annotationFound)
      def appendAnnotation(ref: AnnotatedPhrase, ctp: Concept) {
        ref.concepts = List(ctp)
        //ref.phrases = src.phrases
      }
      if (annotationFound.isEmpty) {
        AnnotatorRegistry.listAnnotators().filter(p => p.isLocal() != true).foreach(a => {
          val synonymous = a.annotate(ph.phrase)
          synonymous.foreach(syn => {
            //check against local KB
            annotationFound = checkLocalKB(syn)
            if (!annotationFound.isEmpty) {
              appendAnnotation(ph, annotationFound.get)
              log trace ("appended annotation={} to phrase={}", annotationFound, ph)
              result = true
              scala.util.control.Breaks.break()
            }
          })
        })
      }
      else {
        appendAnnotation(ph, annotationFound.get)
        log trace ("appended annotation={} to phrase={}", annotationFound, ph)
        result = true
      }
      result
    }


    def recurrentlyCheckPhrase(ph: AnnotatedPhrase): Boolean = {
      val annotateFound = annotatePhrase(ph)
      if (!annotateFound) {
        ph.phrases.foreach(ph1 => {
          recurrentlyCheckPhrase(ph1)
        })
      }
      true
    }

    extractedNarratives.foreach(n => {
      val extractedSentences = n.sentences
      extractedSentences.foreach(s => {
        val extractedPhrases = s.phrases
        extractedPhrases.foreach(curPhrase => {
          recurrentlyCheckPhrase(curPhrase)

        })
      })
    })
    val ctxOutput = ContextHelper(sentences.map(b => b._2).toList, sentences.head._2, "AnnotationResult")
    ctxOutput
  }

  def start() = false

  def stop() = false
}
