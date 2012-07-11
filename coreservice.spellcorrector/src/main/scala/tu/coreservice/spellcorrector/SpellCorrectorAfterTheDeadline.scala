package tu.coreservice.spellcorrector

import javax.xml.parsers.{DocumentBuilder, DocumentBuilderFactory}
import java.io.{OutputStreamWriter, BufferedReader, InputStreamReader}
import tu.coreservice.utilities.Configurator
import java.net._


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

  private var checkDocument = Configurator.spellingAtDServer.concat("checkDocument?key=tuapikeyABCDABCDABCDABCDABCDABCDABCDABCD&data=")

  private var checkGrammar = Configurator.spellingAtDServer.concat("checkGrammar?key=tuapikeyABCDABCDABCDABCDABCDABCDABCDABCD&data=")


  def sendRequest(sentenceToCheck: String, useGrammarOnly: Boolean): String = {

    var correctedSentence: String = sentenceToCheck;

    var url: URL = null;

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


    var data = scala.xml.XML.load(connection.getInputStream)

    //select all errors
    var correctionList = data \\ "error"

    //replace ? with nothing, but only if ? not at the end of line TODO
    correctedSentence = correctedSentence.replace("?","")

    correctionList.foreach(n => {
      //apply corrections only if suggestions available
      var suggestions = n \ "suggestions" \ "option"
      var correctionType = n \ "type"



      if (suggestions.length > 0) {
        //apply
        correctedSentence = correctedSentence.replace((n \ "string").text, suggestions.head.text)
      }
    })

    return correctedSentence

  }

  def correctSentence(inputSentence: String): String = return sendRequest(inputSentence, false)
}
