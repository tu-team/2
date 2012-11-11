package tu.coreservice.utilities

/**
 *  holds multi culture support
 * @author Alexander Toschev
 *          Date: 9/9/12
 *          Time: 7:33 PM
 */
object LocalizedResources {

  private val localizationMap=Map("ErrorOccured"->"An error has occured:",
  "ProvideAdditionalInfo"->"Please provide additional information")

  def GetString(key:String):String={
     localizationMap(key)
  }
}
