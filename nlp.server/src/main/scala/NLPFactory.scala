package tu.nlp.server


/**
 *
 * @author Alexander Toschev
 *         Date: 12/1/12
 *         Time: 1:46 PM
 *
 */
object NLPFactory {
  def createProcessor(): NLPProcessor = {
    RelexNLPProcessor
  }
}
