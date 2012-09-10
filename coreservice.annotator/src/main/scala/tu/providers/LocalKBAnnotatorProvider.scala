package tu.providers

import tu.model.knowledge.annotator.{AnnotatedPhrase, AnnotatedWord}
import tu.dataservice.knowledgebaseserver.KBAdapter

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
    throw new Exception("Method is not supported for local provider")
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


  /**
    *  @return annotated word from local knowledge base
    */
  def apply(word:String ):Option[AnnotatedPhrase] ={
      return KBAdapter.getAnnotationByWord(word)
  }
}
