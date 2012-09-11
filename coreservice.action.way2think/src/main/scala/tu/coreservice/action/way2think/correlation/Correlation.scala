package tu.coreservice.action.way2think.correlation

import tu.model.knowledge.annotator.AnnotatedNarrative
import tu.model.knowledge.domain.{ConceptLink, Concept, ConceptNetwork}
import tu.coreservice.action.way2think.SimulationReformulationAbstract

/**
 * @author max talanov
 *         date 2012-09-10
 *         time: 11:15 PM
 */
class Correlation extends SimulationReformulationAbstract {

  def apply(clarification: AnnotatedNarrative, simulationResult: ConceptNetwork, domainModel: ConceptNetwork): Option[ConceptNetwork] = {
    val notKnown: List[Concept] = filterConceptListNegative(simulationResult.nodes, domainModel)
    if (notKnown.size > 0) {
      Some(ConceptNetwork(processNotKnown(notKnown, clarification, domainModel), this.getClass.getName + "result"))
    } else {
      None
    }
  }

  /**
   * Crates mapping of notKnown List[Concept] to targetModel via mappingNarrative concepts, creating new concept List.
   * @param notKnown List of concepts to be mapped.
   * @param mappingNarrative AnnotatedNarrative to be used for mapping.
   * @param targetModel the List of Concept-s to be mapped to.
   * @return notKnown List[Concept] mapped to targetModel
   */
  def processNotKnown(notKnown: List[Concept], mappingNarrative: AnnotatedNarrative, targetModel: ConceptNetwork): List[Concept] = {
    val clarifiedConcepts = filterConceptList(notKnown, mappingNarrative.conceptNetwork)
    val clarifiedTargetConcepts = clarifiedConcepts.filter {
      c: Concept => {
        findInTarget(c, targetModel).size > 0
      }
    }
    clarifiedTargetConcepts.map {
      c: Concept => {
        findMapToTarget(c, targetModel, List[Concept]())
      }
    }.flatten
  }

  def findInTarget(mappingConcept: Concept, targetModel: ConceptNetwork): List[Concept] = {
    targetModel.getNodeByName(mappingConcept.uri.name)
  }

  def findMapToTarget(mappingConcept: Concept, targetModel: ConceptNetwork, processedConcepts: List[Concept]): List[Concept] = {
    if (findInTarget(mappingConcept, targetModel).size > 0) {
      List(mappingConcept)
    } else {
      if (mappingConcept.links.size > 0) {
        val filteredLinks = filteredLinks(mappingConcept.links, mappingConcept, processedConcepts)
        val intermediateConcepts = filterLinksConcepts(mappingConcept.links, mappingConcept)
        val res: List[List[Concept]] = intermediateConcepts.map {
          c: Concept => {
            findMapToTarget(c, targetModel, processedConcepts ::: List(mappingConcept))
          }
        }
        val foundMappings: List[List[Concept]] = res.filter {
          lC: List[Concept] => lC.size > 0
        }
        val shortestMapping: List[Concept] = foundMappings.reduceLeft((s1, s2) =>
          if (s2.size > s1.size) s1
          else s2
        )
        shortestMapping
      } else {
        List[Concept]()
      }
    }
  }

  def filterLinks(links: List[ConceptLink], currentConcept: Concept, processedLinks: List[Concept]): List[ConceptLink] = {
    links.filter {
      link: ConceptLink => {
        if (link.source == currentConcept) {
          val destination = link.destination
          !processedLinks.contains(destination)
        } else {
          val source = link.source
          !processedLinks.contains(source)
        }
      }
    }
  }

  def filterLinksConcepts(links: List[ConceptLink], currentConcept: Concept): List[Concept] = {
    links.map {
      link: ConceptLink => {
        if (link.source == currentConcept) {
          link.destination

        } else {
          link.source
        }
      }
    }
  }
}
