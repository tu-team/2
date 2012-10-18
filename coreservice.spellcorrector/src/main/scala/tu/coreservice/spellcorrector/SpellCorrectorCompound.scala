package tu.coreservice.spellcorrector

import tu.model.knowledge.communication.{ContextHelper, ShortTermMemory}
import tu.model.knowledge.{Constant, Resource}

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
    val googleCorrector = new SpellCorrectorGoogle
    val atdCorrector = new SpellCorrectorAfterTheDeadline

    //preliminary corrector

    //correct bad ' symbols
    var precorrectedSentence=inputSentence.replaceAll("""((?<=\w)\?(?=\w{1,2}))""","'")

    // correct dots in sentence
    precorrectedSentence=precorrectedSentence.replaceAll("""(\.|\?|\!(?=\w))""",". ")

    val spellingCorrected = googleCorrector.correctSentence(precorrectedSentence)

    //apply additional correction
    spellingCorrected.replaceAll("",". ")

    //correct grammar
    atdCorrector.sendRequest(spellingCorrected,true)

  }

  def start() = false

  def stop() = false

  /**
   * Way2Think interface.
   * @param inputContext ShortTermMemory of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: Context) = {
    ContextHelper(List[Resource](), this.getClass.getName + Constant.RESULT)
  }
}
