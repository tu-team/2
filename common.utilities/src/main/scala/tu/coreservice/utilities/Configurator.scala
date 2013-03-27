package tu.coreservice.utilities

import com.typesafe.config.{Config, ConfigFactory}
import org.slf4j.{Logger, LoggerFactory}

/**
 * @author toschev alex
 *         Date: 28.05.12
 *         Time: 20:35
 */

/*
  Hold configuration for whole solution
 */
object Configurator {


  val log: Logger = LoggerFactory.getLogger(this.getClass)

  /**
   * Proxy description
   */
  def proxyAddress(): ProxyDescription = {
    val res = new ProxyDescription

    try {
      val proxyCfg: Config = ConfigFactory.load()
      res.proxyHost = proxyCfg.getString("proxyHost")
      res.proxyPort = proxyCfg.getInt("proxyPort")
      res.useProxy = proxyCfg.getBoolean("useProxy") // useProxy  // use file $HOME/tu.ini with string "useProxy = yes", please
      log.debug("Use proxy settings from config host {}:{} use: {}", List(res.proxyHost, res.proxyPort.toString, res.useProxy))
      return res
    }
    catch {
      case ex: Exception => log.debug(ex.getMessage)
    }

    res.useProxy = true // useProxy  // use file $HOME/tu.ini with string "useProxy = yes", please
    res
  }

  def spellingAtDServer = "http://service.afterthedeadline.com/"

  /**
   * select spell checker engine GOOGLE, ATD or COMPOUND
   * ATD - after the deadline
   * COMPOUND - spell checker from GOOGLE and grammar checker from After The Deadline
   */

  def spellCheckerEngine(): String = "COMPOUND"

}
