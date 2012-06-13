package tu.model.knowledge

/**
 * Stores Knowledge line, the collection of references of Resources from different domains.
 * @author talanov max
 *         date 2012-05-07
 *         time: 12:16 AM
 * @see http://web.media.mit.edu/~minsky/E8/eb8.html#_Toc518305126
 */

case class KLine(var _frames: Map[KnowledgeURI, Resource], _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(_frames: Map[KnowledgeURI, Resource], _uri: KnowledgeURI) = {
    this(_frames, _uri, new Probability())
  }

  def frames: Map[KnowledgeURI, Resource] = _frames

  def frames_=(value:Map[KnowledgeURI, Resource])=_frames=value

}

object KLine {

  def apply(uri: KnowledgeURI): KLine = {
    new KLine(Map[KnowledgeURI, Resource](), uri)
  }

  def apply(name: String): KLine = {
    new KLine(Map[KnowledgeURI, Resource](), KnowledgeURI(name))
  }
}
