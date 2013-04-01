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

  def values = _frames.values

  /**
   * Returns Some[Resource] if frames contains Resource with specified KnowledgeURI.
   * @param uri to search resource with.
   * @return Option[Resource] with specified KnowledgeURI.
   */
  def get(uri: KnowledgeURI): Option[Resource] = {
    _frames.get(uri)
  }

  /**
   * Searches for Resource with KnowledgeURI with specified UID.
   * @param uid UID to search with.
   * @return Option[Resource] with specified UID in KnowledgeURI.
   */
  def get(uid: String): List[Resource] = {
    _frames.filter {
      keyValue: Pair[KnowledgeURI, Resource] => {
        keyValue._1.uid.equals(uid)
      }
    }.map {
      keyValue: Pair[KnowledgeURI, Resource] => keyValue._2
    }.toList
  }

}

object KLine {

  def apply(uri: KnowledgeURI): KLine = {
    new KLine(Map[KnowledgeURI, Resource](), uri)
  }

  def apply(name: String): KLine = {
    new KLine(Map[KnowledgeURI, Resource](), KnowledgeURI(name))
  }
}
