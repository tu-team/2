package tu.knowledge.domain

/**
 * @author max
 *         date 2012-06-17
 *         time: 10:49 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.domain.{ConceptNetwork, ConceptLink, Concept}
import tu.model.knowledge.annotator.AnnotatedPhrase
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge._

@RunWith(classOf[JUnitRunner])
class ConceptNetworkTest extends FunSuite {

  val namespace = "testNamespace"
  val name = "CONCEPT"
  val revision = "rev"
  val uri = new KnowledgeURI(namespace, name, revision)
  val probability = new Probability
  val sourceContent = "Source"
  val destinationContent = "Dest"
  val content = new KnowledgeString(name, uri)
  val concept = new Concept(TypedKLine[Concept]("generalisations"), TypedKLine[Concept]("specialisations"),
    TypedKLine[AnnotatedPhrase]("user", AnnotatedPhrase("user")), content, List[ConceptLink](), uri)
  val subjectConcept = Concept.createSubConcept(concept, "subject")
  val objectConcept = Concept.createSubConcept(concept, "object")
  val has = ConceptLink(subjectConcept, objectConcept, "has")

  test("test Ok") {
    assert(condition = true)
  }

  test("ConceptNetwork should contain concepts and their contents") {
    val sn0 = new ConceptNetwork(List[Concept](concept), List(has), uri)
    expect(content.toString)(sn0.nodes(0).content.toString)
  }

  test("ConceptNetwork should be exported via toText") {
    val sn0 = new ConceptNetwork(List[Concept](concept, subjectConcept), List(), uri)
    val res0 = sn0.toText
    expect("subject <- CONCEPT")(res0)

    val sn1 = new ConceptNetwork(List[Concept](concept, subjectConcept, objectConcept), List(), uri)
    val res1 = sn1.toText
    expect("subject <- CONCEPT\nobject <- CONCEPT")(res1)

    val sn2 = new ConceptNetwork(List[Concept](concept, subjectConcept, objectConcept), List(has), uri)
    val res2 = sn2.toText
    expect("subject[<has> objectConcept] <- CONCEPT\nobject[subjectConcept <has>] <- CONCEPT")(res2)
  }

}
