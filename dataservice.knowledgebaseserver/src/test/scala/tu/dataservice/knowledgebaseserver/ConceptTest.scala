package tu.dataservice.knowledgebaseserver

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
import communication.{ContextHelper, ShortTermMemory}
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
  val generalConceptName = "General"
  val source: SemanticNetworkNode[KnowledgeString] = new SemanticNetworkNode(new KnowledgeString(sourceContent, uri), List[SemanticNetworkLink](), new KnowledgeURI(namespace, "source", revision))
  val destination: SemanticNetworkNode[KnowledgeString] = new SemanticNetworkNode(new KnowledgeString(destinationContent, uri), List[SemanticNetworkLink](), new KnowledgeURI(namespace, "dest", revision))
  val test: SemanticNetworkNode[KnowledgeString] = new SemanticNetworkNode(new KnowledgeString("TestContent", uri), List[SemanticNetworkLink](), new KnowledgeURI(namespace, "test", revision))
  val f1 = new TypedFrame(Map[KnowledgeURI, Resource](source.uri -> source, destination.uri -> destination), new KnowledgeURI(namespace, "f1", revision))
  val f2 = new TypedFrame(Map[KnowledgeURI, Resource](source.uri -> source, destination.uri -> destination, test.uri -> test), new KnowledgeURI(namespace, "f2", revision))
  val klName = new KnowledgeString("name", uri)
  val kl = new KLine(Map[KnowledgeURI, Resource](f1.uri -> f1), uri)
  val userPhrase = new KLine(Map(KnowledgeURI("user" + "Phrase") -> AnnotatedPhrase("user")), KnowledgeURI("userKLine"))
  val generalConcept = Concept(generalConceptName)

  test("Concept should contain name") {
    val content = new KnowledgeString("name", uri)
    val c = new Concept(TypedKLine[Concept]("generalisations"), TypedKLine[Concept]("specialisations"),
      TypedKLine[AnnotatedPhrase]("user", AnnotatedPhrase("user")), content, List[ConceptLink](), uri)
    expect(c.content)(content)
  }

  test("add generalisation ConceptLink") {
    val content = new KnowledgeString("name", uri)
    val c = new Concept(TypedKLine[Concept]("generalisations"), TypedKLine[Concept]("specialisations"),
      TypedKLine[AnnotatedPhrase]("user", AnnotatedPhrase("user")), content, List[ConceptLink](), uri)
    expect(c.content)(content)
    val generalisationLink = ConceptLink(c, generalConcept, Constant.GENERALISATION_LINK_NAME)
    c.linksAdd(generalisationLink)
    expect(1)(c.generalisations.frames.size)
    expect(generalConceptName.substring(0,7))(c.generalisations.frames.values.head.uri.name.substring(0,7))
  }

}
