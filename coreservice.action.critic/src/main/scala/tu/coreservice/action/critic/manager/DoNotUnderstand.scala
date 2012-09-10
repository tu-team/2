package tu.coreservice.action.critic.manager

import tu.model.knowledge.selector.SelectorRequest
import tu.model.knowledge.{Probability, Constant, KnowledgeURI}

/**
 * @author max talanov
 *         date 2012-09-08
 *         time: 10:10 PM
 */
class DoNotUnderstand {

  def apply(error: Error): SelectorRequest = {
    new SelectorRequest(KnowledgeURI(Constant.SELECTOR_REQUEST_CRY4HELP_URI), KnowledgeURI(Constant.SELECTOR_REQUEST_CRY4HELP_URI_NAME),
      new Probability())
  }

}
