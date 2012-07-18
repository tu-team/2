package tu.providers

/**
 *
 * @author alex
 * Date: 6/28/12
 * Time: 1:38 PM
 *
 */

/**
 * provide access to Wolfram database
 */
class WolframAnnotatorProvider extends AnnotatorProvider{
  def annotate(word: String) = null

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

  /***
   *
   * @param word
   * @return  returns annotated phrase directly from local storage for non local storage exception should be raised
   */
  def apply(word: String) = throw  new Exception("Method is not supported by WolframAnnotatorProvider")
}
