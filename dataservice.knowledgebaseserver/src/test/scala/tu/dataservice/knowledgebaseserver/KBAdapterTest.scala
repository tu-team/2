package tu.dataservice.knowledgebaseserver

/**
 * @author alex
 *         Time stamp: 10/25/12 5:07 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.semanticnetwork.{SemanticNetworkLink, SemanticNetworkNode}
import tu.model.knowledge.frame.TypedFrame
import tu.model.knowledge._
import annotator.AnnotatedPhrase
import communication.{ContextHelper, ShortTermMemory}
import domain.{ConceptLink, Concept}
@RunWith(classOf[JUnitRunner])
class KBAdapterTest extends FunSuite  {


    test("Save and get domain model") {
      val test=KBAdapter.domainModel(KnowledgeURI(Constant.defaultDomainName))
      expect(true)(test!=null)
    }


    test("Save and get simulation model") {
      val test=KBAdapter.simulationModel(KnowledgeURI(Constant.defaultDomainName))
      expect(true)(test!=null)
    }

    test("Save and get reformulation model") {
      val test=KBAdapter.reformulationModel(KnowledgeURI(Constant.defaultDomainName))
      expect(true)(test!=null)
    }


}
