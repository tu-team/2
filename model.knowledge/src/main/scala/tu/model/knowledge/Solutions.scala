package tu.model.knowledge

import domain.ConceptNetwork
import howto.HowTo
import semanticnetwork.SemanticNetwork


/**
 * Created by IntelliJ IDEA.
 * User: adel
 * Date: 10.07.12
 * Time: 7:21
 * To change this template use File | Settings | File Templates.
 */

class Solutions(_uri: KnowledgeURI, _probability: Probability) extends Resource(_uri, _probability) {

  def this() = { this(new KnowledgeURI("defultNamespace", "Solutions", "rev"), new Probability() ) }

  def add(item:SolvedIssue)
  {//TODO
  }

  def search(issue:ConceptNetwork, badSolutions: List[SemanticNetwork]):SolvedIssue =
  {//TODO
    val solve = null

    solve
  }

}