package tu.coreservice.action.way2think

import org.scalatest.FunSuite
import org.scalatest.Assertions._
import tu.model.knowledge.domain.Concept
import tu.model.knowledge.{Probability, KnowledgeURI, Resource}
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.selector.SelectorRequest
import tu.model.knowledge.communication.{Context, ContextHelper}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**.
 * @author adel chepkunov
 *         Date: 30.06.12
 *         Time: 12:05
 */

@RunWith(classOf[JUnitRunner])
class FindMostProbableActionTest extends FunSuite {

  test("test Ok") {
    assert(condition = true)
  }

  test("One entry should work") {

    org.scalatest.Assertions.expect(1 > 0)(true) //expected, actual
    val w2t0 = new SelectorRequest(KnowledgeURI("test1k"), KnowledgeURI("test1"), new Probability(0.9))

    var context0: Context = ContextHelper(Nil, "test context")
    context0.classificationResultsAdd(w2t0)
    val context1 = FindMostProbableAction(context0)

    org.scalatest.Assertions.expect(Some(w2t0))(context1.bestClassificationResult) //expected, actual
    org.scalatest.Assertions.expect(Nil)(context1.classificationResults) //expected, actual

    val context2 = FindMostProbableAction(context1)
    org.scalatest.Assertions.expect(None)(context2.bestClassificationResult) //expected, actual
  }


  test("Multi entry should work") {

    org.scalatest.Assertions.expect(1 > 0)(true) //expected, actual
    val w2t0 = new SelectorRequest(KnowledgeURI("test1k"), KnowledgeURI("test1"), new Probability(0.9))
    val w2t1 = new SelectorRequest(KnowledgeURI("test2k"), KnowledgeURI("test2"), new Probability(0.8))

    val context0: Context = ContextHelper(Nil, "test context")

    context0.classificationResultsAdd(w2t0)
    context0.classificationResultsAdd(w2t1)

    val context1 = FindMostProbableAction(context0)

    org.scalatest.Assertions.expect(Some(w2t0))(context1.bestClassificationResult) //expected, actual
    org.scalatest.Assertions.expect(Some(w2t1))(context1.classificationResults.headOption) //expected, actual

    val context2 = FindMostProbableAction(context1)

    org.scalatest.Assertions.expect(Some(w2t1))(context2.bestClassificationResult) //expected, actual

    //context0.classificationResultsAdd(w2t1)
    //context0.classificationResultsAdd(w2t0)

    val context3 = FindMostProbableAction(context0)

    org.scalatest.Assertions.expect(Some(w2t0))(context3.bestClassificationResult) //expected, actual
    org.scalatest.Assertions.expect(Some(w2t1))(context3.classificationResults.headOption) //expected, actual

  }

}