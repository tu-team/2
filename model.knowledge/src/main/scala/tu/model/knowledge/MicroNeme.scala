package tu.model.knowledge

import frame.TypedFrame

/**
 * Stores Microneme (domain) of model.
 * @author talanov max
 *         date 2012-05-07
 *         time: 11:48 PM
 * @see http://web.media.mit.edu/~minsky/E8/eb8.html#_Toc518305130
 */

case class MicroNeme(var _kLines: Map[KnowledgeURI, KLine], var _frames: Map[KnowledgeURI, TypedFrame[Resource]], _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(_kLines: Map[KnowledgeURI, KLine], _frames: Map[KnowledgeURI, TypedFrame[Resource]], _uri: KnowledgeURI) {
    this(_kLines, _frames, _uri: KnowledgeURI, new Probability())
  }

  def kLines = _kLines

  def kLines_=(a: Map[KnowledgeURI, KLine]): MicroNeme = {
    kLines = a
    this
  }

  def frames = _frames

  def frames_=(a: Map[KnowledgeURI, TypedFrame[Resource]]): MicroNeme = {
    frames = a
    this
  }

}
