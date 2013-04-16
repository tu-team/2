package tu.coreservice.action.way2think.simulation

import tu.model.knowledge.domain.{Concept, ConceptNetwork}
import tu.model.knowledge.annotator.{AnnotatedSentence, AnnotatedPhrase, AnnotatedNarrative}
import tu.model.knowledge.communication.{ContextHelper, ShortTermMemory}
import tu.model.knowledge.Resource
import tu.model.knowledge.primitive.KnowledgeString
import java.rmi.UnexpectedException
import tu.coreservice.action.way2think.SimulationReformulationAbstract
import org.slf4j.LoggerFactory


/**
 * Simple direct concept to concept Simulation Way2Think implementation.
 * @author max talanov
 *         date 2012-06-25
 *         time: 12:45 PM
 */

class Simulation extends SimulationReformulationAbstract {
  val log = LoggerFactory.getLogger(this.getClass)
  val name: String = "Simulation"

  /**
   * Runs through AnnotatedPhrase from AnnotatedNarrative and creates ConceptNetwork of instances of the simulationModel.
   * @param in AnnotatedNarrative to Simulate.
   * @param simulationModel ConceptNetwork base model.
   * @return ConceptNetwork simulation result.
   */
  def apply(in: AnnotatedNarrative, simulationModel: ConceptNetwork): Option[ConceptNetwork] = {

    //Assumption: as KBAnnotator already created the phrases concepts tree,
    // the Sentence structure is not so important here
    val exactMatch: List[AnnotatedPhrase] = in.sentences.map {
      sentence: AnnotatedSentence => {
        val filteredPhrases: List[AnnotatedPhrase] = sentence.phrases.filter {
          phrase: AnnotatedPhrase => {
            this.filterPhrase(phrase, simulationModel).size == 1
          }
        }
        filteredPhrases
      }
    }.flatten

    val hasMatches: List[AnnotatedPhrase] = in.sentences.map {
      sentence: AnnotatedSentence => {
        val filteredPhrases: List[AnnotatedPhrase] = sentence.phrases.filter {
          phrase: AnnotatedPhrase => {
            this.filterPhrase(phrase, simulationModel).size > 0
          }
        }
        filteredPhrases
      }
    }.flatten

    val ambiguous: List[AnnotatedPhrase] = in.sentences.map {
      sentence: AnnotatedSentence => {
        val filteredPhrases: List[AnnotatedPhrase] = sentence.phrases.filter {
          phrase: AnnotatedPhrase => {
            this.filterPhrase(phrase, simulationModel).size > 1
          }
        }
        filteredPhrases
      }
    }.flatten

    val notKnown: List[AnnotatedPhrase] = in.sentences.map {
      sentence: AnnotatedSentence => {
        val filteredPhrases: List[AnnotatedPhrase] = sentence.phrases.filter {
          phrase: AnnotatedPhrase => {
            this.filterPhrase(phrase, simulationModel).size < 1
          }
        }
        filteredPhrases
      }
    }.flatten
    log debug("exact matches={}, matches={}", exactMatch, hasMatches)
    log debug("ambiguous={}", ambiguous)
    log debug("not known={}", notKnown)
    val unAmbiguous = processAmbiguousBackReferences(ambiguous, in)
    log debug("processed ambiguous={}", unAmbiguous)

    if (notKnown.size > 0) {
      return Some(ConceptNetwork.apply(notKnown))
    }

    if (exactMatch.size > 0) {
      return Some(this.processMatches(hasMatches, exactMatch, unAmbiguous, simulationModel))
    }
    None
  }

  private def filterPhrase(phrase: AnnotatedPhrase, simulationModel: ConceptNetwork): List[Concept] = {
    val modelConcepts: List[Concept] = simulationModel.getNodeByPhrase(phrase)
    modelConcepts
  }

  /**
   * Returns map of AnnotatedPhrase to most referenced concept of the AnnotatedPhrase
   * @param in List[AnnotatedPhrase] sentences to be processed.
   * @param text AnnotatedNarrative to find references.
   * @return Map[AnnotatedPhrase, Concept]
   */
  def processAmbiguousBackReferences(in: List[AnnotatedPhrase], text: AnnotatedNarrative): Map[AnnotatedPhrase, Concept] = {
    val mostReferencedConcept: List[Pair[AnnotatedPhrase, Concept]] = in.map {
      phrase: AnnotatedPhrase => {
        Pair(phrase, phrase.concepts.reduceLeft((c1, c2) => {
          if (countLinks(c1, text) > countLinks(c2, text)) c1
          else c2
        }))
      }
    }
    mostReferencedConcept.toMap
  }

  private def countLinks(concept: Concept, text: AnnotatedNarrative): Int = {
    this.countLinks(concept, text.concepts)
  }

  private def processMatches(matches: List[AnnotatedPhrase],
                             exactMatches: List[AnnotatedPhrase],
                             unAmbiguous: Map[AnnotatedPhrase, Concept],
                             simulationModel: ConceptNetwork): ConceptNetwork = {
    // merge exact match and unAmbiguous
    val concepts: List[Concept] = matches.map {
      phrase: AnnotatedPhrase => {
        if (exactMatches.contains(phrase)) {
          phrase.concepts.head
        } else if (unAmbiguous.contains(phrase)) {
          unAmbiguous.get(phrase) match {
            case Some(res: Concept) => {
              res
            }
            case None => {
              throw new UnexpectedException("$Filtered_phrases_must_contains_concepts")
            }
          }
        } else {
          throw new UnexpectedException("$Filtered_phrases_must_be_either_in_exact_matches_or_in_unambigous")
        }
      }
    }
    val res = instantiateConcepts(concepts, name, simulationModel)
    log info("processed matches={}", res.toString)
    res
  }
}
