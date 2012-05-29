package tu

/**
 * @author toschev alex
 *         Date: 28.05.12
 *         Time: 20:49
 */

/**
 * interface that provide spell corrector functionality
 */
trait SpellCorrector {

  /**
   * correct sentence
   * @param inputSentence sentence that should be corrected
   */
  def correctSentence(inputSentence: String): String
}
