package tu.model.knowledge

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import semanticnetwork.SemanticNetworkLink

/**
 * @author adel
 * Date: 23.08.12
 * Time: 6:45
 */

@RunWith(classOf[JUnitRunner])
class ResourceTest extends FunSuite {

  class R (_uri: KnowledgeURI, _probability: Probability = new Probability() ) extends Resource(_uri, _probability)
  {
    def this(name:String) = this(KnowledgeURI(name))
    def this(name:String, _p: Probability) = this(KnowledgeURI(name), _p)

    def this(map:Map[String, String]) = this(new KnowledgeURI(map), new Probability(map))

    override def export:Map[String, String] =
    {
      val x:Map[String, String] = super.export
      //x("class") = this.getClass.className
      x
    }

  }

  test("test Ok") {
    assert(true)
  }

  /*
  test("new exemplar from map should be equivalent to original ") {
    val s = new R("test1")
    val d = new R("test1")
    expect(t.content)(kl)
  } */

}