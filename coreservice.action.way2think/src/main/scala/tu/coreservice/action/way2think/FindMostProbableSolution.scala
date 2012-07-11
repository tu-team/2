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
<<<<<<< HEAD
      outputContext.bestClassificationResult = None
=======
      outputContext.lastResult = null
>>>>>>> 5688b10a5c4d8ab9af827831844be2f94159ec29
    }
    else
    {
      outputContext.classificationResults
        = outputContext.classificationResults.sortWith((s, t) => s.probability.frequency > t.probability.frequency)
<<<<<<< HEAD
      outputContext.bestClassificationResult = outputContext.classificationResults.head
=======
      outputContext.lastResult = outputContext.classificationResults.head
>>>>>>> 5688b10a5c4d8ab9af827831844be2f94159ec29
      outputContext.classificationResults = outputContext.classificationResults.tail
    }

    outputContext
  }

  def start() = false

  def stop() = false
}