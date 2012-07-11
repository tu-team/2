package tu.coreservice.action.way2think

import tu.model.knowledge.communication.Context

/**
 * @author adel
 * Date: 30.06.12
 * Time: 9:12
 */

object FindMostProbableSolution extends Way2Think {

  def apply(inputContext: Context): Context = {
    var outputContext = inputContext
    if ( outputContext.classificationResults.isEmpty)
    {
      outputContext.lastResult = null
    }
    else
    {
      outputContext.classificationResults
        = outputContext.classificationResults.sortWith((s, t) => s.probability.frequency > t.probability.frequency)
      outputContext.lastResult = outputContext.classificationResults.head
      outputContext.classificationResults = outputContext.classificationResults.tail
    }

    outputContext
  }

  def start() = false

  def stop() = false
}