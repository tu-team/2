package tu.model.knowledge

import java.net.URI

/**
 * @author max talanov
 *         Time: 11:20 PM
 */

class KnowledgeURI(_namespace: String, _name: String, _revision: String) {

  private val DELIMITER = "."
  private val REVISION_DELIMITER = "/"
  private val UID_DELIMITER = "#"
  private var _uRI: Option[URI] = None
  private var _uID: String = ""

  def namespace(): String = _namespace
  def name(): String = _name
  def revision(): String = _revision

  def uid(): String = _uID

  def uid_=(in: String): KnowledgeURI = {
    this._uID = in
    this
  }

  def uri(): Option[URI] = {
    _uRI match {
      case None => {
        if (namespace.size > 0 && name.size > 0 && revision.size > 0 && _uID.size > 0) {
          this._uRI = Some(new URI(namespace + DELIMITER + name + REVISION_DELIMITER + revision + UID_DELIMITER + _uID))
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

}
