package tu.knowledge

/**
 * @author max
 *         date 2012-05-07
 *         time: 11:40 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.semanticnetwork.{SemanticNetworkLink, SemanticNetworkNode}
import tu.model.knowledge.frame.Frame
import tu.model.knowledge._

@RunWith(classOf[JUnitRunner])
class MicroNemeTest extends FunSuite {

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
  val kl = new KLine(Map[KnowledgeURI, Resource](f1.uri-> f1, f2.uri -> f2), uri)


  test("test Ok") {
    assert(true)
  }

  test("Frame should contain several resources") {
    val mn = new MicroNeme(Map(kl.uri -> kl), Map(f1.uri -> f1, f2.uri -> f2), uri)
    expect(mn.kLines.get(kl.uri).get)(kl)
    expect(mn.frames.get(f1.uri).get)(f1)
    expect(mn.frames.get(f2.uri).get)(f2)
  }
}
