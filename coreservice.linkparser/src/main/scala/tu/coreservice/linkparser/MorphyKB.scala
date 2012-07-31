package tu.coreservice.linkparser

import relex.morphy.{MorphyJWNL, Morphed}
import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.annotator.{AnnotatedWord, AnnotatedPhrase, AnnotatedSentence}
import java.rmi.UnexpectedException
import tu.model.knowledge.frame.Frame
import tu.model.knowledge.{Resource, KnowledgeURI}
import tu.model.knowledge.domain.Concept
import relex.feature.FeatureNode
import java.util

/**
 * @author alex toschev, max talanov
 *         Time stamp: 7/25/12 5:40 PM
 */

class MorphyKB(var _context: Context) extends MorphyJWNL {

  def this() {
    this(ContextHelper(List[Resource](), "MorphyKBContext"))
  }

  def context = _context
  def context_=(in: Context) = _context = in

  final val NOUN_F: String = "noun"
  final val VERB_F: String = "verb"
  final val ADJ_F: String = "adj"
  final val ADV_F: String = "adv"
  final val ROOT_F: String = "root"
  final val TYPE_F: String = "type"
  final val NEG_F: String = "neg"

  val subjectConceptName = "subject"
  val objectConceptName = "object"
  val actionConceptName = "action"
  val desireConceptName = "desire"
  val formOfPoliteness = "formOfPoliteness"

  override def morph(word: String): Morphed = {
    val res: Morphed = super.morph(word)
    val sentences: List[AnnotatedSentence] = processLastResult(_context)
    val foundPhrases: List[AnnotatedPhrase] = searchWord(word, sentences)
    if (foundPhrases.size < 1) {
      res
    } else if (foundPhrases.size == 1) {
      //unambiguous case
      // get concepts and correct annotation
      val mostGenericConcepts: List[Concept] = foundPhrases(0).concepts.map {
        concept: Concept => {
          mostGenericGeneralisation(concept)
        }
      }.flatten

      /* if (cat.equals("noun"))
m.putRoot(NOUN_F, root);
else if (cat.equals("verb")) {
if (negativeVerb)
m.putRootNegative(VERB_F, root);
else
m.putRoot(VERB_F, root);
} else if (cat.equals("adj"))
m.putRoot(ADJ_F, root);
else if (cat.equals("adv"))
m.putRoot(ADV_F, root);
else
throw new RuntimeException("Unknown WordNet category: [" + cat + "] with root [" + root + "]"); */

      if (!getConceptByName(mostGenericConcepts, subjectConceptName).isEmpty) {
        res.getFeatures.clear()
        res.putRoot(this.NOUN_F, word)
      } else if (!getConceptByName(mostGenericConcepts, objectConceptName).isEmpty) {
        res.getFeatures.clear()
        res.putRoot(this.NOUN_F, word)
      } else if (!getConceptByName(mostGenericConcepts, actionConceptName).isEmpty) {
        res.getFeatures.clear()
        res.putRoot(VERB_F, word)
      } else if (!getConceptByName(mostGenericConcepts, desireConceptName).isEmpty) {
        res.getFeatures.clear()
        res.putRoot(VERB_F, word)
      } else if (!getConceptByName(mostGenericConcepts, formOfPoliteness).isEmpty) {
        res.getFeatures.clear()
        res.putRoot(ADV_F, word)
      }
      res
    } else {
      throw new UnexpectedException("$Ambiguous_case")
    }
  }

  /**
   * Gets concept by name from List of Concept-s
   * @param concepts to find Concept.
   * @param name to filter concepts.
   * @return Option[Concept].
   */
  def getConceptByName(concepts: List[Concept], name: String): Option[Concept] = {
    concepts.find {
      concept: Concept => {
        concept.uri.name == (name + "Concept")
      }
    }
  }

  /**
   * Get most generic generalisation of specified concept, if no generalisations found in current concept, current concept is returned.
   * @param concept to process.
   * @return most generic concept.
   */
  def mostGenericGeneralisation(concept: Concept): List[Concept] = {
    if (concept.generalisations.frames.size < 1) {
      List(concept)
    } else {
      mostGenericGeneralisation(concept.generalisations.frames.values.toList)
    }
  }

  def mostGenericGeneralisation(concepts: List[Concept]): List[Concept] = {

    val nonGeneralisedConcepts = concepts.filter {
      concept: Concept => {
        concept.generalisations.frames.size == 0
      }
    }
    val generalisedConcepts = concepts.filter {
      concept: Concept => {
        concept.generalisations.frames.size > 0
      }
    }

    val mostGenericConcepts = generalisedConcepts.map {
      concept: Concept => {
        mostGenericGeneralisation(concept.generalisations.frames.values.toList)
      }
    }.flatten
    mostGenericConcepts ::: nonGeneralisedConcepts
  }

  def searchWord(aWord: String, sentences: List[AnnotatedSentence]): List[AnnotatedPhrase] = {
    val phrases: List[AnnotatedPhrase] = sentences.map {
      sentence: AnnotatedSentence => {
        sentence.phrases
      }
    }.flatten
    phrases.filter {
      phrase: AnnotatedPhrase => {
        phrase.words.count {
          word: AnnotatedWord => {
            word.value == aWord
          }
        } > 0
      }
    }
  }

  def processLastResult(context: Context): List[AnnotatedSentence] = {
    val lastResult = context.lastResult
    lastResult match {
      case Some(frame: Frame) => {
        val resourcesSentences: List[AnnotatedSentence] = frame.resources.filter {
          uriResource: Pair[KnowledgeURI, Resource] => {
            uriResource._2 match {
              case aS: AnnotatedSentence => {
                true
              }
              case _ => false
            }
          }
        }.map {
          uriResource: Pair[KnowledgeURI, Resource] => {
            uriResource._2.asInstanceOf[AnnotatedSentence]
          }
        }.toList
        resourcesSentences
      }
      case _ => {
        throw new UnexpectedException("$Last_result_contains_unexpected_type " + lastResult.getClass.getName)
      }
    }
  }
}
