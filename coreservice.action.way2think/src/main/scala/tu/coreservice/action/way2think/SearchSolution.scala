package tu.coreservice.action.way2think

import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.domain.ConceptNetwork
import tu.model.knowledge.{Resource, SolvedIssue, Solutions}

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
   * @param inputContext Context of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: Context) = SearchSolution(inputContext)
}

object SearchSolution {

  def apply(inputContext: Context): Context = {

    // TODO fix this !
    // val res = search

    // val outputContext = ContextHelper(res match{ case Some(x) => List[Resource](x) case None => List[Resource]() }, "OutputContex")
    // outputContext.lastResult = res
    // outputContext
    ContextHelper(List[Resource](), this.getClass.getName + " result")
  }

  def search(target: ConceptNetwork, solutions: Solutions): Option[SolvedIssue] = {
    solutions.search(target, Nil)
  }
}