package tu.coreservice.action.way2think.cry4help.providers

import tu.coreservice.action.way2think.cry4help.Cry4HelpCommunicationProvider
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory


/**
 *
 * @author: Alexander Toschev
 *          Date: 9/9/12
 *          Time: 7:06 PM
 *
 */

class ConsoleCommunicationProvider extends Cry4HelpCommunicationProvider {

  val log = LoggerFactory.getLogger(this.getClass)

  /**
   *
   * @param quest quest to user
   * @return response from user
   */
  def askQuestion(quest: String): String = {
    showInfo(quest)
    //try to identify if console is availible


    var consoleAvailible = "false"
    try {
      val conf = ConfigFactory.load("application.properties")
      consoleAvailible = conf.getString("coreservice.action.way2think.cry4help.console")
    }
    catch {
      case ex: Exception => log.error("unable load config")
    }
    if (consoleAvailible == "true") {
      log.info("Console is availible. Read from tu.coreservice.action.way2think.cry4help.console. Using input")
      return Console.readLine()
    }
    else {
      log.info("Console is not availible. Returning empty string")
      return ""
    }

  }

  private def showInfo(in: String) {
    Console.println(in)
  }

  /**
   * just print information without any output
   * @param inf
   */
  def showInformation(inf: String) {
    showInfo(inf)
  }
}
