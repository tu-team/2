package tu.model.knowledge.annotator

import tu.model.knowledge.narrative.Narrative
import tu.model.knowledge.{Resource, Probability, KnowledgeURI}


/**
 * @author toschev alex, talanov max
 *         Date: 01.06.12
 *         Time: 19:30
 *
 */

case class AnnotatedNarrative(_words: List[AnnotatedWord], _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(_words: List[AnnotatedWord], _uri: KnowledgeURI) {
    this(_words, _uri, new Probability())
  }

  def words = _words

}
