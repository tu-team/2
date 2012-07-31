package tu.coreservice.annotator


import org.slf4j.LoggerFactory
import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.{KnowledgeURI, Resource}
import tu.providers.AnnotatorRegistry
import tu.model.knowledge.annotator.{AnnotatedSentence, AnnotatedPhrase}
import tu.model.knowledge.primitive.KnowledgeString
import tu.coreservice.utilities.URIHelper
import tu.model.knowledge.narrative.Narrative

/**
 * Simple KBAnnotator implementation.
 * @author toschev alex
 *         Date: 15.06.12
 *         Time: 18:26
 *
 */
class KBAnnotatorImpl extends Way2Think {

  val log = LoggerFactory.getLogger(this.getClass)

  /**
   * Way2Think interface.
   * @param inputContext Context of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: Context): Context = {
    //we have output context from splitter

    //
    //splitter return annotated sentences array

    val sentences = inputContext.frames.filter(p => p._1.name.contains(URIHelper.splitterMark()))

    val extractedNarratives = sentences.map(b => b._2.asInstanceOf[Narrative[AnnotatedSentence]])




    var wordsDetected = 0

    //trying to annotate sentences

    val localAnnotator = AnnotatorRegistry.getLocalAnnotator()

    def checkLocalKB(phrase: String): Option[AnnotatedPhrase] = {

      var localAnnotated = localAnnotator.apply(phrase)

      return localAnnotated

    }

    extractedNarratives.foreach(n => {
      val extractedSentences = n._resources
      extractedSentences.foreach(s => {

        val extractedPhrases = s.phrases

        extractedPhrases.foreach(ph => {


          var annotationFound = checkLocalKB(ph.phrase)

          def appendAnnotation(ref:AnnotatedPhrase, src:AnnotatedPhrase)={
              ref.concepts=src.concepts
              ref.words=src.words
          }

          if (annotationFound.isEmpty) {

            AnnotatorRegistry.listAnnotators().filter(p => p.isLocal() != true).foreach(a => {
              val synonymous = a.annotate(ph.phrase)

              synonymous.foreach(syn => {
                //check against local KB
                annotationFound = checkLocalKB(syn)

                if (!annotationFound.isEmpty) {
                  appendAnnotation(ph, annotationFound.get)
                  scala.util.control.Breaks.break()
                }
              })

            })

          }
          else
          {
            appendAnnotation(ph, annotationFound.get)
          }
        })
      })
    })



    var ctxOutput = ContextHelper(sentences.map(b => b._2).toList, sentences.head._2, "AnnotationResult")

    ctxOutput
  }

  def start() = false

  def stop() = false
}
