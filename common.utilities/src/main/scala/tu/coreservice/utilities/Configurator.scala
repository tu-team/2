package tu.coreservice.utilities

import com.typesafe.config.{Config, ConfigFactory}
import org.slf4j.{Logger, LoggerFactory}
import tu.model.knowledge.Constant

/**
 * Contains configuration for whole solution.
 * Use file tu.properties with string useProxy = true.
 * Put tu.properties in classpath.
 * @author toschev alex
 *         Date: 28.05.12
 *         Time: 20:35
 */

object Configurator {

  val log: Logger = LoggerFactory.getLogger(this.getClass)

  /**
   * Proxy description
   */
  def proxyAddress(): ProxyDescription = {
    val res = new ProxyDescription

    try {
      val proxyCfg: Config = ConfigFactory.load(Constant.tuIniAddress)
      res.proxyHost = proxyCfg.getString("proxyHost")
      res.proxyPort = proxyCfg.getInt("proxyPort")
      res.useProxy = proxyCfg.getBoolean("useProxy")
      log.debug("Use proxy settings from config host {}:{} use: {}", List(res.proxyHost, res.proxyPort.toString, res.useProxy))
      return res
    }
    catch {
      case ex: Exception => log.debug(ex.getMessage)
    }
   res
  }

  def spellingAtDServer = "http://service.afterthedeadline.com/"

  /**
   * select spell checker engine GOOGLE, ATD or COMPOUND
   * ATD - after the deadline
   * COMPOUND - spell checker from GOOGLE and grammar checker from After The Deadline
   */
  def spellCheckerEngine(): String = "ATD"

}
