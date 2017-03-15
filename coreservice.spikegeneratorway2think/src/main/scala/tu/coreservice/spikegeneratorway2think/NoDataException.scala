package tu.coreservice.spikegeneratorway2think

class NoDataException(message: String) extends Exception {
  def this() = this("No data to operate with")
}
