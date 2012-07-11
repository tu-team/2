package tu.coreservice.action.way2think.simulation

import tu.model.knowledge.domain.{ConceptLink, Concept, ConceptNetwork}
import tu.model.knowledge.annotator.{AnnotatedPhrase, AnnotatedNarrative}
import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.Resource
import tu.model.knowledge.primitive.KnowledgeString
import tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think
import java.rmi.UnexpectedException
import tu.coreservice.action.way2think.SimulationReformulationAbstract

/**
 * Simple direct concept to concept Simulation Way2Think implementation.
 * @author max talanov
 *         date 2012-06-25
 *         time: 12:45 PM
 */

class Simulation extends SimulationReformulationAbstract {
  val name: String = "Simulation"

  /**
   * Runs through AnnotatedPhrase from AnnotatedNarrative and creates ConceptNetwork of instances of the simulationModel.
   * @param in AnnotatedNarrative to Simulate.
   * @param simulationModel ConceptNetwork base model.
   * @return ConceptNetwork simulation result.
   */
  def apply(in: AnnotatedNarrative, simulationModel: ConceptNetwork): Option[ConceptNetwork] = {

    val exactMatch: List[AnnotatedPhrase] = in.phrases.filter {
      phrase: AnnotatedPhrase => {
        this.filterPhrase(phrase, simulationModel).size == 1
      }
    }

    val hasMatches: List[AnnotatedPhrase] = in.phrases.filter {
      phrase: AnnotatedPhrase => {
        this.filterPhrase(phrase, simulationModel).size > 0
      }
    }

    val ambiguous: List[AnnotatedPhrase] = in.phrases.filter {
      phrase: AnnotatedPhrase => {
        this.filterPhrase(phrase, simulationModel).size > 1
      }
    }

    val notKnown: List[AnnotatedPhrase] = in.phrases.filter {
      phrase: AnnotatedPhrase => {
        this.filterPhrase(phrase, simulationModel).size < 1
      }
    }

    val unAmbiguous = processAmbiguousBackReferences(ambiguous, in)

    if (notKnown.size > 0) {
      this.processNotKnown(notKnown)
    }

    if (exactMatch.size > 0) {
      return Some(this.processMatches(hasMatches, exactMatch, unAmbiguous))
    }
    None
  }

  private def filterPhrase(phrase: AnnotatedPhrase, simulationModel: ConceptNetwork): List[Concept] = {
    val modelConcepts: List[Concept] = this.filterConceptList(phrase.concepts, simulationModel)
    modelConcepts
  }

  /**
   * Returns map of AnnotatedPhrase to most referenced concept of the AnnotatedPhrase
   * @param in List[AnnotatedPhrase] phrases to be processed.
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

  private def processNotKnown(in: List[AnnotatedPhrase]): Context = {
    var res: List[Resource] = in
    res = res ++ List[Resource](KnowledgeString("Please clarify phreses", "Please.clarify.phrases"))
    val context = ContextHelper.apply(res, "notKnown")
    val cry4helpWay2Think = new Cry4HelpWay2Think()
    cry4helpWay2Think.apply(context)
  }

  private def processMatches(matches: List[AnnotatedPhrase],
                             exactMatches: List[AnnotatedPhrase],
                             unAmbiguous: Map[AnnotatedPhrase, Concept]): ConceptNetwork = {
    // merge exact match and unAmbiguous
    val concepts: List[Concept] = matches.map {
      phrase: AnnotatedPhrase => {
        if (exactMatches.contains(phrase)) {
          phrase.concepts(0)
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
    instantiateConcepts(concepts, name)
  }
}
