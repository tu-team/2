package tu.model.knowledge

import java.net.URI
import util.Random
import org.slf4j.LoggerFactory
import tu.model.knowledge.helper.URIGenerator

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

  def uid = _uID

  def uid_=(in: String) = _uID=in


  def uri(): Option[URI] = {
    _uRI match {
      case None => {
        //if (namespace().size > 0 && name.size > 0 && revision.size > 0 ) {
        if (_uID=="")
        {
          //generate UID
          //_uID=URIGenerator.generateUID()
        }
        this._uRI = Some(new URI(namespace + Constant.DELIMITER + name.replace(" ","-") + Constant.REVISION_DELIMITER + revision + Constant.UID_DELIMITER + _uID))
        this._uRI
        //} else {
        //  None
        //}
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
    uri().get.toString
    //namespace() + delimiter + name + "@" + revision()
  }

  def export: Map[String, String] = {
    Map(
      "namespace" -> this.namespace(),
      "uri-name" -> this.name,
      "revision" -> this.revision())
  }

  /**
   * Compares specified uri and current uri, via name, namespace, revision.
   * @param aUri
   * @return
   */
  def equals(aUri: KnowledgeURI): Boolean = {
    name.equals(aUri.name) && namespace.eq(aUri.namespace) && revision().equals(aUri.revision())
  }

  /**
   * Returns instance identifier reduced concept name
   */
  def reduceInstanceIdentifier: String = {
    val indexOfUIDDelimiter = this.name indexOf tu.model.knowledge.Constant.UID_INSTANCE_DELIMITER
    if (indexOfUIDDelimiter > -1) {
      this.name.substring(0, indexOfUIDDelimiter)
    } else {
      this.name
    }
  }
}

object KnowledgeURI {

  def apply(name: String): KnowledgeURI = {
    new KnowledgeURI(Constant.defaultNamespace, name, Constant.defaultRevision)
  }

  private def checkIfThisIsRawString(input:String):Boolean={
    return input.contains(Constant.DELIMITER) &&
      input.contains(Constant.REVISION_DELIMITER)  &&
      input.contains(Constant.UID_DELIMITER)
  }

  def apply(raw: String,uri:Boolean): KnowledgeURI = {
    if (!checkIfThisIsRawString(raw)) return KnowledgeURI(raw)
    // Some(new URI(namespace + Constant.DELIMITER + name + Constant.REVISION_DELIMITER + revision + Constant.UID_DELIMITER + _uID))
    val namespace = raw.substring(0, raw.indexOf(Constant.DELIMITER))
    val name =  raw.substring(raw.indexOf(Constant.DELIMITER)+1, raw.indexOf(Constant.REVISION_DELIMITER )).replace("-"," ")
    val revision =      raw.substring(raw.indexOf(Constant.REVISION_DELIMITER)+1, raw.indexOf(Constant.UID_DELIMITER ))
    val uID=   raw.substring(raw.indexOf(Constant.UID_DELIMITER)+1)

    var kURI =new KnowledgeURI(namespace, name, revision )

    kURI.uid=uID

    return kURI
  }
}
