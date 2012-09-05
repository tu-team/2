import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import tu.dataservice.knowledgebaseserver.N4JKB
import tu.model.knowledge.annotator.AnnotatedPhrase
import tu.model.knowledge.communication.ContextHelper
import tu.model.knowledge.domain.{ConceptNetwork, ConceptLink, Concept}
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.{KnowledgeURI, TypedKLine}

/**
 * @author achepkunov
 *         Date: 28.08.12
 *         Time: 12:41
 */

@RunWith(classOf[JUnitRunner])
class StoreModelTest extends FunSuite {

  val uri = KnowledgeURI("testuri")
  val uri1 = KnowledgeURI("testuri1")

  test("Concept should be stored and restored") {

    val context = ContextHelper(Nil, "test context") //context is parent node for x:Concept
    N4JKB.saveResource(context, "testContext")

    // empty concept

    val content = new KnowledgeString("content", uri)
    val x = new Concept(TypedKLine[Concept]("generalisations"), TypedKLine[Concept]("specialisations"),
      TypedKLine[AnnotatedPhrase]("user", AnnotatedPhrase("user")), content, List[ConceptLink](), uri)

    x.save(N4JKB, context, "testKey", "testRelation")

    val y = Concept.load(N4JKB, context, "testKey", "testRelation")

    expect(x.content.uri.name)(y.content.uri.name)
    expect(x.generalisations.frames.size)(y.generalisations.frames.size)
    expect(x.links.size)(y.links.size)


    // concept with generalisation

    val objectConcept = Concept("object")
    val systemConcept = Concept.createSubConcept(objectConcept, "system")

    systemConcept.save(N4JKB, context, "testKey1", "testRelation")

    val z = Concept.load(N4JKB, context, "testKey1", "testRelation")

    expect(systemConcept.content.uri.name)(z.content.uri.name)
    expect(systemConcept.generalisations.frames.size)(z.generalisations.frames.size)
    expect(systemConcept.links.size)(z.links.size)

  }

  test("ConceptNetwork should be stored and restored") {

    val context = ContextHelper(Nil, "test context") //context is parent node for x:Concept
    N4JKB.saveResource(context, "testContext")

    // empty concept

    val content = new KnowledgeString("content", uri)

    val x = new Concept(TypedKLine[Concept]("generalisations"), TypedKLine[Concept]("specialisations"),
      TypedKLine[AnnotatedPhrase]("user", AnnotatedPhrase("user")), content, List[ConceptLink](), uri)
    val cNX = new ConceptNetwork(List[Concept](x), List[ConceptLink](), uri1)
    cNX.save(N4JKB, context, "testKey", "testRelation")

    val y: ConceptNetwork = ConceptNetwork.load(N4JKB, context, "testKey", "testRelation")

    expect(x.content.uri.name)(y.nodes(0).uri.name)
    expect(x.links.size)(y.links.size)

  }

  test("ConceptNetwork should be stored and restored") {

    val context = ContextHelper(Nil, "test context") //context is parent node for x:Concept
    N4JKB.saveResource(context, "testContext")

    // empty concept

    val userConcept = Concept.createSubConcept(subjectConcept, "user")
    val x = AnnotatedPhrase("user", userConcept)

    x.save(N4JKB, context, "testUserPhrase", "testRelation")

    val y: AnnotatedPhrase = AnnotatedPhrase.load(N4JKB, context, "testUserPhrase", "testRelation")

    expect(x.content.uri.name)(y.nodes(0).uri.name)
    expect(x.concepts.size)(y.concepts.size)
    expect(x.concepts(1).uri)(y.concepts(1).uri)

    expect(x.phrase)(y.phrase)

  }


}
