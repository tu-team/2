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
}
