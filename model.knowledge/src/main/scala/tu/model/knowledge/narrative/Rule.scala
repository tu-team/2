package tu.model.knowledge.narrative

import tu.model.knowledge.{Probability, KnowledgeURI, Resource, Expression}
import tu.model.knowledge.howto.HowTo


/**
 * Critic's rule
 * @author toschev alex
 *         Date: 03.05.12
 *         Time: 11:21
 */

case class RuleHowTo(__antecedent: Expression, __consequent: List[HowTo], val _uri: KnowledgeURI, val _probability: Probability)
  extends Rule[HowTo](__antecedent, __consequent, _uri, _probability) {
}

class Rule[Type <: Resource](var _antecedent: Expression, var _consequent: List[Type], _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {


  def this(_antecedent: Expression, _consequent: List[Type], _uri: KnowledgeURI) {
    this(_antecedent: Expression, _consequent: List[Type], _uri: KnowledgeURI, new Probability())
  }

  /**
   * Get all antecedent of rule
   */
  def antecedent = _antecedent

  /**
   * Set rule's antecedent
   */
  def antecedent_=(value: Expression): Rule[Type] = {
    _antecedent = value
    this
  }

  /**
   * Get all consequent of rule
   */
  def consequent = _consequent

  /**
   * Set consequent of rule
   */
  def consequent_=(value: List[Type]): Rule[Type] = {
    _consequent = value
    this
  }
}

