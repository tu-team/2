package tu.providers

import tu.coreservice.utilities.Configurator
import java.net.{URLConnection, InetSocketAddress, URL, URLEncoder}
import java.io.{InputStreamReader, BufferedReader}

/**
 * Provider for WordNetAnnotator.
 * @author alex toschev
 *         Date: 6/25/12
 *         Time: 7:00 PM
 */

/**
 * Sends
 */
class WordnetAnnotatorProvider extends AnnotatorProvider {

  def annotate(word: String): List[String] = {

    val url: URL = new URL("http://wordnetweb.princeton.edu/perl/webwn?s=" + URLEncoder.encode(word, "UTF8"))

    var res: List[String] = List[String]()

    //Sends request to wordnet
    var rawString=""

    try {
      val connection = if (Configurator.proxyAddress().useProxy) {
        val proxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(Configurator.proxyAddress().proxyHost, Configurator.proxyAddress().proxyPort))
        url.openConnection(proxy)
      } else {
        url.openConnection()
      }
      //setup connection
      // we need only input
      connection.setDoInput(true)
      connection.setDoOutput(false)
      connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; .NET CLR 1.0.3705; .NET CLR 1.1.4322; .NET CLR 1.2.30703)")

      //open stream

      val in = new BufferedReader(new InputStreamReader(connection.getInputStream))

      val xHtml = new StringBuilder

      //convert to string (scala xml trying to use DTD while load)
      var str = ""
      str = in.readLine()
      while (str != null) {
        xHtml.append(str)
        str = in.readLine()
      }

      in.close()
       rawString = xHtml.toString()
    }
    catch {
      case e: Exception => {
          //any exception with internet connection
          return res
      }
    }





    //get only li segment

    val targetString = if (rawString.indexOf("<li>") > 0 && rawString.indexOf("</li>") > 0) {
      rawString.substring(rawString.indexOf("<li>"), rawString.indexOf("</li>") + 5)
    } else {
      return res
      ""
    }

    val data = scala.xml.XML.loadString(targetString)

    (data \ "a").foreach(a => {
      //skip system symbols
      if (!a.text.contains("(n)") && !a.text.contains("S:")) {
        if (res == null) res = List(a.text)
        else res ::= a.text
      }
    })

    res

  }

  /**
   * priority of annotator. 0 top most local repository
   * @return
   */
  def priority() = 2

  /**
   * indicates that this is a local KB Annotator
   * @return  true if local annotator
   */
  def isLocal() = false

  def apply(word: String) = throw new Exception("Method is not supported by WordnetAnnotatorProvider")
}
