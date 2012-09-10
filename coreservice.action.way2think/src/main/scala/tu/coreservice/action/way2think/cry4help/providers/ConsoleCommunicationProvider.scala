package tu.coreservice.action.way2think.cry4help.providers

import tu.coreservice.action.way2think.cry4help.Cry4HelpCommunicationProvider

/**
 *
 * @author: Alexander Toschev
 *          Date: 9/9/12
 *          Time: 7:06 PM
 *
 */

class ConsoleCommunicationProvider extends Cry4HelpCommunicationProvider {
  /**
   *
   * @param quest quest to user
   * @return response from user
   */
  def askQuestion(quest: String):String ={
     showInfo(quest)
     Console.readLine()
  }

  private def showInfo(in:String) {
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
