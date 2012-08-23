package tu.model.knowledge

import java.text.DecimalFormat

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

  def this(map:Map[String, String]) = {
    this(  map.get("frequency") match { case Some(x) => x.toDouble  case _ => 0.0},
           map.get("confidence") match { case Some(x) => x.toDouble  case _ => 1.0}
    )
  }
}
