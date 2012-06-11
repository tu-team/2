package tu.coreservice.action.way2think

import tu.model.knowledge.frame.TransFrame
import tu.model.knowledge.communication.Context
import tu.model.knowledge.Resource

/**
 * @author max talanov
 *         date 2012-05-28
 *         time: 11:09 PM
 */

trait Way2Think {

  /**
   * Way2Think interface.
   * @param inputContext
   * @return outputContext
   */
  def apply(inputContext: Context): Context

}
