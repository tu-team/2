package tu.model.knowledge.communication

/**
 * @author max talanov
 *         date 2012-07-06
 *         time: 6:42 PM
 */

import tu.model.knowledge.{KnowledgeURI, Probability, Resource}

class Request(_inputResource: Resource, _uri: KnowledgeURI,_domainName:KnowledgeURI , _probability: Probability = new Probability())
  extends Resource(_uri, _probability) {

  def inputResource = _inputResource

  def domainName = _domainName

}
