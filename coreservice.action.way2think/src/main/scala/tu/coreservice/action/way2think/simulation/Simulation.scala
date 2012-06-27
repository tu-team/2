package tu.coreservice.action.way2think.simulation

import tu.model.knowledge.domain.{ConceptLink, Concept, ConceptNetwork}
import tu.model.knowledge.annotator.{AnnotatedPhrase, AnnotatedNarrative}
import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.Resource
import tu.model.knowledge.primitive.KnowledgeString
import tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think
import java.rmi.UnexpectedException

/**
 * Simple direct concept to concept Simulation Way2Think implementation.
 * @author max talanov
 *         date 2012-06-25
 *         time: 12:45 PM
 */

class Simulation {
  val name: String = "Simulation"

  /**
   * Runs through AnnotatedPhrase from AnnotatedNarrative and creates ConceptNetwork of instances of the simulationModel.
   * @param in AnnotatedNarrative to Simulate.
   * @param simulationModel ConceptNetwork base model.
   * @return ConceptNetwork simulation result.
   */
  def apply(in: AnnotatedNarrative, simulationModel: ConceptNetwork): Option[ConceptNetwork] = {

    // val instancesList: List[Concept] =
    // check concept of a phrase and if it is in simulationModel crate it's instance.
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
    val modelConcepts: List[Concept] = phrase.concepts.filter {
      c => simulationModel.nodes.contains(c)
    }
    modelConcepts
  }

  /**
   * Really stupid method to process ambiguity.
   * @param in List[Concept]
   * @return Context of Cry4HelpWay2Think
   * @deprecated
   */
  def processAmbiguous(in: List[AnnotatedPhrase]): Context = {
    // ambiguity
    var res: List[Resource] = in
    res = res ++ List[Resource](KnowledgeString("Please clarify ambiquity", "Please.clarify.ambiquity"))
    val context = ContextHelper.apply(res, "ambiguity")
    val cry4helpWay2Think = new Cry4HelpWay2Think()
    cry4helpWay2Think.apply(context)
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
    concept.links.filter {
      link: ConceptLink => {
        if (link.source == concept) {
          val destination = link.destination
          val references: List[Concept] = text.concepts.filter {
            c: Concept => c equals destination
          }
          references.size > 0
        } else {
          val source = link.source
          val references: List[Concept] = text.concepts.filter {
            c: Concept => c equals source
          }
          references.size > 0
        }
      }
    }.size
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
    val concepts: List[Concept] = matches.map{
      phrase: AnnotatedPhrase => {
        if (exactMatches.contains(phrase)) {
          phrase.concepts(0)
        } else if(unAmbiguous.contains(phrase)) {
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
    simulateMatches(concepts)
  }

  private def simulateMatches(concepts: List[Concept]): ConceptNetwork = {
    var processedConcepts: List[Concept] = List[Concept]()
    val instancesLinks: List[Pair[Concept, List[ConceptLink]]] = concepts.map {
      concept: Concept => {
        val currentInstance = Concept.createInstanceConcept(concept)
        val notProcessedLinks: List[ConceptLink] = concept.links.filter {
          link: ConceptLink => {
            if (link.source == concept) {
              !processedConcepts.contains(link.destination)
            } else if (link.destination == concept) {
              !processedConcepts.contains(link.source)
            } else {
              false
            }
          }
        }

        val linkInstances = notProcessedLinks.map {
          link: ConceptLink => {
            if (link.source == concept) {
              val currentDestination = Concept.createInstanceConcept(link.destination)
              ConceptLink.createInstanceConceptLink(link, currentInstance, currentDestination)
            } else {
              val currentSource = Concept.createInstanceConcept(link.source)
              ConceptLink.createInstanceConceptLink(link, currentSource, currentInstance)
            }
          }
        }
        currentInstance.links = linkInstances
        processedConcepts = processedConcepts ++ List(concept)
        (currentInstance, linkInstances)
      }
    }
    val instances: List[Concept] = instancesLinks.map {
      i: Pair[Concept, List[ConceptLink]] => {
        i._1
      }
    }

    val links: List[List[ConceptLink]] = instancesLinks.map {
      i: Pair[Concept, List[ConceptLink]] => {
        i._2
      }
    }
    val flatLinks: List[ConceptLink] = links.flatten
    ConceptNetwork(instances, flatLinks, name)
  }
}
