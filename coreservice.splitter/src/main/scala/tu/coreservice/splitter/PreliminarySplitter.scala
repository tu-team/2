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

  val names: List[String] = List("_subj", "_obj", "_iobj", "_advmod")

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
      val parse = relexSentence.getParses.get(0)
      var  phrases  = List[AnnotatedPhrase]()

      processNode(parse.getLeft.get("head"))


      def processNode(feature: FeatureNode):Boolean = {

        try {

          val name: String = getName(feature)

          //apply sentence index
          val sentenceIndex = feature.get("nameSource").get("index_in_sentence").getValue().toDouble

          if (name.contains("_"))
          {
            //split phrase by two
            phrases ::= AnnotatedPhrase(name.split("_").map(b=> AnnotatedPhrase(b)).toList,sentenceIndex)

          }
          else
          {
            phrases ::= AnnotatedPhrase(name,sentenceIndex)
          }
            if (feature.get("links") != null) {
            val filteredFeatures = feature.get("links").getFeatureNames.filter {
              n: String => names.contains(n)
            }
            if (filteredFeatures.size > 0) {

              filteredFeatures.foreach(f=>{
                processNode(feature.get("links").get(f))
              })


            }
          }

          val next = feature.get("NEXT")
          if (next != null) {
            log info "=>"
            processNode(next)
          }
          true
        } catch {
          case e: RuntimeException => {
            log error e.getMessage
            throw new UnexpectedException("$Wrong_feature_requested " + e.getMessage)
          }
        }
      }

      //rearrange phrases according to sentence occurence
      phrases=phrases.sortBy(b=>b.sentenceIndex)

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




  def getName(feature: FeatureNode): String = {

    if (feature.get("name") != null) {
      val name = feature.get("name").getValue
      log info "name=" + feature.get("name").getValue
      name
    } else if (feature.get("orig_str") != null) {
      val origStr = feature.get("orig_str").getValue
      log info "orig_str" + feature.get("orig_str").getValue
      origStr
    } else {
      throw new UnexpectedException("$No_name_specified")
    }
  }


  def start() = false

  def stop() = false
}
