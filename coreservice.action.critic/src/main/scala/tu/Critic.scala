package tu

import org.specs.specification.Context

/**
 * Critic trait.
 * @author toscheva
 * Date: 03.05.12
 * Time: 11:05
 */

trait Critic {

  def excludesLinks()

  def includesLinks()

  /**
   * Gets
   * @param context
   * @return
   */
  def apply(context:Context): Context
}
