package tu.knowledge.primitive

/**
 * @author talanov max
 *         date 2012-05-09
 *         time: 9:09 AM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.semanticnetwork.{SemanticNetworkNode, SemanticNetworkLink}
import tu.model.knowledge.primitive.{KnowledgeString, KnowledgeBoolean}
import tu.model.knowledge.{Probability, KnowledgeURI, Expression}
import tu.model.knowledge.narrative.{RulesNarrative, Rule}

@RunWith(classOf[JUnitRunner])
class NarrativeTest extends FunSuite {

  val namespace = "testNamespace"
  val name = "name"
  val revision = "rev"
  val uri = new KnowledgeURI(namespace, name, revision)
  val probability = new Probability
  val sourceContentString = "Source"
  val destinationContent = "Dest"
  val sourceContent = new KnowledgeString(sourceContentString, uri)
  val source: SemanticNetworkNode[KnowledgeString] = new SemanticNetworkNode(sourceContent, List[SemanticNetworkLink](), new KnowledgeURI(namespace, "source", revision))
  val ex: Expression = new Expression(uri) {
    def apply = new KnowledgeBoolean(false, uri)
  }
  val r = new Rule(ex, List(sourceContent), uri)

  test("test Ok") {
    assert(true)
  }

  test("RulesNarrative sohuld store rules"){
    val n = new RulesNarrative(List(r), uri)
    expect(n.rules)(List(r))
  }

}
