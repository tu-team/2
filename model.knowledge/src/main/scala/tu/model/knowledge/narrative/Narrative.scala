package tu.model.knowledge.narrative

import tu.model.knowledge.{Probability, KnowledgeURI, Resource}


/**
 * @author toscheva
 *         Date: 03.05.12
 *         Time: 12:22
 */

case class Narrative[Type <: Resource](var _rules: List[Rule[Type]], _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {
  // use constructor to declare local vars, everything is Resource

  def this(_rules: List[Rule[Type]], _uri: KnowledgeURI) {
     this(_rules, _uri, new Probability)
  }

  // Get all rules from container
  def rules = _rules

  // Set rule's antecedent
  def rules_=(value: List[Rule[Type]]): Narrative[Type] = {
    _rules = value
    this // Return modified object in functional way
  }
}
