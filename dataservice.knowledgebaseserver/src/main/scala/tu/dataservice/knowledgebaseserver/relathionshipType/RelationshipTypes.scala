package tu.dataservice.knowledgebaseserver.relathionshipType

import org.neo4j.graphdb.RelationshipType
import tu.model.knowledge.way2think.Way2ThinkModel

/**
 * Created with IntelliJ IDEA.
 * User: ChepkunovA
 * Date: 17.08.12
 * Time: 17:48
 * To change this template use File | Settings | File Templates.
 */

class Has extends RelationshipType {
  def name(): String = "HasRelationship"
}

class Goal extends RelationshipType {
  def name(): String = {
    tu.model.knowledge.training.Goal.getClass.getName
  }
}

/*
I hope, we should not create new type if it no need. "Has" relationship enough for most non-root nodes
class Way2ThinkModel extends RelationshipType
{
  def name():String = {Way2ThinkModel.getClass.getName}
}
*/
