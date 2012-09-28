package tu.model.knowledge

import domain.ConceptNetwork
import howto.Solution
import tu.model.knowledge.KBMap._
import tu.exception.UnexpectedException
import scala._
import tu.model.knowledge.helper.URIGenerator

/**
 * @author adel chepkunov
 *         Date: 10.07.12
 *         Time: 7:37
 */

case class SolvedIssue(var issue: ConceptNetwork, solution: Solution, _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {
  override def save(kb: KB, parent: KBNodeId, key: String, linkType: String, saved: List[String] = Nil): Boolean = {
    var res = kb.saveResource(this, parent, key, linkType)
    issue.save(kb, this, "", Constant.CONCEPTS_NETWORK_NAME)
    solution.save(kb, this, "", Constant.SOLUTION_NAME)
  }
}

object SolvedIssue {
  def apply(issue: ConceptNetwork, solution: Solution): SolvedIssue = {
    new SolvedIssue(issue, solution, URIGenerator.generateURI(SolvedIssue.getClass.getName), new Probability())
  }

  def load(kb: KB, parentId: KBNodeId, key: String, linkType: String): SolvedIssue = {
    val selfMap = kb.loadChild(parentId, key, linkType)
    if (selfMap.isEmpty) {
      //log.error("Concept not loaded for link {}/{} for {}", List(key, linkType, parentId.toString))
      throw new UnexpectedException("Concept not loaded for link " + key + "/" + linkType + " for " + parentId.toString)
    }
    load(kb, selfMap)
  }

  def load(kb: KB, selfMap: Map[String, String]): SolvedIssue = {
    val ID = new KBNodeId(selfMap)
    val issue: ConceptNetwork = ConceptNetwork.load(kb, ID, "", Constant.CONCEPTS_NETWORK_NAME)
    val solution: Solution = Solution.load(kb, ID, "", Constant.SOLUTION_NAME)
    new SolvedIssue(issue, solution, new KnowledgeURI(selfMap), new Probability(selfMap))
  }
}
