package tu.coreservice.action.way2think

import tu.model.knowledge.communication.Context

/**
 * Created by IntelliJ IDEA.
 * User: adel
 * Date: 30.06.12
 * Time: 9:12
 * To change this template use File | Settings | File Templates.
 */

object FindMostProbableSolution extends Way2Think {

  def apply(inputContext: Context): Context = {
    var outputContext = inputContext
    if ( outputContext.ClassificationResults.isEmpty)
    {
      outputContext.LastResult = null
    }
    else
    {
      outputContext.ClassificationResults
        = outputContext.ClassificationResults.sortWith((s, t) => s.probability.frequency > t.probability.frequency)
      outputContext.LastResult = outputContext.ClassificationResults.head
      outputContext.ClassificationResults = outputContext.ClassificationResults.tail
    }

    outputContext
  }

  def start() = false

  def stop() = false
}