package tu.model.knowledge

/**
 * @author toschev alex
 *         Date: 03.05.12
 *         Time: 11:14
 */

trait Analyser {

  def apply(expression: Expression): Probability
}
