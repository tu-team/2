package tu.knowledge.annotator

/**
 * @author max
 *         date 2012-06-03
 *         time: 12:28 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.{KnowledgeURI, Probability, Resource}
import tu.model.knowledge.semanticnetwork.{SemanticNetwork, SemanticNetworkNode, SemanticNetworkLink}
import tu.model.knowledge.domain.Concept
import tu.model.knowledge.annotator.{AnnotatedSemanticNetwork, AnnotatedWord}

@RunWith(classOf[JUnitRunner])
class AnnotatedSemanticNetworkTest extends FunSuite {

  test("test Ok") {
    assert(condition = true)
  }

  val namespace = "testNamespace"
  val name = "name"
  val revision = "rev"
  val uri = new KnowledgeURI(namespace, name, revision)
  val probability = new Probability
  val sourceContent = "Source"
  val destinationContent = "Dest"
  val source: SemanticNetworkNode[AnnotatedWord] = new SemanticNetworkNode(new AnnotatedWord(List[Concept](), sourceContent, uri), List[SemanticNetworkLink](), uri)
  val destination: SemanticNetworkNode[AnnotatedWord] = new SemanticNetworkNode(new AnnotatedWord(List[Concept](), destinationContent, uri), List[SemanticNetworkLink](), uri)

  test("SemanticNetwork should store several root nodes") {
    val sn0 = new AnnotatedSemanticNetwork(List[SemanticNetworkNode[AnnotatedWord]](source), uri)
    expect(sn0.rootNodes(0).content.toString)(sourceContent)
    val sn1 = new AnnotatedSemanticNetwork(List[SemanticNetworkNode[AnnotatedWord]](source, destination), uri)
    expect(sn1.words(1).content.toString)(destinationContent)
  }

}
