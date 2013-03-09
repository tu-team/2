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

  /**
   * Runs search of the solution based on relevance of issue and list of found and rejected solutions.
   * @param issue the ConceptNetwork of the issue.
   * @param badSolutions list of rejected solutions.
   * @return Option[SolvedIssue] if solution is found.
   */
  def search(issue: ConceptNetwork, badSolutions: List[ConceptNetwork]): Option[SolvedIssue] = {
    val res = searchNotEffective(issue, badSolutions, 1)
    if (res.size == 0) {
      None
    } else {
      Some(res.sortWith((s, t) => s.issue.nodes.size < t.issue.nodes.size).head)
    }
  }

  /**
   * Not effective search based on issue ConceptNetwork and list of rejected Solutions.
   * @param issue ConceptNetwork describing issue
   * @param badSolutions found and rejected Solutions
   * @param errors number of errors allowed for solution.
   * @return List[SolvedIssue] of found solutions.
   */
  private def searchNotEffective(issue: ConceptNetwork, badSolutions: List[ConceptNetwork], errors: Int): List[SolvedIssue] = {
    var found_solutions: List[SolvedIssue] = Nil
    for (s <- solutions) {
      if (distance(issue, s.issue, 0) == 0)
        found_solutions = s :: found_solutions
    }
    //If not found, then return None else - return SolvedIssue with minimal size
    val res: List[SolvedIssue] = if (errors == 0 || !found_solutions.isEmpty) {
      found_solutions
    } else {
      val subIssues = CNMinusList(issue)
      subIssues.map(p => searchNotEffective(p, badSolutions, errors - 1)).reduce((A, B) => A ::: B)
    }
    log info("solutions found={}", res)
    res
  }

  private def CNMinusList(issue: ConceptNetwork): List[ConceptNetwork] = {
    def issueWithoutNode(n: Concept): ConceptNetwork = {
      val nodes = issue.rootNodes.filter(p => p != n)
      new ConceptNetwork(nodes, issue.links, issue.uri, issue.probability)
    }
    issue.rootNodes.map(issueWithoutNode)
  }

  //TODO: allow K errors
  /**
   * Calculates relevance of the specified issue and master ConceptNetwork-s.
   * @param issue ConceptNetwork to calculate distance.
   * @param master ConceptNetwork to compare with.
   * @param errors Int number of errors to be ok, currently not used.
   * @return
   */
  def distance(issue: ConceptNetwork, master: ConceptNetwork, errors: Int): Int = {
    //find first node
    val listWithFirstNode = master.rootNodes.filter(p => compareWithGeneralisation(p, issue.rootNodes(0)))
    if (listWithFirstNode.isEmpty) {
      return 1
    }
    val depth = issue.rootNodes.size
    if (!masterHasAllLinks(issue.rootNodes(0), issue, listWithFirstNode(0), master, depth)) {
      return 1
    }
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
    if (p.uri.name == q.uri.name) {
      return true
    }

    for (pgkey <- p.uri :: p._generalisations.frames.keys.toList) {
      for (qgkey <- q.uri :: q._generalisations.frames.keys.toList) {
        if (pgkey.name == qgkey.name) {
          return true
        }
      }
    }
    false
  }
}