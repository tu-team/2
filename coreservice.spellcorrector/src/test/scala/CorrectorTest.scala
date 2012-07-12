import org.junit._
import Assert._
import tu.coreservice.spellcorrector.SpellCorrector


@Test
class CorrectorTest {

  @Test
  def testCompoundCorrector() = {
    //Hello world test
    var corrector = SpellCorrector.apply()
    var test = corrector.correctSentence("The wrld are red")
    assertTrue(test == "The world is red")

  }

 /* @Test
  def testWorkingSentence() = {
    //Seems to CatiaV5 aren?t installed correcly

    var corrector = SpellCorrector.apply()
   // var test = corrector.correctSentence("Seems to CatiaV5 aren?t installed correcly")
   // assertTrue(test == "Seems to CatiaV5 aren't installed correctly")

    var test = corrector.correctSentence("installed, teamcenter and cnext5, seems to work.Seems to CatiaV5 aren?t installed correcly.errormessenge: a program cannot display messge. the program need a promission or information to complete a task!It?s a Vista-PC.")
    assertTrue(test == "user is not able to use the wireless function on his laptop")

    //Flash Player - User reports problems with his current version of flash player.User reports that he has two computers, one with a working (and newer) flash player that he uses to work with and the other computer seems to have a older flash version which does not work properly.Could you take a look at this and see if there is any flash update that can (re)sent to his computer?
    // Outlook Web Access (VCC) - 403 - Forbidden: Access is denied'C:\WINDOWS\system32\CCM\Cache\A0000B72.1.System\install.vbs'
    //The problem I have now is that Iam failing to install Microsoft Office and other applications.
    //In Windows XP SP3, When ever i open a folder and try to close i am getting "windows explorer has encountered a problem and needs to close.We are sorry for the inconvenience"
  }    */
  /*
  @Test
  def testAdditional() = {
    //It?s a Vista-PC
    var corrector = SpellCorrector.apply()
    var test = corrector.correctSentence("It?s a Vista-PC")
    assertTrue(test == "It?s a Vista-PC")

  }     */

  //    @Test
  //    def testKO() = assertTrue(false)

}


