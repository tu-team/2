package tu.extensions.algorithms

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
      //check the DB or config to select proper class otherwise return default
      return new defaults.Generalizer()
  }
}
