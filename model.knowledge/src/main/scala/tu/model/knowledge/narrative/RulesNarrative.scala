package tu.model.knowledge.narrative

import tu.model.knowledge.{Probability, KnowledgeURI, Resource}


/**
 * Narrative based on Rule-s
 * @author toschev alex, talanov max
 *         Date: 03.05.12
 *         Time: 12:22
 */

case class RulesNarrative[Type <: Resource](_rules: List[Rule[Type]],val _uri: KnowledgeURI,val _probability: Probability)
  extends Narrative[Rule[Type]](_rules, _uri, _probability) {
  // use constructor to declare local vars, everything is Resource

  def this(_rules: List[Rule[Type]], _uri: KnowledgeURI) {
     this(_rules, _uri, new Probability)
  }

  /**
   * Get all rules from container
   */
  def rules = _rules
}
