package tu.coreservice.linkparser

import relex.morphy.{MorphyJWNL, Morphed}
import tu.model.knowledge.communication.Context
import tu.model.knowledge.annotator.AnnotatedSentence
import java.rmi.UnexpectedException

/**
 * @author alex toschev, max talanov
 *         Time stamp: 7/25/12 5:40 PM
 */

class MorphyKB(val context: Context) extends MorphyJWNL {

  override def morph(word: String):Morphed = {
    val res: Morphed = super.morph(word)

    res
  }

  def processLastResult(context: Context): AnnotatedSentence = {
    val lastResult = context.lastResult
    lastResult match {
      case sentence: AnnotatedSentence => {
        sentence
      }
      case _ => {
        throw new UnexpectedException("$Last_result_contains_unexpected_type " + lastResult.getClass.getName)
      }
    }
  }
}
