package tu.coreservice.action.way2think.cry4help

import providers.ConsoleCommunicationProvider

/**
 *
 * @author Alexander Toschev
 *         Date: 9/9/12
 *         Time: 6:25 PM
 *
 */

trait Cry4HelpCommunicationProvider {

  /**
   * Request addressing method.
   * @param quest question to user
   * @return response from user
   */
  def askQuestion(quest: String): String


  /**
   * just print information without any output
   * @param inf String to be printed.
   */
  def showInformation(inf: String)

}

/**
 * selects suitable provider for cry4help output
 */
object Cry4HelpProviders {

  def GetProvider(): Cry4HelpCommunicationProvider = {
    new ConsoleCommunicationProvider
  }

}