package tu.coreservice.action.way2think

import tu.model.knowledge.TypedKLine._
import tu.model.knowledge.annotator.AnnotatedPhrase
import tu.model.knowledge.annotator.AnnotatedPhrase._
import tu.model.knowledge.domain.Concept._
import tu.model.knowledge.domain.ConceptLink._
import org.scalatest.Assertions._
import tu.model.knowledge.domain.{ConceptNetwork, ConceptLink, Concept}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge._
import communication.ContextHelper
import communication.ContextHelper._
import howto.Solution
import narrative.Rule
import primitive.{KnowledgeBoolean, KnowledgeString}
import tu.coreservice.utilities.TestDataGenerator
import tu.dataservice.knowledgebaseserver.KBPrototype


/**
 * @author adel
 * Date: 10.07.12
 * Time: 7:11
 */

@RunWith(classOf[JUnitRunner])
class SearchSolutionTest  extends FunSuite
{

  // all test cases should be placed in tests of Solutions object
  // in this unit we only testing input and output parameters we

  val si1 =KBPrototype.solutions().head





  test("SearchSolution should work") {

    val net1 =KBPrototype.solutions().head.issue

    val inputContext = ContextHelper(List[Resource](), this.getClass.getName + " result")
    inputContext.lastResult = Some(net1)

    val instance = new SearchSolution


    val outputContext = instance.apply (inputContext)

    outputContext.lastResult match {
      case Some(si: SolvedIssue) => {
        expect(si.issue.uri)(si1.issue.uri)
      }
      case _ => {
        //Unexpected type or None instead Some
        expect(0)(1)
      }
    }

  }



  /*def getTestSolvedIssue1(): SolvedIssue = {
    val uri = new KnowledgeURI("namespace", "name", "revision")

    val ex: Expression = new Expression(uri) {
      def apply = new KnowledgeBoolean(false, uri)
    }

    val r = new Rule(ex, List(TestDataGenerator.generateInstallFirefoxHowTo), uri)

    val s = new Solution(List(r), uri)
    new SolvedIssue(TestDataGenerator.pleaseInstallFFSimulation, s, uri, new Probability)
  } */

}