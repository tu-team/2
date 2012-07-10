package tu.coreservice.action.way2think

import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.TypedKLine._
import tu.model.knowledge.annotator.AnnotatedPhrase
import tu.model.knowledge.annotator.AnnotatedPhrase._
import tu.model.knowledge.domain.Concept._
import tu.model.knowledge.domain.ConceptLink._
import org.scalatest.Assertions._
import tu.model.knowledge.domain.{ConceptNetwork, ConceptLink, Concept}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.{TypedKLine, Probability, KnowledgeURI}


/**
 * Created by IntelliJ IDEA.
 * User: adel
 * Date: 10.07.12
 * Time: 7:11
 * To change this template use File | Settings | File Templates.
 */

@RunWith(classOf[JUnitRunner])
class SearchSolutionTest  extends FunSuite
{


  val sn0 = createConceptNetwork

  def createConceptNetwork():ConceptNetwork =
  {
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

    val sn0 = new ConceptNetwork(List[Concept](concept), List(has),uri)
      sn0
  }
}