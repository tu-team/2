package fi.neter.opencog.reasoning.pln;

import java.math.BigInteger;

import fi.neter.opencog.atomspace.Vertex;
import fi.neter.opencog.util.Tree.IteratorBase;

/**
 * From file PLNUtils.h
 * 
 * @author tero
 *
 */
public class BoundVTree extends VTree {
	/**
	 * BoundVTree() {}
	 */
	public static native BoundVTree construct();

	/**
	 * 	// Joel: Had to add constructors to route to the parent
	 * 	// How this compiled in Novamente without them, I have no idea.
	 * BoundVTree(const vtree::iterator_base& i) : vtree(i) {}
	 */
	public static native BoundVTree construct(IteratorBase i);

	/**
	 * BoundVTree(const Vertex& v) : vtree(v) {}
	 */
	public static native BoundVTree construct(Vertex v);

	/**
	 * BoundVTree(const ModifiedVTree& rhs)
	 */
	public static native BoundVTree construct(ModifiedVTree rhs);

	/**
	 * BoundVTree(const vtree& rhs, Btr<bindingsT> _bindings = Btr<bindingsT>())
	 */
	public static native BoundVTree construct(VTree rhs, BindingsT bindings);

	/**
	 * BoundVTree* Clone() const
	 */
	public native BoundVTree clone();

	/**
	 * Btr<bindingsT> bindings;
	 */
	public native BindingsT getBindings();

	/**
	 * const vtree& getStdTree();
	 */
	public native VTree getStdTree();
	
	/**
	 * unsigned long getFingerPrint();
	 */
	public native BigInteger getFingerPrint();
	
	/**
	 * void createMyStdTree();
	 */
	public native void createMyStdTree();
}
