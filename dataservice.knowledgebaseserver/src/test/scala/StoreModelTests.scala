import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import tu.dataservice.knowledgebaseserver.providers.N4JKB
import tu.dataservice.knowledgebaseserver.{KBAdapter}
import tu.dataservice.memory.LongTermMemory
import tu.model.knowledge.annotator.AnnotatedPhrase
import tu.model.knowledge.communication.ContextHelper
import tu.model.knowledge.domain.{ConceptNetwork, ConceptLink, Concept}
import tu.model.knowledge.KBMap._
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge._
import annotator.AnnotatedPhrase._

/**
 * @author achepkunov
 *         Date: 28.08.12
 *         Time: 12:41
 */



@RunWith(classOf[JUnitRunner])
class StoreModelTest extends FunSuite {

  val uri = KnowledgeURI("testuri")
  val uri1 = KnowledgeURI("testuri1")
  val uri2 = KnowledgeURI("testuri2")

  test("Concept should be stored and restored") {

    val context = ContextHelper(Nil, "test context") //context is parent node for x:Concept
    N4JKB.saveResource(context, "testContext")

    // empty concept

    val content = new KnowledgeString("content", uri)
    val x = new Concept(TypedKLine[Concept]("generalisations"), TypedKLine[Concept]("specialisations"),
      TypedKLine[AnnotatedPhrase]("user", AnnotatedPhrase("user")), content, List[ConceptLink](), uri)

    x.save(N4JKB, new KBNodeId( KBMap.get(context)), "testKey", "testRelation")

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

    val CONCEPT = Concept("concept")
    val subjectConcept = Concept.createSubConcept(CONCEPT, "subject")
    val CONCEPT_LINK = ConceptLink(CONCEPT, CONCEPT, "conceptLink")
    val objectConcept = Concept.createSubConcept(CONCEPT, "object")
    val has = ConceptLink.createSubConceptLink(CONCEPT_LINK, subjectConcept, objectConcept, "has", new Probability(1.0, 1.0))
    val concepts = List[Concept](CONCEPT, subjectConcept, objectConcept)
    val conceptLinks: List[ConceptLink] = List(CONCEPT_LINK, has)
    val cN2 = new ConceptNetwork(concepts, conceptLinks, uri2)

    cN2.save(N4JKB, context, "testKeyCN2", "testRelation")

    val y2: ConceptNetwork = ConceptNetwork.load(N4JKB, context, "testKeyCN2", "testRelation")

    expect(cN2.links.size)(y2.links.size)

  }

  test("AnnotatedPhrase should be stored and restored") {

    val context = ContextHelper(Nil, "test context 3") //context is parent node for x:Concept
    N4JKB.saveResource(context, "testContext")

    // empty phrase
    val subjectConcept = Concept("subject")
    val userConcept = Concept.createSubConcept(subjectConcept, "user")
    val x = AnnotatedPhrase("user", userConcept)

    x.save(N4JKB, context, "testUserPhrase", "testRelation")

    val y: AnnotatedPhrase = AnnotatedPhrase.load(N4JKB, context, "testUserPhrase", "testRelation")

    expect(x.uri.name)(y.uri.name)
    expect(x.concepts.size)(y.concepts.size)
    expect(x.concepts(0).uri.name)(y.concepts(0).uri.name)

    expect(x.phrase)(y.phrase)

    // rec phrase
    val manualConcept = Concept.createSubConcept(subjectConcept, "manual")
    val xx = AnnotatedPhrase("manual", manualConcept)

    val x2 = AnnotatedPhrase(List(x, xx))

    x2.save(N4JKB, context, "testUserPhrase2", "testRelation")

    val y2: AnnotatedPhrase = AnnotatedPhrase.load(N4JKB, context, "testUserPhrase2", "testRelation")


   // expect(x2.phrase)(y2.phrase)
    expect(x2.phrases.size)(y2.phrases.size)
    expect(true)(x2.phrases.count(p=> p.phrase==  y2.phrases(0).phrase)>0)
    expect(true)(x2.phrases.count(p=> p.phrase==  y2.phrases(1).phrase)>0)

  }

  test("Get Self Reflective Critics")
  {
    expect ( KBAdapter.getReflectiveCritics.isEmpty)(false)
  }

  test("Test getAnnotationByWord")
  {
    expect ( KBAdapter.getAnnotationByWord("word").isEmpty)(false)

    //TODO add save test
  }

}





