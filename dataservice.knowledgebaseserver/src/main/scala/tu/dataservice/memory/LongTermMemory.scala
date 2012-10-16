package tu.dataservice.memory

import tu.model.knowledge.KnowledgeURI
import tu.dataservice.knowledgebaseserver.KBAdapter
import tu.model.knowledge.domain.ConceptNetwork

/**
 * @author max talanov
 *         Date: 10/16/12
 *         Time: 12:35 AM
 */
object LongTermMemory {

  /**
   * Merges domain addressed via domainURI and specified Resource via substation of longTermMemoryResource by resource of ShortTermMemoryResourceWrapper.
   * @param updatedResource the ShortTermMemoryResourceWrapper that contains updatedResource and longTermMemoryResource.
   * @param domainURI the URI of domain to be updated.
   * @return updated domain ConceptNetwork.
   */
  def merge(updatedResource: ShortTermMemoryResourceWrapper, domainURI: KnowledgeURI): ConceptNetwork = {
    val domainModel = KBAdapter.domainModel(domainURI)
    if (domainModel.nodes.contains(updatedResource.longTermMemoryResource)) {
      domainModel
    }
    domainModel
  }

}
