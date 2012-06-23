package tu.model.knowledge.narrative

import tu.model.knowledge.annotator.AnnotatedWord
import tu.model.knowledge.{KnowledgeURI, Resource}

/**
 * Narrative with annotation of Concepts.
 * @author toschev alex
 * Date: 01.06.12
 * Time: 19:30
 *
 */


case class AnnotatedNarrative(var _words:List[AnnotatedWord],__uri:KnowledgeURI) extends Narrative(_words,__uri) {

}
