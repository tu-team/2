package tu.coreservice.utilities

import util.matching.Regex
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

/**
 * @author toschev alex
 *         Date: 28.05.12
 *         Time: 20:35
 */

/*
  Hold configuration for whole solution
 */
object Configurator {



  val log = LoggerFactory.getLogger(this.getClass)
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

    try{

      var proxyCfg=ConfigFactory.load("proxy.conf")
      res.proxyHost = proxyCfg.getString ("proxyHost")
      res.proxyPort = proxyCfg.getInt ("proxyPort")
      res.useProxy = proxyCfg.getBoolean ("useProxy")// useProxy  // use file $HOME/tu.ini with string "useProxy = yes", please
      log.debug(String.format ( "Use proxy settings from config host %1$s:%2$s use: %3$s",res.proxyHost,res.proxyPort.toString,res.useProxy.toString ))
      return res
    }
    catch {
      case ex: Exception => log.debug("Porxy: use DIRECT connection")
    }

    res.useProxy = false// useProxy  // use file $HOME/tu.ini with string "useProxy = yes", please
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
