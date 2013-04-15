package tu.coreservice.utilities

import tu.model.knowledge.Constant

/**
 * Is multi culture support
 * @author Alexander Toschev
 *         Date: 9/9/12
 *         Time: 7:33 PM
 */
object LocalizedResources {

  private val localizationMap = Map("$ErrorOccured" -> "An error has occurred:",
    "$ProvideAdditionalInfo" -> "Please provide additional information",
    "$ClarifyPhrases" -> "Sorry, i can't understand next phrases: ",
    "$ClarifyConcepts" -> "Sorry, i can't understand next concepts: ",
    Constant.understoodConcepts -> "Understood concepts: ",
    Constant.notUnderstoodConcepts -> "Not understood concepts: "
  )

  /**
   * return localized string or string ig nothing found.
   * @param key the string to localize.
   * @return localized string in target language.
   */
  def GetString(key: String): String = {
    if (localizationMap.contains(key))
      return localizationMap(key)
    key
  }
}
