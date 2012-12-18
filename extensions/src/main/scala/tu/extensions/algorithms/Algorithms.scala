package tu.extensions.algorithms

import tu.extensions.Extensions
import tu.extensions.interfaces.{Generalizer, ContentModifier}

/**
 * 
 * @author: Alexander Toschev
 * Date: 12/10/12
 * Time: 12:35 AM
 * 
 */
object Algorithms {

  /**
   * instatiate current generalizer algorithm
   * @return
   */
  def generalizer():ContentModifier =
  {
      return Extensions.load[Generalizer]()
  }


}
