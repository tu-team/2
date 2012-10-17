package tu.dataservice.memory

import tu.model.knowledge.Resource

/**
 * Container for ShortTermMemoryResource and longTermMemoryResource pair
 * @author max talanov
 *         Date: 10/16/12
 *         Time: 12:42 AM
 */
class ShortTermMemoryResourceWrapper[T <: Resource](val resource: T, val longTermMemoryResource: T)
