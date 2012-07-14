package tu.coreservice.splitter.test
import junit.framework._;
import Assert._
import tu.coreservice.splitter.PreliminarySplitter
import tu.model.knowledge.communication.ContextHelper
import tu.model.knowledge.KnowledgeURI
import tu.model.knowledge.primitive.KnowledgeString
;
/**
 * @author toscheva
 * Date: 06.06.12
 * Time: 13:34
 */

/**
 * Unit test for simple App.
 */
class SplitterTest extends TestCase("splitter") {

    def testSplitter()={

      var splitter= new PreliminarySplitter()

      splitter.processSentences("I have a problem with Firefox. Please reinstall.")

    }


}

