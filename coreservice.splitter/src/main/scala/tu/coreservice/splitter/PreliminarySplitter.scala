package tu.coreservice.splitter

import _root_.relex.corpus.{DocSplitterFactory, DocSplitter}
import tu.coreservice.action.way2think.{UnexpectedException, Way2Think}
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.{Resource, KnowledgeURI}
import tu.coreservice.spellcorrector.SpellCorrector
import relex.entity.EntityMaintainer
import relex.RelationExtractor
import relex.output.OpenCogScheme
import scala.collection.JavaConversions._
import tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think


/**
 * @author toschev alex
 *         Date: 01.06.12
 *         Time: 18:59
 *
 */

/**
 * split text in sentence
 * https://github.com/development-team/2/blob/master/doc/design-specification/splitting-text-to-phrases.md
 */
class PreliminarySplitter extends Way2Think {

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
   * apply way 2 think
   * @param inputContext
   * @param outputContext
   * @return split in sentence text
   */
  def apply(inputContext: Context, outputContext: Context) = {
    //extract text from input context
    var textFrame = inputContext.frames.filter(p =>

      p._1.name == "inputtext"

    ).head

    //initialize output context
    ContextHelper.initializeContext(outputContext);

    var sentenceURI = new KnowledgeURI("tu-project.com", "sentence", "0.3")


    // split text using relex
    var ds: DocSplitter = DocSplitterFactory.create()

    //correct all text before splitting to sentence
    var text = textFrame._2.asInstanceOf[KnowledgeString].value

    var corrector = SpellCorrector()
    text = corrector.correctSentence(text);

    ds.addText(text)

    var sntOrder = 1

    var sentence = ds.getNextSentence
    while (sentence != null) {
      //check sentence using autocorrector
      //append extracted sentence to context and increase counter for sentence
      outputContext.frames += (new KnowledgeURI("tu-project.com", sentenceURI.name + "-" + sntOrder, "0.3") -> new KnowledgeString(sentence, sentenceURI))
      sntOrder = sntOrder + 1
      sentence = ds.getNextSentence
    }

  }


  def processSentences(sentence: String) {

    var em: EntityMaintainer = new EntityMaintainer()

    var relExt = setup;

  }

  /**
   * Way2Think interface.
   * @param inputContext
   * @return outputContext
   */
  def apply(inputContext: Context): Context = {
    val textFrames = inputContext.frames.filter(p =>
      p._1.name == "inputtext"
    )

    val textFrame = if (textFrames.size > 0) {
      textFrames.head
    } else {
      val cry4Help = Cry4HelpWay2Think("$Could_not_find " + "inputtext")
      val outputContext = ContextHelper(List(cry4Help), this.getClass.getName + " result")
      return outputContext
    }

    val sentenceURI = new KnowledgeURI("tu-project.com", "sentence", "0.3")

    // split text using relex
    val ds: DocSplitter = DocSplitterFactory.create()

    //correct all text before splitting to sentence
    var text = textFrame._2.asInstanceOf[KnowledgeString].value

    val corrector = SpellCorrector()
    text = corrector.correctSentence(text);

    ds.addText(text)

    var sntOrder = 1

    var sentence: String = ds.getNextSentence
    var outputContext = ContextHelper(List[Resource](), this.getClass.getName())
    while (sentence != null) {
      //check sentence using autocorrector
      //append extracted sentence to context and increase counter for sentence

      //run relex and extract phrases
      val em: EntityMaintainer = new EntityMaintainer()

      val relExt = setup;

      val relexSentence = relExt.processSentence(sentence, em);

      val tree = relexSentence.getParses().get(0).getPhraseTree()
      //extract all phrases
      tree.iterator().foreach(u => {
        //append phrase
        var tempString = u.getNode.toString
      });

      //relexSentence.getParses.toList.map(b=>new Phrase(b.getPhraseTree.))

      //var convertedPhrases = relexSentence.getParses.toArray.map(b=> new Phrase(b.))
      outputContext.frames += (new KnowledgeURI("tu-project.com", sentenceURI.name + "-" + sntOrder, "0.3")
        -> new KnowledgeString(sentence, sentenceURI))

      //also add phrases to sentence

      sntOrder = sntOrder + 1
      sentence = ds.getNextSentence
    }

    //TODO get rid of this, there should be outputContext only
    outputContext
  }

  def start() = false

  def stop() = false
}
