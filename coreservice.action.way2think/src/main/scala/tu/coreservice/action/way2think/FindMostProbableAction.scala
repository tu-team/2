package tu.coreservice.action.way2think

import tu.model.knowledge.communication.{ContextHelper, ShortTermMemory}
import tu.model.knowledge.Resource
import tu.model.knowledge.selector.SelectorRequest
import org.slf4j.LoggerFactory

/**
 * @author adel chepkunov, max talanov
 *         Date: 30.06.12
 *         Time: 9:12
 */


class FindMostProbableAction extends Way2Think {

  def start() = false

  def stop() = false

  /**
   * Way2Think interface.
   * @param inputContext ShortTermMemory of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: ShortTermMemory) = {
    FindMostProbableAction(inputContext)
  }
}

object FindMostProbableAction {

  val log = LoggerFactory.getLogger(this.getClass)

  def apply(inputContext: ShortTermMemory): ShortTermMemory = {
    val outputContext = ContextHelper(List[Resource](), "OutputContex")
    if (inputContext.classificationResults.isEmpty) {
      outputContext.bestClassificationResult = None
      outputContext.lastResult = None
    }
    else {
      outputContext.classificationResults
        = inputContext.classificationResults
        .filter(p => !inputContext.checkedClassificationResults.exists(q => p == q))
        .sortWith((s, t) => s.probability.frequency > t.probability.frequency)
      outputContext.bestClassificationResult = outputContext.classificationResults.headOption
      outputContext.lastResult = outputContext.classificationResults.headOption
      log info("best classification result={}", outputContext.bestClassificationResult)
      log info("last result={}", outputContext.lastResult)
      outputContext.bestClassificationResult match {
        case Some(sR: SelectorRequest) => {
          log info("processed classification resluts={}", sR.toString)
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