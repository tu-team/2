package tu.model.knowledge.primitive

import tu.model.knowledge.{Resource, Probability, KnowledgeURI}
import java.util.Calendar


/**
 * @author adel
 * Date: 24.06.12
 * Time: 13:53
 */

class KnowledgeTime(val value: Calendar, _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Resource(_uri, _probability) {
}