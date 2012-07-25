package tu.model.knowledge.action

/**
 * @author max
 *         date 2012-07-12
 *         time: 10:32 AM
 */

import tu.model.knowledge.{Probability, Resource, KnowledgeURI}

class ActionModel(_uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

}
