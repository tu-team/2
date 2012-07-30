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

    var sentences=inputContext.frames.filter(p=> p._1.name.contains(URIHelper.splitterMark()))

    var extractedPhrases= sentences.map(b=> b._2.asInstanceOf[Narrative[AnnotatedSentence]].phrase)


    //instatiate output context
    var ctxOutput = ContextHelper.apply(annotatedPhrasesCollection.map(t=>t._2).toList,"AnnotatorResults")

    var wordsDetected=0

    //trying to annotate sentences

    val localAnnotator = AnnotatorRegistry.getLocalAnnotator()

    def checkLocalKB(phrase:String):Boolean={

      var localAnnotated=localAnnotator.apply(phrase)

      if (!localAnnotated.isEmpty)
      {
        addAnnotatedPhraseToOutputContext(localAnnotated.get)

        return true

      }

      return false

    }


    def addAnnotatedPhraseToOutputContext(phrase:AnnotatedPhrase)={
      wordsDetected+=1

      //find this phrase and context and append concepts to it
      ctxOutput.frames.find(
        p=>p._2.asInstanceOf[AnnotatedPhrase].phrase==phrase.phrase)
        .head._2.asInstanceOf[AnnotatedPhrase].concepts=phrase.concepts

    }

    extractedPhrases.foreach(ph=> {

      var annotationFound= checkLocalKB(ph)

      if (!annotationFound)
      {

           AnnotatorRegistry.listAnnotators().filter(p=> p.isLocal()!=true).foreach(a=> {
           val synonymous =a.annotate(ph)

           synonymous.foreach(syn=>{
             //check against local KB
             if (checkLocalKB(syn))
              {
                annotationFound=true

                scala.util.control.Breaks.break()
              }
           })

        })

        //if annotation still not found, just append as a empty phrase
        if (!annotationFound)
        {
          addAnnotatedPhraseToOutputContext(AnnotatedPhrase.apply(ph))
          //comment

        }
      }
    })




    ctxOutput
  }

  def start() = false

  def stop() = false
}
