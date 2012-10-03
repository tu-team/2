package tu.coreservice.linkparser

import relex.morphy.{MorphyJWNL, Morphed}
import tu.model.knowledge.communication.Context
import tu.model.knowledge.annotator.{AnnotatedPhrase, AnnotatedSentence}
import java.rmi.UnexpectedException
import tu.model.knowledge.frame.Frame
import tu.model.knowledge.{Constant, Resource, KnowledgeURI}
import tu.model.knowledge.domain.Concept

/**
 * @author alex toschev, max talanov
 *         Time stamp: 7/25/12 5:40 PM
 */

class MorphyKB(var _sentences: List[AnnotatedSentence]) extends MorphyJWNL {

  def this() {
    this(List[AnnotatedSentence]())
  }

  def sentences = _sentences

  def sentences_=(in: List[AnnotatedSentence]) = _sentences = in

  override def morph(word: String): Morphed = {
    val res: Morphed = super.morph(word)
    val foundPhrases: List[AnnotatedPhrase] = searchWord(word, _sentences)
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

      if (!getConceptByName(mostGenericConcepts, Constant.subjectConceptName).isEmpty) {
        res.getFeatures.clear()
        res.putRoot(Constant.NOUN_F, word)
      } else if (!getConceptByName(mostGenericConcepts, Constant.objectConceptName).isEmpty) {
        res.getFeatures.clear()
        res.putRoot(Constant.NOUN_F, word)
      } else if (!getConceptByName(mostGenericConcepts, Constant.actionConceptName).isEmpty) {
        res.getFeatures.clear()
        res.putRoot(Constant.VERB_F, word)
      } else if (!getConceptByName(mostGenericConcepts, Constant.desireConceptName).isEmpty) {
        res.getFeatures.clear()
        res.putRoot(Constant.VERB_F, word)
      } else if (!getConceptByName(mostGenericConcepts, Constant.formOfPoliteness).isEmpty) {
        res.getFeatures.clear()
        res.putRoot(Constant.ADV_F, word)
      }
      res
    } else {
      throw new UnexpectedException("$Ambiguous_case")
    }
  }

  /**
   * Gets source by name from List of Concept-s
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
   * Get most generic generalisation of specified source, if no generalisations found in current source, current source is returned.
   * @param concept to process.
   * @return most generic source.
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
        phrase.phrases.count {
          word: AnnotatedPhrase => {
            word.text == aWord
          }
        } > 0 || phrase.text == aWord
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
