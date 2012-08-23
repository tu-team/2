package tu.model.knowledge

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import semanticnetwork.SemanticNetworkLink

/**
 * Created by IntelliJ IDEA.
 * User: adel
 * Date: 23.08.12
 * Time: 6:45
 * To change this template use File | Settings | File Templates.
 */

@RunWith(classOf[JUnitRunner])
class ResourceTest extends FunSuite {

  class R (_uri: KnowledgeURI, _probability: Probability = new Probability() ) extends Resource(_uri, _probability)
  {
    def this(name:String) = this(KnowledgeURI(name))
    def this(name:String, _p: Probability) = this(KnowledgeURI(name), _p)

    //def this(map:Map[String, String]) = this(new KnowledgeURI(map, this.getClass()), new Probability(map, this.getClass()))
  }

  test("test Ok") {
    assert(true)
  }

  /*
  test("new exemplar from map shoul be equivalent to original ") {
    val s = new R("test1")
    val d = new R("test1")
    expect(t.content)(kl)
  } */

}