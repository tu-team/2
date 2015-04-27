package tu.knowledge.semanticnetwork

/**
 * @author max
 *         date 2012-05-03
 *         time: 11:27 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.semanticnetwork.{SemanticNetwork, SemanticNetworkLink, SemanticNetworkNode}
import tu.model.knowledge.{Resource, Probability, KnowledgeURI}

@RunWith(classOf[JUnitRunner])
class SemanticNetworkTest extends FunSuite {

  val namespace = "testNamespace"
  val name = "name"
  val revision = "rev"
  val uri = new KnowledgeURI(namespace, name, revision)
  val probability = new Probability
  val sourceContent = "Source"
  val destinationContent = "Dest"
  val source: SemanticNetworkNode[Resource] = new SemanticNetworkNode(new KnowledgeString(sourceContent, uri), List[SemanticNetworkLink](), uri)
  val destination: SemanticNetworkNode[Resource] = new SemanticNetworkNode(new KnowledgeString(destinationContent, uri), List[SemanticNetworkLink](), uri)
  val isolated: SemanticNetworkNode[Resource] = new SemanticNetworkNode(new KnowledgeString("isolated", uri), List[SemanticNetworkLink](), uri)



  test("SemanticNetwork should store several root nodes") {
    val sn0 = new SemanticNetwork(List[SemanticNetworkNode[Resource]](source), uri)
    expect(sn0.rootNodes(0).content.toString)(sourceContent)
    val sn1 = new SemanticNetwork(List[SemanticNetworkNode[Resource]](source, destination), uri)
    expect(sn1.rootNodes(1).content.toString)(destinationContent)
  }

  test("SemanticNetwork should store new link") {
    val sn = new SemanticNetwork(List[SemanticNetworkNode[Resource]](source, destination, isolated), uri)
    sn.addLink(source,  destination)

    assert(! sn.rootNodes(0).links.isEmpty)
    assert(! sn.rootNodes(1).links.isEmpty)
    assert(sn.rootNodes(2).links.isEmpty)
  }


}
