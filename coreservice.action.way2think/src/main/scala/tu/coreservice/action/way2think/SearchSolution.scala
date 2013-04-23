package tu.coreservice.action.way2think

import tu.model.knowledge.communication.{ContextHelper, ShortTermMemory}
import tu.model.knowledge.domain.ConceptNetwork
import tu.model.knowledge.{Constant, Resource, SolvedIssue}
import org.slf4j.LoggerFactory
import tu.model.knowledge.narrative.Narrative

/**
 * @author adel chepkunov
 *         Date: 10.07.12
 *         Time: 7:00
 */

class SearchSolution extends Way2Think {
  def start() = false

  def stop() = false

  /**
   * Way2Think interface.
   * @param inputContext ShortTermMemory of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: ShortTermMemory) = SearchSolution(inputContext)
}

object SearchSolution {

  val log = LoggerFactory.getLogger(this.getClass)
  val searcher = new Solutions

  def apply(inputContext: ShortTermMemory): ShortTermMemory = {
    searcher.solutions = inputContext.solutions
    val res = inputContext.lastResult match {
      case Some(cn: ConceptNetwork) => {
        if (cn.rootNodes.size <= 0) {
          return inputContext
        }
        searcher.search(cn, Nil)
      }
      case _ => None
    }
    log debug("search solution result={}", res)
    val outputContext = ContextHelper(List[Resource](), this.getClass.getName + " result")
    outputContext.lastResult = res
    outputContext

  }

  def setReport(solution: Option[SolvedIssue], context: ShortTermMemory): ShortTermMemory = {
    solution match {
      case Some(issue: SolvedIssue) => {
        this.setResultsToReport(Constant.FOUND_SOLUTIONS, context, List[SolvedIssue](issue))
      }
      case None => {
        ContextHelper(List[Resource](), this.getClass.getName)
      }
    }
  }

  def search(target: ConceptNetwork): Option[SolvedIssue] = {
    searcher.search(target, Nil)
  }

  /**
   * Sets concepts to result to report.
   * @param identifier the result identifier in ShortTermMemory.
   * @param context ShortTermMemory to set understood Concepts to report.
   * @param issues found solved issues to set in ShortTermMemory.
   * @return updated ShortTermMemory
   */
  def setResultsToReport(identifier: String, context: ShortTermMemory, issues: List[SolvedIssue]): ShortTermMemory = {
    val foundSolutions = Narrative[SolvedIssue](identifier, issues)
    context.solutionsToReport = context.solutionsToReport + foundSolutions
    context
  }

}