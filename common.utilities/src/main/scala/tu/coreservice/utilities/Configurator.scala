package tu.coreservice.utilities

import util.matching.Regex

/**
 * @author toschev alex
 *         Date: 28.05.12
 *         Time: 20:35
 */

/*
  Hold configuration for whole solution
 */
object Configurator {

  /* this is not correct way to load ini!
  val configFile = java.lang.System.getProperty("user.home") + "/tu.ini"
  val fileLines = io.Source.fromFile(configFile).getLines.toList

  val useProxyPattern = new Regex("useProxy *= *(true|yes|1)")
  val useProxy = fileLines.exists(useProxyPattern.findAllIn(_).hasNext)   */
  /*
    proxy description
   */
  def proxyAddress(): ProxyDescription = {
    val res = new ProxyDescription
    res.proxyHost = "RU100279159"
    res.proxyPort = 3128
    res.useProxy = true // useProxy  // use file $HOME/tu.ini with string "useProxy = yes", please
    res
  }

  def spellingAtDServer = "http://service.afterthedeadline.com/"

  /*
     select spell checher engine GOOGLE, ATD or COMPOUND
     ATD - after the deadline
     COMPOUND - spell checker from GOOGLE and grammar checker from After The Deadline
   */

  def spellCheckerEngine(): String = "COMPOUND"

}
