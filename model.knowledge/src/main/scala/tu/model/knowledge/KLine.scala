package tu.model.knowledge

/**
 * @author max
 *         date 2012-05-07
 *         time: 12:16 AM
 */

class KLine(_frames: Map[KnowledgeURI, Resource], _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(_frames: Map[KnowledgeURI, Resource], _uri: KnowledgeURI) = {
    this(_frames, _uri, new Probability())
  }

  def frames: Map[KnowledgeURI, Resource] = _frames

}

object KLine {

  def apply(uri: KnowledgeURI): KLine = {
    new KLine(Map[KnowledgeURI, Resource](), uri)
  }

  def apply(name: String): KLine = {
    new KLine(Map[KnowledgeURI, Resource](), KnowledgeURI(name))
  }
}
