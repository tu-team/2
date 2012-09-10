package tu.coreservice.utilities

/**
 *
 * @author: Alexander Toschev
 *          Date: 9/9/12
 *          Time: 7:33 PM
 *
 */

/**
 * holds multi culture support
 */
object LocalizedResources {

  private val localizationMap=Map("ErrorOccured"->"An error has been occured:",
  "ProvideAdditionalInfo"->"Please provide additional info")

  def GetString(key:String):String={
     localizationMap(key)
  }
}
