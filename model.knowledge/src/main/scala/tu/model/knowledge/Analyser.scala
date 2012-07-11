package tu.model.knowledge

/**
 * @author toschev alex
 *         Date: 03.05.12
 *         Time: 11:14
 */

//TODO should be moved to it's project
trait Analyser {

  def apply(expression: Expression): Probability
}
