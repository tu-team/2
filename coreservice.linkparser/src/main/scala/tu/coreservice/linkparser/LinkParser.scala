package tu.coreservice.linkparser


import collection.JavaConversions._
import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.communication.{ContextHelper, ShortTermMemory}
import tu.model.knowledge.{Constant, KnowledgeURI, Resource}
import tu.model.knowledge.annotator.{AnnotatedPhrase, AnnotatedSentence, AnnotatedNarrative}
import tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think
import relex.entity.EntityMaintainer
import relex.output.OpenCogScheme
import relex.ParsedSentence
import relex.feature.{FeatureNameFilter, FeatureNode}
import org.slf4j.LoggerFactory
import tu.model.knowledge.domain.{Concept, ConceptLink}
import tu.exception.{NoExpectedInformationException, UnexpectedException}
import tu.dataservice.knowledgebaseserver.Defaults
import tu.nlp.server.NLPFactory
import tu.exception.UnexpectedException

/**
 * Processes AnnotatedSentence each AnnotatedSentence via RelationExtractorKB.
 * @author max talanov
 *         Date: 7/31/12
 *         Time: 4:18 AM
 */

class LinkParser extends Way2Think {

  val log = LoggerFactory.getLogger(this.getClass)

  val relexServer = NLPFactory.createProcessor()

  def start() = false

  def stop() = false

  /**
   * Way2Think interface.
   * @param inputContext ShortTermMemory of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: ShortTermMemory): ShortTermMemory = {
    val narrative = getLastResult(inputContext)
    val sentences: List[AnnotatedSentence] = narrative.sentences
    val updatedSentences = processSentences(sentences, inputContext)
    val updatedNarrative = AnnotatedNarrative(updatedSentences, KnowledgeURI(Constant.LINK_PARSER_RESULT_NAME))
    val outputContext = ContextHelper(List[Resource](updatedNarrative), updatedNarrative, this.getClass.getName)
    outputContext
  }

  def getLastResult(inputContext: ShortTermMemory): AnnotatedNarrative = {
    try {
      inputContext.lastResult match {
        case Some(narrative: AnnotatedNarrative) => narrative
        case _ => throw new NoExpectedInformationException("$Last_result_contains_no_narrative")
      }
    } catch {
      case e: ClassCastException => {
        val cry4Help = Cry4HelpWay2Think("$Context_lastResult_is_not_expectedType " + e.getMessage)
        ContextHelper(List[Resource](cry4Help), cry4Help, this.getClass.getName + " result")
        throw new NoExpectedInformationException("$Context_lastResult_is_not_expectedType " + e.getMessage)
      }
    }
  }

  /**
   * Run through List of AnnotatedSentence and processes each AnnotatedSentence via RelationExtractorKB.
   * @param sentences to process.
   * @param context ShortTermMemory to store Errors in.
   * @return List of AnnotatedSentence.
   */
  def processSentences(sentences: List[AnnotatedSentence], context: ShortTermMemory): List[AnnotatedSentence] = {
    sentences.map {
      sentence: AnnotatedSentence => {
        val parse: ParsedSentence = processSentenceRelex(sentence, sentences)
        log debug("parse = {}", parse.toString)
        val node: FeatureNode = new FeatureNode()
        node.set("head", parse.getLeft.get("head"))
        node.set("background", parse.getLeft.get("background"))
        val res = processNode(node.get("head"), sentence)
        res match {
          case (None, Some(e: Error)) => {
            context.errors = context.errors ::: List(e)
          }
          case (Some(c: Concept), None) => {
            // no processing
          }
          case (Some(c: Concept), Some(e: Error)) => {
            context.errors = context.errors ::: List(e)
          }
          case (None, None) => {
            // no processing
          }
        }
      }
    }
    sentences
  }

  /**
   * Processes sentence with RelationExtractorKB that takes in account KBAnnotator results.
   * Run relex and extract sentences.
   * @param sentence to process via RelationExtractorKB
   * @param sentences processed
   * @return ProcessedSentence
   */
  def processSentenceRelex(sentence: AnnotatedSentence, sentences: List[AnnotatedSentence]): ParsedSentence = {
    val relexSentence = relexServer.processSentence(sentence.text, sentences)
    log debug("relexSentence ={}", relexSentence)
    val parsesNum = relexSentence.getNumParses
    if (parsesNum < 1) {
      throw new UnexpectedException("$No_parses_produced")
    } else {
      relexSentence.getParses.get(0)
    }
  }


  /**
   * Gets Concept from sentence if not found, creates orphan Concept and returns Error, if found returns Concept with no Error, otherwise only Error is returned.
   * @param feature FeatureNode to process: find AnnotatedPhrase -> Concept,
   * @param sentence to find FeatureNode in.
   * @return Pair of Concept and Error
   */
  def processNode(feature: FeatureNode, sentence: AnnotatedSentence): Pair[Option[Concept], Option[Error]] = {
    log trace("processNode(feature={})", feature)
    try {
      val nameOrigString: Pair[Option[String], Option[String]] = getNameOrigString(feature)
      val name: String = nameOrigString match {
        case Pair(Some(n: String), _) => n
        case Pair(None, Some(oS: String)) => oS
        case Pair(None, None) => throw new UnexpectedException("$No_name_found")
      }
      val phraseConceptError: Triple[AnnotatedPhrase, Option[Concept], Option[Error]] = getConcept(name, sentence)
      phraseConceptError match {
        case Triple(annotatedPhrase: AnnotatedPhrase, Some(concept: Concept), None) => {
          val updatedConcept = addLinks(feature, concept, annotatedPhrase, sentence)
          log debug("updated concept={}, it was={}", updatedConcept, concept)
          (Some(updatedConcept), None)
        }
        case Triple(annotatedPhrase: AnnotatedPhrase, Some(concept: Concept), Some(e: Error)) => {
          val updatedConcept = addLinks(feature, concept, annotatedPhrase, sentence)
          log debug("updated concept={}, it was={}", updatedConcept.toString, concept.toString)
          (Some(updatedConcept), None)
        }
        case Triple(annotatedPhrase: AnnotatedPhrase, None, Some(e: Error)) => {
          log debug("produced parsing error={}", e)
          (None, Some(e))
        }
      }
    } catch {
      case e: RuntimeException => {
        log error e.getMessage
        throw new UnexpectedException("$Wrong_feature_requested " + e.getMessage)
      }
    }
  }

  /**
   * Updates ConceptLink-s of tense and pos adding links.
   * @param feature based on it the Concept's ConceptLink-s are updated.
   * @param concept Concept to update ConceptLink-s
   * @param annotatedPhrase AnnotatedPhrase to add POS and tense.
   * @param sentence to recursively process FeatureNode-s and ConceptLink-s.
   * @return updated Concept.
   */
  private def addLinks(feature: FeatureNode, concept: Concept, annotatedPhrase: AnnotatedPhrase, sentence: AnnotatedSentence): Concept = {
    if (feature.get("tense") != null) {
      log trace "tense=" + feature.get("tense").getValue
      annotatedPhrase.tense = feature.get("tense").getValue
      log debug("added tense={} to phrase={}", feature.get("tense").getValue, annotatedPhrase)
    }
    if (feature.get("pos") != null) {
      log trace "pos=" + feature.get("pos").getValue
      annotatedPhrase.pos = feature.get("pos").getValue
      log debug("added pos={} to phrase={}", feature.get("pos").getValue, annotatedPhrase)
    }

    if (feature.get("links") != null) {
      // log debug "links=" + feature.get("links")
      log trace "==>"
      val processedLinks = processLink(feature.get("links"), concept, sentence)
      concept.links = concept.links ::: processedLinks
    }
    val next = feature.get("NEXT")
    if (next != null) {
      log trace "=>"
      processNode(next, sentence)
    }
    concept
  }

  /**
   * Searches for FeatureNode with specified name.
   * @param feature FeatureNode to start search.
   * @param nameToFind the String name to find.
   * @return first found FeatureNode or None.
   */
  private def findFeatureNode(feature: FeatureNode, nameToFind: String): Option[FeatureNode] = {
    val name: String = getName(feature)
    if (name == nameToFind) {
      Some(feature)
    } else {
      if (feature.get("links") != null) {
        log trace "==>"
        val processedLinks = findFeatureInLinks(feature.get("links"), nameToFind)
        processedLinks
      }
      val next = feature.get("NEXT")
      if (next != null) {
        log trace "=>"
        findFeatureNode(next, nameToFind)
      }
      None
    }
  }

  private def findFeatureInLinks(feature: FeatureNode, nameToFind: String): Option[FeatureNode] = {
    val filteredFeatures = feature.getFeatureNames.filter {
      name: String => Constant.RelexFeatures.contains(name)
    }
    if (filteredFeatures.size > 0) {
      val foundNodeString: Option[String] = filteredFeatures.toList.find {
        (name: String) => {
          val found = findFeatureNode(feature.get(name), nameToFind)
          found match {
            case Some(f) => {
              true
            }
            case Pair(None, Some(e: Error)) => {
              false
            }
          }
        }
      }
      foundNodeString match {
        case Some(featureName) => {
          val found: Option[FeatureNode] = findFeatureNode(feature.get(featureName), nameToFind)
          found
        }
        case None => None
      }
    } else {
      None
    }
  }

  /**
   * Searches for AnnotatedPhrase via specified name in specified AnnotatedSentence, if no Concept found new orphan Concept is created and Error is set, if more than 1 Concept found Error returned.
   * @param name to be used to search for a AnnotatedPhrase.
   * @param sentence to search AnnotatedPhrase in.
   * @return Concept and Error pair.
   */
  def getConcept(name: String, sentence: AnnotatedSentence): Triple[AnnotatedPhrase, Option[Concept], Option[Error]] = {
    log trace("getConcept(name = {})", name)
    val phrases = findPhrase(name, sentence)
    if (phrases.size == 1) {
      val phrase = phrases.head
      val concepts = phrase.concepts
      if (concepts.size == 1) {
        val concept = concepts.head
        log trace("returned concept={}", concept)
        (phrase, Some(concept), None)
      } else if (concepts.size < 1) {
        val concept = Concept.createInstanceConcept(phrase)
        phrase.conceptsAdd(concept)
        concept.phrasesAdd(phrase)
        log trace("created new concept={}", concept)
        log trace("added it to phrase={}", phrase)
        (phrase, Some(concept), Some(new Error("$No_concepts_found_for_phrase: " + phrase)))
      } else {
        log info ("concepts were ambiguous")
        (phrase, None, Some(new Error("$Ambiguous_concepts")))
      }
    } else if (phrases.size < 1) {
      throw new UnexpectedException("$No_phrases_found " + name)
    } else {
      throw new UnexpectedException("$Ambiguous_phrases " + name)
    }
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

  /**
   * Returns Pair of name and origString if found in specified FeatureNode.
   * @param feature FeatureNode to process
   * @return Pair of Option name and Option origString
   */
  def getNameOrigString(feature: FeatureNode): Pair[Option[String], Option[String]] = {
    val optionName: Option[String] = if (feature.get("name") != null) {
      val name = feature.get("name").getValue
      log trace "name=" + feature.get("name").getValue
      Some(name)
    } else {
      None
    }
    val optionOrigString: Option[String] = if (feature.get("orig_str") != null) {
      val origStr = feature.get("orig_str").getValue
      log trace "orig_str" + feature.get("orig_str").getValue
      Some(origStr)
    } else if (feature.get("nameSource") != null && feature.get("nameSource").get("orig_str") != null) {
      val origStr = feature.get("nameSource").get("orig_str").getValue
      log trace "orig_str" + feature.get("nameSource").get("orig_str").getValue
      Some(origStr)
    } else {
      None
    }
    (optionName, optionOrigString)
  }

  def findPhrase(value: String, sentence: AnnotatedSentence): List[AnnotatedPhrase] = {
    val underscoreLess: String = value.replaceAll("_", " ")
    val filteredPhrase: List[AnnotatedPhrase] = sentence.phrases.filter {
      phrase: AnnotatedPhrase => {
        phrase.findPhrase(underscoreLess.trim.toLowerCase) match {
          case Some(ph: AnnotatedPhrase) => true
          case None => false
        }
      }
    }
    filteredPhrase
  }

  def processLink(feature: FeatureNode, source: Concept, sentence: AnnotatedSentence): List[ConceptLink] = {
    log trace("processLink(feature: {}", feature.get("name"))
    log trace("feature names={}", feature.getFeatureNames)
    try {
      val filteredFeatures = feature.getFeatureNames.filter {
        name: String => Constant.RelexFeatures.contains(name)
      }
      log trace("filteredFeatures ={}", filteredFeatures)
      if (filteredFeatures.size > 0) {
        val filteredDestinationFeatures = filteredFeatures.toList.filter {
          (name: String) => {
            val destinationError = processNode(feature.get(name), sentence)
            destinationError match {
              case Pair(Some(destination), None) => {
                true
              }
              case Pair(None, Some(e: Error)) => {
                false
              }
            }
          }
        }
        val conceptLinks: List[ConceptLink] = filteredDestinationFeatures.toList.map(
          (name: String) => {
            val destinationError = processNode(feature.get(name), sentence)
            destinationError match {
              case Pair(Some(destination), None) => {
                val conceptLinkName = if (name.indexOf("_") > -1) {
                  name.substring(1)
                } else {
                  name
                }
                ConceptLink(source, destination, conceptLinkName)
              }
            }
          }
        )
        conceptLinks
      } else {
        log.warn("$No_links_found")
        //throw new UnexpectedException("$No_links_found")
        List[ConceptLink]()
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
    Constant.RelexFeatures.map {
      name: String => order.add(name)
    }
    new FeatureNameFilter(ignores, order)
  }
}
