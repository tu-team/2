package  tu.nlp.server
import scala._
import tu.model.knowledge.annotator.AnnotatedSentence

/**
 * 
 * @author: Alexander Toschev
 * Date: 12/1/12
 * Time: 12:57 PM
 * 
 */
/**
 * NLPProcessor interface
 */
trait NLPProcessor {

  /**
   * process sentence using default processing
   * withour correction
   * @param sent sentence string
   * @return relex.Sentence
   */
  def processSentence (sent:String):relex.Sentence


  /**
   * process sentence using corrections
   * @param sent
   * @param sentences
   * @return
   */
  def processSentence (sent:String, sentences:List[AnnotatedSentence]):relex.Sentence

  /**
   * split sentences from text
   * @param txt Source text
   */
  def splitSentences(txt:String):List[String]


}
