package tu.knowledge.annotator

/**
 * @author max
 *         date 2012-06-02
 *         time: 4:04 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.domain.Concept
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.frame.TypedFrame
import tu.model.knowledge.semanticnetwork.{SemanticNetworkNode, SemanticNetworkLink}
import tu.model.knowledge.{Probability, KLine, KnowledgeURI, Resource}
import tu.model.knowledge.annotator.{AnnotatedSentence, AnnotatedPhrase, AnnotatedNarrative, AnnotatedWord}

@RunWith(classOf[JUnitRunner])
class AnnotatedNarrativeTest extends FunSuite {

  test("test Ok") {
    assert(condition = true)
  }

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

  test("Annotated narrative should contain list of sentences") {
    val word = "word"
    val phrases = List[AnnotatedPhrase](AnnotatedPhrase(word))
    val content = List[AnnotatedSentence](AnnotatedSentence(phrases))
    val a = new AnnotatedNarrative(content, uri)
    expect(content)(a.sentences)
  }


}
