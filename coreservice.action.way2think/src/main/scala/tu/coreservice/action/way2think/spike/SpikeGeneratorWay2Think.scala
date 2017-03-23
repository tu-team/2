package tu.coreservice.action.way2think.spike

import java.io.{File, PrintWriter}
import java.nio.file.attribute.BasicFileAttributeView
import java.nio.file.{Files, Paths}

import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.Constant.{SPIKE_RESOURCE, GENERATED_SPIKE_FILES_MAX_NUMBER}
import tu.model.knowledge.{KnowledgeURI, Probability}
import tu.model.knowledge.communication.ShortTermMemory
import tu.model.knowledge.neugogar.RoboticDataContainer

/**
 * @author Artur Enikeev
 */
class SpikeGeneratorWay2Think(_storageDirectory: String, _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Way2Think(_uri, _probability) {

  // Checks number of files in directory.
  // If it greater than max number, then deletes oldest file in directory
  private def checkDir(): Unit = {
    val directory = new File(_storageDirectory)
    val content = directory.listFiles()
    if (content.length > GENERATED_SPIKE_FILES_MAX_NUMBER){
      val compareCreationDate = (file1: File, file2: File) => {
        val path1 = Paths.get(file1.getAbsolutePath)
        val path2 = Paths.get(file2.getAbsolutePath)
        val time1 = Files.getFileAttributeView(path1, classOf[BasicFileAttributeView]).readAttributes().creationTime()
        val time2 = Files.getFileAttributeView(path2, classOf[BasicFileAttributeView]).readAttributes().creationTime()
        if (time1.compareTo(time2) <= 0) file1 else file2
      }
      content.toStream.filter(f => f.isFile).reduce(compareCreationDate).delete
    }
  }

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
      case Some(roboticDataContainer: RoboticDataContainer) =>
        for (spike <- roboticDataContainer._data){
          checkDir()
          val spikeFile = new File(_storageDirectory+"/spike-"+spike._data+"-"+spike._time+".txt")
          if (!spikeFile.exists()) spikeFile.createNewFile()
          Some(new PrintWriter(spikeFile)).foreach{p =>
            p.write(pretty(render(("family" -> spike._data) ~ ("activationTime" -> spike._time))))
            p.close()
          }
        }
      case None => throw new NoDataException()
    }
    inputContext
  }
}
