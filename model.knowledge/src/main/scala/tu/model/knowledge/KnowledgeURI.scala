package tu.model.knowledge

import java.net.URI
import util.Random
import org.slf4j.LoggerFactory

/**
 * Universal Identifier of the model.
 * @author max talanov
 *         Time: 11:20 PM
 */

class KnowledgeURI(_namespace: String, var _name: String, _revision: String) {

  def this(map: Map[String, String]) = {
    this(
      map.get("namespace") match {
        case Some(x) => x
        case None => Constant.defaultNamespace
      },
      map.get("uri-name") match {
        case Some(x) => x
        case None => {
          val randomName: String = Random.nextInt(Constant.INSTANCE_ID_RANDOM_SEED).toString
          val scope = map.get("class") match {
            case Some(x) => x
            case None => KnowledgeURI.getClass.getName
          }
          LoggerFactory.getLogger(scope).warn("$Empty_name_replaced_with_{}", randomName)
          randomName
        }
      },
      map.get("revision") match {
        case Some(x) => x
        case None => Constant.defaultRevision
      }
    )
  }

  private val delimiter = "#"
  private var _uRI: Option[URI] = None
  private var _uID: String = ""

  def namespace(): String = _namespace

  def name = _name

  def name_=(value: String) = _name = value

  def revision(): String = _revision

  def uid(): String = _uID

  def uid_=(in: String): KnowledgeURI = {
    this._uID = in
    this
  }

  def uri(): Option[URI] = {
    _uRI match {
      case None => {
        if (namespace().size > 0 && name.size > 0 && revision.size > 0 ) {
          this._uRI = Some(new URI(namespace + Constant.DELIMITER + name + Constant.REVISION_DELIMITER + revision + Constant.UID_DELIMITER + _uID))
          this._uRI
        } else {
          None
        }
      }
      case Some(_) => {
        _uRI
      }
    }
  }

  def uri_=(in: URI): KnowledgeURI = {
    this._uRI = Some(in)
    this
  }

  override def toString: String = {
    namespace() + delimiter + name + "@" + revision()
  }

  def export: Map[String, String] = {
    Map(
      "namespace" -> this.namespace(),
      "uri-name" -> this.name,
      "revision" -> this.revision())
  }

  def equals(aUri: KnowledgeURI): Boolean = {
    name.equals(aUri.name) && namespace.eq(aUri.namespace) && revision().equals(aUri.revision())
  }
}

object KnowledgeURI {

  def apply(name: String): KnowledgeURI = {
    new KnowledgeURI(Constant.defaultNamespace, name, Constant.defaultRevision)
  }
  def apply(name: String,uri:Boolean): KnowledgeURI = {
    new KnowledgeURI(Constant.defaultNamespace, name, Constant.defaultRevision)
  }
}
