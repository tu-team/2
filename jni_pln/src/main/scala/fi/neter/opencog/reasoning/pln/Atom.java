package fi.neter.opencog.reasoning.pln;

import java.util.List;
import java.util.Map;
import java.util.Set;

import fi.neter.opencog.atomspace.Handle;
import fi.neter.opencog.atomspace.Type;
import fi.neter.opencog.util.Tree;
import fi.neter.opencog.util.Tree.Iterator;


/**
 * From PLNAtom.h
 * 
 * atom structure. An obsolete mess from before vtree.
 * Also called a MetaPredicate or MP in the rules. But not anymore, because
 * MP is now based on vtree.
 *
 * @todo remove need for the atom class in the unification methods.
 */
public class Atom {
	/**
	 * Pointer to the C++ object. Note: 64-bit only.
	 */
	public long handle;

	/**
	 *     mutable pHandle handle;
	 * Handle getHandle() const;
	 */
	public native Handle getHandle();

    /**
     * void setHandle(pHandle h);   
     * @param h
     * @return
     */
	public native void setHandle(Handle handle);
	
    /**
     * mutable std::map<std::string,atom>* bindings;
     */
	public native Map<String, Atom> getBindings();
	public native Map<String, Atom> setBindings();
	
	/**
	 * Type T;
	 */
	public native Type getT();
	public native void setT(Type t);
	
    /**
     * int arity;
     */
	public native int getArity();
	public native void setArity(int arity);
	
    /**
     * std::string name;
     */
	public native String getName();
	public native void setName(String name);
	
    /**
     * std::vector<boost::shared_ptr<atom> > hs;
     */
	public native List<Atom> getHs();
	public native void setHs(List<Atom> hsList);
    
    /**
     * mutable std::set<subst>* forbiddenBindings;
     */
	public native Set<Subst> getForbiddenBindings();
	public native void getForbiddenBindings(Set<Subst> forbiddenBindings);
	
    /**
     * bool operator<(const atom& rhs) const;
     */
	public native boolean lessThan(Atom rhs);
    
	/**
	 * bool operator!=(const atom& rhs) const { return (*this)<rhs || rhs<(*this); }
	 * @param h
	 */
	public native boolean ne(Atom rhs);

	
    /**
     * explicit atom(pHandle h);
     */
	public static native Atom construct(Handle h);
    
	/**
	 * atom();
	 */
	public static native Atom construct();
    
	/**
	 * atom(const atom& rhs);
	 * @param _T
	 */
	public static native Atom construct(Atom rhs);
    
	/**
	 * atom(Type _T, std::string _name);
	 * @param _T
	 * @param _arity
	 */
	public static native Atom construct(Type T, String name);
    
	/**
	 * atom(Type _T, int _arity, ...);
	 * @param _T
	 */
	public static native Atom construct(Type T, int arity);
	
	/**
	 * atom(Type _T, std::vector<boost::shared_ptr<atom> > hs);
	 */
	public static native Atom construct(Type T, List<Atom> hs);
    
	/**
	 * atom(const tree<boost::shared_ptr<atom> >& a,
	 *      tree<boost::shared_ptr<atom> >::iterator parent_node,
	 *      bool root = true);
	 */
	public static native Atom construct(Tree<Atom> a, Iterator<Tree<Atom>> parentNode, boolean root);
       
    /**
     * atom(const vtree& a, vtree::iterator parent_node, bool root = true);
     */
	public static native Atom construct(VTree a, Iterator<VTree> parentNode, boolean root);

    /**
     * ~atom();
     * @return
     */
	public native void destroy();

    /**
     * bool well_formed() const;
     * @param _hs
     */
	public native boolean wellFormed();
    
    /**
     * /// Probably expensive operation.
     * void SetOutgoing(pHandleSeq _hs);
     * @return
     */
	public native void setOutgoing(PHandleSeq hs);
    
    
    /**
     * /// Create a copy of this wrapper into the core, regardless whether this
     * /// was created from a core handle.
     * pHandle attach(iAtomSpaceWrapper* core) const;
     */
	public native PHandle attach(IAtomSpaceWrapper core);

	/**
	 * void detach() const;
	 * @return
	 */
	public native void detach();

    /**
     * /// Copy this as an integer array into the dest array
     * int asIntegerArray(unsigned int* dest, unsigned int patlen, std::map<atom,int,lessatom>& node2pat_id,
     *                    unsigned int& next_free_pat_id, int index = 0) const;
     */
	public native int asIntegerArray(long[] dest, long patlen, Map<Atom, Integer> node2PatId,
			long nextFreePatId, int index);
       
    /**
     * MetaPredicate* getMeta() const;
     * @return
     */
	public native Atom getMeta();
    
    /**
     * bool operator==(const atom& rhs) const;
     * @return
     */
	public native boolean equals(Atom rhs);
	
    /**
     * Type execType() const;
     */
	public native Type execType();
	
    /**
     * std::vector<boost::shared_ptr<atom> > execOutTree() const;
     * @return
     */
    public native List<Atom> execOutTree();
	
	/**
	 * bool matchType(const atom& rhs) const;
	 * @param rhsT
	 * @return
	 */
    public native boolean matchType(Atom rhs);
    
	/**
	 * bool matchType(Type rhsT) const;
	 */
    public native boolean matchType(Type rhsT);
    
	/**
	 * std::string execName() const { return name; }
	 * @return
	 */
    public native String execName();
    
    /**
     * bool containsVar() const;
     * @return
     */
    public native boolean containsVar();
    
	/**
	 * bool containsFWVar() const;
	 * @return
	 */
    public native boolean containsFWVar();

    /**
     * bool forbidLastSubstitution() const;
     */
    public native boolean forbidLastSubstitution();

    /**
     * /// Metapredicate operation
     * bool operator()(pHandle h) const;
     */
    public native boolean apply(PHandle h);

	/**
	 * static bool ValidMetaPredicate(Type T);
	 */
    public native boolean validMetaPredicate(Type t);

    /**
     * static pHandleSeq convertVector(const std::vector<boost::shared_ptr<atom> >& hs,
     *                                 iAtomSpaceWrapper* table);
     */
    public static native PHandleSeq convertVector(List<Atom> hs, IAtomSpaceWrapper table);

    /**
     * void substitute(pHandle rhs, std::string varname);
     */
    public native void substitute(PHandle rhs, String varName);
	
    /**
     * void substitute(pHandle dest, atom src);
     */
    public native void substitute(PHandle rhs, Atom src);
	
    /**
     * void substitute(const atom& dest, const atom& src);
     */
    public native void substitute(Atom dest, Atom src);
    
	/**
	 * void substitute(const atom& rhs, std::string varname);
	 */
    public native void substitute(Atom rhs, String varname);
    
    /**
     * tree< boost::shared_ptr<atom> > maketree() const;
     * @return
     */
    public native Tree<Atom> maketree();
    
    /**
     * vtree makeHandletree(iAtomSpaceWrapper* table, bool fullVirtual=false) const;
     */
    public native VTree makeHandletree(IAtomSpaceWrapper table, boolean fullVirtual);
    
    /**
     * /// Actualize the substitution from bindings, delete forbiddenBindings, on new instance..
     * void getWithActualizedSubstitutions(atom& target) const;
     * @return
     */
    public native void getWithActualizedSubstitution(Atom target);
    
	/**
	 * pHandle bindHandle(iAtomSpaceWrapper* table) const;
	 */
    public native Handle bindHandle(IAtomSpaceWrapper table);

    /**
     * void extractVars(std::set<std::string>& vars) const;
     */
    public native void extractVars(Set<String> vars);
	
    /**
     * void extractFWVars(std::set<std::string>& vars) const;
     */
    public native void extractFWVars(Set<String> vars);
}
