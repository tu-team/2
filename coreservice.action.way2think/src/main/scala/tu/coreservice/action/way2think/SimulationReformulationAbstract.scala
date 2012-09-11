package tu.coreservice.action.way2think

import cry4help.Cry4HelpWay2Think
import tu.model.knowledge.annotator.AnnotatedPhrase
import tu.model.knowledge.domain.{ConceptLink, Concept, ConceptNetwork}
import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.Resource
import tu.model.knowledge.primitive.KnowledgeString

/**
 * @author max talanov
 *         date 2012-07-03
 *         time: 12:01 PM
 */

trait SimulationReformulationAbstract {

  /**
   * Filters list of concepts, returns only concepts found in model.
   * @param currentConcepts List[Concept] to filter.
   * @param model ConceptNetwork to be filtered at.
   * @return List[Concept] filtered model concepts.
   */
  def filterConceptList(currentConcepts: List[Concept], model: ConceptNetwork): List[Concept] = {
    val concepts: List[Concept] = currentConcepts.filter {
      c => {
        model.getNodeByName(c.uri.name).size > 0
      }
    }
    concepts
  }

  /**
   * Filters list of concepts, returns only concepts not found in model.
   * @param currentConcepts List[Concept] to filter.
   * @param model ConceptNetwork to be filtered at.
   * @return List[Concept] filtered.
   */
  def filterConceptListNegative(currentConcepts: List[Concept], model: ConceptNetwork): List[Concept] = {
    val concepts: List[Concept] = currentConcepts.filter {
      c => {
        model.getNodeByName(c.uri.name).size < 1
      }
    }
    concepts
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
   * Counts links of the specified concept to concepts of the List
   * @param concept to count links.
   * @param list List[Concept] to filter links of the specified concept.
   * @return Int number of filtered links
   */
  def countLinks(concept: Concept, list: List[Concept]): Int = {
    concept.links.filter {
      link: ConceptLink => {
        if (link.source == concept) {
          val destination = link.destination
          val references: List[Concept] = list.filter {
            c: Concept => c equals destination
          }
          references.size > 0
        } else {
          val source = link.source
          val references: List[Concept] = list.filter {
            c: Concept => c equals source
          }
          references.size > 0
        }
      }
    }.size
  }

  /**
   * Creates ConceptNetwork using Concept instances crated from concepts parameter and their links.
   * @param concepts List[Concept] to process.
   * @param name String name of ConceptNetwork.
   * @return ConceptNetwork.
   */
  def instantiateConcepts(concepts: List[Concept], name: String, simulationModel: ConceptNetwork): ConceptNetwork = {
    var processedConcepts: List[Concept] = List[Concept]()
    val instancesLinks: List[Pair[Concept, List[ConceptLink]]] = concepts.map(
      (concept: Concept) => {
        val currentInstance = Concept.createInstanceConcept(concept)
        val incidentLinksNotProcessed: List[ConceptLink] = concept.links.filter {
          link: ConceptLink => {
            if (simulationModel.getLinkByName(link.uri.name).size > 0) {
              if (link.source == concept) {
                !processedConcepts.contains(link.destination)
              } else if (link.destination == concept) {
                !processedConcepts.contains(link.source)
              } else {
                false
              }
            } else {
              false
            }
          }
        }

        val modelConcepts = simulationModel.getNodeByName(concept.uri.name)
        val modelLinksNotProcessed: List[ConceptLink] = if (modelConcepts.size > 0) {
          val mLinks: List[ConceptLink] = modelConcepts.head.links.filter(
            (link: ConceptLink) => {
              !incidentLinksNotProcessed.contains(link)
            }
          )
          val gLinks: List[ConceptLink] = getGeneralisedLinks(modelConcepts.head, concepts)

          val gFLinks: List[ConceptLink] = if (gLinks.size > 0) {
            gLinks.filter {
              g: ConceptLink => !incidentLinksNotProcessed.contains(g)
            }
          } else {
            List[ConceptLink]()
          }
          mLinks ::: gFLinks
        } else {
          List[ConceptLink]()
        }
        val notProcessedLinks = incidentLinksNotProcessed ::: modelLinksNotProcessed
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
    )

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

  def getGeneralisedLinks(concept: Concept, incidentConcepts: List[Concept]): List[ConceptLink] = {
    val gLinks: List[ConceptLink] = concept.generalisations.frames.values.map {
      c: Concept =>
        this.getGeneralisedLinks(c, incidentConcepts)
    }.toList.flatten

    val filteredConceptLinks = concept.links.filter {
      l: ConceptLink => {
        if (l.source == concept) {
          selectWithGeneralisations(l.destination, incidentConcepts).size > 0
        } else {
          selectWithGeneralisations(l.source, incidentConcepts).size > 0
        }
      }
    }
    filteredConceptLinks ::: gLinks
  }


  def selectWithGeneralisations(concept: Concept, checkList: List[Concept]): List[Concept] = {
    checkList.filter {
      c: Concept => {
        c.getGeneralisationsRec().filter {
          cG: Concept => cG.uri.name == concept.uri.name
        }.size > 0 || c.uri.name == concept.uri.name
      }
    }
  }
}
