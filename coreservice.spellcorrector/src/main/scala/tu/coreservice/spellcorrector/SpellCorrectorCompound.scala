package tu.coreservice.spellcorrector

/**
 * Created by IntelliJ IDEA.
 * User: toscheva
 * Date: 31.05.12
 * Time: 15:05
 *
 */

/**
 * Compound corrector class
 */
class SpellCorrectorCompound extends SpellCorrector {
  def correctSentence(inputSentence: String): String = {
    //instantiate Google corrector
    var googleCorrector = new SpellCorrectorGoogle
    var atdCorrector = new SpellCorrectorAfterTheDeadline

    var spellingCorrected = googleCorrector.correctSentence(inputSentence)

    //apply additional correction
    spellingCorrected.replaceAll("",". ")

    //correct grammar
    return atdCorrector.sendRequest(spellingCorrected,true)

  }
}
