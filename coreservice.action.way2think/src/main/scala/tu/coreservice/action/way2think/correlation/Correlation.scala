package tu.coreservice.action.way2think.correlation

import tu.model.knowledge.annotator.{AnnotatedPhrase, AnnotatedNarrative}
import tu.model.knowledge.domain.{ConceptLink, Concept, ConceptNetwork}
import tu.coreservice.action.way2think.SimulationReformulationAbstract
import tu.model.knowledge.{KnowledgeURI, Constant}
import tu.exception.UnexpectedException
import org.slf4j.LoggerFactory
import scala.collection.JavaConversions._
import tu.dataservice.knowledgebaseserver.Defaults

/**
 * @author max talanov
 *         date 2012-09-10
 *         time: 11:15 PM
 */
class Correlation extends SimulationReformulationAbstract {

  val log = LoggerFactory.getLogger(this.getClass)

  /**
   * Process clarification over simulationResult, creating mapping from simulationResult to domainModel
   * @param clarification AnnotatedNarrative to map simulationResult to domainModel
   * @param simulationResult clarified
   * @param domainModel model to be mapped to
   * @return Option of Triple of shortestMaps, domainConcepts, notUnderstood concepts from clarification response.
   */
  def apply(clarification: AnnotatedNarrative,
            simulationResult: ConceptNetwork,
            domainModel: ConceptNetwork): Option[Triple[List[Concept], List[Concept], List[Concept]]] = {
    val notKnown: List[Concept] = filterConceptListNegative(simulationResult.nodes, domainModel)
    if (notKnown.size > 0) {
      val processed = processNotKnown(notKnown, clarification, domainModel)
      Some(processed._1, processed._2, processed._3)
    } else {
      None
    }
  }

  /**
   * Process clarification (simple training), creating mapping from clarification to domainModel
   * @param clarification AnnotatedNarrative to map simulationResult to domainModel
   * @param domainModel model to be mapped to
   * @return Option of Triple of shortestMaps, domainConcepts, notUnderstood concepts from clarification response.
   */
  def apply(clarification: AnnotatedNarrative,
            domainModel: ConceptNetwork): Option[Triple[List[Concept], List[Concept], List[Concept]]] = {
    val processed = processClarification(clarification, domainModel)
    Some(processed._1, processed._2, processed._3)
  }

  /**
   * Creates mapping of mappingNarrative to targetModel via mappingNarrative concepts, creating new concept List.
   * @param mappingNarrative AnnotatedNarrative to be used for mapping.
   * @param targetModel the List of Concept-s to be mapped to.
   * @return Triple of shortestMaps, domainConcepts, notUnderstood concepts from clarification response.
   */
  def processClarification(mappingNarrative: AnnotatedNarrative,
                           targetModel: ConceptNetwork): Triple[List[Concept], List[Concept], List[Concept]] = {
    val clarifiedConcepts = mappingNarrative.conceptNetwork.nodes
    processClarifiedConcepts(clarifiedConcepts, targetModel)
  }

  /**
   * Creates mapping of notKnown List[Concept] to targetModel via mappingNarrative concepts, creating new concept List.
   * @param notKnown List of concepts to be mapped.
   * @param mappingNarrative AnnotatedNarrative to be used for mapping.
   * @param targetModel the List of Concept-s to be mapped to.
   * @return Triple of shortestMaps, domainConcepts, notUnderstood concepts from clarification response.
   */
  def processNotKnown(notKnown: List[Concept],
                      mappingNarrative: AnnotatedNarrative,
                      targetModel: ConceptNetwork): Triple[List[Concept], List[Concept], List[Concept]] = {
    val clarifiedConcepts = filterConceptList(notKnown, mappingNarrative.conceptNetwork)
    processClarifiedConcepts(clarifiedConcepts, targetModel)
  }

  def processClarifiedConcepts(clarifiedConcepts: List[Concept], targetModel: ConceptNetwork): Triple[List[Concept], List[Concept], List[Concept]] = {
    val clarifiedTargetConcepts = clarifiedConcepts.filter {
      c: Concept => {
        findInTarget(c, targetModel) match {
          case Some(c: Concept) => false
          case None => true
        }
      }
    }
    val shortestMaps: List[List[Concept]] = clarifiedTargetConcepts.map {
      c: Concept => {
        findMapToTarget(c, targetModel, List[Concept]())
      }
    }
    val notUnderstood = this.checkShortestMaps(clarifiedConcepts, shortestMaps, targetModel)
    val domainConcepts = createDomainConcepts(shortestMaps.flatten)
    log.info("found maps={}, ", shortestMaps)
    log.info("created domain concepts={},", domainConcepts)
    log.info("not understood concepts={}", notUnderstood)
    (shortestMaps.flatten, domainConcepts, notUnderstood)
  }

  /**
   * Checks if all shortest maps tail concepts are in targetModel.
   * @param shortestMaps = maps to check
   * @return List of tail concepts not found in targetModel.
   */
  def checkShortestMaps(clarifiedTargetConcepts: List[Concept], shortestMaps: List[List[Concept]], targetModel: ConceptNetwork):
  List[Concept] = {
    val notUnderstood = shortestMaps.filter {
      c: List[Concept] => {
        c.size > 0 && targetModel.getNodeByName(c.last.uri.name).size > 0
      }
    }
    val notMapped = clarifiedTargetConcepts.filter {
      c: Concept => {
        shortestMaps.flatten.filter {
          cSM: Concept => cSM.uri.name.equals(c.uri.name)
        }.size > 0
      }
    }
    val compoundNotUnderstood = notMapped ::: notUnderstood
    if (compoundNotUnderstood.size > 0) {
      notUnderstood.map {
        c: List[Concept] => {
          c.last
        }
      }
    } else {
      List[Concept]()
    }
  }

  def createDomainConcepts(mappingConceptsInstances: List[Concept]): List[Concept] = {
    val parents = mappingConceptsInstances.map {
      c: Concept => {
        if (c.uri.name.contains(Constant.UID_INSTANCE_DELIMITER)) {
          val parentName = if (c.uri.name.indexOf(Constant.conceptSuffix) != -1
            && c.uri.name.indexOf(Constant.conceptSuffix) < c.uri.name.indexOf(Constant.UID_INSTANCE_DELIMITER)) {
            c.uri.name.substring(0, c.uri.name.indexOf(Constant.conceptSuffix))
          } else {
            c.uri.name.substring(0, c.uri.name.indexOf(Constant.UID_INSTANCE_DELIMITER))
          }
          val parentConcept = Concept(parentName)
          c.generalisations = c.generalisations + (parentConcept.uri -> parentConcept)
          parentConcept
        } else {
          throw new UnexpectedException("$Can_not_create_parent_not_from_instance")
        }
      }
    }
    reduceLinks(mappingConceptsInstances)
    parents ::: mappingConceptsInstances
  }

  def reduceLinks(mappingConceptsInstances: List[Concept]): List[ConceptLink] = {
    val reducibleConcepts = mappingConceptsInstances.filter {
      c: Concept => {
        Defaults.reduceLinks.keySet.filter {
          cReducible: Concept => {
            c.hasGeneralisationRec(cReducible)
          }
        }.size > 0
      }
    }
    reducibleConcepts.map {
      c: Concept => {
        val optionKey = Defaults.reduceLinks.keySet.find {
          cReducible: Concept => {
            c.hasGeneralisationRec(cReducible)
          }
        }
        optionKey match {
          case Some(x: Concept) => {
            val optionLink = Defaults.reduceLinks.get(x)
            optionLink match {
              case Some(x: ConceptLink) => {
                createLinkFromConcept(c, x)
              }
              case None => {
                throw new UnexpectedException("$Filtered_concepts_should_contain_key")
              }
            }
          }
          case None => {
            throw new UnexpectedException("$Filtered_concepts_should_contain_key")
          }
        }
      }
    }
  }

  def createLinkFromConcept(concept: Concept, link: ConceptLink): ConceptLink = {
    val links: List[ConceptLink] = concept.links
    val destinations: List[ConceptLink] = links.filter {
      cL: ConceptLink => cL.destination.uri.name != concept.uri.name && Defaults.reduceLinks.contains(cL.uri.name)
    }
    val sources: List[ConceptLink] = links.filter {
      cL: ConceptLink => cL.source.uri.name != concept.uri.name && Defaults.reduceLinks.contains(cL.uri.name)
    }

    if (sources.size + destinations.size < 1) {
      throw new UnexpectedException("$Not_enough_links")
    }
    if (sources.size + destinations.size > 2) {
      throw new UnexpectedException("$Ambiguous_links")
    } else {
      ConceptLink.createSubConceptLink(link, sources(0).source, destinations(0).destination, link.uri.name)
    }
  }

  /**
   * Process lists of sources and destination ConceptLink-s and return pair source -> destination, if at least one source and destination set first one is returned, if at least two sources or destinations are set
   * links with name Defaults.subjectLinkName is treated as source, Defaults.objectLinkName is treated as destination.
   * @param sources ConceptLinks to sources
   * @param destinations ConceptLinks to destinations
   * @return pair source -> destination
   */
  def processSourcesDestinations(sources: List[ConceptLink], destinations: List[ConceptLink]): Pair[Concept, Concept] = {

    if (sources.size > 0 && destinations.size > 0) {
      (sources(0).source, destinations(0).destination)
    } else if (sources.size > 1) {
      processLinks(sources)
    } else if (sources.size > 1) {
      processLinks(destinations)
    } else {
      throw new UnexpectedException("$Not_enough_links")
    }
  }

  private def processLinks(conceptLinks: List[ConceptLink]): Pair[Concept, Concept] = {
    val sources = conceptLinks.filter {
      cL: ConceptLink => {
        cL.uri.name == Defaults.subjectLinkName
      }
    }
    val destinations = conceptLinks.filter {
      cL: ConceptLink => {
        cL.uri.name == Defaults.objectLinkName
      }
    }
    if (sources.size > 0 && destinations.size > 0) {
      (sources(0).source, destinations(0).source)
    } else {
      throw new UnexpectedException("$Not_enough_links")
    }
  }

  def findInTarget(mappingConcept: Concept, targetModel: ConceptNetwork): Option[Concept] = {
    if (mappingConcept.phrases.size > 0) {
      val res = mappingConcept.phrases.frames.filter {
        (uriPhrase: Pair[KnowledgeURI, AnnotatedPhrase]) => {
          targetModel.getNodeByPhrase(uriPhrase._2).size > 0
        }
      }
      if (res.size > 0) {
        Some(mappingConcept)
      } else {
        None
      }
    } else {
      None
    }
  }

  def findMapToTarget(mappingConcept: Concept, targetModel: ConceptNetwork, processedConcepts: List[Concept]): List[Concept] = {
    findInTarget(mappingConcept, targetModel) match {
      case Some(c: Concept) => {
        List(c)
      }
      case None => {
        if (mappingConcept.links.size > 0) {
          val filteredLinks = filterLinks(mappingConcept.links, mappingConcept, processedConcepts)
          val intermediateConcepts = filterLinksConcepts(filteredLinks, mappingConcept)
          val res: List[List[Concept]] = intermediateConcepts.map {
            c: Concept => {
              findMapToTarget(c, targetModel, processedConcepts ::: List(mappingConcept))
            }
          }
          val foundMappings: List[List[Concept]] = res.filter {
            lC: List[Concept] => lC.size > 0
          }
          if (foundMappings.size > 0) {
            val shortestMapping: List[Concept] = foundMappings.reduceLeft((s1, s2) =>
              if (s2.size > s1.size) s1
              else s2
            )
            List(mappingConcept) ::: shortestMapping
          } else {
            List(mappingConcept)
          }
        } else {
          List(mappingConcept)
        }
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
