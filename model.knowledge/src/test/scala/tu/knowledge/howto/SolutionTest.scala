package tu.knowledge.howto

/**
 * @author max
 *         date 2012-05-09
 *         time: 7:00 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.frame.Frame
import tu.model.knowledge.semanticnetwork.{SemanticNetworkNode, SemanticNetworkLink}
import tu.model.knowledge._
import howto.{Solution, HowTo}
import narrative.{Rule, Narrative}
import primitive.{KnowledgeBoolean, KnowledgeString}

@RunWith(classOf[JUnitRunner])
class SolutionTest extends FunSuite {

  val namespace = "testNamespace"
  val name = "name"
  val revision = "rev"
  val uri = new KnowledgeURI(namespace, name, revision)
  val probability = new Probability
  val sourceContent = "Source"
  val destinationContent = "Dest"
  val source: SemanticNetworkNode[KnowledgeString] = new SemanticNetworkNode(new KnowledgeString(sourceContent, uri), List[SemanticNetworkLink](), new KnowledgeURI(namespace, "source", revision))
  val destination: SemanticNetworkNode[KnowledgeString] = new SemanticNetworkNode(new KnowledgeString(destinationContent, uri), List[SemanticNetworkLink](), new KnowledgeURI(namespace, "dest", revision))
  val test: SemanticNetworkNode[KnowledgeString] = new SemanticNetworkNode(new KnowledgeString("TestContent", uri), List[SemanticNetworkLink](), new KnowledgeURI(namespace, "test", revision))
  val f1 = new Frame(Map[KnowledgeURI, Resource](source.uri -> source, destination.uri -> destination), new KnowledgeURI(namespace, "f1", revision))
  val f2 = new Frame(Map[KnowledgeURI, Resource](source.uri -> source, destination.uri -> destination, test.uri -> test), new KnowledgeURI(namespace, "f2", revision))
  val klName = new KnowledgeString("name", uri)
  val kl = new KLine(Map[KnowledgeURI, Resource](f1.uri -> f1), uri)
  val t = new Tag(kl, List[SemanticNetworkLink](), uri)
  val f = new Frame(Map[KnowledgeURI, Resource](), uri)
  val h = new HowTo(List(f), List(t), uri)
  val ex: Expression = new Expression(uri) {
    def apply = new KnowledgeBoolean(false, uri)
  }
  val r = new Rule(ex, List(h), uri)

  test("test Ok") {
    assert(true)
  }

  test("Solution should contain HowTo-s") {
    val s = new Solution(List(r), uri)
    expect(s.rules)(List(r))
  }

}
