package tu.model.knowledge

import collection.mutable.HashMap


/**
 * @author: adel
 * Date: 30.08.12
 * Time: 11:37
 */


class KBNodeId (val ID:Long) {
  def this(r:Resource){
    this(KBMap.get(r))
  }
  def this(m:Map[String,  String]) {this(KB.getIdFromMap(m))}
}



object KBNodeId {

  def apply(ID:Long):KBNodeId = {new KBNodeId (ID)}
  def apply(r:Resource):KBNodeId = {

    new KBNodeId (KBMap.get(r))
  }
  def apply(m:Map[String,  String]):KBNodeId = {new KBNodeId (KB.getIdFromMap(m))}
}


object KBMap {

  implicit def Resource2KBNodeId(h:Resource) = new KBNodeId(h)

  var uri2id = HashMap[String, Long]()
  var id2object = HashMap[Long, Resource]()

  def register(r:Resource, id:Long)  {
    val uri = r.uri.toString
    uri2id(uri) = id
    id2object(id) = r
  }


  def register(r:Resource) {

    val uri = r.uri.toString
    if (uri2id.get(uri) == None)
      uri2id(uri) = Constant.NO_KB_NODE
  }

  def get(r:Resource): Long = {
    val uri = r.uri.toString
    uri2id.get(uri) match {
      case Some(x) => x
      case None => Constant.NO_KB_NODE
    }

  }

}