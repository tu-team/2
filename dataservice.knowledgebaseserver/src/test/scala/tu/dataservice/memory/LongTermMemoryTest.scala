package tu.dataservice.memory

/**
 * @author max
 *         date 2012-10-20
 *         time: 8:44 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.dataservice.knowledgebaseserver.KBAdapter
import tu.model.knowledge.domain.Concept
import tu.model.knowledge.KnowledgeURI

@RunWith(classOf[JUnitRunner])
class LongTermMemoryTest extends FunSuite {

  val testURIString = "test"
  val testURI = KnowledgeURI(testURIString)

  test("test Ok") {
    assert(true)
  }

  test("merge concepts") {
    val cp = Concept("parent")
    val cc = Concept.createSubConcept(cp, "child")
    val dm = KBAdapter.domainModel(testURIString)
    assert(dm.nodes.size > 0)
    val c = dm.nodes(0)
    val stmc = new ShortTermMemoryConceptWrapper(cc, c)
    val resCN = LongTermMemory.merge(stmc, testURI)
    /* val children = resCN.nodes.filter {
      c: Concept => {
        c.generalisations.frames.filter{
          val keyValue = {

          }
        }
      }
    }*/
    // assert (children.size > 0)
  }

}
