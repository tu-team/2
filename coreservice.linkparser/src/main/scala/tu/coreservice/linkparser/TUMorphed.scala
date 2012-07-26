package tu.coreservice.linkparser

import relex.morphy.{Morphed, Morphy}

/**
 * @author alex toschev, max talanov
 *         Time stamp: 7/25/12 5:40 PM
 */

class TUMorphed extends Morphy {
  def initialize() {}

  def morph(word: String):Morphed = {
    val m: Morphed = new Morphed(word)
    m
  }
}
