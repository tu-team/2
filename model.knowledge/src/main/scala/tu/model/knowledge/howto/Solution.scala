package tu.model.knowledge.howto

import tu.model.knowledge.domain.ConceptNetwork
import tu.model.knowledge.{KBNodeId, KB, Probability, KnowledgeURI}
import tu.model.knowledge.narrative.{Narrative, Rule, RulesNarrative}

/**
 * Stores Solution that is container for List of HowTo-s.
 * @author max talanov
 *         date 2012-05-09
 *         time: 7:08 PM
 */

class Solution(_rules: List[HowTo], _uri: KnowledgeURI, _probability: Probability)
  extends Narrative[HowTo](_rules, _uri, _probability){

  def this(_rules: List[HowTo], _uri: KnowledgeURI) {
    this(_rules: List[HowTo], _uri: KnowledgeURI, new Probability)
  }

}

object Solution {
  def apply(rules: List[HowTo]): Solution = {
    new Solution(rules,null,null)
  }


  def load(kb: KB, parentId: KBNodeId, key: String, linkType: String): Solution = {
    Solution(List())
    //TODO do it
  }

}
