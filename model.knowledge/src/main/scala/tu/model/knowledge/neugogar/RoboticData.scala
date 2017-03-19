package tu.model.knowledge.neugogar

/**
  * Created by bublik on 14.03.17.
  */
case class RoboticData (_data:String, _time: Long) {

  def apply (data: String, time: Long) : RoboticData = {
    new RoboticData(data, time)
  }

}
