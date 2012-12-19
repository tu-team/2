package tu.coreservice.action.way2think

import tu.model.knowledge.SolvedIssue
import tu.model.knowledge.domain.{ConceptLink, Concept, ConceptNetwork}
import tu.dataservice.knowledgebaseserver.KBAdapter
import org.slf4j.LoggerFactory


/**
 * @author adel chepkunov
 *         Date: 10.07.12
 *         Time: 7:21
 */

//class Solutions(_uri: KnowledgeURI, _probability: Probability) extends Resource(_uri, _probability) {
//def this() = { this(new KnowledgeURI("defaultNamespace", "Solutions", "rev"), new Probability() ) }
//}

class Solutions {

  val log = LoggerFactory.getLogger(this.getClass)
  var solutions: List[SolvedIssue] = Nil

  def add(item: SolvedIssue): List[SolvedIssue] = {
    KBAdapter.solutionsAdd(item)
    solutions = item :: solutions
    solutions
  }

  def search(issue: ConceptNetwork, badSolutions: List[ConceptNetwork]): Option[SolvedIssue] = {
    val res = searchNotEffective(issue, badSolutions, 1)
    if (res.size == 0) return None
    Some(res.sortWith((s, t) => s.issue.nodes.size < t.issue.nodes.size).head)
  }

  private def searchNotEffective(issue: ConceptNetwork, badSolutions: List[ConceptNetwork], k:Int): List[SolvedIssue] = {
    var found_solutions: List[SolvedIssue] = Nil
    for (s <- solutions) {
      if (distance(issue, s.issue, 0) == 0)
        found_solutions = s :: found_solutions
    }

    //If not found, then return None else - return SolvedIssue with minimal size
    val res: List[SolvedIssue] = if ( k == 0 || !found_solutions.isEmpty) {
      found_solutions
    } else {
      val subIssues = CNMinusList(issue)
      subIssues.map(p => searchNotEffective(p, badSolutions, k-1)).reduce((A, B) => A::: B)
    }
    log info("solutions found={}", res)
    res
  }

  private def CNMinusList(issue: ConceptNetwork): List[ConceptNetwork] = {
    def issueWithoutNode(n:Concept):ConceptNetwork = {
      val nodes=issue.rootNodes.filter(p => p!=n)
      return new ConceptNetwork(nodes, issue.links, issue.uri, issue.probability)
    }
    issue.rootNodes.map(issueWithoutNode)
  }

  def distance(issue: ConceptNetwork, master: ConceptNetwork, k: Int): Int = {
    //TODO: allow K errors
    //

    //find first node
    val listWithFirstNode = master.rootNodes.filter(p => compareWithGeneralisation(p, issue.rootNodes(0)))
    if (listWithFirstNode.isEmpty)
      return 1

    val depth = issue.rootNodes.size

    if (!masterHasAllLinks(issue.rootNodes(0), issue, listWithFirstNode(0), master, depth))
      return 1

    0
  }

  def masterHasAllLinks(issueConcept: Concept, issue: ConceptNetwork, masterConcept: Concept, master: ConceptNetwork, depth: Int): Boolean = {
    if (depth == 0) return true

    val links = getConceptChildren(issueConcept, issue.links)
    if (links.isEmpty) return true

    for (issueNext <- links) {
      // forall for empty list always return true, so we use not (!masterHasAllLinks)
      val bad = getConceptChildren(masterConcept, master.links).filter(p => compareWithGeneralisation(p, issueNext))
        .forall(p => !masterHasAllLinks(issueNext, issue, p, master, depth - 1))
      if (bad) return false
    }

    true
  }

  def getConceptChildren(concept: Concept, links: List[ConceptLink]): List[Concept] = {
    links.filter(p => compareWithGeneralisation(concept, p.source)).map(p => p.destination) :::
      links.filter(p => compareWithGeneralisation(concept, p.destination)).map(p => p.source)
  }

  def compareWithGeneralisation(p: Concept, q: Concept): Boolean = {
    if (p.uri == q.uri)  {
      return true
    }

    for (pgkey <- p.uri :: p._generalisations.frames.keys.toList) {
      for (qgkey <- q.uri :: q._generalisations.frames.keys.toList) {
        if (pgkey.toString == qgkey.toString) {
          return true
        }
      }
    }
    false
  }
}