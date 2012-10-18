package tu.dataservice.memory

import tu.model.knowledge.domain.Concept

/**
 * Container for ShortTermMemoryResource and longTermMemoryResource pair.
 * @author max talanov
 * date 2012-10-18
 * time: 12:41 AM
 */
class ShortTermMemoryConceptWrapper(val concept: Concept, val longTermMemoryConcept: Concept)
  extends ShortTermMemoryResourceWrapper[Concept](concept, longTermMemoryConcept)
