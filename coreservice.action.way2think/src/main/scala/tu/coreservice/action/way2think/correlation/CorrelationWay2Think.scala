package tu.coreservice.action.way2think.correlation

import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.communication.Context

/**
 * @author max talanov
 *         date 2012-09-10
 *         time: 11:23 PM
 */
class CorrelationWay2Think extends Way2Think{
  def start() = false

  def stop() = false

  /**
   * Way2Think interface.
   * @param inputContext Context of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: Context) = {

    null
  }
}
