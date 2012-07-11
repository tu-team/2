package tu.model.knowledge.communication

/**
 * @author max talanov
 *         date 2012-07-06
 *         time: 6:42 PM
 */

import tu.model.knowledge.{Probability, Resource, KnowledgeURI}

class Request(_uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

}
