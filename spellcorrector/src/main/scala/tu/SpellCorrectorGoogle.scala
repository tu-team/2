package tu

import org.xeustechnologies.googleapi.spelling.{SpellRequest, Language, SpellChecker, Configuration}


/**
 * @author toscheva
 *         Date: 28.05.12
 *         Time: 20:52
 */

class SpellCorrectorGoogle extends SpellCorrector {
  /**
   * correct sentence
   * @param inputSentence sentence that should be corrected
   */
  def correctSentence(inputSentence: String): String = {

    var corrected = new StringBuffer(inputSentence)

    var config = new Configuration();

    if (Configurator.proxyAddress().useProxy)
      config.setProxy(Configurator.proxyAddress().proxyHost, Configurator.proxyAddress().proxyPort, Configurator.proxyAddress().proxyType);

    var checker = new SpellChecker(config);
    checker.setOverHttps(true); // Use https (default true from v1.1)
    checker.setLanguage(Language.ENGLISH); // Use English (default)

    var request = new SpellRequest();
    request.setText(inputSentence);
    request.setIgnoreDuplicates(true); // Ignore duplicates

    var spellResponse = checker.check(request);

    var offsetDelta = 0;

    spellResponse.getCorrections.foreach(c => {
      //perform correction only of confidence 1
      if (c.getConfidence == 1) {
        //extract first value
        var toReplace = c.getWords.head

        //clean up wrong word and replace by proper
        corrected.replace(c.getOffset()+offsetDelta, c.getOffset + c.getLength+offsetDelta, toReplace)
        offsetDelta=toReplace.length()-c.getLength
      }
    })

    //TODO process output
    //for( SpellCorrection sc : spellResponse.getCorrections() )
    //System.out.println( sc.getValue() );

    return corrected.toString
  }
}
