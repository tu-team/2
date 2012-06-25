package tu.coreservice.action.way2think

import tu.model.knowledge.communication.Context

/**
 * Way2Think interface.
 * @author max talanov
 *         date 2012-05-28
 *         time: 11:09 PM
 */

trait Way2Think {

  /**
   * Way2Think interface.
   * @param inputContext Context of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: Context): Context

}
