package tu.knowledge.semanticnetwork

/**
 * @author max talanov
 *         date 2012-05-02
 *         time: 11:22 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.semanticnetwork.{SemanticNetworkNode, SemanticNetworkLink}
import tu.model.knowledge.{Resource, Probability, KnowledgeURI}

@RunWith(classOf[JUnitRunner])
class SemanticNetworkLinkTest extends FunSuite {

  val namespace = "testNamespace"
  val name = "name"
  val revision = "rev"
  val uri = new KnowledgeURI(namespace, name, revision)
  val probability = new Probability
  val sourceContent = "Source"
  val destinationContent = "Dest"
  val source: SemanticNetworkNode[Resource] = new SemanticNetworkNode(new KnowledgeString(sourceContent, uri), List[SemanticNetworkLink](), uri)
  val destination: SemanticNetworkNode[Resource] = new SemanticNetworkNode(new KnowledgeString(destinationContent, uri, probability), List[SemanticNetworkLink](), uri, probability)

  test("test Ok") {
    assert(true)
  }

  test("SemanticNetworkLink should store source and destination") {
    val s = new SemanticNetworkLink(source, destination, uri)
    expect(s.source.content.toString)(sourceContent)
    expect(s.destination.content.toString)(destinationContent)
  }
}
