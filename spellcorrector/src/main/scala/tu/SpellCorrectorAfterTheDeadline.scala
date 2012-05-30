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

  private var checkDocument=  "http://service.afterthedeadline.com/checkDocument?key=tuapikeyABCDABCDABCDABCDABCDABCDABCDABCD&data="

  private var checkGrammar=  "http://service.afterthedeadline.com/checkGrammar?key=tuapikeyABCDABCDABCDABCDABCDABCDABCDABCD&data="


  def sendRequest(sentenceToCheck:String)={
    //ecndoe string
    var urlString= URLEncoder.encode(checkDocument,"UTF8").concat ( URLEncoder.encode(sentenceToCheck,"UTF-8"));

    var url = new URL(checkDocument+URLEncoder.encode ( sentenceToCheck))


    var proxy= new java.net.Proxy(java.net.Proxy.Type.HTTP,new InetSocketAddress (Configurator.proxyAddress().proxyHost,Configurator.proxyAddress().proxyPort) )
    
    var connection:URLConnection=null;
    if (Configurator.proxyAddress().useProxy)
    {
      connection=url.openConnection(proxy)
    }
    else
    {
      connection=url.openConnection()
    }

    //send request
    connection.setDoInput(true)
    //var outStream=connection.getOutputStream();
   // var writer = new OutputStreamWriter(outStream);
    //writer.flush()



    var factory = DocumentBuilderFactory.newInstance()
    var builder = factory.newDocumentBuilder()
    var doc = builder.parse(connection.getInputStream);

    //parse output and correct sentence

  }

  def correctSentence(inputSentence: String): String = ""
}
