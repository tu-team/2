package tu.model.knowledge.narrative

import tu.model.knowledge.{Probability, KnowledgeURI, Resource}


/**
 * Narrative is the sequence of Resources.
 * @author toschev alex, talanov max
 *         Date: 03.05.12
 *         Time: 12:22
 */

case class Narrative[Type <: Resource](_resources: List[Type], _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Resource(_uri, _probability) {

  def this(_resources: List[Type], _uri: KnowledgeURI) {
    this(_resources: List[Type], _uri: KnowledgeURI, new Probability())
  }

  def resources = _resources

}

