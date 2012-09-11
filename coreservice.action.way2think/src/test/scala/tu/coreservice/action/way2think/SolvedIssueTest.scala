package tu.coreservice.action.way2think

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

import tu.model.knowledge.semanticnetwork.{SemanticNetworkLink, SemanticNetworkNode, SemanticNetwork}
import tu.model.knowledge.frame.Frame
import tu.model.knowledge.frame.{Frame, TypedFrame}
import tu.model.knowledge.howto.{HowTo, Solution}


import tu.model.knowledge.primitive.{KnowledgeBoolean, KnowledgeString}
import tu.model.knowledge.narrative.Rule
import tu.coreservice.utilities.TestDataGenerator
import tu.model.knowledge._
import domain.{ConceptLink, ConceptTag, Concept, ConceptNetwork}
import domain.ConceptLink
import narrative.Rule
import primitive.KnowledgeBoolean
import semanticnetwork.SemanticNetwork
import semanticnetwork.SemanticNetworkLink
import semanticnetwork.SemanticNetworkNode

/**
 * @author adel
 *         Date: 11.07.12
 *         Time: 2:06
 */

@RunWith(classOf[JUnitRunner])
class SolvedIssueTest extends FunSuite {

  val uri = new KnowledgeURI("namespace", "name", "revision")
  val probability = new Probability
  val s = new SolvedIssue(TestDataGenerator.pleaseInstallFFSimulation, getTestSolution(), uri, probability)

  test("SolvedIssue should contain issue and solution") {
    expect(s.issue.uri.toString)(TestDataGenerator.pleaseInstallFFSimulation.uri.toString)
    expect(s.solution.uri.toString)(getTestSolution().uri.toString)
  }


  def getTestSN(): SemanticNetwork = {
    val namespace = "testNamespace"
    val name = "name"
    val revision = "rev"
    val uri = new KnowledgeURI(namespace, name, revision)
    val sourceContent = "Source"
    val destinationContent = "Dest"
    val test: SemanticNetworkNode[Resource] = new SemanticNetworkNode(new KnowledgeString("TestContent", uri), List[SemanticNetworkLink](), new KnowledgeURI(namespace, "test", revision))
    val list: List[SemanticNetworkNode[Resource]] = List[SemanticNetworkNode[Resource]](test)
    new SemanticNetwork(list, uri)
  }

  def getTestSolution(): Solution = {
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
    val c1 = Concept("f1")
    val f1 = new Frame(Map[KnowledgeURI, Resource](source.uri -> source, destination.uri -> destination), new KnowledgeURI(namespace, "f1", revision))
    val f2 = new Frame(Map[KnowledgeURI, Resource](source.uri -> source, destination.uri -> destination, test.uri -> test), new KnowledgeURI(namespace, "f2", revision))
    val klName = new KnowledgeString("name", uri)
    val kl = new TypedKLine[Concept](Map[KnowledgeURI, Concept](c1.uri -> c1), uri)
    val t = new ConceptTag(kl, List[ConceptLink](), uri)
    val f = new Frame(Map[KnowledgeURI, Resource](), uri)
    val h = new HowTo(List(f), List(t), uri)
    def apply = new KnowledgeBoolean(false, uri)
    //val ex: Expression = new Expression(uri) {
//      def apply = new KnowledgeBoolean(false, uri)
  //  }
    //val r = new Rule(ex, List(h), uri)

    val s = new Solution(List(h), uri)
    s
  }
}