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
import tu.dataservice.knowledgebaseserver.KBAdapter


/**
 * @author adel
 * Date: 10.07.12
 * Time: 7:11
 */

@RunWith(classOf[JUnitRunner])
class SearchSolutionTest  extends FunSuite
{

  // all test cases should be placed in tests of Solutions object
  // in this unit we only testing input and output parameters


  test("SearchSolution should work") {

    val searcher = new Solutions

    //searcher.solutions = Nil

    //searcher.add(getTestSolvedIssue1)

    val si1 = searcher.solutions.head

    val net1 = si1.issue

    val inputContext = ContextHelper(List[Resource](), this.getClass.getName + " request")
    inputContext.lastResult = Some(net1)

    val instance = new SearchSolution


    val outputContext = instance.apply (inputContext)

    outputContext.lastResult match {
      case Some(si: SolvedIssue) => {
        expect(si.issue.uri.name)(si1.issue.uri.name)
      }
      case _ => {
        //Unexpected type or None instead Some
        expect(0)(1)
      }
    }

  }



  def getTestSolvedIssue1(): SolvedIssue = {
    val uri = new KnowledgeURI("namespace", "name", "revision")

    val ex: Expression = new Expression(uri) {
      def apply = new KnowledgeBoolean(false, uri)
    }

    val s = new Solution(List(TestDataGenerator.generateInstallFirefoxHowTo), uri)
    new SolvedIssue(TestDataGenerator.pleaseInstallFFSimulation, s, uri, new Probability)
  }

}