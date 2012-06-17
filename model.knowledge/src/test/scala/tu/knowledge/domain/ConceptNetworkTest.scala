package tu.knowledge.domain

/**
 * @author max
 *         date 2012-06-17
 *         time: 10:49 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.{Resource, TypedKLine, Probability, KnowledgeURI}
import tu.model.knowledge.domain.{ConceptNetwork, ConceptLink, Concept}
import tu.model.knowledge.annotator.AnnotatedPhrase
import tu.model.knowledge.primitive.KnowledgeString

@RunWith(classOf[JUnitRunner])
class ConceptNetworkTest extends FunSuite {

  val namespace = "testNamespace"
  val name = "name"
  val revision = "rev"
  val uri = new KnowledgeURI(namespace, name, revision)
  val probability = new Probability
  val sourceContent = "Source"
  val destinationContent = "Dest"
  val content = new KnowledgeString("name", uri)
  val concept = new Concept(TypedKLine[Concept]("generalisations"), TypedKLine[Concept]("specialisations"),
    TypedKLine[AnnotatedPhrase]("user", AnnotatedPhrase("user")), content, List[ConceptLink](), uri)
  val subject = Concept("subject")
  val objectConcept = Concept("object")
  val has = ConceptLink(subject, objectConcept, "has")

  test("test Ok") {
    assert(condition = true)
  }

  test("ConceptNetwork should contain concepts and their contents") {
    val sn0 = new ConceptNetwork(List[Concept](concept), List(has),uri)
    expect(sn0.nodes(0).content.toString)(content.toString)
  }

}
