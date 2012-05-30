import org.junit._
import Assert._
import tu.coreservice.spellcorrector.SpellCorrectorAfterTheDeadline


@Test
class AppTest {

  @Test
  def testCorrector()={
    //Hello world test
    var corrector = new SpellCorrectorAfterTheDeadline
    corrector.sendRequest("Hllo wrold")


  }

  //    @Test
  //    def testKO() = assertTrue(false)

}


