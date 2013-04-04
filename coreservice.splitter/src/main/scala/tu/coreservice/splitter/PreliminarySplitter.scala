package tu.coreservice.splitter

import _root_.relex.corpus.{DocSplitterFactory, DocSplitter}
import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.communication.{ContextHelper, ShortTermMemory}
import tu.model.knowledge.{Constant, Resource, KnowledgeURI}
import tu.coreservice.spellcorrector.SpellCorrector
import relex.entity.EntityMaintainer
import relex.RelationExtractor
import relex.output.OpenCogScheme
import scala.collection.JavaConversions._
import tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think
import org.slf4j.LoggerFactory
import relex.feature.FeatureNode
import tu.model.knowledge.annotator.{AnnotatedNarrative, AnnotatedSentence, AnnotatedPhrase}
import tu.exception.UnexpectedException
import tu.coreservice.utilities.URIHelper
import tu.nlp.server.NLPFactory


/**
 * @author toschev alex
 * @author talanov max
 *         Date: 01.06.12
 *         Time: 18:59
 *
 */

/**
 * Split text in sentence and phrases.
 * https://github.com/development-team/2/blob/master/doc/design-specification/splitting-text-to-sentences.md
 */
class PreliminarySplitter extends Way2Think {

  val log = LoggerFactory.getLogger(this.getClass)


  val relexServer =  NLPFactory.createProcessor()


  /**
   * Way2Think interface.
   * @param inputContext the ShortTermMemory to process.
   * @return outputContext
   */
  def apply(inputContext: ShortTermMemory): ShortTermMemory = {

    log trace "apply(" + inputContext + ": ShortTermMemory)"

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

    val sentenceURI = new KnowledgeURI("tu-project.com", "sentence", Constant.defaultRevision)


    //correct all text before splitting to sentence
    var text = textFrame._2.asInstanceOf[KnowledgeString].value

    val corrector = SpellCorrector()
    text = corrector.correctSentence(text)
    val splitedSentences = relexServer.splitSentences(text)

    var sntOrder = 1

    val outputContext = ContextHelper(List[Resource](), this.getClass.getName)
    var annotatedSentences: List[AnnotatedSentence] = List[AnnotatedSentence]()

    splitedSentences.foreach(sentence => {
      //check sentence using auto-corrector
      //append extracted sentence to context and increase counter for sentence
      val relexSentence = relexServer.processSentence(sentence)
      log debug ("relexSentence={}", relexSentence)
      val parse = relexSentence.getParses.get(0)

      var phrases = processNodeRec(parse.getLeft.get("head"))

           //rearrange phrases according to sentence occurrence
      phrases = phrases.sortBy(b => b.sentenceIndex)

      outputContext.frames += (new KnowledgeURI(URIHelper.uriProjectName, sentenceURI.name + "-" + sntOrder, URIHelper.version())
        -> new KnowledgeString(sentence, sentenceURI))

      //also add sentences to sentence
      val annotatedSentence = AnnotatedSentence(sentence, phrases,
        new KnowledgeURI(URIHelper.uriProjectName, sentenceURI.name + "-" + sntOrder, URIHelper.version()))
      sntOrder = sntOrder + 1
      annotatedSentences ::= annotatedSentence
      log trace ("created phrases={}", phrases)
      log trace ("created sentences={}", annotatedSentences)
    })

    val annotatedNarrative = AnnotatedNarrative(annotatedSentences, KnowledgeURI(this.getClass.getName + " result"))
    log debug ("created narrative={}", annotatedNarrative)
    outputContext.lastResult = Some(annotatedNarrative)
    log trace ("apply()= {}", outputContext.toString)
    outputContext
  }


  def getName(feature: FeatureNode): String = {

    if (feature.get("name") != null) {
      val name = feature.get("name").getValue
      log trace "name=" + feature.get("name").getValue
      name
    } else if (feature.get("orig_str") != null) {
      val origStr = feature.get("orig_str").getValue
      log trace "orig_str" + feature.get("orig_str").getValue
      origStr
    } else {
      throw new UnexpectedException("$No_name_specified")
    }
  }


  def start() = false

  def stop() = false

  private def processNodeRec(feature: FeatureNode): List[AnnotatedPhrase] = {
    var phrases = List[AnnotatedPhrase]()
    try {
      val name: String = getName(feature)
      //apply sentence index
      val sentenceIndex = feature.get("nameSource").get("index_in_sentence").getValue.toDouble

      val currentPhrase = if (name.contains("_")) {
        //split phrase by two
        AnnotatedPhrase(name.split("_").map(b => AnnotatedPhrase(b)).toList, sentenceIndex)
      }
      else {
        AnnotatedPhrase(name, sentenceIndex)
      }

      if (feature.get("links") != null) {
        val filteredFeatures = feature.get("links").getFeatureNames.filter {
          n: String => Constant.RelexFeatures.contains(n)
        }
        var notCompound = false
        if (filteredFeatures.size > 0) {
          filteredFeatures.foreach(f => {
            val childPhrases = processNodeRec(feature.get("links").get(f))
            if (Constant.RelexFeaturesPhrases.contains(f)) {
              val connectionPhrase = AnnotatedPhrase(f, sentenceIndex)
              if (childPhrases.size > 0) {
                val compoundPhrase = AnnotatedPhrase(List(currentPhrase, connectionPhrase, childPhrases(0)), sentenceIndex)
                phrases :::= List(compoundPhrase) ::: childPhrases.slice(1, childPhrases.size)
              } else {
                val compoundPhrase = AnnotatedPhrase(List(currentPhrase, connectionPhrase), sentenceIndex)
                phrases ::= compoundPhrase
              }
            } else {
              notCompound = true
              phrases :::= childPhrases
            }
          })
          if (notCompound) {
            phrases ::= currentPhrase
          }
        }
      } else {
        phrases ::= currentPhrase
      }

      val next = feature.get("NEXT")
      if (next != null) {
        log trace "=>"
        phrases :::= processNodeRec(next)
      }
      phrases
    } catch {
      case e: RuntimeException => {
        log error e.getMessage
        throw new UnexpectedException("$Wrong_feature_requested " + e.getMessage)
      }
    }
  }
}