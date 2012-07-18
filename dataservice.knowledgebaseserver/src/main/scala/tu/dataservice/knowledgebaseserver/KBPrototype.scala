package tu.dataservice.knowledgebaseserver

import tu.coreservice.utilities.TestDataGenerator
import tu.model.knowledge.training.Goal
import tu.model.knowledge.way2think.{JoinWay2ThinkModel, Way2ThinkModel}
import tu.model.knowledge.action.ActionModel
import tu.model.knowledge.critic.CriticModel
import tu.model.knowledge.annotator.{AnnotatedPhrase, AnnotatedWord}

/**
 * KBSever stub only for prototype purposes.
 * @author max talanov
 *         date 2012-07-06
 *         time: 1:58 PM
 */

object KBPrototype {

  def model = TestDataGenerator.generateDomainModelConceptNetwork

  def goalResourceMap =
    Map[Goal, List[ActionModel]](
      Goal("ProcessIncident") ->
        List[Way2ThinkModel](Way2ThinkModel("tu.coreservice.splitter.PreliminarySplitter"),
          Way2ThinkModel("tu.coreservice.spellcorrector.SpellCorrectorCompound"),
          Way2ThinkModel("tu.coreservice.annotator.KBAnnotatorImpl")
        ),
      Goal("ClassifyIncident") ->
        List[JoinWay2ThinkModel](JoinWay2ThinkModel(
          List[CriticModel](CriticModel("tu.coreservice.action.critic.analyser.DirectInstructionAnalyserCritic"),
            CriticModel("tu.coreservice.action.critic.analyser.ProblemDescriptionAnalyserCritic"),
            CriticModel("tu.coreservice.action.critic.analyser.ProblemDescriptionWithDesiredStateAnalyserCritic")
          ), "tu.model.knowledge.way2think.JoinWay2ThinkModel")
        ),
      Goal("GetMostProbableAction") ->
        List[Way2ThinkModel](Way2ThinkModel("tu.coreservice.action.way2think.FindMostProbableAction")
        ),
      Goal("SearchSolution") ->
        List[Way2ThinkModel](Way2ThinkModel("tu.coreservice.action.way2think.SearchSolution")
        )
    )

  def annotations=Map[String, AnnotatedPhrase](
  "Please" ->
  AnnotatedPhrase.apply("Please")
  )

  def goals = List(Goal("ProcessIncident"), Goal("ClassifyIncident"), Goal("GetMostProbableAction"), Goal("SearchSolution"))

  def getByGoalName(name: String): Option[List[ActionModel]] = {
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

  /***
   *
   * @param word
   * @return annotated phrase by word (for example get rid off)
   */
  def getAnnotationByWord(word:String):Option[AnnotatedPhrase] ={

    val resources = this.annotations
    val keys: Iterable[String] = resources.keys.filter {
      g: String => {
        g.equals(word)
      }
    }
    if (keys.size > 0) {
      resources.get(keys.head)
    } else {
      None
    }
  }

}
