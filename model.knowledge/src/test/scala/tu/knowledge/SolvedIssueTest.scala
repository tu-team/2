package tu.knowledge

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.domain.{Concept, ConceptNetwork}
import tu.model.knowledge.semanticnetwork.{SemanticNetworkLink, SemanticNetworkNode, SemanticNetwork}
import tu.model.knowledge.frame.TypedFrame
import tu.model.knowledge.howto.{HowTo, Solution}
import tu.model.knowledge._
import tu.model.knowledge.primitive.{KnowledgeBoolean, KnowledgeString}
import tu.model.knowledge.narrative.Rule

/**
 * Created by IntelliJ IDEA.
 * User: adel
 * Date: 11.07.12
 * Time: 2:06
 * To change this template use File | Settings | File Templates.
 */

@RunWith(classOf[JUnitRunner])
class SolvedIssueTest extends FunSuite {

  val uri = new KnowledgeURI("namespace", "name", "revision")
  val probability = new Probability
  val s = new SolvedIssue(TestDataGenerator.pleaseInstallFFSimulation, getTestSolution(),   uri, probability)

  test("SolvedIssue should contain issue and solution") {
    //TODO do it
    //expect(s.issue.toString())(getTestSN().toString())
    //expect(s.solution.toString())(getTestSolution().toString())
  }


  def getTestSN(): SemanticNetwork = {
    val namespace = "testNamespace"
    val name = "name"
    val revision = "rev"
    val uri = new KnowledgeURI(namespace, name, revision)
    val sourceContent = "Source"
    val destinationContent = "Dest"
    val test: SemanticNetworkNode[Resource] = new SemanticNetworkNode(new KnowledgeString("TestContent", uri), List[SemanticNetworkLink](), new KnowledgeURI(namespace, "test", revision))
    val list:List[SemanticNetworkNode[Resource]] = List[SemanticNetworkNode[Resource]](test)
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
    val f1 = new TypedFrame(Map[KnowledgeURI, Resource](source.uri -> source, destination.uri -> destination), new KnowledgeURI(namespace, "f1", revision))
    val f2 = new TypedFrame(Map[KnowledgeURI, Resource](source.uri -> source, destination.uri -> destination, test.uri -> test), new KnowledgeURI(namespace, "f2", revision))
    val klName = new KnowledgeString("name", uri)
    val kl = new KLine(Map[KnowledgeURI, Resource](f1.uri -> f1), uri)
    val t = new Tag(kl, List[SemanticNetworkLink](), uri)
    val f = new TypedFrame(Map[KnowledgeURI, Resource](), uri)
    val h = new HowTo(List(f), List(t), uri)
    def apply = new KnowledgeBoolean(false, uri)
    val ex: Expression = new Expression(uri) {
      def apply = new KnowledgeBoolean(false, uri)
    }
    val r = new Rule(ex, List(h), uri)

    val s = new Solution(List(r), uri)
    s
  }
}