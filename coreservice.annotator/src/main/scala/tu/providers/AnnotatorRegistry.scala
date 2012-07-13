package tu.providers

/**
 * @author alex toschev
 * Date: 6/24/12
 * Time: 10:25 AM
 */

/**
 * @define provide list of availible annotators
 */
object AnnotatorRegistry {

  /**
   * provide all availible annotators
   * @return list of annotators
   */
  def listAnnotators(): List[AnnotatorProvider] = {
    //Append annotator classes here
    return List(new LocalKBAnnotatorProvider,new WordnetAnnotatorProvider)
  }
}
