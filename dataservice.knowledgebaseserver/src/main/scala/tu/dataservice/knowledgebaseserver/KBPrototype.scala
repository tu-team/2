package tu.dataservice.knowledgebaseserver

import tu.coreservice.utilities.TestDataGenerator
import tu.model.knowledge.training.Goal
import tu.model.knowledge.way2think.Way2ThinkModel

/**
 * KBSever stub only for prototype purposes.
 * @author max talanov
 *         date 2012-07-06
 *         time: 1:58 PM
 */

object KBPrototype {

  def model = TestDataGenerator.generateDomainModelConceptNetwork

  def goalResourceMap =
    Map(
      Goal("ProcessIncident") ->
        List[Way2ThinkModel](Way2ThinkModel("tu.coreservice.splitter.PreliminarySplitter"),
          Way2ThinkModel("tu.coreservice.spellcorrector.SpellCorrector"),
          Way2ThinkModel("tu.coreservice.annotator.KBAnnotatorImplKBAnnotatorImpl")
        ),
      Goal("ClassifyIncident") ->
        List[Way2ThinkModel](Way2ThinkModel("tu.coreservice.action.critic.analyser.DirectInstructionAnalyser"),
          Way2ThinkModel("tu.coreservice.action.critic.analyser.ProblemDescriptionAnalyser"),
          Way2ThinkModel("tu.coreservice.action.critic.analyser.ProblemDescriptionWithDesiredStateAnalyser")
        ),
      Goal("GetMostProbableAction") ->
        List[Way2ThinkModel](Way2ThinkModel("tu.coreservice.action.way2think.FindMostProbableSolution")
        ),
      Goal("SearchSolution") ->
        List[Way2ThinkModel](Way2ThinkModel("tu.coreservice.action.way2think.SearchSolution")
        )
    )

  def goals = List(Goal("ProcessIncident"), Goal("ClassifyIncident"), Goal("GetMostProbableAction"), Goal("SearchSolution"))

  def getByGoalName(name: String): Option[List[Way2ThinkModel]] = {
    val resources = this.goalResourceMap
    val keys: Iterable[Goal] = resources.keys.filter {
      g: Goal => {
        g.uri.name.equals(name)
      }
    }
    if (keys.size > 0) {
      resources.get(keys.head)
    } else {
      None
    }
  }

}
