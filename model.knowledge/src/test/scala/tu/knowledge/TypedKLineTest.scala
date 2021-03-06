package tu.knowledge

/**
 * @author max talanov
 *         date 2012-06-08
 *         time: 10:56 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.semanticnetwork.{SemanticNetworkLink, SemanticNetworkNode}
import tu.model.knowledge.frame.TypedFrame
import tu.model.knowledge.{TypedKLine, Resource, Probability, KnowledgeURI}

@RunWith(classOf[JUnitRunner])
class TypedKLineTest extends FunSuite {



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
  val klName = new KnowledgeString("name", uri)

  test("TypedKLine should contain several resources") {
    val kl = new TypedKLine[TypedFrame[Resource]](Map(f1.uri -> f1), uri)
    expect(kl.frames.get(f1.uri).get)(f1)
    expect(kl.get(f1.uri).get)(f1)
  }

}
