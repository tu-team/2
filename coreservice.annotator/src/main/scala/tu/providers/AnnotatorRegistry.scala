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
    return List(new LocalKBAnnotatorProvider,new WordnetAnnotatorProvider).sortWith((it1,it2)=> it1.priority() < it2.priority() )
  }

  /**
   * local KBAnnotator provider
   * @return local KBAnnotator provider
   */
  def getLocalAnnotator():AnnotatorProvider ={
    return new LocalKBAnnotatorProvider
  }
}
