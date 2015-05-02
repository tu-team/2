package tu.coreservice.action.way2think

import tu.model.knowledge.{Constant, SolvedIssue}
import tu.model.knowledge.domain.{ConceptLink, Concept, ConceptNetwork}
import tu.dataservice.knowledgebaseserver.KBAdapter
import org.slf4j.LoggerFactory

/**
 * @author adel chepkunov, max talanov
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
    var found_solutions_map: Map[Double,SolvedIssue] = Map()
    var found_solutions: List[SolvedIssue] = List()
    for (s <- solutions) {
      found_solutions_map+=((relevance(issue, s.issue),s));
      /*if (relevance(issue, s.issue) <= Constant.DistanceThreadHold) {
        found_solutions = s :: found_solutions
      }*/
    }
    found_solutions=List(found_solutions_map.minBy(_._1)._2);
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

  /**
   * Creates list of ConceptNetworks reduced by one concept of specified issue ConceptNetwork.
   * @param issue ConceptNetwork to create reduced ConceptNetworks List.
   * @return reduced ConceptNetworks List.
   */
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
   * @return Int distance of specified issue to master ConceptNetwork-s.
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


  /**
   * Calculates relevance of specified issue and master ConceptNetworks, each missing issue Concept add 0.1 each master missing concept adds 1.
   * @param issue ConceptNetwork to compare with master.
   * @param master ConceptNetwork to be compared with.
   * @return Double relevance calculated according to missing concepts of issue and master.
   */
  def relevance(issue: ConceptNetwork, master: ConceptNetwork): Double = {
    val missingLists = findMissing(issue, master)
    val sum: Double = missingLists._1.size * Constant.IssueMissingFactor + missingLists._2.size * Constant.DomainMissingFactor
    sum
  }

  /**
   * Searches for missing Concept-s of issue ConceptNetwork and master ConceptNetwork, comparing list of concepts according to generalisation of issue Concept-s to master Concept-s.
   * @param issue ConceptNetwork to search generalisations in master.
   * @param master ConceptNetwork to search in generalisations.
   * @return Pair of issue concepts missing and master concept missing .
   */
  def findMissing(issue: ConceptNetwork, master: ConceptNetwork): Pair[List[Concept], List[Concept]] = {
    val issueNodes = issue.nodes
    val masterNodes = master.nodes
    val issueMissing = issueNodes.filter {
      iC: Concept => {
        masterNodes.filter {
          mC: Concept => {
            iC.hasExactGeneralisationRec(mC)
          }
        }.size <= 0
      }
    }
    val masterMissing = masterNodes.filter {
      mC: Concept => {
        issueNodes.filter {
          iC: Concept => {
            iC.hasExactGeneralisationRec(mC)
          }
        }.size <= 0
      }
    }
    (issueMissing, masterMissing)
  }

  /**
   * Checks if masterConcept has same ConceptLinks as has all the links of specified issueConcept.
   * @param issueConcept to check with master.
   * @param issue
   * @param masterConcept
   * @param master
   * @param depth
   * @return
   */
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