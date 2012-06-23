package fi.neter.opencog.reasoning.pln;

import fi.neter.opencog.atomspace.Type;
import fi.neter.opencog.atomspace.AtomSpace;

/** The bridge between the OpenCog AtomSpace and PLN.
*
* <h3>Fixing Fresh = true</h3>
* 
* The original version of Probabilistic Logic Networks made improper use of a
* parameter in Novemente called \b fresh when adding new atoms to the
* #opencog::AtomSpace. This allowed atoms to be added to the AtomSpace without
* checking for duplicates. The end result is that atoms were no longer unique:
* nodes
* with the same name and type, or links that had the same outgoing set and
* type, could duplicated. OpenCog does not allow this behaviour.
* 
* To fix this, all accesses to the AtomSpace now occur through the
* AtomSpaceWrapper, which presents fake Handles to PLN and interprets them to a
* combination of a real #opencog::Handle and a #opencog::VersionHandle. A
* system of dummy contexts is used to emulate the behaviour of allowing
* duplicate atoms in the AtomSpace by storing multiple #opencog::TruthValues
* within a #opencog::CompositeTruthValue.
*
* The AtomSpaceWrapper did exist in the original PLN, but it was more for
* carrying out normalisation and allowing different AtomSpace backends to be
* used for efficiency reasons. 
* 
* Dummy contexts are represented as directed links. A root dummy context,
* represented as a Concept Node with name "___PLN___" is used for the outgoing
* set of dummy contexts in duplicate nodes. For links that are duplicates the
* outgoing set of their dummy context consists of the dummy context of any
* duplicate atoms they refer to, or the root dummy context if they refer to the
* original version of an atom.
* 
* <h4> Node example </h4>
*
* If a node already exists, then a dummy context is created that links to the
* root PLN dummy context.
* 
* E.g. say we have
*
* \code
*  ConceptNode "x" <0.8, 0.9>
* \endcode
*
* and we want to add a new node with the same name and type, but with TV
* <0.5,0.5>. To achieve this we create a new dummy context link:
*
* \code
*  dc <- OrderedLink (ConceptNode "___PLN___")
* \endcode
* 
* \c dc is then used as the context for the new TV:
* 
* \code
*  ConceptNode "x" <0.8, 0.9> [ Context dc <0.5, 0.5> ]
* \endcode
* 
* <h4> Link example </h4>
* 
* If a link already exists, then the appropriate contexts for each outgoing
* atom are linked by a new dummy context link (with the appropriate context
* links of each atom in the outgoing set of the link in the same order as the
* outgoing set of the existing link). This link's outgoing set/list is prefixed
* by the root dummy context node if such a context to context link doesn't
* exist, if it does exist then the context links are searched for the "bottom"
* link and this is used as the prefix.
* 
* E.g. say we have an InheritanceLink composed of two ConceptNodes that both
* have CompositeTruthValues:
* 
* \code
*  dc_x <- OrderedLink (ConceptNode "___PLN___")
*  dc_y <- OrderedLink (ConceptNode "___PLN___")
*  InheritanceLink <0.8, 0.9>
*      ConceptNode "x" <0.8, 0.9> [ Context dc_x <0.5, 0.5> ]
*      ConceptNode "y" <0.8, 0.9> [ Context dc_y <0.1, 0.5> ]
* \endcode
* 
* If we want to create another link with a different \c TruthValue, but between
* the versions of x and y that are under the dummy context (instead of the
* original \c TruthValues) then we get:
* 
* \code
*  dc_x_y <- OrderedLink ((ConceptNode "___PLN___") dc_x dc_y)
*  InheritanceLink <0.8, 0.9> [ Context dc_x_y <0.3, 0.5> ]
*      ConceptNode "x" <0.8, 0.9> [ Context dc_x <0.5, 0.5> ]
*      ConceptNode "y" <0.8, 0.9> [ Context dc_y <0.1, 0.5> ]
* \endcode
*
* Then if we want to create yet another with a different truthvalue, we get:
*
* \code
* dc_x_y_2 <- OrderedLink (dc_x_y dc_x dc_y)
* InheritanceLink <0.8, 0.9> (Context dc_x_y_2 <0.777, 0.5>)
*   ConceptNode "x" <0.8, 0.9> (Context dc_x <0.5, 0.5>)
*   ConceptNode "y" <0.8, 0.9> (Context dc_y <0.1, 0.5>)
* \endcode
*
* @todo short-term: intercept FW_VARIABLE_NODES and prevent them from being put in the
* AtomSpace
* @todo long-term: replace FW_VARIABLES_NODES with a string in the Vertex type.
* @todo Normalisation of AtomSpace: EquivalenceLink <-> 2 x ImplicationLinks
* @todo Explicit representation of ContextLinks (since AtomSpaceWrapper
* maps each context TV to a separate Handle) or function to get context atom.
* 
* 	class AtomSpaceWrapper : public iAtomSpaceWrapper
*/
public class AtomSpaceWrapper implements IAtomSpaceWrapper {
	/**
	 * Pointer to the C++ object. Note: 64-bit only.
	 */
	public long handle;
	
	public static native AtomSpaceWrapper getAsw();
	
/**
 */
	    //! How to represent the universe size
	    // CONST_SIZE = constant value
	    // REAL_SIZE  = actual size of knowledge/experience
	    /**
	     * enum USizeMode_t { CONST_SIZE, REAL_SIZE };
	     */

	    //! Universe size
	    //uint USize;
	    //! How the universe size is being calculated
	    /**
	     * USizeMode_t USizeMode;
	     */

	    //! To get around the lack of a fresh=true method in OpenCog, and to allow
	    //! multiple atoms with either the same type, name and/or outgoing set, we
	    //! create a number of dummy PLN contexts, each providing a different
	    //! VersionHandle with which to store multiple TruthValues in an Atom.
	    //! This parameter indicates the name of the dummy contexts that have
	    //! been used so far.
	    /**
	     * std::set<VersionHandle> dummyContexts;
	     */
	    
	    //! This string is the prefix of PLN dummy context root Node
	    /**
	     * const std::string rootContext;
	     */
	    //! The handle of the root context
	    /**
	     * Handle rootContextHandle;
	     */

	    // typedef bimap< unordered_set_of< vhpair >, set_of<Handle> > vhmap_t;
	    // typedef vhmap_t::value_type vhmap_pair_t;
	    //! Bidrectional map with right index being PLN "handle", left a <handle,
	    //! versionhandle> pair.
	    //!
	    //! @todo pHandle (typedefed above) is used to ensure
	    //! distinctness from general OpenCog Handles... which is now defined  
	    //! as a class. In the future. PLN may be adapted to use vhpairs
	    //! directly instead of relying on the AtomSpaceWrapper.
	    /**
	     * typedef std::map< pHandle, vhpair > vhmap_t;
	     */
	    /**
	     * typedef vhmap_t::value_type vhmap_pair_t;
	     */
	    /**
	     * typedef std::map< vhpair, pHandle > vhmap_reverse_t;
	     */
	    /**
	     * typedef vhmap_reverse_t::value_type vhmap_reverse_pair_t;
	     */
	    //! Instead of above bimap stuff, we will temporarily use two normal maps
	    /**
	     * vhmap_t vhmap;
	     */
	    /**
	     * vhmap_reverse_t vhmap_reverse;
	     */

	    /**
	     * Add a tree of non real atoms to AtomSpace.
	     * @param v of atoms to add.
	     * @param vi iterator to start adding atoms from
	     * @param tvn what truth value they should be given
	     * @param tvn what truth value they should be given
	     * @param fresh allows atoms to be added with the same name/outgoing set.
	     *              If fresh == false and the atom already exist then the new
	     *              truth value is merged (via TruthValue::merge) with the old.
	     *              Otherwise (fresh == true) then a new dummy context
	     *              is associated to that new truth value.
	     * @return The pHandle corresponding to the added atom
	     */
	    /**
	     * pHandle addAtom(vtree& v, vtree::iterator vi, const TruthValue& tvn,
	     *              bool fresh);
	     */
	       

	    //! Used by getImportantHandles
	    /**
	     * struct compareSTI {
	        AtomSpace *atomspace;

	        compareSTI(AtomSpace* _a): atomspace(_a) {};

	        //! @warning uses real atomspace handles in comparison
	        bool operator()(const Handle& a, const Handle& b) {
	            return atomspace->getAV(a).getSTI() >
	                atomspace->getAV(b).getSTI();
	        }
	    };
	     */
	    
	    // For monitoring additions to the AtomSpace from outside of PLN
	    /**
	     * bool handleAddSignal(AtomSpaceImpl *as, Handle h); //!< Signal handler for atom adds.
	     * @return
	     */
	    /**
	     * bool handleRemoveSignal(AtomSpaceImpl *as, Handle h); //!< Signal handler for atom removals.
	     */

	    //! Whether AtomSpaceWrapper is listening for AtomSpace signals.
	    /**
	     * bool watchingAtomSpace;
	     */

	    /**
	     * boost::signals::connection c_add; //! Connection to add atom signals
	     */
	    /**
	     * boost::signals::connection c_remove; //! Connection to remove atom signals
	     */

	    /**
	     * inline AtomSpace* getAtomSpace() const { return atomspace; };
	     * @param watching
	     */
		public native AtomSpace getAtomSpace();

	    //! Change whether AtomSpaceWrapper is listening for AtomSpace signals.
	    /**
	     * void setWatchingAtomSpace(bool watching);
	     */
	    //! Whether AtomSpaceWrapper is listening for AtomSpace signals.
	    /**
	     * void isWatchingAtomSpace();
	     * @return
	     */

	    //! Check whether a pHandle is known to the AtomSpaceWrapper
	    /**
	     * bool isValidPHandle(const pHandle h) const;
	     * @return
	     */
	    //! Convert a specific VersionHandled TruthValue to a pln handle
	    // Note that realToFakeHandle cannot be const because it modifies vhmap
	    // Or vhmap should be declared mutable
	    /**
	     * pHandle realToFakeHandle(const Handle h, const VersionHandle vh);
	     * @return
	     */
	    //! Convert a a real handle into a fake handle for each VersionedHandled TV
	    /**
	     * pHandleSeq realToFakeHandle(const Handle hs);
	     * @return
	     */
	    //! Convert real handles to pln pHandleSeq , optionally expanding to
	    //! include every VersionHandled TV in each real handle
	    /**
	     * pHandleSeq realToFakeHandles(const HandleSeq& hs, bool expand=false);
	     * @return
	     */
	    //! Convert real handles to pln pHandleSeq, all under a given context
	    /**
	     * pHandleSeq realToFakeHandles(const HandleSeq& hs, Handle context);
	     */

	    /** Get the pHandle that represents the non-contextual/primary TV the real handle
	     * behind ph.
	     */
	    /**
	     * pHandle getPrimaryFakeHandle(pHandle ph);
	     * @return
	     */

	    /**
	     * vhpair fakeToRealHandle(const pHandle f) const;
	     */

	    //! Which XML files have been loaded by PLN to populate the AtomSpace.
	    //! No longer accessed (but it is updated).
	    /**
	     * std::set<std::string> loadedFiles;
	     * @param logLevel
	     */
	    
	    //! Debug method to display nodes
	    /**
	     * void DumpCoreNodes(int logLevel);
	     * @param logLevel
	     */
	    //! Debug method to display links
	    /**
	     * void DumpCoreLinks(int logLevel);
	     * @param T
	     */
	    //! Debug method to display all atoms of Type T 
	    /**
	     * void DumpCore(Type T);
	     */

	    //! return the size of the universe
	    //! @todo get the universe from the real AtomSpace if USizeMode == REAL_SIZE
	    /**
	     * unsigned int getUniverseSize() const { return USize; }
	     * @param _USizeMode
	     */

	    //! set the universe size (only if USizeMode == CONST_SIZE)
	    /**
	     * void setUniverseSize(USizeMode_t _USizeMode, unsigned int _USize)
	     */

	    //! Get pHandles with type t and name str optionally subtypes as well
	    /**
	     * virtual Btr<std::set<pHandle> > getHandleSet(Type T,
	     *                                           const std::string& name,
	     *                                          bool subclass = false);
	     */
	       
	      
	    //! Get pHandle corresponding to the context-free Handle of node
	    //! with type t and name str
	    /**
	     * pHandle getHandle(Type t,const std::string& str);
	     * @param t
	     * @return
	     */
	    //! Get handle of link with type t and outgoing set 
	    /**
	     * pHandle getHandle(Type t,const pHandleSeq& outgoing);
	     */
	    // helper methods for getHandle
	    /**
	     * inline pHandle getHandle(Type t, pHandle ha) {
	     */
	    /**
	     * inline pHandle getHandle(Type t, pHandle ha, pHandle hb) {
	     */

	    /**
	     * pHandleSeq getOutgoing(const pHandle h);
	     * @return
	     */

	    /**
	     * pHandle getOutgoing(const pHandle h, const int i);
	     * @return
	     */

	    //! Get the incoming set for an atom
	    /**
	     * pHandleSeq getIncoming(const pHandle h);
	     * @return
	     */

	    /**
	     * Type getType(const pHandle h) const;
	     */
	    /**
	     * std::string getName(const pHandle h) const;
	     */

	    //! Reset the AtomSpace
	    /**
	     * void reset();
	     */

	    //! Initialize new AtomSpaceWrapper with const universe size
	    /**
	     * AtomSpaceWrapper(AtomSpace* as);
	     */
	    /**
	     * virtual ~AtomSpaceWrapper();
	     * @return
	     */

	    //! Load axioms from given xml filename
	    /**
	     * bool loadAxioms(const std::string& path);
	     * @return
	     */
	    //! Load other axioms from given xml filename and optionally replace?
	    /**
	     * bool loadOther(const std::string& path, bool replaceOld);
	     */

	    /**
	     * Update the TruthValue of a given pHandle. If the given handle
	     * does not point an atom (UNDEFINED or Type) then an OC_ASSERT is raised.
	     *
	     * @param h Handle to apply the update on
	     * @param tv TruthValue to update
	     * @param fresh If fresh == false then the new truth value is merged
	     *              (via TruthValue::merge) with the old.
	     *              Otherwise (fresh == true) then a new dummy context
	     *              is associated to that new truth value.
	     *
	     * @return The pHandle updated. Note that it is note necessarily
	     *         equal to h because it associate automatically
	     *         a new dummy context to it, which gets translated a different
	     *         pHandle.
	     */
	    /**
	     * pHandle updateTV(pHandle h, const TruthValue& tv, bool fresh);
	     */

	    /**
	     * Add atom from tree vertex
	     * @param tvn what truth value they should be given
	     * @param fresh allows atoms to be added with the same name/outgoing set
	     * @param       If fresh == false then the new truth value is merged
	     *              (via TruthValue::merge) with the old.
	     *              Otherwise (fresh == true) then a new dummy context
	     *              is associated to that new truth value.
	     * @return the pHandle corresponding to the atom added
	     */
	    /**
	     * pHandle addAtom(tree<Vertex>&, const TruthValue& tvn, bool fresh=false);
	     */

	    //! Add link, pure virtual
	    //! @param tvn what truth value they should be given
	    //! @param fresh allows atoms to be added with the same name/outgoing set
	    /**
	     * virtual pHandle addLink(Type T, const pHandleSeq& hs, const TruthValue& tvn,
	     *                      bool fresh=false)=0;
	     */
	       

	    // helper methods for addLink
	    /**
	     * inline pHandle addLink(Type t, pHandle ha, const TruthValue& tvn,
	     *                     bool fresh=false)
	     */
	    /**
	     * inline pHandle addLink(Type t, pHandle ha, pHandle hb,
	     *                     const TruthValue& tvn, bool fresh=false)
	     */
	    /**
	     * inline pHandle addLink(Type t, pHandle ha, pHandle hb, pHandle hc,
	     *                     const TruthValue& tvn, bool fresh=false)
	     */

	    //! Add node, pure virtual
	    //! @param tvn what truth value they should be given
	    //! @param fresh allows atoms to be added with the same name/outgoing set
	    /**
	     * virtual pHandle addNode(Type T, const std::string& name,
	     *                      const TruthValue& tvn, bool fresh=false)=0;
	     */
	       


	    //! Remove fake handle from vhmap and vhmap_reverse
	    /**
	     * void removeFakeHandle(pHandle h);
	     */

	    //! Remove Atom
	    /**
	     * virtual bool removeAtom(pHandle h);
	     * @param number
	     * @return
	     */

	    //! get a number of high STI handles
	    /**
	     * pHandleSeq getImportantHandles(int number);
	     * @param h
	     * @return
	     */

	    //! Whether the handle h has high enough TruthValue to be consider a binary True.
	    //! @todo move to TruthValue classes
	    /**
	     * bool binaryTrue(pHandle h);
	     * @param h
	     * @return
	     */

	    //! @todo Move the below conversion tools in a Converter class
	    
	    //! Wrap h in a NOT_LINK and return that 
	    /**
	     * pHandle invert(pHandle h);
	     * @return
	     */
	    //! Convert from And to Or link
	    /**
	     * pHandle And2OrLink(pHandle& andL, Type _AndLinkType, Type _OrLinkType);
	     * @return
	     */
	    //! Convert from Equivalence to Implication link
	    /**
	     * hpair Equi2ImpLink(pHandle& exL);
	     * @return
	     */
	    //! Convert from Existance to For All link
	    /**
	     * pHandle Exist2ForAllLink(pHandle& exL);
	     * @return
	     */
	    //! Convert from Or to And link
	    /**
	     * pHandle Or2AndLink(pHandle& andL);
	     * @return
	     */
	    //! Convert from And to Or link
	    /**
	     * pHandle And2OrLink(pHandle& andL);
	     * @param h
	     * @param destContexts
	     * @return
	     */
	    
	    /**
	     * Handle getNewContextLink(Handle h, HandleSeq destContexts);
	     */

	    //! Whether to generate CrispTheoremRules for all crisp theorems
	    //! and add them to CrispTheoremRule::thms.
	    /**
	     * bool archiveTheorems;
	     */

	    //! Generate CrispTheoremRules for all crisp theorems in AtomSpace
	    //! and add to CrispTheoremRule::thms.
	    /**
	     * void makeCrispTheorems();
	     * @param p
	     */

	    //! Generate a CrispTheoremRule for crisp theorem pointed to by
	    //! p then and add to CrispTheoremRule::thms.
	    //! A crisp theorem has the format:
	    //! ImplicationLink ( And < tv 1.0 > (...), result )
	    /**
	     * void makeCrispTheorem(pHandle p);
	     * @param h
	     * @param T
	     * @return
	     */

	    //! returns whether the type of h is T or inherits from T
	    /**
	     * bool isSubType(pHandle h, Type T);
	     * @param subT
	     * @param superT
	     * @return
	     */
	    //! returns whether type subT has superT as a parent type
	    /**
	     * bool inheritsType(Type subT, Type superT) const;
	     * @param h
	     * @return
	     */
		public native boolean inheritsType(Type subT, Type superT);

	    //tv_summary_t getTV(pHandle h) const;
	    /**
	     * TruthValuePtr getTV(pHandle h) const;
	     * @param h
	     * @return
	     */
	    /**
	     * strength_t getMean(pHandle h) const;
	     * @param h
	     * @return
	     */
	    /**
	     * confidence_t getConfidence(pHandle h) const;
	     * @param h
	     */

	    /**
	     * void setTV(pHandle h, const TruthValue& tv);
	     * @return
	     */

	    /**
	     * bool isType(const pHandle h) const;
	     */

	    /**
	     * const TimeServer& getTimeServer() const;
	     */

	    /** Retrieve the arity of a given link */
	    /**
	     * int getArity(pHandle) const;
	     * @param t
	     * @return
	     */

	    /**
	     * pHandleSeq filter_type(Type t);
	     * @return
	     */

	    /**
	     * bool equal(const HandleSeq& lhs, const HandleSeq& rhs);
	     * @param A
	     * @param B
	     * @return
	     */
	    /**
	     * bool equal(Handle A, Handle B);
	     * @param hs
	     * @param T
	     * @return
	     */

	    /**
	     * int getFirstIndexOfType(pHandleSeq hs, Type T) const;
	     * @param T
	     * @return
	     */
	    /**
	     * bool symmetricLink(Type T);
	     * @param h
	     * @return
	     */
	    /**
	     * bool isEmptyLink(pHandle h);
	     * @param hs
	     * @return
	     */
	    /**
	     * bool hasFalsum(pHandleSeq hs);
	     * @param Andlink
	     * @param h
	     * @return
	     */
	    /**
	     * bool containsNegation(pHandle Andlink, pHandle h);
	     * @return
	     */

	    //! return the type of the root of _target
	    /**
	     * Type getTypeV(const tree<Vertex>& _target) const;
	     */

	    /**
	     * bool allowFWVarsInAtomSpace;
	     */

	    //for debugging
	    /**
	     * std::string vhmapToString() const;
	     */
	    /**
	     * std::string pHandleToString(pHandle ph) const;
	     */
}
