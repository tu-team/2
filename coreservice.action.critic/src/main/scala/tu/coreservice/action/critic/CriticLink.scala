package tu.coreservice.action.critic

import tu.model.knowledge.{KnowledgeURI, Link}

/**
 * @author max talanov
 *         date 2012-07-01
 *         time: 4:47 PM
 */

class CriticLink(_source: Critic, _destination: Critic, _uri: KnowledgeURI)
  extends Link[Critic](_source, _destination, _uri) {

}
