package tu.coreservice.action.way2think

import tu.model.knowledge.communication.ShortTermMemory
import tu.model.knowledge.{Constant, KnowledgeURI, Probability}
import tu.coreservice.action.{Action, AkkaAction}
import tu.model.knowledge.helper.URIGenerator
import tu.model.knowledge.domain.Concept
import tu.model.knowledge.narrative.Narrative


/**
 * Way2Think interface.
 * @author max talanov
 *         date 2012-05-28
 *         time: 11:09 PM
 */

abstract class Way2Think(_uri: KnowledgeURI, _probability: Probability = new Probability())
  extends AkkaAction(_uri, _probability) {

  /**
   * Way2Think interface.
   * @param inputContext ShortTermMemory of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: ShortTermMemory): ShortTermMemory

  def this() = this(URIGenerator.generateURI("Way2Think"))

  /**
   * Sets concepts to result to report.
   * @param context ShortTermMemory to set understood Concepts to report.
   * @param concepts understood concepts to set in ShortTermMemory.
   * @return updated ShortTermMemory
   */
  def setResultsToReport(context: ShortTermMemory, concepts: List[Concept]): ShortTermMemory = {
    val understoodConcepts = Narrative[Concept](Constant.UNDERSTOOD_CONCEPTS, concepts)
    context.resultToReport = context.resultToReport + understoodConcepts
    context
  }
}
