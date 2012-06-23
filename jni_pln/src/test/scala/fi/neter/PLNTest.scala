package fi.neter

import org.junit._
import Assert._
import fi.neter.opencog.atomspace._;
import fi.neter.opencog.reasoning.pln._;
import fi.neter.opencog.server._;

/**
 * FIXME: Expressions that should be able to be processed by the PLN library are like this:
 *
 *  (pln-bc test-target nsteps)
 *  (define y (InheritanceLink (ConceptNode "Osama") (ConceptNode "Abu")))
 *    (display y)
 *    (pln-bc y 200)
 *    (display y)
 *
 *
 *  Make sure there is enough interface implemented to handle it.
 */
@Test
class PLNTest {

	
  /**
   * Turn the following C++ program into Java equivalent, using the interface classes.
   *
   * #define AS_PTR (CogServer::getAtomSpace())
   * Handle DirectATW::addNode(Type T, const string& name,
   * const TruthValue& tvn, bool fresh,bool managed)
   * {
   *    printf("directATW addNode\n");
   *   	AtomSpace *a = AS_PTR;
   *    const TruthValue& tv = SimpleTruthValue(tvn.getMean(), 0.0f);
   *    const TruthValue& mod_tvn =
   *       (!inheritsType(T, VARIABLE_NODE))? tvn : tv;
   *    return a->addNode( T, name, mod_tvn, fresh,managed);
   * }
   */
  @Test
  def testAtomSpace() {
	System.loadLibrary("JniPlnNative");

    val tvn: TruthValue = TruthValue.TRUE_TV();
    val t: Type = new Type();
    val name = "Name";

	/*
	 * CogServer& cogserver = static_cast<CogServer&>(server());
	 **/
    val cogserver = JNIPLN.cogServer();

    System.out.println("cs: " + cogserver);
    // This is also necessary to make sure that the ASW is initialized
    // at the right time.
    /*
     * asw = ASW(cogserver.getAtomSpace());
     */
    /*
     * ASW => AtomSpaceWrapper* ASW(AtomSpace *a = NULL);
     */

    val atomspace = CogServer.getAtomSpace();
    System.out.println("as: " + atomspace);
    val aw = JNIPLN.ASW(atomspace);
    System.out.println("AW: " + aw);
    /*
     * Note: This is a simple test. You should use AtomSpaceWrapper methods with PLN instead of AtomSpace methods
     * in the real code.
     */
    val a: AtomSpace = aw.getAtomSpace();
    val tv: TruthValue = SimpleTruthValue.construct(tvn.getMean(), 0.0f);
    var modTvn: TruthValue = tv;
    if (!aw.inheritsType(t, AtomTypes.VARIABLE_NODE.getType())) {
    	modTvn = tvn;
    }
    val handle = a.addNode(t, name, modTvn);
    assertNotNull(handle);
  }

  @Test
  def testBasicCase() = {
    // (pln-bc test-target nsteps)
    // (define y (InheritanceLink (ConceptNode "Osama") (ConceptNode "Abu")))
    // (display y)
    // (pln-bc y 200)
    // (display y)
  }

}
