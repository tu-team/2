package tu.providers

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 6/24/12
 * Time: 10:19 AM
 * @author alexander toschev
 */

/**
 * @define provide interface for annotator providers
 */
trait AnnotatorProvider {

  def annotate(word:String):List[String]


  /**
   * priority of annotator. 0 top most local repository
   * @return
   */
  def priority():Int

}
