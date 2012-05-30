package tu.coreservice.spellcorrector

import tu.coreservice.utilities.Configurator

/**
 * User: toschev alex
 * Date: 28.05.12
 * Time: 20:54
 */

object SpellCorrectorFactory {

  /**
   * Construct suitable configurator
   */
  def construct(): SpellCorrector = {
    if (Configurator.spellCheckerEngine() == "GOOGLE")
      return new SpellCorrectorGoogle()

    //google is a default spellchecker engine
    return new SpellCorrectorGoogle()
  }
}
