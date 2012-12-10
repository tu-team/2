package tu.extensions

import interfaces.{Generalizer, BaseExtension}
import scala.None
import scala.collection.mutable.Map

/**
 * @author alex
 *         Time stamp: 12/10/12 9:39 AM
 *         Load specified extensions
 */
object Extensions {

  /**
   * load extension for
   * @tparam T  interface of Extensions
   */
  def load[T <: BaseExtension]()(implicit m: scala.reflect.Manifest[T]): T = {

    currentExtensions.get(m.erasure.getName()) match {
      case Some(clsMap) => {
        clsMap.head._2 match {
          case Some(obj)=> obj.asInstanceOf[T]
          case None =>
          {
            var clazz = Class.forName (clsMap.head._1)
            clsMap(clsMap.head._1)=Some(clazz.newInstance().asInstanceOf[T])
            clsMap.head._2.get.asInstanceOf[T]

          }
        }

      }
      case None => throw new Exception("Extensions of " + m.erasure + " is not supported")
    }
  }


  private var  currentExtensions = Map(("tu.extensions.interfaces.Generalizer"->Map[String,Option[BaseExtension]]( "tu.extensions.algorithms.defaults.GeneralizerImp"->None)))
}
