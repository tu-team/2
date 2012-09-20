package tu.model.knowledge.communication

/**
 * @author max talanov
 *         date 2012-09-20
 *         time: 12:03 AM
 */

import tu.model.knowledge.{Probability, KnowledgeURI}
import tu.model.knowledge.primitive.KnowledgeString

class TrainingRequest(_inputText: KnowledgeString, _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Request(_inputText, _uri, _probability)