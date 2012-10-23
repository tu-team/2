package tu.dataservice.memory

import tu.model.knowledge.KnowledgeURI
import tu.dataservice.knowledgebaseserver.KBAdapter
import tu.model.knowledge.domain.{Concept, ConceptNetwork}

/**
 * @author max talanov
 *         Date: 10/16/12
 *         Time: 12:35 AM
 */
object LongTermMemory {

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

}
