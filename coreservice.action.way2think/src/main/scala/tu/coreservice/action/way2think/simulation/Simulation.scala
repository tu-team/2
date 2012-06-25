package tu.coreservice.action.way2think.simulation

import tu.model.knowledge.domain.{ConceptLink, Concept, ConceptNetwork}
import tu.model.knowledge.annotator.{AnnotatedPhrase, AnnotatedNarrative}
import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.Resource
import tu.model.knowledge.primitive.KnowledgeString
import tu.coreservice.action.way2think.cry4help.Cry4HelpWay2Think

/**
 * Simulation Way2Think implementation.
 * @author max talanov
 *         date 2012-06-25
 *         time: 12:45 PM
 */

class Simulation {
  val name: String = "Simulation"

  /**
   * Runs through AnnotatedPhrase from AnnotatedNarrative and creates ConceptNetwork of instances of the simulationModel.
   * @param in AnnotatedNarrative to Simulate.
   * @param simulationModel ConceptNetwork base model.
   * @return ConceptNetwork simulation result.
   */
  def apply(in: AnnotatedNarrative, simulationModel: ConceptNetwork): ConceptNetwork = {

    // val instancesList: List[Concept] =
    // check concept of a phrase and if it is in simulationModel crate it's instance.
    val exactMatch: List[AnnotatedPhrase] = in.phrases.filter {
      phrase: AnnotatedPhrase => {
        this.filterPhrase(phrase, simulationModel).size == 1
      }
    }

    val ambiguous: List[AnnotatedPhrase] = in.phrases.filter {
      phrase: AnnotatedPhrase => {
        this.filterPhrase(phrase, simulationModel).size > 1
      }
    }

    val notKnown: List[AnnotatedPhrase] = in.phrases.filter {
      phrase: AnnotatedPhrase => {
        this.filterPhrase(phrase, simulationModel).size < 1
      }
    }

    if (ambiguous.size > 0) {
      this.processAmbiguous(ambiguous)
    }

    if (notKnown.size > 0) {
      this.processNotKnown(notKnown)
    }

    if (exactMatch.size > 0) {
      this.processExactMatch(exactMatch)
    }
    null
  }

  private def filterPhrase(phrase: AnnotatedPhrase, simulationModel: ConceptNetwork): List[Concept] = {
    val modelConcepts: List[Concept] = phrase.concepts.filter {
      c => simulationModel.nodes.contains(c)
    }
    modelConcepts
  }

  /**
   * Really stupid method to process ambiguity.
   * @param in List[Concept]
   * @return Context of Cry4HelpWay2Think
   */
  private def processAmbiguous(in: List[AnnotatedPhrase]): Context = {
    // ambiguity
    var res: List[Resource] = in
    res = res ++ List[Resource](KnowledgeString("Please clarify ambiquity", "Please.clarify.ambiquity"))
    val context = ContextHelper.apply(res, "ambiguity")
    val cry4helpWay2Think = new Cry4HelpWay2Think()
    cry4helpWay2Think.apply(context)
  }

  private def processNotKnown(in: List[AnnotatedPhrase]): Context = {
    var res: List[Resource] = in
    res = res ++ List[Resource](KnowledgeString("Please clarify phreses", "Please.clarify.phrases"))
    val context = ContextHelper.apply(res, "notKnown")
    val cry4helpWay2Think = new Cry4HelpWay2Think()
    cry4helpWay2Think.apply(context)
  }

  private def processExactMatch(in: List[AnnotatedPhrase]): ConceptNetwork = {
    val concepts = in.map {
      phrase: AnnotatedPhrase => phrase.concepts(0)
    }

    concepts.map{
      concept: Concept => {
         var processedConcepts: Map[Concept, Concept] = Map[Concept, Concept]()
        // TODO extend this
      }
    }
    // TODO fix this
    // new ConceptNetwork(instances, links, name)
    null
  }
}
