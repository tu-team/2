package tu.coreservice.utilities

/**
 *  holds multi culture support
 * @author Alexander Toschev
 *          Date: 9/9/12
 *          Time: 7:33 PM
 */
object LocalizedResources {

  private val localizationMap=Map("ErrorOccured"->"An error has occured:",
  "ProvideAdditionalInfo"->"Please provide additional information",
  "$ClarifyPhrases"->"Sorry, i can't undestand next phrases: ")

  /**
   * return localized string or string ig nothing found
   * @param key
   * @return
   */
  def GetString(key:String):String={
   if (localizationMap.contains(key) )
     return localizationMap(key)
   key
  }
}
