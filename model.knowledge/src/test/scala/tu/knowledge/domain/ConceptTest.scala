package tu.knowledge.domain

/**
 * @author max
 *         date 2012-06-01
 *         time: 8:58 AM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.semanticnetwork.{SemanticNetworkLink, SemanticNetworkNode}
import tu.model.knowledge.frame.TypedFrame
import tu.model.knowledge._
import annotator.AnnotatedPhrase
import communication.{ContextHelper, Context}
import domain.{ConceptLink, Concept}

@RunWith(classOf[JUnitRunner])
class ConceptTest extends FunSuite {

  val namespace = "testNamespace"
  val name = "name"
  val revision = "rev"
  val uri = new KnowledgeURI(namespace, name, revision)
  val probability = new Probability
  val sourceContent = "Source"
  val destinationContent = "Dest"
  val source: SemanticNetworkNode[KnowledgeString] = new SemanticNetworkNode(new KnowledgeString(sourceContent, uri), List[SemanticNetworkLink](), new KnowledgeURI(namespace, "source", revision))
  val destination: SemanticNetworkNode[KnowledgeString] = new SemanticNetworkNode(new KnowledgeString(destinationContent, uri), List[SemanticNetworkLink](), new KnowledgeURI(namespace, "dest", revision))
  val test: SemanticNetworkNode[KnowledgeString] = new SemanticNetworkNode(new KnowledgeString("TestContent", uri), List[SemanticNetworkLink](), new KnowledgeURI(namespace, "test", revision))
  val f1 = new TypedFrame(Map[KnowledgeURI, Resource](source.uri -> source, destination.uri -> destination), new KnowledgeURI(namespace, "f1", revision))
  val f2 = new TypedFrame(Map[KnowledgeURI, Resource](source.uri -> source, destination.uri -> destination, test.uri -> test), new KnowledgeURI(namespace, "f2", revision))
  val klName = new KnowledgeString("name", uri)
  val kl = new KLine(Map[KnowledgeURI, Resource](f1.uri -> f1), uri)
  val userPhrase = new KLine(Map(KnowledgeURI("user" + "Phrase") -> AnnotatedPhrase("user")), KnowledgeURI("userKLine"))

  test("Concept should contain name") {
    val content = new KnowledgeString("name", uri)
    val c = new Concept(TypedKLine[Concept]("generalisations"), TypedKLine[Concept]("specialisations"),
      TypedKLine[AnnotatedPhrase]("user", AnnotatedPhrase("user")), content, List[ConceptLink](), uri)
    expect(c.content)(content)
  }


  //TODO: Max, I have wish. Could you write unit-tests for compare written and restored Concept objects?
  //now stored only generalisation links, but for true TDD tests should be written before $)
  test("Concept should be stored and restored") {
    val content = new KnowledgeString("name", uri)
    val x = new Concept(TypedKLine[Concept]("generalisations"), TypedKLine[Concept]("specialisations"),
      TypedKLine[AnnotatedPhrase]("user", AnnotatedPhrase("user")), content, List[ConceptLink](), uri)

    val context = ContextHelper(Nil, x, "test context")

    N4JKB.save(context)
    x.save(N4JKB, context, "testKey", "testRelation")

    val y = new Concept(N4JKB.loadChild(context, "testKey", "testRelation"))
    y.loadLinks(N4JKB)

    expect(x.content)(y.content)
  }


}
