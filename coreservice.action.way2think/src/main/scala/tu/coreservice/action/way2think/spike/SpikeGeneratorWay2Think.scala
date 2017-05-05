package tu.coreservice.action.way2think.spike

import java.io.File

import com.fasterxml.jackson.core.{JsonEncoding, JsonFactory, JsonGenerator}
import org.slf4j.LoggerFactory
import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.Constant.SPIKE_RESOURCE
import tu.model.knowledge.communication.ShortTermMemory
import tu.model.knowledge.neugogar.RoboticDataContainer

/**
  * @author Artur Enikeev
  */
class SpikeGeneratorWay2Think
  extends Way2Think {

  val CHANNEL_MAPPING
  : Map[Int, String] = Map.newBuilder.+=(0 -> "hand")
    .+=(1 -> "distance")
  .result()


  val SPIKE_FILES_STORAGE_DIRECTORY_PROPERTY = "spikeDirectory"
  val SPIKE_FILE_INTERVAL_PROPERTY = "spikeInterval"

  val SPIKE_FILE_DEFAULT_INTERVAL = 10000

  var jsonGenerator: JsonGenerator = _
  var creationDate: Long = Long.MinValue

  val log = LoggerFactory.getLogger("application")

  override def start(): Boolean = true

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
    * "family": "hand"
    * "activationTime": "dd-mm-yyyy:hh-mm-ss-mls" or "mls"
    * }
    *
    * @param inputContext ShortTermMemory of all inbound parameters.
    * @return inputContext due to the fact it's unchanged
    */

  override def apply(inputContext: ShortTermMemory): ShortTermMemory = {
    inputContext.findByName(SPIKE_RESOURCE) match {
      case Some(roboticDataContainer: RoboticDataContainer) =>
        CHANNEL_MAPPING get roboticDataContainer.channel match  {
          case Some(family: String) => {

            log.debug(family)
            validateFile()
            for (spikeData <- roboticDataContainer.values) {
              jsonGenerator.writeStartObject()
              jsonGenerator.writeStringField("family", family)
              jsonGenerator.writeNumberField("time", spikeData.time)
              jsonGenerator.writeStringField("data", spikeData.data)
              jsonGenerator.writeEndObject()
            }
          }
          case None => {
            //do nothing
          }
        }
        emptyContext()
      case _: Any => emptyContext()
    }
  }

  def validateFile(): Unit = {
    if (creationDate + interval() < System.currentTimeMillis()) {
      if (jsonGenerator != null) {
        closeFile()
      }
      creationDate = System.currentTimeMillis()
      val spikeFile = new File(spikeDirectory() + "/spikes-" + creationDate + "-" + (creationDate + interval()))
      if (!spikeFile.exists()) {
        spikeFile.createNewFile()
      }
      val factory: JsonFactory = new JsonFactory()
      jsonGenerator = factory.createGenerator(spikeFile, JsonEncoding.UTF8)
      jsonGenerator.writeStartArray()
    }
  }

  def closeFile(): Unit = {
    jsonGenerator.writeEndArray()
    jsonGenerator.close()
  }

  def spikeDirectory(): String = {
    val directory = System.getProperty(SPIKE_FILES_STORAGE_DIRECTORY_PROPERTY)
    if (directory == null) {
      throw new RuntimeException("Spike Directory not specified. Use -DspikeDirectory=/path/to/directory")
    }
    directory
  }

  def interval(): Long = {
    val interval = System.getProperty(SPIKE_FILE_INTERVAL_PROPERTY)
    if (interval == null) {
      SPIKE_FILE_DEFAULT_INTERVAL
    } else {
      interval.toLong
    }
  }


}
