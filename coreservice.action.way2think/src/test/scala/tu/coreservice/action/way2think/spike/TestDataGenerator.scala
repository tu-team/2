package tu.coreservice.action.way2think.spike

import java.util.Date

import tu.model.knowledge.communication.{ContextHelper, ShortTermMemory}
import tu.model.knowledge.neugogar.{RoboticData, RoboticDataContainer}

import scala.util.Random

// For the name of Random!
object TestDataGenerator {

  private val random = new Random()
  private val MAX_DATA_SIZE = 10
  private val MAX_SENSOR_DATA_VALUE = 5000
  private val MIN_SENSOR_DATA_VALUE = 100

  def generateData(channel: Integer): RoboticDataContainer = {
    var dataList: List[RoboticData] = List()
    var len = random.nextInt(MAX_DATA_SIZE)+1
    while (len > 0){
      val data = random.nextInt(MAX_SENSOR_DATA_VALUE)+MIN_SENSOR_DATA_VALUE
      val time = new Date().getTime
      dataList = new RoboticData(data.toString, time) :: dataList
      len -= 1
    }
    new RoboticDataContainer(channel, dataList)
  }

  // === if it's needed ===
  def putToSTM(data: RoboticDataContainer): ShortTermMemory = {
    ContextHelper(List(data),"TestData")
  }

  def putToSTM(shortTermMemory: ShortTermMemory, data: RoboticDataContainer): ShortTermMemory = {
    //shortTermMemory.__frames += data.uri -> data
    shortTermMemory
  }
  // ======================
}
