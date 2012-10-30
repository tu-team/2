package tu.model.knowledge.helper

import util.Random
import tu.model.knowledge.{Constant, KnowledgeURI}

/**
 * @author alex toschev
 *         Time stamp: 9/7/12 8:32 PM
 */
object URIGenerator {
  /**
   * Generates URI for instance with specified name
   * @param inst the name of instance for URI to be generated
   * @return KnowledgeURI
   */
  def generateURI(inst: String): KnowledgeURI = {
    new KnowledgeURI("", inst + Constant.UID_INSTANCE_DELIMITER + Random.nextInt(Constant.INSTANCE_ID_RANDOM_SEED), "1.0")
  }

  def generateUID(): String = {

    val key = uniqueRandomKey(chars.mkString(""), 8, isUnique)
    return key
  }


  def uniqueRandomKey(chars: String, length: Int, uniqueFunc: String=>Boolean) : String =
  {
    val newKey = (1 to length).map(
      x =>
      {
        val index = Random.nextInt(chars.length)
        chars(index)
      }
    ).mkString("")

    if (uniqueFunc(newKey))
      newKey
    else
      uniqueRandomKey(chars, length, uniqueFunc)
  }

  /**
   * implement your own is unique here
   */
  def isUnique(s:String):Boolean = true

  val chars = ('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9')

}
