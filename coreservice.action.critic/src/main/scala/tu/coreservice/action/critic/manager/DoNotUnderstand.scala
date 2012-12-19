package tu.coreservice.action.critic.manager

import tu.model.knowledge.selector.SelectorRequest
import tu.model.knowledge.{Constant, KnowledgeURI}
import tu.model.knowledge.domain.Concept
import tu.model.knowledge.annotator.AnnotatedPhrase

/**
 * @author max talanov
 *         date 2012-09-08
 *         time: 10:10 PM
 */
class DoNotUnderstand {

  def apply(error: Error): SelectorRequest = {
    SelectorRequest(KnowledgeURI(Constant.SELECTOR_REQUEST_CRY4HELP_URI), KnowledgeURI(Constant.SELECTOR_REQUEST_CRY4HELP_URI_NAME))
  }

  def apply(notUnderstoodConcepts: List[Concept]): SelectorRequest = {
    SelectorRequest(KnowledgeURI(Constant.SELECTOR_REQUEST_CRY4HELP_URI), KnowledgeURI(Constant.SELECTOR_REQUEST_CRY4HELP_URI_NAME))
  }

  def apply():SelectorRequest ={
    SelectorRequest(KnowledgeURI(Constant.SELECTOR_REQUEST_CRY4HELP_URI), KnowledgeURI(Constant.SELECTOR_REQUEST_CRY4HELP_URI_NAME))
  }

}
