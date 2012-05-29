package tu.model.knowledge

import frame.Frame

/**
 * @author talanovm
 *         date 2012-05-07
 *         time: 11:48 PM
 */

class MicroNeme(var _kLines: Map[KnowledgeURI, KLine], var _frames: Map[KnowledgeURI, Frame[Resource]], _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(_kLines: Map[KnowledgeURI, KLine], _frames: Map[KnowledgeURI, Frame[Resource]], _uri: KnowledgeURI) {
    this(_kLines, _frames, _uri: KnowledgeURI, new Probability())
  }

  def kLines = _kLines

  def kLines_=(a: Map[KnowledgeURI, KLine]): MicroNeme = {
    kLines = a
    this
  }

  def frames = _frames

  def frames_=(a: Map[KnowledgeURI, Frame[Resource]]): MicroNeme = {
    frames = a
    this
  }

}
