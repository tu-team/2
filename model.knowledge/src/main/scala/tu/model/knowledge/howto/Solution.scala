package tu.model.knowledge.howto

import tu.model.knowledge.domain.ConceptNetwork
import tu.model.knowledge.narrative.{Narrative, Rule, RulesNarrative}
import tu.exception.UnexpectedException
import tu.model.knowledge.frame.Frame
import tu.model.knowledge.howto.HowTo._
import tu.model.knowledge._

/**
 * Stores Solution that is container for List of HowTo-s.
 * @author max talanov
 *         date 2012-05-09
 *         time: 7:08 PM
 */

class Solution(_rules: List[HowTo], _uri: KnowledgeURI, _probability: Probability)
  extends Narrative[HowTo](_rules, _uri, _probability){

  def this(_rules: List[HowTo], _uri: KnowledgeURI) {
    this(_rules: List[HowTo], _uri: KnowledgeURI, new Probability)
  }

  def save(kb: KB, parent: KBNodeId, key: String, linkType: String): Boolean = {
    var res = kb.saveResource(this, parent, key, linkType)

    for (x: HowTo <- resources) {
      res &= x.save(kb, KBNodeId(KBMap.get(this)), x.uri.toString, Constant.DEFAULT_LINK_NAME)
    }

    res
  }


}

object Solution {
  def apply(rules: List[HowTo]): Solution = {
    new Solution(rules,null,null)
  }


  def load(kb: KB, parent: KBNodeId, key: String, linkType: String): Solution = {
    //todo: move this method to parent class
    val selfMap = kb.loadChild(parent, key, linkType)
    if (selfMap.isEmpty) {
      //log.error("Concept not loaded for link {}/{} for {}", List(key, linkType, parent.ID.toString))
      throw new UnexpectedException("LoadError for " + parent.ID.toString)
    }

    load(kb, selfMap)
  }

  def load(kb: KB, selfMap: Map[String, String]): Solution = {

    val ID = KBNodeId(selfMap)

    //
    val resources: List[HowTo] = kb.loadChildrenList(ID).map(x => HowTo.load(kb, x))

    new Solution(resources,
      new KnowledgeURI(selfMap),
      new Probability(selfMap)
    )
  }

}
