package tu.model.knowledge.annotator

import tu.model.knowledge.{Resource, Probability, KnowledgeURI}
import tu.model.knowledge.primitive.KnowledgeString


/**
 * Created by IntelliJ IDEA.
 * User: toscheva
 * Date: 04.06.12
 * Time: 11:36
 *
 */

/**
 * provide word information
 * @param __value actually word
 * @param __uri uri of resource
 * @param __probability probability (it will be 1.0, but may be other in future)
 */
case class Word(__value: String, __uri: KnowledgeURI, __probability: Probability) extends KnowledgeString(__value,__uri,__probability) {

}
