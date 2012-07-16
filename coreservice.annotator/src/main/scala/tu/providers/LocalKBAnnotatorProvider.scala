package tu.providers

/**
 * @author alex
 * Date: 6/24/12
 * Time: 10:38 AM
 */

/**
 * @define local knowledge base provider
 */
class LocalKBAnnotatorProvider extends AnnotatorProvider {

  def annotate(word: String):List[String] = {
    List[String]()
  }

  /**
   * priority of annotator. 0 top most local repository
   * @return
   */
  def priority() = 0

  /**
   * indicates that this is a local KB Annotator
   * @return  true if local annotator
   */
  def isLocal() = true
}
