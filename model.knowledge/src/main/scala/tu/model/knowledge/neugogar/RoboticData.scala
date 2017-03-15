package tu.model.knowledge.neugogar

/**
  * Created by bublik on 14.03.17.
  */
case class RoboticData (_chanel:Integer, _data:String, _time: Long) {

  def apply(channel: Integer, data: String, time: Long) : RoboticData = {
    new RoboticData(channel, data, time)
  }

}
