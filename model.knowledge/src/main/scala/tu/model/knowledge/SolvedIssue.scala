package tu.model.knowledge

import annotator.AnnotatedWord
import domain.{Concept, ConceptNetwork}
import howto.{Solution, HowTo}
import semanticnetwork.SemanticNetwork
import tu.model.knowledge.KBMap._
import tu.exception.UnexpectedException

/**
 * Created by IntelliJ IDEA.
 * User: adel
 * Date: 10.07.12
 * Time: 7:37
 * To change this template use File | Settings | File Templates.
 */

case class SolvedIssue (var issue:ConceptNetwork, val solution: Solution,   _uri: KnowledgeURI, _probability: Probability)
                                                                              extends Resource(_uri, _probability) {
  override def save(kb:KB, parent:KBNodeId, key:String, linkType:String, saved: List[String] = Nil): Boolean = {
    var res = kb.saveResource(this, parent, key, linkType)

    issue.save(kb, this, this.uri.name + "issue", Constant.DEFAULT_LINK_NAME)
    solution.save(kb, this, this.uri.name + "solution", Constant.DEFAULT_LINK_NAME)
  }
}


object SolvedIssue {
  def apply(issue:ConceptNetwork,solution:Solution): SolvedIssue = {
    new SolvedIssue(issue,solution,null,null)
  }


  def load(kb: KB, parentId: KBNodeId, key: String, linkType: String): SolvedIssue = {
    val selfMap = kb.loadChild(parentId, key, linkType)
    if (selfMap.isEmpty) {
      //log.error("Concept not loaded for link {}/{} for {}", List(key, linkType, parentId.toString))
      throw new UnexpectedException("Concept not loaded for link " + key + "/" + linkType + " for " + parentId.toString)
    }

    val ID = new KBNodeId(selfMap)
    val issue:ConceptNetwork = ConceptNetwork.load(kb, ID, selfMap("uri-name") + "issue", Constant.DEFAULT_LINK_NAME)
    val solution: Solution = Solution.load(kb, ID, selfMap("uri-name") + "solution", Constant.DEFAULT_LINK_NAME)
    SolvedIssue(issue, solution)
  }
}
