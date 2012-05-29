package fi.neter.opencog.reasoning.pln.rules;

import java.util.List;

import fi.neter.opencog.atomspace.VertexSeq;
import fi.neter.opencog.reasoning.pln.AtomSpaceWrapper;
import fi.neter.opencog.reasoning.pln.BBVTree;
import fi.neter.opencog.reasoning.pln.BoundVertex;
import fi.neter.opencog.reasoning.pln.PHandle;

/** Rule is a superclass for types of inference rules.
*
* In determining the proper input on the basis of getting a specific output:
*
* For some rules, the exact input atom can be determined.  For other rules,
* only constraints on the atom properties can be determined.  Eg. And(A, B, C)
* can be derived from And(A,B) & And(B,C) etc. ie. from any collection of
* AndLinks with A, B, and C occurring somewhere. This can only be expressed as
* a MP (metapredicate).
* 
* From file: rule.h
*/
public class Rule {
	// Maximum number of inputs a rule will ever take?
	/**
	 * const int RULE_INPUT_ARITY_MAX;
	 */

	/** Rule constructor
	 * @param _asw Pointer to the AtomSpace interface.
	 * @param _freeInputArity Are the number of arguments predetermined?
	 * @param _composer Whether the rule has premises.
	 * @param _name The name of the rule.
	 * 
	 * Rule(AtomSpaceWrapper *_asw,
	 *       bool _freeInputArity,
	 *       bool _composer,
	 *       std::string _name = "");
	 */
	public static native Rule construct(AtomSpaceWrapper asw, boolean freeInputArity,
			boolean composer, String name);
	
	/**
	 * Rule();
	 */
	public static native Rule construct();

	/**
	 * virtual ~Rule();
	 */
	public native void destroy();

	/** The generic rule computation method.  This method must be implemented in
	 * subclasses.  If the rule can take the args in any order, the \c Rule
	 * object must take care of ordering them.
	 *
	 * @param h The vertices to compute on.
	 * @param CX Context to use for rule computation. Currently unused.
	 * @param fresh allows atoms to be added with the same name/outgoing set.
	 *              If fresh == false and the atom already exist then the new
	 *              truth value is merged (via TruthValue::merge) with the old.
	 *              Otherwise (fresh == true) then a new dummy context
	 *              is associated to that new truth value.
	 * @todo A future implementation may include 'bool ordered_already'
	 * parameter to speed up.
	 * 
	 * 	    virtual BoundVertex compute(const VertexSeq& h,
	 * 	                                pHandle CX = PHANDLE_UNDEFINED,
	 * 	                                bool fresh = true) const=0;
	 */

	/** Perform some checks then run the other version of compute. Note that
	 * this is the method that is actually called by the back and forward
	 * chainers.
	 * @see Rule::compute(const VertexSeq& h, pHandle CX = PHANDLE_UNDEFINED)
	 * 
	 * 	BoundVertex compute(const std::vector<BoundVertex>& h,
	 * 			pHandle CX = PHANDLE_UNDEFINED,
	 * 			bool fresh = true) const;
	 */
	public native BoundVertex compute(List<BoundVertex> h,
			PHandle cx,
			boolean fresh);
	
	/**
	 * //! Try to call rule as a direct producer (Generator)
	 * virtual Btr<std::set<BoundVertex> > attemptDirectProduction(meta h,
	 * 			bool fresh = true) const=0;
	 */

	/**
	 * //! Just calls compute()
	 *  BoundVertex operator() (const VertexSeq h,
	 *		pHandle CX = PHANDLE_UNDEFINED,
	 *		bool fresh = true) const
	 */
	public native BoundVertex apply(VertexSeq h, PHandle cx, boolean fresh);
	
	/** Check validity.
	 * If this fails, then it re-orders the premises in order to gain it.
	 * (not sure about this ???)
	 *
	 * @param h The vertices to check validity for.
	 * @return Whether the provided vertices fit the rule requirements or not.
	 * 
	 * 	    bool validate(const VertexSeq& h) const;
	 */
	public native boolean validate(VertexSeq h);

	/** ARI: Another alternative for checking validity. Only used by deduction,
	 * to avoid things like "Imp A A" where an atom implies itself. This method
	 * should be refactored out.
	 *
	 * @param args The vertices to check validity for.
	 * @return Whether the provided vertices fit the rule requirements or not.
	 * 
	 * virtual bool validate2(MPs& args) const=0;
	 */

	/** Only perform compute if arguments are valid. ???
	 *
	 * @param h The vertices to compute the rule for
	 * @param CX Context to use for rule computation. Currently unused.
	 * @return The result of the rule being computed.
	 * 
	 * 	BoundVertex computeIfValid (const VertexSeq& h,
	 * 			pHandle CX = PHANDLE_UNDEFINED,
	 * 			bool fresh = true) const;
	 */
	public native BoundVertex computeIfValid(VertexSeq h,
			PHandle cx,
			boolean fresh);

	/** Called while the BIT is determining whether to create a new child node
	 * with this Rule. Used by BITNode::expandRule(). Determines the target for
	 * the child node.
	 *
	 * Calls the o2iMetaExtra method in the subclass.
	 * If that method sets overrideInputFilter to true, this returns its result.
	 * Otherwise, this returns an empty set - that is, there are no possible
	 * child nodes in this case.
	 *
	 * @param outh a metapredicate specifying the target for that BITNode.
	 * @return a set of vectors of MetaPredicates. Each vector is one possible
	 * input, with a MetaPredicate for each input slot (maybe -- JaredW).
	 * 
	 * setOfMPs o2iMeta(meta outh) const;
	 */
	public native SetOfMPs o2iMeta(Meta outh);

	/**
	 * //! Get the inputFilter for this Rule.
	 *  MPsIn& getInputFilter() { return inputFilter; }
	 */
	public native MPsIn getInputFilter();

	/**
	 * virtual setOfMPs fullInputFilter() const;
	 * @return
	 */
	public native SetOfMPs fullInputFilter();

	/**
	 * //! Does the Rule have unfixed input arity?
	 *   bool hasFreeInputArity() const { return freeInputArity; }
	 */
	public native boolean hasFreeInputArity();
	
	/**
	 * bool isComposer() const { return composer; }
	 */
	public native boolean isComposer();

	/** Copy a bunch of vertices from src to dest ???
	 *
	 * @param src Source
	 * @param dest Destination
	 * 
	 * 	    static void CloneArgs(const Rule::MPs& src, Rule::MPs& dest);
	 */
	public static native void cloneArgs(List<BBVTree> src, List<BBVTree> dest);

	/**
	 * // Used for logging purposes and retrieving rule based on its name
	 * std::string name;
	 */ 
	public native void setName(String name);

	/**
	 * const std::string& getName() const { return name; }
	 */
	public native String getName();
}
