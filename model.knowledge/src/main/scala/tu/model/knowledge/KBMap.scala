package tu.model.knowledge

import collection.mutable.HashMap

/**
 * @author: adel
 * Date: 30.08.12
 * Time: 11:37
 */

object KBMap {

  var uri2id = HashMap[String, Long]()
  var id2object = HashMap[Long, Resource]()

  def register(r:Resource, id:Long)  {
    val uri = r.uri.toString
    uri2id(uri) = id
    id2object(id) = r
  }

  def register(r:Resource) { register(r:Resource, Constant.NO_KB_NODE)}



}