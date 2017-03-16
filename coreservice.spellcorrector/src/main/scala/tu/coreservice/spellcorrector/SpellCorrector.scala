package tu.coreservice.spellcorrector

import tu.coreservice.utilities.Configurator
import tu.model.knowledge.way2think.Way2ThinkModel
import tu.coreservice.action.way2think.Way2Think

/**
 * @author toschev alex, talanov max
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

  /**
   * Check if word are reserved
   * @param word target word
   * @return true if it is a special word
   * TODO: populate from Database (also include aliases
   */
  def ifWordIsReserved(word: String): Boolean = {
    if (word == "CatiaV5" || word=="cnext5") return true

    return false
  }
}
object SpellCorrector {

  /**
   * Construct suitable configurator
   */
  def apply(): SpellCorrector = {
    if (Configurator.spellCheckerEngine() == "COMPOUND")
      return new SpellCorrectorCompound()
    else if (Configurator.spellCheckerEngine() == "ATD")
          return new SpellCorrectorAfterTheDeadline()
    //google is a default spellchecker engine
    return new SpellCorrectorAfterTheDeadline()
  }
}

