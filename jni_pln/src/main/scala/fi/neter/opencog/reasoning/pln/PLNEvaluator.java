package fi.neter.opencog.reasoning.pln;

import fi.neter.opencog.atomspace.Vertex;
import fi.neter.opencog.util.Tree;
import fi.neter.opencog.util.Tree.Iterator;

/**
 * From file PLNEvaluator.h
 * @author tero
 *
 */
public class PLNEvaluator {
  	/**
  	 * // Calls the simple Boolean bottom-up PLN bw chainer
	 * static Vertex v_evaluate(	const tree<Vertex>& target,
	 * 						tree<Vertex>::iterator top,
	 * 						MetaProperty policy);
	 */
	public static native Vertex vEvaluate(Tree<Vertex> target,
			Iterator<Tree<Vertex>> top, MetaProperty policy);
		

}
