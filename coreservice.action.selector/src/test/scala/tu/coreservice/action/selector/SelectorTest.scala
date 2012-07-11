package tu.coreservice.action.selector

/**
 * @author max talanov
 *         date 2012-07-08
 *         time: 10:54 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.way2think.Way2ThinkModel
import tu.model.knowledge.training.Goal

@RunWith(classOf[JUnitRunner])
class SelectorTest extends FunSuite {

  val selector = new Selector()

  test("test Ok") {
    assert(condition = true)
  }

  test("Selector Request processing should be ok") {
    val res = selector.apply(Goal("ProcessIncident"))
    val expected = List[Way2ThinkModel](Way2ThinkModel("tu.coreservice.splitter.PreliminarySplitter"),
      Way2ThinkModel("tu.coreservice.spellcorrector.SpellCorrector"),
      Way2ThinkModel("tu.coreservice.annotator.KBAnnotatorImplKBAnnotatorImpl")
    )
    expect(expected.size)(res.size)
  }

}
