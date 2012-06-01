package tu.model.knowledge.annotator

import tu.model.knowledge.{Probability, KnowledgeURI}
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.domain.Concept

/**
 * @author max
 *         date 2012-05-31
 *         time: 11:32 PM
 */

case class AnnotatedWord(_concepts: List[Concept],_value: String, _uri: KnowledgeURI, _probability: Probability)
  extends KnowledgeString(_value, _uri, _probability){

}
