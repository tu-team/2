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
}
