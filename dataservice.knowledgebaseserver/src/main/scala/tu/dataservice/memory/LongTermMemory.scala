package tu.dataservice.memory

import tu.model.knowledge.{KnowledgeURI, Resource}
import tu.dataservice.knowledgebaseserver.KBAdapter

/**
 * @author max talanov
 *         Date: 10/16/12
 *         Time: 12:35 AM
 */
object LongTermMemory {

  def merge(updatedResource: ShortTermMemoryResourceWrapper, domainURI: KnowledgeURI) {
     KBAdapter.domainModel()
  }

}
