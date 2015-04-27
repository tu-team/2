package tu.knowledge.semanticnetwork

/**
 * @author talanov max
 *         date 2012-05-03
 *         time: 11:23 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.{Probability, KnowledgeURI}
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.semanticnetwork.{SemanticNetworkNode, SemanticNetworkLink}

@RunWith(classOf[JUnitRunner])
class SemanticNetworkNodeTest extends FunSuite {

  val namespace = "testNamespace"
  val name = "name"
  val revision = "rev"
  val uri = new KnowledgeURI(namespace, name, revision)
  val probability = new Probability
  val sourceContent = "Source"
  val destinationContent = "Dest"



  test("SemanticNetworkNode should store KnowledgeString") {
    val s: SemanticNetworkNode[KnowledgeString] = new SemanticNetworkNode[KnowledgeString](new KnowledgeString(sourceContent, uri), List[SemanticNetworkLink](), uri)
    expect(s.content.toString)(sourceContent)
  }

}
