package tu.coreservice.action.way2think

import tu.model.knowledge.communication.Context
import tu.model.knowledge.domain.ConceptNetwork
import tu.model.knowledge.howto.Solution
import tu.model.knowledge.{Resource, SolvedIssue, Solutions}

/**
 * Created by IntelliJ IDEA.
 * User: adel
 * Date: 10.07.12
 * Time: 7:00
 * To change this template use File | Settings | File Templates.
 */

object SearchSolution {

  def apply(inputContext: Context): Context = {

    val res = search

    val outputContext = new tu.model.knowledge.communication.ContextHelper(res match{ case Some(x) => List[Resource](x) case None => List[Resource]() }, "OutputContex")
    outputContext.lastResult = res
    outputContext
  }

  def search(target: ConceptNetwork, solutions:Solutions):SolvedIssue =
  {
    solutions.search(target, Nil)
  }
}