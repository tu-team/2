package tu.model.knowledge.howto

import tu.model.knowledge.{Probability, KnowledgeURI}
import tu.model.knowledge.narrative.{Rule, Narrative}

/**
 * @author max
 *         date 2012-05-09
 *         time: 7:08 PM
 */

class Solution(_rules: List[Rule[HowTo]], _uri: KnowledgeURI, _probability: Probability)
  extends Narrative[HowTo](_rules, _uri, _probability){

  def this(_rules: List[Rule[HowTo]], _uri: KnowledgeURI) {
    this(_rules: List[Rule[HowTo]], _uri: KnowledgeURI, new Probability)
  }

}
