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

  @Test
  def testWorkingSentence() = {
    //Seems to CatiaV5 aren?t installed correcly

    var corrector = SpellCorrector.apply()
    var test = corrector.correctSentence("installed, teamcenter and cnext5, seems to work.Seems to CatiaV5 aren?t installed correcly.errormessenge: a program cannot display messge. the program need a promission or information to complete a task.It?s a Vista-PC.")
    assertTrue(test == "installed, teamcenter and cnext5, seems to work.Seems to CatiaV5 aren?t installed correcly.errormessenge: a program cannot display messge. the program need a promission or information to complete a task.It's a Vista-PC.")

    test = corrector.correctSentence("user is not able to use the wirelsess function on his laptop")
    assertTrue(test == "user is not able to use the wireless function on his laptop")

  }
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


