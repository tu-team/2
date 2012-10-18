package tu.coreservice.action.way2think

import tu.model.knowledge.communication.ShortTermMemory
import tu.model.knowledge.{Probability, KnowledgeURI}
import tu.coreservice.action.{Action}

import tu.model.knowledge.helper.URIGenerator


/**
 * Way2Think interface.
 * @author max talanov
 *         date 2012-05-28
 *         time: 11:09 PM
 */

abstract class Way2Think(_uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Action(_uri, _probability) {

  /**
   * Way2Think interface.
   * @param inputContext ShortTermMemory of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: ShortTermMemory): ShortTermMemory

  def this() = this(URIGenerator.generateURI("Way2Think"))

}
