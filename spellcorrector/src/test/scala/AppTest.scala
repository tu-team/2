import org.junit._
import Assert._
import tu.SpellCorrectorFactory

@Test
class AppTest {

  @Test
  def testCorrector()={
    //Hello world test
    var testString="Hllo wrold"
    var inst = SpellCorrectorFactory.construct();
    var corrected=inst.correctSentence(testString);

    assertTrue(corrected=="Hello world")


  }

  //    @Test
  //    def testKO() = assertTrue(false)

}


