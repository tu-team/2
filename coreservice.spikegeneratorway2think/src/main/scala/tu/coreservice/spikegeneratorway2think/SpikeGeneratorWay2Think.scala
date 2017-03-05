package tu.coreservice.spikegeneratorway2think

import java.io.{File, PrintWriter}

import org.json4s.jackson.JsonMethods._
import org.json4s.JsonDSL._
import tu.coreservice.action.way2think.Way2Think
import tu.dataservice.knowledgebaseserver.providers.N4JKB
import tu.model.knowledge.KBNodeId
import tu.model.knowledge.communication.ShortTermMemory
import tu.model.knowledge.Constant.SPIKE_RESOURCE

/**
 * @author Artur Enikeev
 */
class SpikeGeneratorWay2Think() extends Way2Think {

  // WARNING: This is a temporary solution.
  // TODO: Understand meaning and purposes of those two methods below and rewrite.
  override def start(): Boolean = false
  override def stop(): Boolean = false

  /**
    * Spike Generator Way2Think.
    *
    * Activation of this way2think produces a series of spikes stored in file in JSON format.
    * Expected format of input information:
    *
    * TODO: think about how represent it
    *
    * Format of output information is illustrated below:
    * {
    *   "family": "hand"
    *   "activationTime": "dd-mm-yyyy:hh-mm-ss-mls" or "mls"
    * }
    *
    * @param inputContext ShortTermMemory of all inbound parameters.
    * @return inputContext due to the fact it's unchanged
    */

  override def apply(inputContext: ShortTermMemory): ShortTermMemory = {
    inputContext.findByName(SPIKE_RESOURCE) match {
      case Some(resource) =>
        val content = N4JKB.loadChildrenList(KBNodeId.apply(resource))
        for (spike <- content){
          val jspike = ("family" -> spike.get("family")) ~
                       ("period" -> spike.get("period"))
          val fileName = "spike:"+spike.get("family")+"-"+spike.get("period")
          val spikeFile = new File(fileName)
          if (!spikeFile.exists()) spikeFile.createNewFile()
          Some(new PrintWriter(spikeFile)).foreach{p =>
            p.write(pretty(render(jspike)))
            p.close()
          }
        }
      case None => throw new NoDataException()
    }
    inputContext
  }
}
