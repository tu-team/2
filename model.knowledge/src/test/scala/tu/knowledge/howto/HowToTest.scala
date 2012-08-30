package tu.knowledge.howto

/**
 * @author max
 *         date 2012-05-08
 *         time: 11:16 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.semanticnetwork.{SemanticNetworkLink, SemanticNetworkNode}
import tu.model.knowledge.frame.TypedFrame
import tu.model.knowledge._
import domain.{Concept, ConceptLink, ConceptTag}
import howto.HowTo

@RunWith(classOf[JUnitRunner])
class HowToTest extends FunSuite {

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
  val f1 = new TypedFrame(Map[KnowledgeURI, Resource](source.uri -> source, destination.uri -> destination), new KnowledgeURI(namespace, "f1", revision))
  val f2 = new TypedFrame(Map[KnowledgeURI, Resource](source.uri -> source, destination.uri -> destination, test.uri -> test), new KnowledgeURI(namespace, "f2", revision))
  val tagName = new KnowledgeString("tagName", new KnowledgeURI(namespace, "tag", revision))
  val c = Concept("c")
  val kl = new TypedKLine[Concept](Map[KnowledgeURI, Concept](c.uri -> c), uri)
  val t = new ConceptTag(kl, List[ConceptLink](), uri)
  val f = new TypedFrame(Map[KnowledgeURI, Resource](), uri)

  test("test Ok") {
    assert(true)
  }

  test("HowTo should store tags") {
    val h = new HowTo(List(f), List(t), uri)
    expect(h.tags)(List(t))
  }

  test("HowTo should store parameters") {
    val h = new HowTo(List(f), List(t), uri)
    expect(h.parameters)(List(f))
  }

}
