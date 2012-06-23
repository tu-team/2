package samples


import tu.model.knowledge.primitive.Number
import org.junit.runner.RunWith
import org.specs._
import org.specs.matcher._
import runner.{ScalaTest, JUnitSuiteRunner, JUnit}
import scala.collection.immutable.Range.Double
import tu.model.knowledge.KnowledgeURI

//import org.scalacheck.Gen

/**
 * Sample specification.
 * 
 * This specification can be executed with: scala -cp <your classpath=""> ${package}.SpecsTest
 * Or using maven: mvn test
 *
 * For more information on how to write or run specifications, please visit: http://code.google.com/p/specs.
 *
 */
@RunWith(classOf[JUnitSuiteRunner])
class MySpecTest extends Specification with JUnit with ScalaTest /*with ScalaCheck*/ {

  "My" should {
    "allow " in {
      
      //0
    }
    "deny " in {
      //0
    }
  }
  
  "A List" should {
    "have a size method returning the number of elements in the list" in {
      List(1, 2, 3).size must_== 3
    }
    // add more examples here
    // ...
  }

  "KnowledgeURI" should {
    "store namespace, name, revision" in {
      val uri = new KnowledgeURI("test.namespace", "test", "0.0")
      uri.namespace must_== "test.namespace"
      uri.name must_== "test"
      uri.revision must_== "0.0"
    }
  }

  "Nuber" should {
    "store Double" in {
      val uri = new KnowledgeURI("test.namespace", "three", "0.0")
      // val n = new Number(new Double(3.0), )

    }
  }
}

object MySpecMain {
  def main(args: Array[String]) {
    new MySpecTest().main(args)
  }
}
