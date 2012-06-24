package tu.model.knowledge.primitive

import tu.model.knowledge.{Resource, Probability, KnowledgeURI}
import java.util.Calendar


/**
 * Created by IntelliJ IDEA.
 * User: adel
 * Date: 24.06.12
 * Time: 13:53
 * To change this template use File | Settings | File Templates.
 */

class KnowledgeTime(val value: Calendar, _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Resource(_uri, _probability) {

}