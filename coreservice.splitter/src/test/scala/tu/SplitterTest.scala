package tu.coreservice.splitter.test
import junit.framework._;
import Assert._
import tu.coreservice.splitter.PreliminarySplitter
import tu.model.knowledge.communication.ContextHelper
import tu.model.knowledge.KnowledgeURI
import tu.model.knowledge.primitive.KnowledgeString
;
/**
 * Created by IntelliJ IDEA.
 * User: toscheva
 * Date: 06.06.12
 * Time: 13:34
 *
 */

/**
 * Unit test for simple App.
 */
class SplitterTest extends TestCase("splitter") {

    def testSplitter()={

      var splitter= new PreliminarySplitter()

      //initialize input context and output context
      var input=ContextHelper.initializeContext(null)
      var output=ContextHelper.initializeContext(null )

      //place text to input
      input.frames+=(new KnowledgeURI("tu.com","inputtext","0.3")-> new KnowledgeString("This are a simple text. Test contain sentence. And this is a third sentence.",null))

      splitter(input,output)

      assertTrue(output.frames.count(p=>true) == 3)

      //check grammar


    }


}

