package tu.providers

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 6/24/12
 * Time: 10:25 AM
 * To change this template use File | Settings | File Templates.
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
