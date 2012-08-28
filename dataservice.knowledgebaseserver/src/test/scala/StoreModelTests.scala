import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import tu.dataservice.knowledgebaseserver.N4JKB
import tu.model.knowledge.annotator.AnnotatedPhrase
import tu.model.knowledge.communication.ContextHelper
import tu.model.knowledge.domain.{ConceptLink, Concept}
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.TypedKLine

/**
 * @author achepkunov
 * Date: 28.08.12
 * Time: 12:41
 */

  @RunWith(classOf[JUnitRunner])
  class StoreModelTest extends FunSuite{

  //now stored only generalisation links, but for true TDD tests should be written before $)
  test("Concept should be stored and restored") {
    val content = new KnowledgeString("content", uri)
    val x = new Concept(TypedKLine[Concept]("generalisations"), TypedKLine[Concept]("specialisations"),
      TypedKLine[AnnotatedPhrase]("user", AnnotatedPhrase("user")), content, List[ConceptLink](), uri)

    val context = ContextHelper(Nil, x, "test context")

    //TODO there is no such method save
    N4JKB.saveResource(context, "testContext")
    x.save(N4JKB, context, "testKey", "testRelation")

    val y = new Concept(N4JKB, context, "testKey", "testRelation")

    expect(x.content)(y.content)
  }


}
