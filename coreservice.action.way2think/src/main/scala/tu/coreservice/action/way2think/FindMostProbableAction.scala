package tu.coreservice.action.way2think

import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.Resource
import tu.model.knowledge.selector.SelectorRequest

/**
 * @author adel chepkunov, max talanov
 *         Date: 30.06.12
 *         Time: 9:12
 */


class FindMostProbableAction extends  Way2Think {
  def start() = false

  def stop() = false

  /**
   * Way2Think interface.
   * @param inputContext Context of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: Context) = {
    FindMostProbableAction(inputContext)
  }
}

object FindMostProbableAction {

  def apply(inputContext: Context): Context = {
    val outputContext = ContextHelper(List[Resource](), "OutputContex")
    if (inputContext.classificationResults.isEmpty) {
      outputContext.bestClassificationResult = None
    }
    else {
      outputContext.classificationResults
        =inputContext.classificationResults
             .filter( p => ! inputContext.checkedClassificationResults.exists ( q => p==q))
             .sortWith((s, t) => s.probability.frequency > t.probability.frequency)
      outputContext.bestClassificationResult = outputContext.classificationResults.headOption
      outputContext.lastResult = outputContext.classificationResults.headOption
      outputContext.bestClassificationResult match {
        case Some(sR: SelectorRequest) => {
          outputContext.checkedClassificationResults = List(sR) ::: outputContext.checkedClassificationResults
        }
        case _ => {
          //none
        }

      }
      outputContext.classificationResults = outputContext.classificationResults.tail
    }

    outputContext
  }
}