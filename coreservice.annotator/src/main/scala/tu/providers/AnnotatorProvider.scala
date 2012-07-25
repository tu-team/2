package tu.providers

import tu.model.knowledge.annotator.AnnotatedPhrase

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


  /**
   * indicates that this is a local KB Annotator
   * @return  true if local annotator
   */
  def isLocal():Boolean


  /***
   *
   * @param word
   * @return  returns annotated phrase directly from local storage for non local storage exception should be raised
   */
  def apply(word:String):Option[AnnotatedPhrase]

}
