package tu.extensions.algorithms

import tu.model.knowledge.communication.ShortTermMemory

/**
 * 
 * @author: Alexander Toschev
 * Date: 12/10/12
 * Time: 12:23 AM
 *
 */
trait ContentModifier {

  /**
   * Apply all modifications on context
   * @param ctx context object
   */
   def apply(ctx: ShortTermMemory)
}
