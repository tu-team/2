package tu.extensions.interfaces

import tu.model.knowledge.communication.ShortTermMemory


/**
 * 
 * @author Alexander Toschev
 * Date: 12/10/12
 * Time: 12:23 AM
 *
 */
trait ContentModifier extends BaseExtension  {

  /**
   * Apply all modifications on context
   * @param ctx context object
   */
   def apply(ctx: ShortTermMemory)
}
