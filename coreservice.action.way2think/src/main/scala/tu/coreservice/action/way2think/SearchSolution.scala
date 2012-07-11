package tu.coreservice.action.way2think

import tu.model.knowledge.communication.Context
import tu.model.knowledge.domain.ConceptNetwork
import tu.model.knowledge.howto.Solution
import tu.model.knowledge.{SolvedIssue, Solutions}

/**
 * Created by IntelliJ IDEA.
 * User: adel
 * Date: 10.07.12
 * Time: 7:00
 * To change this template use File | Settings | File Templates.
 */

object SearchSolution {

  def apply(inputContext: Context): Context = {
    var outputContext = inputContext

    outputContext
  }

  def search(target: ConceptNetwork, solutions:Solutions):SolvedIssue =
  {
    solutions.search(target, Nil)
  }
}