package tu.coreservice.action.way2think

import tu.model.knowledge.{SolvedIssue, Resource, Probability, KnowledgeURI}
import tu.model.knowledge.domain.{ConceptLink, Concept, ConceptNetwork}
import tu.dataservice.knowledgebaseserver.KBAdapter


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

class Solutions{
  var solutions:List[SolvedIssue] = KBAdapter.solutions()

  def add(item:SolvedIssue):List[SolvedIssue] =
  {
    KBAdapter.solutions_add(item)
    solutions = item :: solutions
    solutions
  }

  def search(issue:ConceptNetwork, badSolutions: List[ConceptNetwork]):Option[SolvedIssue] =
  {
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
    val listWithFirstNode = master.rootNodes.filter(p => compareWithGeneralisation(p, issue.rootNodes(0)))
    if (listWithFirstNode.isEmpty )
      return 1

    val depth = issue.rootNodes.size

    if (! masterHasAllLinks(issue.rootNodes(0), issue, listWithFirstNode(0), master, depth))
      return 1

    0
  }

  def masterHasAllLinks(issueConcept:Concept, issue:ConceptNetwork, masterConcept:Concept, master:ConceptNetwork, depth:Int):Boolean =
  {
    if (depth == 0) return true

    val links = getConceptChildren(issueConcept, issue.links)
    if (links.isEmpty) return true

    for (issueNext <- links)
    {
      // forall for empty list always return true, so we use not (!masterHasAllLinks)
      val bad = getConceptChildren(masterConcept, master.links).filter(p => compareWithGeneralisation(p,issueNext))
                  .forall(p => ! masterHasAllLinks(issueNext, issue, p, master, depth-1)   )
      if (bad) return false
    }

    true
  }

  def getConceptChildren(concept:Concept, links:List[ConceptLink]):List[Concept] ={
    links.filter(p=> compareWithGeneralisation(concept, p.source)).map(p => p.destination ) :::
      links.filter(p=> compareWithGeneralisation(concept, p.destination)).map(p => p.source )
  }

  def compareWithGeneralisation(p:Concept, q:Concept):Boolean =
  {
    if( p.uri == q.uri)
       return true

    for (pgkey <- p.uri :: p._generalisations.frames.keys.toList)
      for (qgkey <- q.uri :: q._generalisations.frames.keys.toList)
        if( pgkey.toString == qgkey.toString)
          return true

    return false
  }
}