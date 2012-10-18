package tu.coreservice.thinkinglifecycle

import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.communication.ShortTermMemory
import tu.coreservice.action.Action

/**
 * @author max talanov
 *         date 2012-07-12
 *         time: 4:04 PM
 */

case class JoinWay2Think(actions: List[Action]) extends Way2Think{
  def start() = false

  def stop() = false

  /**
   * Way2Think interface.
   * @param inputContext ShortTermMemory of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: ShortTermMemory) = null
}
