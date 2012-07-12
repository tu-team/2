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
    proxy description
   */
  def proxyAddress(): ProxyDescription
  = {
    var res = new ProxyDescription
    res.proxyHost = "ru100279159"
    res.proxyPort = 3128
    res.useProxy = false
    return res
  }


  def spellingAtDServer = "http://service.afterthedeadline.com/"

  /*
     select spell checher engine GOOGLE, ATD or COMPOUND
     ATD - after the deadline
     COMPOUND - spell checker from GOOGLE and grammar checker from After The Deadline
   */
  def spellCheckerEngine(): String = "COMPOUND"

}
