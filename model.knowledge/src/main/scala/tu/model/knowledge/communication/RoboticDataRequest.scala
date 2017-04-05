package tu.model.knowledge.communication

import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.{KnowledgeURI, Probability}

class RoboticDataRequest(_inputText: KnowledgeString, _uri: KnowledgeURI, domain:KnowledgeURI, _probability: Probability = new Probability())
  extends Request(_inputText, _uri,domain:KnowledgeURI, _probability)