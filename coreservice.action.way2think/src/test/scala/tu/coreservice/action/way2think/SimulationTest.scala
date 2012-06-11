package tu.coreservice.action.way2think

/**
 * @author max
 *         date 2012-05-28
 *         time: 11:38 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.domain.{ConceptLink, Concept}
import tu.model.knowledge.Probability
import tu.model.knowledge.annotator.{AnnotatedNarrative, AnnotatedPhrase}
import tu.model.knowledge.semanticnetwork.SemanticNetwork

@RunWith(classOf[JUnitRunner])
class SimulationTest extends FunSuite {

  /**
   * Simulation structures
   */
  val namespace = "2/concepts/"
  val revision = "0.0"

  /**
   * concepts
   */
  val subject = Concept("subject")
  val objectConcept = Concept("object")
  val userConcept = Concept("user")
  val computerConcept = Concept("computer")
  val softwareConcept = Concept("sofware")
  val photoShop = Concept.createSubConcept(softwareConcept, "Adobe Photoshop")
  val firefox = Concept.createSubConcept(softwareConcept, "Mozilla Firefox")
  val network = Concept("network")
  val internet = Concept.createSubConcept(network, "internet")
  val sharedResources = Concept("sharedResources")
  val sharedDisk = Concept.createSubConcept(sharedResources, "sharedDisk")
  val account = Concept("account")

  // actions
  val action = Concept("action")
  val install = Concept.createSubConcept(action, "install")
  val remove = Concept.createSubConcept(action, "remove")
  val clean = Concept.createSubConcept(action, "clean")

  /**
   * links
   */
  val has = ConceptLink(subject, objectConcept, "has")
  val userCompLinkedPair = ConceptLink.likConcepts(has, userConcept, computerConcept)
  val hasComputer = ConceptLink.createSubConceptLink(has, userConcept, computerConcept, "hasComputer", new Probability(1.0, 1.0))
  val userComputerLinkedPair = ConceptLink.likConcepts(hasComputer, userConcept, computerConcept)
  val hasSoftware = ConceptLink.createSubConceptLink(has, computerConcept, softwareConcept, "hasSoftware", new Probability(1.0, 0.9))
  val computerSoftwareLinkedPair = ConceptLink.likConcepts(hasSoftware, computerConcept, softwareConcept)
  val hasAccount = ConceptLink.createSubConceptLink(has, userConcept, account, "hasAccount", new Probability(1.0, 0.9))
  val userAccountLinkedPair = ConceptLink.likConcepts(has, userConcept, account)

  /**
   * phrases
   */
  val userPhrase = AnnotatedPhrase("user", userConcept)
  val customerPhrase = AnnotatedPhrase("customer", userConcept)
  val notebookPhrase = AnnotatedPhrase("notebook", computerConcept)
  val laptopPhrase = AnnotatedPhrase("laptop", computerConcept)
  val desktopPhrase = AnnotatedPhrase("desktop", computerConcept)
  val computerPhrase = AnnotatedPhrase("computer", computerConcept)

  test("test Ok") {
    assert(condition = true)
  }

  test("implicit information identification") {
    val storyText = "la la la"
    // TODO add splitter here
    /* val story = new AnnotatedNarrative()
    val model = new SemanticNetwork()
    Simulation.appendImplicitInformation(story, model)
    */
  }

}
