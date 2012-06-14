package tu.model.knowledge.annotator

import tu.model.knowledge.narrative.Narrative
import tu.model.knowledge.{Resource, Probability, KnowledgeURI}


/**
 * @author toschev alex, talanov max
 *         Date: 01.06.12
 *         Time: 19:30
 *
 */

case class AnnotatedNarrative(_phrases: List[AnnotatedPhrase], _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Resource(_uri, _probability) {

  def phrases = _phrases

}
