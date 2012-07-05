package tu.model.knowledge

/**
 * @author max talanov
 * date 2012-04-29
 * Time: 11:34 PM
 */

class Probability(val frequency: Double = 0.0, val confidence: Double = 1.0) {

  //def frequency: Double = _frequency
  //def confidence: Double = _confidence

  override def toString = {
    "" + frequency + ":" + confidence
  }

}
