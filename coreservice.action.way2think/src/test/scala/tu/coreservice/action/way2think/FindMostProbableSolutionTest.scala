package tu.coreservice.action.way2think

import org.scalatest.FunSuite
import org.scalatest.Assertions._
import tu.model.knowledge.communication.ContextHelper
import tu.model.knowledge.domain.Concept
import tu.model.knowledge.{Probability, KnowledgeURI, Resource}
import tu.model.knowledge.primitive.KnowledgeString

/**
 * Created by IntelliJ IDEA.
 * User: adel
 * Date: 30.06.12
 * Time: 12:05
 * To change this template use File | Settings | File Templates.
 */

class FindMostProbableSolutionTest extends FunSuite {

  test("One entry should work") {

    org.scalatest.Assertions.expect(1 > 0)(true) //expected, actual

    val resources: List[Resource] = List(
      new KnowledgeString("test1", KnowledgeURI("test1"), new Probability(0.9))

    )
    var context = ContextHelper(resources, "test context")
    org.scalatest.Assertions.expect(context)(FindMostProbableSolution(context)) //expected, actual
    FindMostProbableSolution
  }


  test("Multi entry should work") {

    org.scalatest.Assertions.expect(1 > 0)(true) //expected, actual

    val resources: List[Resource] = List(
      new KnowledgeString("test1", KnowledgeURI("test1"), new Probability(0.9)),
      new KnowledgeString("test2", KnowledgeURI("test2"), new Probability(0.8))

    )
    var context = ContextHelper(resources, "test context")
    var res_context = ContextHelper(List(
      new KnowledgeString("test1", KnowledgeURI("test1"), new Probability(0.9))), "test context")

    org.scalatest.Assertions.expect(res_context)(FindMostProbableSolution(context)) //expected, actual
    FindMostProbableSolution

  }

}