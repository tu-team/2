package tu.coreservice.splitter

import _root_.relex.corpus.{DocSplitterFactory, DocSplitter}
import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.{Resource, KnowledgeURI}
import tu.coreservice.spellcorrector.SpellCorrector
import relex.entity.EntityMaintainer
import relex.RelationExtractor
import relex.output.OpenCogScheme
import scala.collection.JavaConversions._
import tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think
import org.slf4j.LoggerFactory
import relex.feature.{FeatureNodeCallback, FeatureNode}
import tu.model.knowledge.annotator.{AnnotatedWord, AnnotatedNarrative, AnnotatedSentence, AnnotatedPhrase}
import tu.coreservice.action.UnexpectedException
import tu.model.knowledge.domain.{ConceptLink, Concept}
import tu.coreservice.utilities.TestDataGenerator


/**
 * @author toschev alex
 *         Date: 01.06.12
 *         Time: 18:59
 *
 */

/**
 * split text in sentence
 * https://github.com/development-team/2/blob/master/doc/design-specification/splitting-text-to-sentences.md
 */
class PreliminarySplitter extends Way2Think {

  val log = LoggerFactory.getLogger(this.getClass)

  /**
   * list of phrase's types that should be processed as separate Annotated Phrases
   */
  private val separateProcessingPhraseType=List[String]()

  def setup: RelationExtractor = {
    // relex.RelationExtractor -n 4 -l -t -f -r -a
    val re = new RelationExtractor(false)
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
   * Way2Think interface.
   * @param inputContext the Context to process.
   * @return outputContext
   */
  def apply(inputContext: Context): Context = {

    log info "apply(" + inputContext + ": Context)"

    val textFrames = inputContext.frames.filter(p =>
      p._1.name == "inputtext"
    )

    val textFrame = if (textFrames.size > 0) {
      textFrames.head
    } else {
      val cry4Help = Cry4HelpWay2Think("$Could_not_find " + "inputtext")
      val outputContext = ContextHelper(List[Resource](cry4Help), this.getClass.getName + " result")
      return outputContext
    }

    val sentenceURI = new KnowledgeURI("tu-project.com", "sentence", "0.3")

    // split text using relex
    val ds: DocSplitter = DocSplitterFactory.create()

    //correct all text before splitting to sentence
    var text = textFrame._2.asInstanceOf[KnowledgeString].value

    val corrector = SpellCorrector()
    text = corrector.correctSentence(text)

    ds.addText(text)

    var sntOrder = 1

    var sentence: String = ds.getNextSentence
    val outputContext = ContextHelper(List[Resource](), this.getClass.getName)
    var annotatedSentences: List[AnnotatedSentence] = List[AnnotatedSentence]()
    while (sentence != null) {
      //check sentence using auto-corrector
      //append extracted sentence to context and increase counter for sentence

      //run relex and extract sentences
      val em: EntityMaintainer = new EntityMaintainer()
      val relExt = setup
      val relexSentence = relExt.processSentence(sentence, em)





      val tree = relexSentence.getParses.get(0).getPhraseTree

      var  phrases  = List[AnnotatedPhrase]()

      extractedPhrases(tree.getNode)

      def addAnnotatedPhrase(vl:FeatureNode)={

        phrases=phrases ::: List(AnnotatedPhrase(vl.getValue))
        true
      }

      def fillAnnotatedPhrase(vl:AnnotatedPhrase, word:FeatureNode)={
        vl.phrases::= AnnotatedPhrase(word.getValue)
      }

      def extractedPhrases(head:FeatureNode):Boolean={
        /**
         * Regenerate the phrase structure tree from the
         * feature node graph.
         */

          if (head == null) return false
          var fn:FeatureNode = head.get("phr-head")
          if (fn == null) {
            fn = head.get("phr-type")
            if (fn != null) return false
            fn = head.get("phr-word")
            if (fn != null) {
              fn = fn.get("orig_str")
              if (fn != null) addAnnotatedPhrase(fn)
            }
            return false
          }
          val str: String = ""
          _extractedPhrases(fn, str)

      }

      def _extractedPhrases( fn:FeatureNode,  str:String):Boolean={
          var fn_type: FeatureNode = fn.get("phr-type")
          val sepProc= separateProcessingPhraseType.contains( fn_type.getValue=="NP" )
          var wordList = List[AnnotatedPhrase]()
          var fn1 = fn.get("phr-next")
          while (fn1 != null) {

            var fn_word: FeatureNode = fn1.get("phr-word")
            if (fn_word != null) {
              fn_word = fn_word.get("orig_str")
              if (fn_word != null && fn_type.getValue!="S") {
                if (sepProc)
                {
                  addAnnotatedPhrase(fn_word)
                }
                else
                {
                   wordList = wordList ::: List(AnnotatedPhrase(fn_word.getValue))  ;

                }

              }
            }
            val head: FeatureNode = fn1.get("phr-head")
            if (head != null) {

               _extractedPhrases(head, str)
            }
            fn1 = fn1.get("phr-next")
          }
        //close phrase
        if (wordList.length>0)
        {
          phrases =  List(AnnotatedPhrase(wordList)) :::phrases
          //phrases = ph
        }
          true

      }

      //extract all sentences



       /* .filter(n => {
        val feature = n.getNode
        feature.get("orig_str") != null
      }
      ).map(
        u => {
          val feature = u.getNode

          val phraseValue = feature.get("orig_str").getValue;
          val phrase: AnnotatedPhrase = AnnotatedPhrase(phraseValue)
          /*val phrs: List[AnnotatedPhrase] = wordList.map {
            w: FeatureNode => {
              //append word
              val phrase: AnnotatedPhrase = AnnotatedPhrase(w.get("orig_str").getValue)
              phrase
            }
          }.toList*/
          phrase
        }
      ).toList  */

      //relexSentence.getParses.toList.map(b=>new Phrase(b.getPhraseTree.))
      //var convertedPhrases = relexSentence.getParses.toArray.map(b=> new Phrase(b.))
      outputContext.frames += (new KnowledgeURI("tu-project.com", sentenceURI.name + "-" + sntOrder, "0.3")
        -> new KnowledgeString(sentence, sentenceURI))

      //also add sentences to sentence
      val annotatedSentence = AnnotatedSentence(sentence, phrases,
        new KnowledgeURI("tu-project.com", sentenceURI.name + "-" + sntOrder, "0.3"))
      sntOrder = sntOrder + 1
      sentence = ds.getNextSentence
      annotatedSentences ::= annotatedSentence
    }
    val annotatedNarrative = AnnotatedNarrative(annotatedSentences, KnowledgeURI(this.getClass.getName + " result"))
    outputContext.lastResult = Some(annotatedNarrative)
    log info "apply():" + outputContext.toString
    outputContext
  }

  /*def processNode(feature: FeatureNode, sentence: AnnotatedSentence): AnnotatedPhrase = {
    val leftWall = "LEFT-WALL"
    try {


      val next = feature.get("NEXT")
      if (next != null) {
        log info "=>"
        processNode(next, sentence)
      }


    } catch {
      case e: RuntimeException => {
        log error e.getMessage
        throw new UnexpectedException("$Wrong_feature_requested " + e.getMessage)
      }
    }
  } */

  def start() = false

  def stop() = false
}
