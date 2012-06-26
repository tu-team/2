package tu.providers

import tu.coreservice.utilities.Configurator
import java.net.{URLConnection, InetSocketAddress, URL, URLEncoder}
import java.io.{InputStreamReader, BufferedReader}

/**
 *
 * @author: alex
 *          Date: 6/25/12
 *          Time: 7:00 PM
 *          To change this template use File | Settings | File Templates.
 */

/**
 * Sends
 */
class WordnetAnnotatorProvider extends AnnotatorProvider {

  def annotate(word: String): List[String] = {

    var url: URL = new URL("http://wordnetweb.princeton.edu/perl/webwn?s=" + URLEncoder.encode(word, "UTF8") + "&sub=Search+WordNet&o2=&o0=&o8=1&o1=&o7=&o5=&o9=&o6=&o3=&o4=&h=00000000000000000000");

    var res:List[String] = null

    //Sends request to wordnet
    var proxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(Configurator.proxyAddress().proxyHost, Configurator.proxyAddress().proxyPort))

    var connection: URLConnection = null;
    if (Configurator.proxyAddress().useProxy) {
      connection = url.openConnection(proxy)
    }
    else {
      connection = url.openConnection()
    }

    //setup connection
    // we need only input
    connection.setDoInput(true)
    connection.setDoOutput(false)
    connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; .NET CLR 1.0.3705; .NET CLR 1.1.4322; .NET CLR 1.2.30703)");

    //open stream

    val in = new BufferedReader(new InputStreamReader(connection.getInputStream()));


    var xHtml = new StringBuilder;

    //convert to string (scala xml trying to use DTD while load)
    var str = ""
    str = in.readLine()
    while (str != null) {
      xHtml.append(str)
      str = in.readLine()
    }

    in.close()
    var rawString = xHtml.toString()

    //get only li segment
    var targetString = rawString.substring(rawString.indexOf("<li>"), rawString.indexOf("</li>") + 5)


    var data = scala.xml.XML.loadString(targetString)

    (data \ "a").foreach(a => {
      //skip system symbols
      if (!a.text.contains("(n)") && !a.text.contains("S:"))
      {
        if (res==null) res= List(a.text)
        else res ::= a.text
      }
    })


    return res

  }

  /**
   * priority of annotator. 0 top most local repository
   * @return
   */
  def priority() = 2
}
