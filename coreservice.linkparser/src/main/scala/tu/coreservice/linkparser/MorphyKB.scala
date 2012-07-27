package tu.coreservice.linkparser

import relex.morphy.{MorphyJWNL, Morphed}
import tu.model.knowledge.communication.Context
import tu.model.knowledge.annotator.AnnotatedSentence

/**
 * @author alex toschev, max talanov
 *         Time stamp: 7/25/12 5:40 PM
 */

class MorphyKB(val context: Context) extends MorphyJWNL {

  override def morph(word: String):Morphed = {
    val res: Morphed = super.morph(word)

    res
  }

  def processLastResult(contex: Context): AnnotatedSentence = {
    null
  }
}
