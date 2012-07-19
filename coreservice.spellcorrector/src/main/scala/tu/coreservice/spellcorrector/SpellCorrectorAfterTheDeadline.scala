package tu.coreservice.spellcorrector

import tu.coreservice.utilities.Configurator
import java.net._
import tu.model.knowledge.communication.Context
import org.slf4j.LoggerFactory


/**
 * Created by IntelliJ IDEA.
 * User: toscheva
 * Date: 29.05.12
 * Time: 20:14
 * To change this template use File | Settings | File Templates.
 */

/**
 * API to integrate with After the Deadline corrector
 */
class SpellCorrectorAfterTheDeadline extends SpellCorrector {

  val log = LoggerFactory.getLogger(this.getClass)
  val checkDocument = Configurator.spellingAtDServer.concat("checkDocument?key=tuapikeyABCDABCDABCDABCDABCDABCDABCDABCD&data=")
  val checkGrammar = Configurator.spellingAtDServer.concat("checkGrammar?key=tuapikeyABCDABCDABCDABCDABCDABCDABCDABCD&data=")


  def sendRequest(sentenceToCheck: String, useGrammarOnly: Boolean): String = {

    var correctedSentence: String = sentenceToCheck
    var url: URL = null

    if (useGrammarOnly) {
      url = new URL(checkGrammar + URLEncoder.encode(sentenceToCheck, "UTF8"))
    }
    else {
      url = new URL(checkDocument + URLEncoder.encode(sentenceToCheck, "UTF8"))
    }


    var proxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(Configurator.proxyAddress().proxyHost, Configurator.proxyAddress().proxyPort))

    var connection: URLConnection = null;
    if (Configurator.proxyAddress().useProxy) {
      connection = url.openConnection(proxy)
    }
    else {
      connection = url.openConnection()
    }
    //send request
    connection.setDoInput(true)
    try {
      val data = scala.xml.XML.load(connection.getInputStream)

      //select all errors
      val correctionList = data \\ "error"

      //replace ? with nothing, but only if ? not at the end of line TODO
      correctedSentence = correctedSentence.replace("?", "")
      correctionList.foreach(n => {
        //apply corrections only if suggestions available
        val suggestions = n \ "suggestions" \ "option"
        val correctionType = n \ "type"
        if (suggestions.length > 0) {
          //apply
          correctedSentence = correctedSentence.replace((n \ "string").text, suggestions.head.text)
        }
      })
    } catch {
      case e: Exception => log error e.getMessage
    }
    correctedSentence

  }

  def correctSentence(inputSentence: String): String = return sendRequest(inputSentence, false)

  def start() = false

  def stop() = false

  /**
   * Way2Think interface.
   * @param inputContext Context of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: Context) = null
}
