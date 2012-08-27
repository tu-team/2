package tu.dataservice.knowledgebaseserver

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.{Probability, KnowledgeURI}
import util.Random
import org.scalatest.{BeforeAndAfterAll, Suite, FunSuite}

/**
 * Created by IntelliJ IDEA.
 * User: adel
 * Date: 26.08.12
 * Time: 22:24
 * To change this template use File | Settings | File Templates.
 */

@RunWith(classOf[JUnitRunner])
class N4JKBTest extends FunSuite{

  test("write string to db and read from")
  {
    val random = Random.nextInt().toString
    val x = new KnowledgeString("value" + random, KnowledgeURI("uri" + random), new Probability(0.77, 0.44) )
    assert(x.KB_ID == -1)
    val saveResult = N4JKB.saveResource(x, "key" + random)

    assert(saveResult)
    assert(x.KB_ID != -1)


    val y = new KnowledgeString(N4JKB.loadChild("key" + random))

    expect(x.value)(y.value)
    expect(x.uri.toString)(y.uri.toString)

    expect(x.uri.toString)(y.uri.toString)

    expect(x.KB_ID)(y.KB_ID)

  }

}

/*
@RunWith(classOf[JUnitRunner])
class MasterSuite extends Suite with BeforeAndAfterAll {

   // Set up the temp file needed by the test, taking
   // a file name from the configMap
   //override def beforeAll(configMap: Map[String, Any]) {
   override def beforeAll() {

     N4JKB.init()

   }

   override def nestedSuites =
     List(new N4JKBTest)

   override def afterAll() {
     //     maybe remove all test data?
   }
 }
 */ //todo - try to run with with BeforeAndAfterAll Suite