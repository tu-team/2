package tu.coreservice.action.way2think

import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.Resource
import tu.model.knowledge.selector.SelectorRequest

/**
 * @author adel
 *         Date: 30.06.12
 *         Time: 9:12
 */

object FindMostProbableSolution extends Way2Think {

  def apply(inputContext: Context): Context = {
    val outputContext = ContextHelper(List[Resource](), "OutputContex")
    if (outputContext.classificationResults.isEmpty) {
      outputContext.bestClassificationResult = None
    }
    else {
      outputContext.classificationResults
        = outputContext.classificationResults
             .filter( p => ! outputContext.checkedClassificationResults.exists ( q => p==q))
             .sortWith((s, t) => s.probability.frequency > t.probability.frequency)
      outputContext.bestClassificationResult = outputContext.classificationResults.headOption
      outputContext.lastResult = outputContext.classificationResults.headOption
      outputContext.bestClassificationResult match {
        case Some(sR: SelectorRequest) => {
          outputContext.checkedClassificationResults = List(sR) ::: outputContext.checkedClassificationResults
        }

      }
      outputContext.classificationResults = outputContext.classificationResults.tail
    }

    outputContext
  }

  def start() = false

  def stop() = false
}