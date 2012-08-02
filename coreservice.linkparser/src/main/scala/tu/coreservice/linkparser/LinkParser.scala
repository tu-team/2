package tu.coreservice.linkparser


import collection.JavaConversions._
import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.Resource
import tu.model.knowledge.annotator.{AnnotatedPhrase, AnnotatedSentence, AnnotatedNarrative}
import tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think
import tu.coreservice.action.UnexpectedException
import relex.entity.EntityMaintainer
import relex.output.OpenCogScheme
import relex.ParsedSentence
import relex.feature.{FeatureNameFilter, FeatureNode}
import org.slf4j.LoggerFactory
import tu.model.knowledge.domain.{Concept, ConceptLink}
import tu.coreservice.utilities.TestDataGenerator


/**
 * @author max talanov
 *         Date: 7/31/12
 *         Time: 4:18 AM
 */

class LinkParser extends Way2Think {

  val log = LoggerFactory.getLogger(this.getClass)

  val names: List[String] = List("_subj", "_obj", "_iobj", "_advmod")

  def start() = false

  def stop() = false

  /**
   * Way2Think interface.
   * @param inputContext Context of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: Context): Context = {

    val narrative = getLastResult(inputContext)
    val sentences: List[AnnotatedSentence] = narrative.sentences
    val text = narrative.text

    val outputContext = ContextHelper(List[Resource](), this.getClass.getName)
    outputContext
  }

  def getLastResult(inputContext: Context): AnnotatedNarrative = {
    try {
      val narrative: AnnotatedNarrative = inputContext.lastResult.asInstanceOf[AnnotatedNarrative]
      narrative
    } catch {
      case e: ClassCastException => {
        val cry4Help = Cry4HelpWay2Think("$Context_lastResult_is_not_expectedType " + e.getMessage)
        // throw new UnexpectedException("$Context_lastResult_is_not_expectedType " + e.getMessage)
        ContextHelper(List[Resource](cry4Help), cry4Help, this.getClass.getName + " result")
        throw new UnexpectedException("$Context_lastResult_is_not_expectedType " + e.getMessage)
      }
    }
  }

  def processSentences(sentences: List[AnnotatedSentence], context: Context) = {
    sentences.map {
      sentence: AnnotatedSentence => {
        val parse: ParsedSentence = processSentence(sentence, context)
        val node: FeatureNode = new FeatureNode()
        node.set("head", parse.getLeft.get("head"))
        node.set("background", parse.getLeft.get("background"))
        processNode(node.get("head"), sentence)
      }
    }
  }

  def processSentence(sentence: AnnotatedSentence, context: Context): ParsedSentence = {
    //run relex and extract sentences
    val em: EntityMaintainer = new EntityMaintainer()
    val relExt = setup(context)
    val relexSentence = relExt.processSentence(sentence.text, em)
    relexSentence.getParses.get(0)
  }

  def setup(context: Context): RelationExtractorKB = {
    // relex.RelationExtractor -n 4 -l -t -f -r -a
    val re = new RelationExtractorKB(false, context)
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

  def processNode(feature: FeatureNode, sentence: AnnotatedSentence): Concept = {
    val leftWall = "LEFT-WALL"
    try {

      val name: String = getName(feature)
      val concept: Concept = getConcept(name, sentence)

      if (feature.get("tense") != null) {
        log info "tense=" + feature.get("tense").getValue
        val tense = Concept.createInstanceConcept(TestDataGenerator.tenseConcept)
        val tenseLink = ConceptLink.createInstanceConceptLink(TestDataGenerator.tenseLink, concept, tense)
        concept.links = concept.links ::: List(tenseLink)
      }
      if (feature.get("pos") != null) {
        log info "pos=" + feature.get("pos").getValue
        val pos = Concept.createInstanceConcept(TestDataGenerator.posConcept)
        val posLink = ConceptLink.createInstanceConceptLink(TestDataGenerator.posLink, concept, pos)
        concept.links = concept.links ::: List(posLink)
      }

      if (feature.get("links") != null) {
        // log info "links=" + feature.get("links").toString(getZHeadsFilter)
        log info "==>"
        processLink(feature.get("links"), concept, sentence)
      }
      val next = feature.get("NEXT")
      if (next != null) {
        log info "=>"
        processNode(next, sentence)
      }
      concept
    } catch {
      case e: RuntimeException => {
        log error e.getMessage
        throw new UnexpectedException("$Wrong_feature_requested " + e.getMessage)
      }
    }
  }


  def getConcept(name: String, sentence: AnnotatedSentence): Concept = {
    val phrases = findPhrase(name, sentence)
    if (phrases.size == 1) {
      val phrase = phrases.head
      val concepts = phrase.concepts
      if (concepts.size == 1) {
        val concept = concepts.head
        concept
      } else if (concepts.size < 1) {
        throw new UnexpectedException("$No_concepts_found")
      } else {
        throw new UnexpectedException("$Ambiguous_concepts")
      }
    } else if (phrases.size < 1) {
      throw new UnexpectedException("$No_links_found")
    } else {
      throw new UnexpectedException("$Ambiguous_links")
    }
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

  def findPhrase(value: String, sentence: AnnotatedSentence): List[AnnotatedPhrase] = {
    val filteredPhrase: List[AnnotatedPhrase] = sentence.phrases.filter {
      phrase: AnnotatedPhrase => {
        phrase.text.trim == value.trim
      }
    }
    filteredPhrase
  }

  def processLink(feature: FeatureNode, source: Concept, sentence: AnnotatedSentence): ConceptLink = {

    try {
      val filteredFeatures = feature.getFeatureNames.filter {
        name: String => names.contains(name)
      }
      if (filteredFeatures.size == 1) {
        val name: String = filteredFeatures.head
        log info name
        val destination = processNode(feature.get("_subj"), sentence)
        ConceptLink(source, destination, name.substring(1))
      } else if (filteredFeatures.size < 1) {
        throw new UnexpectedException("$No_links_found")
      } else {
        throw new UnexpectedException("$Ambiguous_links")
      }
    } catch {
      case e: RuntimeException => {
        throw new UnexpectedException("$No_features_found " + e.getMessage)
      }
    }
  }

  def linksFilter: FeatureNameFilter = {
    val ignores: java.util.HashSet[String] = new java.util.HashSet[String]
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
    ignores.add("name")
    ignores.add("tense")
    ignores.add("PREP-FLAG")
    ignores.add("links")
    ignores.add("")
    ignores.add("head")
    ignores.add("background")
    ignores.add("words")

    val order: java.util.ArrayList[String] = new java.util.ArrayList[String]
    names.map {
      name: String => order.add(name)
    }
    new FeatureNameFilter(ignores, order)
  }
}
