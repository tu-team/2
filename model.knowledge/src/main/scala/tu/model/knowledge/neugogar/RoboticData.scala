package tu.model.knowledge.neugogar

/**
  * Created by bublik on 14.03.17.
  */
case class RoboticData (_data:String, _time: Long) {

  def apply (_data: String, _time: Long) : RoboticData = {
    new RoboticData(_data, _time)
  }

  def data = _data

  def time = _time

}
