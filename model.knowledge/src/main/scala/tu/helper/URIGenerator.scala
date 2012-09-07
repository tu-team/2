package tu.model.knowledge.helper

import util.Random
import tu.model.knowledge.{Constant, KnowledgeURI}

/**
 * @author alex
 *         Time stamp: 9/7/12 8:32 PM
 */
object URIGenerator {
  /**
   *
   * @return
   */
  def generateURI(inst: String):KnowledgeURI={
    return new KnowledgeURI("", inst +"&ID=" + Random.nextInt(Constant.INSTANCE_ID_RANDOM_SEED),"1.0")
  }
}
