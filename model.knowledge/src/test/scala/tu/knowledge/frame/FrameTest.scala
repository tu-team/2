package tu.knowledge.frame

/**
 * @author max talanov
 *         date 2012-05-03
 *         time: 11:56 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.semanticnetwork.{SemanticNetworkLink, SemanticNetworkNode}
import tu.model.knowledge.frame.TypedFrame
import tu.model.knowledge.{Resource, Probability, KnowledgeURI}

@RunWith(classOf[JUnitRunner])
class FrameTest extends FunSuite {

  val namespace = "testNamespace"
  val name = "name"
  val revision = "rev"
  val uri = new KnowledgeURI(namespace, name, revision)
  val probability = new Probability
  val sourceContent = "Source"
  val destinationContent = "Dest"
  val source: SemanticNetworkNode[KnowledgeString] = new SemanticNetworkNode(new KnowledgeString(sourceContent, uri), List[SemanticNetworkLink](), uri)
  val destination: SemanticNetworkNode[KnowledgeString] = new SemanticNetworkNode(new KnowledgeString(destinationContent, uri), List[SemanticNetworkLink](), uri)

  test("test Ok") {
    assert(true)
  }

  test("TypedFrame should contain several resources") {
    val f = new TypedFrame(Map[KnowledgeURI, Resource](), uri)
    f.resources = Map[KnowledgeURI, Resource](source.uri -> source, destination.uri -> destination)
    expect(f.resources.get(destination.uri).get)(destination)
  }

}
