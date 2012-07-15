package tu.coreservice.action.way2think

import tu.model.knowledge.{SolvedIssue, Resource, Probability, KnowledgeURI}
import tu.model.knowledge.domain.ConceptNetwork


/**
 * Created by IntelliJ IDEA.
 * User: adel
 * Date: 10.07.12
 * Time: 7:21
 * To change this template use File | Settings | File Templates.
 */

//class Solutions(_uri: KnowledgeURI, _probability: Probability) extends Resource(_uri, _probability) {
//def this() = { this(new KnowledgeURI("defaultNamespace", "Solutions", "rev"), new Probability() ) }
//}

object Solutions{
  private var solutions:List[SolvedIssue] = Nil

  def add(item:SolvedIssue) =
  {
    solutions = item::solutions
    Solutions
  }

  def search(issue:ConceptNetwork, badSolutions: List[ConceptNetwork]):Option[SolvedIssue] =
  {//TODO
    var found_solutions:List[SolvedIssue] = Nil
    for(s <- solutions)
    {
      if (distance(issue, s.issue, 0) == 0)
        found_solutions = s::found_solutions
    }


    //If not found, then return None else - return SolvedIssue with minimal size
    return if( found_solutions.isEmpty)
      None
    else
      Some(found_solutions.sortWith((s, t) => s.issue.nodes.size < t.issue.nodes.size).head)

  }

  def distance(issue:ConceptNetwork, master:ConceptNetwork, k:Int):Int  =
  {
    //TODO: allow K errors
    //

    //find first node
    val listWithFirstNode = master.rootNodes.filter(p => p == issue.rootNodes(0))
    if (listWithFirstNode.isEmpty )
      return 1

    0
  }

}