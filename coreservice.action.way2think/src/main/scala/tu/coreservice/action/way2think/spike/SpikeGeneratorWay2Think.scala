package tu.coreservice.action.way2think.spike

import java.io.{File, PrintWriter}
import java.nio.file.attribute.BasicFileAttributeView
import java.nio.file.{Files, Paths}

import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.Constant.{SPIKE_FILES_STORAGE_DIRECTORY, SPIKE_RESOURCE}
import tu.model.knowledge.communication.ShortTermMemory
import tu.model.knowledge.neugogar.{RoboticDataContainer, SpikeDataContainer}

/**
 * @author Artur Enikeev
 */
class SpikeGeneratorWay2Think(_storageDirectory: String)
  extends Way2Think {

  val GENERATED_SPIKE_FILES_MAX_NUMBER = 1000
  private var generatedSpikeFiles = 0

  def this() = this(SPIKE_FILES_STORAGE_DIRECTORY)

  // Checks number of files in directory.
  // If it greater than max number, then deletes oldest file in directory
  private def refreshDir(): Unit = {
    val content = new File(_storageDirectory).listFiles()
    val compareCreationDate = (file1: File, file2: File) => {
      val path1 = Paths.get(file1.getAbsolutePath)
      val path2 = Paths.get(file2.getAbsolutePath)
      val time1 = Files.getFileAttributeView(path1, classOf[BasicFileAttributeView]).readAttributes().creationTime()
      val time2 = Files.getFileAttributeView(path2, classOf[BasicFileAttributeView]).readAttributes().creationTime()
      if (time1.compareTo(time2) <= 0) file1 else file2
    }
    content.toStream.filter(f => f.isFile).reduce(compareCreationDate).delete()
  }

  // WARNING: This is a temporary solution.
  // TODO: Understand meaning and purposes of those two methods below and rewrite.
  override def start(): Boolean = false
  override def stop(): Boolean = false

  /**
    * Spike Generator Way2Think.
    *
    * Activation of this way2think produces a series of spikes stored in file in JSON format.
    * Generated files are stored in storage directory provided by constructor.
    *
    * Input information: [[List]] bounded by [[RoboticDataContainer]]
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
      case Some(spikeDataContainer: SpikeDataContainer) =>
        for (spikeTime <- spikeDataContainer._time){
          val spikeFile = new File(_storageDirectory+"/spike-"+spikeDataContainer._family+"-"+spikeTime+".txt")
          if (!spikeFile.exists()) spikeFile.createNewFile()
          generatedSpikeFiles += 1
          if (generatedSpikeFiles > GENERATED_SPIKE_FILES_MAX_NUMBER) refreshDir()
          Some(new PrintWriter(spikeFile)).foreach{p =>
            p.write(pretty(render(("family" -> spikeDataContainer._family) ~ ("activationTime" -> spikeTime))))
            p.close()
          }
        }
        inputContext
      case None => inputContext //throw new NoDataException()
    }
  }
}
