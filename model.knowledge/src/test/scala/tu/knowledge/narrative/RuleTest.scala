package tu.knowledge.narrative

/**
 * @author talanov max
 *         date 2012-05-09
 *         time: 8:51 AM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.primitive.{KnowledgeString, KnowledgeBoolean}
import tu.model.knowledge.semanticnetwork.{SemanticNetworkLink, SemanticNetworkNode}
import tu.model.knowledge.narrative.Rule
import tu.model.knowledge.{Expression, Probability, KnowledgeURI}

@RunWith(classOf[JUnitRunner])
class RuleTest extends FunSuite {

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

  test("test Ok") {
    assert(true)
  }

  test("Rule should store Expression") {
    val r = new Rule(ex, List(sourceContent), uri)
    expect(r.antecedent)(ex)
  }

  test("Rule should store List of resources (consequent)") {
    val r = new Rule(ex, List(sourceContent), uri)
    expect(r.consequent)(List(sourceContent))
  }


}
