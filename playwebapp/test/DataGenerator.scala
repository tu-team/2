import java.util.Date

import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.{ContentType, StringEntity}
import org.apache.http.impl.client.HttpClientBuilder
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import tu.model.knowledge.neugogar.{RoboticData, RoboticDataContainer}

import scala.util.Random

// Move from tu.coreservice.action.way2think test folder
// due to the fact I'm not sure it will work correctly on that location.
class DataGenerator(_host: String) {

  private val random = new Random()
  private val MAX_DATA_SIZE = 10
  private val MAX_SENSOR_DATA_VALUE = 5000
  private val MIN_SENSOR_DATA_VALUE = 100
  private val httpClient = HttpClientBuilder.create().build()

  def generateData(channel: Integer): RoboticDataContainer = {
    var dataList: List[RoboticData] = List()
    var len = random.nextInt(MAX_DATA_SIZE)+1
    while (len > 0){
      val data = random.nextInt(MAX_SENSOR_DATA_VALUE)+MIN_SENSOR_DATA_VALUE
      val time = new Date().getTime
      dataList = RoboticData(data.toString, time) :: dataList
      len -= 1
    }
    new RoboticDataContainer(channel, dataList)
  }

  def toJSON(roboticDataContainer: RoboticDataContainer): String = {
    pretty(render(
      ("channel" -> roboticDataContainer._channel.intValue()) ~
      ("data" -> roboticDataContainer._data.map { data =>
        ("" -> data._data) ~ ("" -> data._time)
      })
    ))
  }

  // being guided by RoboticDataTest.java
  def sendRoboticData(data: String): Int = {
    val httpPost = new HttpPost(_host+"/robotic")
    httpPost.setEntity(new StringEntity(data, ContentType.APPLICATION_JSON))
    httpClient.execute(httpPost).getStatusLine.getStatusCode
  }
}

object DataGenerator {
  private val DEFAULT_HOST = "http://localhost:9100"

  def apply(_host: String): DataGenerator = new DataGenerator(_host)

  def apply: DataGenerator = new DataGenerator(DEFAULT_HOST)
}
