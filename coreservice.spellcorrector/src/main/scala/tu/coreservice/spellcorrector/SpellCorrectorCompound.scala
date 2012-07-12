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

    //preliminary corrector

    //correct bad ' symbols
    var precorrectedSentence=inputSentence.replaceAll("""((?<=\w)\?(?=\w{1,2}))""","'")

    // correct dots in sentence
    precorrectedSentence=precorrectedSentence.replaceAll("""(\.|\?|\!(?=\w))""",". ")

    var spellingCorrected = googleCorrector.correctSentence(precorrectedSentence)

    //correct grammar
    return atdCorrector.sendRequest(spellingCorrected,true)

  }
}
