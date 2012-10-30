package tu.coreservice.utilities

import java.net.URI

/**
 * @author toscheva
 *         Date: 06.06.12
 *         Time: 10:50
 *
 */

/**
 * Contain helpers to work with URI
 */
object URIHelper {

  /**
   * Check if URI contain specific tag
   * @param tag tagName
   * @param uri target URI
   * @return true if uri contains tag
   */
  def isContainTag(tag: String, uri: URI): Boolean = {
    uri.toString.contains(tag)
  }

  /**
   * generate empty uri
   * @return  empty uri
   */
  def emptyURI() = "http://tu-project.com/knowledge/empty"

  def uriPrefix() = "http://tu-project.com/"

  def uriProjectName = "tu-project.com"

  def generateURIForFrame(frameUniqueName: String): String = {
    "http://tu-project.com/" + frameUniqueName
  }

  /**
   *
   * @return  namespace for annotator
   */
  def annotatorNamespace(): String = {
    "tu.coreservice.annotator"
  }

  /**
   * return project version
   * @return  project version
   */
  def version(): String = {
    "1.0"
  }


  /**
   * text mark for splitter results
   * @return
   */
  def splitterMark(): String = {
    // return "splitted"
    "tu.coreservice.splitter.PreliminarySplitter"
  }

  def sentenceMark(): String = {
    "sentense"
  }


}
