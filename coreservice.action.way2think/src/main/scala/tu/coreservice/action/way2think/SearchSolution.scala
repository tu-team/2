package tu.coreservice.action.way2think

import tu.model.knowledge.communication.{ContextHelper, ShortTermMemory}
import tu.model.knowledge.domain.ConceptNetwork
import tu.model.knowledge.{Resource, SolvedIssue}

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
  val searcher = new Solutions

  def apply(inputContext: ShortTermMemory): ShortTermMemory = {
    searcher.solutions=inputContext.solutions

    val res = inputContext.lastResult match {
      case Some(cn: ConceptNetwork) =>
        searcher.search(cn, Nil)
      case _ => None
    }

    val outputContext = ContextHelper(List[Resource](), this.getClass.getName + " result")
    outputContext.lastResult = res
    outputContext

  }

  //TODO: use second argument to skip previous bad choices
  def search(target: ConceptNetwork): Option[SolvedIssue] = {
    searcher.search(target, Nil)
  }
}