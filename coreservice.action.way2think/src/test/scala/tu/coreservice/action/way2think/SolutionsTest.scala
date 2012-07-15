package tu.coreservice.action.way2think

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.domain.Concept._
import tu.model.knowledge.domain.ConceptLink._
import tu.model.knowledge.domain.{ConceptLink, Concept, ConceptNetwork}
import tu.model.knowledge.{Resource, Probability, KnowledgeURI}
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.semanticnetwork.{SemanticNetwork, SemanticNetworkLink, SemanticNetworkNode}

/**
 * Created by IntelliJ IDEA.
 * User: adel
 * Date: 11.07.12
 * Time: 2:30
 * To change this template use File | Settings | File Templates.
 */

@RunWith(classOf[JUnitRunner])
class SolutionsTest extends FunSuite{

  var db = new Solutions()

/*

  test("ConceptNetwork should contain concepts and their contents") {
    val subject = Concept("subject")
    val objectConcept = Concept("object")
    val has = ConceptLink(subject, objectConcept, "has")


    val sn0 = new ConceptNetwork(List[Concept](concept), List(has),uri)
    expect(sn0.nodes(0).content.toString)(content.toString)
  }

  val uri = new KnowledgeURI("namespace", "name", "revision")
  val probability = new Probability
  val computer = new KnowledgeString("computer", uri)
  val software = new KnowledgeString("software", uri)
  val firefox = new KnowledgeString("firefox", uri)
  val access = new KnowledgeString("firefox", uri)
  val login = new KnowledgeString("login", uri)
  val password = new KnowledgeString("password", uri)

  val source: SemanticNetworkNode[Resource] = new SemanticNetworkNode(computer, List[SemanticNetworkLink](), uri)
  val source: SemanticNetworkNode[Resource] = new SemanticNetworkNode(new KnowledgeString(sourceContent, uri), List[SemanticNetworkLink](), uri)
  val destination: SemanticNetworkNode[Resource] = new SemanticNetworkNode(new KnowledgeString(destinationContent, uri), List[SemanticNetworkLink](), uri)

  test("test Ok") {
    assert(condition = true)
  }

  test("SemanticNetwork should store several root nodes") {
    val sn0 = new SemanticNetwork(List[SemanticNetworkNode[Resource]](source), uri)
    expect(sn0.rootNodes(0).content.toString)(sourceContent)
    val sn1 = new SemanticNetwork(List[SemanticNetworkNode[Resource]](source, destination), uri)
    expect(sn1.rootNodes(1).content.toString)(destinationContent)
  }
  */


  /*
  test("Empty list should be return null") {
    expect(s.issue)(getTestSN())
  }

  db.add(getSolvedIssue1())

  test("Dummy list should be return this element") {
    expect(s.issue)(getTestSN())
  }

  db.add(getSolvedIssue2())

  test("list should be return same element") {
    expect(s.issue)(getTestSN())
    expect(s.issue)(getTestSN())
  }

  test("list should be return nearest element") {

  def getSolvedIssue1() = {1}
  */
}