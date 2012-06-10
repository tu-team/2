package tu.coreservice.action.way2think

/**
 * @author max
 *         date 2012-05-28
 *         time: 11:38 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import tu.model.knowledge.annotator.AnnotatedPhrase
import tu.model.knowledge.domain.{ConceptLink, Concept}

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
  val hasComputer = ConceptLink.createSubConceptLink(has, userConcept, computerConcept, "hasComputer")
  val userComputerLinkedPair = ConceptLink.likConcepts(hasComputer, userConcept, computerConcept)
  val hasSoftware = ConceptLink.createSubConceptLink(has, computerConcept, softwareConcept, "hasSoftware")
  val computerSoftwareLinkedPair = ConceptLink.likConcepts(hasSoftware, computerConcept, softwareConcept)
  val hasAccount = ConceptLink.createSubConceptLink(has, userConcept, account, "hasAccount")
  val userAccountLinkedPair = ConceptLink.likConcepts(has, userConcept, account)

  /**
   * phrases
   */
  val userPhrase = AnnotatedPhrase("user")
  val customerPhrase = AnnotatedPhrase("customer")
  val notebookPhrase = AnnotatedPhrase("notebook")
  val laptopPhrase = AnnotatedPhrase("laptop")
  val desktopPhrase = AnnotatedPhrase("desktop")
  val computerPhrase = AnnotatedPhrase("computer")

  test("test Ok") {
    assert(condition = true)
  }

}
