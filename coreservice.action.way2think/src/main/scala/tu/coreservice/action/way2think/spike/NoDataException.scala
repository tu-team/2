package tu.coreservice.action.way2think.spike

class NoDataException(message: String) extends Exception {
  def this() = this("No data to operate with")
}
