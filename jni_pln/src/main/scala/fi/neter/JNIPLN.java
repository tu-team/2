package fi.neter;

import java.util.List;
import java.util.Set;

import fi.neter.opencog.atomspace.AtomSpace;
import fi.neter.opencog.atomspace.Handle;
import fi.neter.opencog.atomspace.Type;
import fi.neter.opencog.reasoning.pln.Atom;
import fi.neter.opencog.reasoning.pln.AtomSpaceWrapper;
import fi.neter.opencog.reasoning.pln.BoundVertex;
import fi.neter.opencog.reasoning.pln.Subst;
import fi.neter.opencog.server.CogServer;

/*
 * http://wiki.opencog.org/w/PLN
 */

/*
 * FIXME: Make sure the next classes are implemented properly (*) = should be fine:
 *  - Formula
 *  - (*) Rule
 *  - TruthValue
 *  - (*) Vertex
 *  - (*) Handle
 *  - BITNodeRoot
 *  - (*) Vertex Tree
 *  - Backward Chainer (internal only? Perhaps no separate interface needed?)
 *  [probably not needed] - PLN_Forward_Chainer 
 */


public class JNIPLN {
	// PLNAtom.h
	/**
	 * void printAtomTree(const atom& a, int level = 0, int LogLevel = 5);
	 */
	public static native void printAtomTree();
	
	/*public static boolean equal_atom_ignoreVarNameDifferences(a,b) {
	    return (!lessatom_ignoreVarNameDifferences()(a,b) &&
	    	    !lessatom_ignoreVarNameDifferences()(b,a));
	}*/

	// typedef std::set<atom, lessatom> atomset;

	/**
	 * void getAtomTreeString(const atom& a, std::string& outbuf);
	 * @param a
	 * @return
	 */
	public static native String getAtomTreeString(Atom a);
	
	/**
	 * void VariableMPforms(const atom& src, std::set<atom, lessatom_ignoreVarNameDifferences>& res,
	 *                      std::set<subst>* forbiddenBindings);
	 */
	public static native Set<Atom> VariableMPforms(Atom src, Set<Subst> forbiddenBindings);
	
	/**
	 * bool getLargestIntersection2(const std::set<atom,lessatom>& keyelem_set,
	 * 	                            const std::vector<pHandle>& link_set, std::vector<boost::shared_ptr<atom> >& result);
	 */
	public static native boolean getLargestIntersection2();

	/**
	 * atom* neBoundVertexWithNewType(Handle h, Type T);
	 */
	public static native Atom neBoundVertexWithNewType(Handle h, Type t);
	
	/**
	 * From file: PLNEvaluator.h
	 * 
	 * Handle exec(Handle* hs, const int N);
	 */
	public static native Handle exec(Handle hs, int n);
	
	/**
	 * From file: PLNEvaluator.h
	 * 
	 * Handle exec(std::vector<Handle>& hs);
	 */
	public static native Handle exec(List<Handle> hs);

	/**
	 * From file: PLNEvaluator.h
	 * 
	 * Handle exec(const std::vector<BoundVertex>& hs);
	 */
	public static native Handle execBV(List<BoundVertex> hs);
	
	public static native CogServer cogServer();
	
	/**
	 * Singleton instance of AtomSpaceWrapper
	 * (following meyer's design pattern)
	 * 
	 * From AtomSpaceWrapper.h
	 * 
	 * AtomSpaceWrapper* ASW(AtomSpace *a = NULL);
	 */
	public static native AtomSpaceWrapper ASW(AtomSpace a);
	
	public static void main(String[] args)
	{
	}
	static{
		System.loadLibrary("JniPlnNative");
	}
}
