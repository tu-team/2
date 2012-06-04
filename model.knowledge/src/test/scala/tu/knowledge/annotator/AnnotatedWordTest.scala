package tu.knowledge.annotator

/**
 * @author max
 *         date 2012-06-02
 *         time: 1:08 AM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.semanticnetwork.{SemanticNetworkLink, SemanticNetworkNode}
import tu.model.knowledge.frame.Frame
import tu.model.knowledge.{KLine, Resource, Probability, KnowledgeURI}
import tu.model.knowledge.domain.Concept
import tu.model.knowledge.annotator.AnnotatedWord

@RunWith(classOf[JUnitRunner])
class AnnotatedWordTest extends FunSuite {

  test("test Ok") {
    assert(true)
  }

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

  test("Annotated word should contain string") {
    val content = "word"
    val a = new AnnotatedWord(List[Concept](), content, uri)
    expect(a.value)(content)
  }

}
