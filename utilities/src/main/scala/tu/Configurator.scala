package tu.coreservice.utilities

/**
 * @author toschev alex
 *         Date: 28.05.12
 *         Time: 20:35
 */

/*
  Hold configuration for whole solution
 */
object Configurator {

  /*
    proxy desciption
   */
  def proxyAddress(): ProxyDescription
  = {
    var res = new ProxyDescription
    res.proxyHost="localhost"
    res.proxyPort=8888
    res.useProxy = true
    return res
  }


  /*

   */
  def spellCheckerEngine(): String = "GOOGLE"

}
