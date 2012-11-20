package tu.dataservice.memory

import tu.model.knowledge._
import communication.ShortTermMemory
import tu.dataservice.knowledgebaseserver.{Defaults, KBAdapter}
import tu.model.knowledge.domain.{Concept, ConceptNetwork}
import tu.dataservice.knowledgebaseserver.providers.N4JKB
import tu.model.knowledge.communication.ShortTermMemory
import collection.mutable.ListBuffer
import org.slf4j.LoggerFactory

/**
 * @author max talanov
 *         Date: 10/16/12
 *         Time: 12:35 AM
 */
object LongTermMemory {
  val log = LoggerFactory.getLogger(this.getClass)

  private def kb: KB = N4JKB;

  /**
   * Merges domain addressed via domainURI and specified Resource via substation of longTermMemoryResource by resource of ShortTermMemoryResourceWrapper.
   * @param updatedConcept the ShortTermMemoryResourceWrapper that contains updatedConcept and longTermMemoryResource.
   * @param domainURI the URI of domain to be updated.
   * @return updated domain ConceptNetwork.
   */
  def merge(updatedConcept: ShortTermMemoryConceptWrapper, domainURI: KnowledgeURI): ConceptNetwork = {
    val domainModel = KBAdapter.domainModel(domainURI)
    if (ConceptNetwork.containsConceptByURI(domainModel, updatedConcept.longTermMemoryConcept.uri)) {
      val copiedConcept = Concept.copy(updatedConcept.resource, updatedConcept.longTermMemoryConcept)
      val preservedConcepts = domainModel.nodes.filterNot {
        c: Concept => updatedConcept.longTermMemoryConcept.uri.equals(c.uri)
      }
      domainModel.nodes = preservedConcepts ::: List(copiedConcept) ::: copiedConcept.generalisations.frames.values.toList :::
        copiedConcept.specialisations.frames.values.toList ::: copiedConcept.linkedConcepts.toList
    }
    domainModel
  }

  def solutions(domain: KnowledgeURI): List[SolvedIssue] = {
    KBAdapter.solutions()
  }

  /**
   * load concept network according to selected domain
   * @param domain uri of selected domain
   * @return concept network
   */
  def domainModel(domain: KnowledgeURI): ConceptNetwork = someModel(domain)


  /**
   * load simulation model according to selected domain
   * @param domain uri of selected domain
   * @return  simulation model
   */
  def simulationModel(domain: KnowledgeURI): ConceptNetwork = someModel(domain)

  /**
   * reformulation model according to selected domain
   * @param domain uri of selected domain
   * @return reformulation model
   */
  def reformulationModel(domain: KnowledgeURI): ConceptNetwork = someModel(domain)

  private def someModel(modelName: KnowledgeURI): ConceptNetwork = {
    try {
      ConceptNetwork.load(kb, KBNodeId(0), modelName.uri().get.toString, Constant.DEFAULT_LINK_NAME)
    }
    catch {
      case e: Exception =>
        log.error(e.toString)
        val res: ConceptNetwork = Defaults.domainModelConceptNetwork
        res.save(kb, KBNodeId(0), modelName.uri().get.toString, Constant.DEFAULT_LINK_NAME)
        someModel(modelName)
    }
  }


  /**
   * save provided model in storage
   * @param modelName uri of target model
   * @param model model
   */
  def saveModel(modelName: KnowledgeURI, model: ConceptNetwork) = {
    //instantiate save context
    val alreadySaved = new ListBuffer[String]()
    model.save(kb, KBNodeId(0), modelName.uri().get.toString, Constant.DEFAULT_LINK_NAME, alreadySaved)

  }

  /**
   * save the whole ST context to LT memory
   * @param context to be transferred to LT memory
   */
  def saveShortTermContext(context: ShortTermMemory) = {

    if (context.domainModel.isDefined)
    //save domain model
      saveModel(context.domainModel.get.uri, context.domainModel.get)

    if (context.reformulationModel.isDefined)
    //save reformulation model
      saveModel(context.reformulationModel.get.uri, context.reformulationModel.get)

    if (context.simulationModel.isDefined)
    //save simulationModel model
      saveModel(context.simulationModel.get.uri, context.simulationModel.get)

  }

}
