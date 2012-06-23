package fi.neter.opencog.atomspace;

import fi.neter.opencog.reasoning.pln.AtomSpaceWrapper;

public class AtomSpace {
	/**
	 * Pointer to the C++ object. Note: 64-bit only.
	 */
	public long handle;
	
	public static native AtomSpace construct();
	
    /**
     * void do_merge_tv(Handle, const TruthValue&);
     */

    /**
     * AtomSpace(void);
     */
    /**
     * Create an atomspace that will send requests to an existing AtomSpace
     * event-loop.
     */
    /**
     * AtomSpace(AtomSpaceAsync& a);
     */

    /**
     * AtomSpace(const AtomSpace&);
     */
    /**
     * ~AtomSpace();
     */

    /**
     * Recursively store the atom to the backing store.
     * I.e. if the atom is a link, then store all of the atoms
     * in its outgoing set as well, recursively.
     * @deprecated Use AtomSpaceAsync::storeAtom in new code.
     */
    /**
     * inline void storeAtom(Handle h) {
     */
    public native void storeAtom(Handle h);

    /**
     * Return the atom with the indicated handle. This method will
     * explicitly use the backing store to obtain an instance of the
     * atom. If an atom corresponding to the handle cannot be found,
     * then an undefined handle is returned. If the atom is found, 
     * then the corresponding atom is guaranteed to have been
     * instantiated in the atomspace.
     * @deprecated Use AtomSpaceAsync::fetchAtom in new code.
     */
    /**
     * inline Handle fetchAtom(Handle h) {
     */
    public native Handle fetchAtom(Handle h);

    /**
     * Use the backing store to load the entire incoming set of the atom.
     * If the flag is true, then the load is done recursively. 
     * This method queries the backing store to obtain all atoms that 
     * contain this one in their outgoing sets. All of these atoms are
     * then loaded into this AtomSpace's AtomTable.
     * @deprecated Use AtomSpaceAsync::fetchIncomingSet in new code.
     */
    /**
     * inline Handle fetchIncomingSet(Handle h, bool recursive) {
     */

    /**
     * Get time server associated with the AtomSpace
     * @deprecated Use AtomSpaceAsync::getTimeServer in new code.
     * @return a const reference to the TimeServer object of this AtomSpace
     */
    /**
     * inline TimeServer& getTimeServer() const {
     */

    /**
     * Get space server associated with the AtomSpace
     * @deprecated Use AtomSpaceAsync::getSpaceServer in new code.
     * @return a reference to the SpaceServer object of this AtomSpace
     */
    /**
     * inline SpaceServer& getSpaceServer() const {
     */

    /**
     * inline AttentionBank& getAttentionBank()
     */

    /**
     * Return the number of atoms contained in the space.
     */
    /**
     * inline int getSize() const
     */

    /**
     * DEPRECATED! Add an atom an optional TruthValue object to the Atom Table
     * This is a deprecated function; do not use it in new code,
     * if at all possible.
     *
     * @param atom the handle of the Atom to be added
     * @param tvn the TruthValue object to be associated to the added
     *        atom. NULL if the own atom's tv must be used.
     * @return Handle referring to atom after it's been added.
     * @deprecated This is a legacy code left-over from when one could
     * have non-real atoms, i.e. those whose handles were
     * less than 500, and indicated types, not atoms.
     * Instead of using that method, one should use
     * addNode or addLink (which is a bit faster too) which is actually called
     * internally by this wrapper.
     */
    /**
     * Handle addRealAtom(const Atom& atom,
     *                 const TruthValue& tvn = TruthValue::NULL_TV());
     */
       

    /**
     * Prints atoms of this AtomSpace to the given output stream.
     * @param output  the output stream where the atoms will be printed.
     * @param type  the type of atoms that should be printed.
     * @param subclass  if true, matches all atoms whose type is
     *              subclass of the given type. If false, matches
     *              only atoms of the exact type.
     */
    /**
     * void print(std::ostream& output = std::cout,
     *         Type type = ATOM, bool subclass = true) const;
     */
       

    /** Add a new node to the Atom Table,
     * if the atom already exists then the old and the new truth value is merged
     * \param t     Type of the node
     * \param name  Name of the node
     * \param tvn   Optional TruthValue of the node. If not provided, uses the DEFAULT_TV (see TruthValue.h) 
     * @deprecated New code should directly use the AtomSpaceAsync::addNode method.
     */
    /**
     * inline Handle addNode(Type t, const std::string& name = "", const TruthValue& tvn = TruthValue::DEFAULT_TV())
     */
    public native Handle addNode(Type t, String name, TruthValue tvn);

    /**
     * Add a new node to the AtomTable. A random 16-character string
     * will be appended to the provided name.
     * @TODO: Later on, the names can include server/time info to decrease
     * the probability of collisions and be more informative.
     **/
    /**
     * Handle addPrefixedNode(Type t, const std::string& prefix = "", const TruthValue& tvn = TruthValue::DEFAULT_TV());
     */

    /**
     * Add a new link to the Atom Table
     * If the atom already exists then the old and the new truth value
     * is merged
     * @param t         Type of the link
     * @param outgoing  a const reference to a HandleSeq containing
     *                  the outgoing set of the link
     * @param tvn       Optional TruthValue of the node. If not
     *                  provided, uses the DEFAULT_TV (see TruthValue.h)
     * @deprecated New code should directly use the AtomSpaceAsync::addLink method.
     */
    /**
     * inline Handle addLink(Type t, const HandleSeq& outgoing,
     *             const TruthValue& tvn = TruthValue::DEFAULT_TV())
     */
       

    /**
     * inline Handle addLink(Type t, Handle h,
     *             const TruthValue& tvn = TruthValue::DEFAULT_TV())
     */
       

    /**
     * inline Handle addLink(Type t, Handle ha, Handle hb,
     *             const TruthValue& tvn = TruthValue::DEFAULT_TV())
     */
       

    /**
     * inline Handle addLink(Type t, Handle ha, Handle hb, Handle hc,
     *             const TruthValue& tvn = TruthValue::DEFAULT_TV())
     */
       

    /**
     * inline Handle addLink(Type t, Handle ha, Handle hb, Handle hc, Handle hd,
     *             const TruthValue& tvn = TruthValue::DEFAULT_TV())
     */
       

    /**
     * inline Handle addLink(Type t, Handle ha, Handle hb, Handle hc, Handle hd, Handle he,
     *             const TruthValue& tvn = TruthValue::DEFAULT_TV())
     */
       

    /**
     * inline Handle addLink(Type t, Handle ha, Handle hb, Handle hc,
     *                    Handle hd, Handle he, Handle hf,
                   const TruthValue& tvn = TruthValue::DEFAULT_TV())
     */
       

    /**
     * inline Handle addLink(Type t, Handle ha, Handle hb, Handle hc,
     *                    Handle hd, Handle he, Handle hf, Handle hg,
                   const TruthValue& tvn = TruthValue::DEFAULT_TV())
     */
       

    /**
     * inline Handle addLink(Type t, Handle ha, Handle hb, Handle hc,
     *                    Handle hd, Handle he, Handle hf, Handle hg,
                          Handle hh,
                   const TruthValue& tvn = TruthValue::DEFAULT_TV())
     */
       

    /**
     * inline Handle addLink(Type t, Handle ha, Handle hb, Handle hc,
     *                       Handle hd, Handle he, Handle hf, Handle hg,
                          Handle hh, Handle hi,
                   const TruthValue& tvn = TruthValue::DEFAULT_TV())
     */
    

    /**
     * Removes an atom from the atomspace
     *
     * @param h The Handle of the atom to be removed.
     * @param recursive Recursive-removal flag; if set, the links in the
     *        incoming set of the atom to be removed will also be
     *        removed.
     * @return True if the Atom for the given Handle was successfully
     *         removed. False, otherwise.
     */
    /**
     * bool removeAtom(Handle h, bool recursive = false) {
     */
    public native boolean removeAtom(Handle h, boolean recursive);

    /**
     * Retrieve from the Atom Table the Handle of a given node
     *
     * @param t     Type of the node
     * @param str   Name of the node
    */
    /**
     * Handle getHandle(Type t, const std::string& str) const {
     */

    /**
     * Retrieve from the Atom Table the Handle of a given link
     * @param t        Type of the node
     * @param outgoing a reference to a HandleSeq containing
     *        the outgoing set of the link.
    */
    /**
     * Handle getHandle(Type t, const HandleSeq& outgoing) const {
     */

    /** Get the atom referred to by Handle h represented as a string. */
    /**
     * std::string atomAsString(Handle h, bool terse = true) const {
     */

    /** Retrieve the name of a given Handle */
    /**
     * std::string getName(Handle h) const {
     */
    public native String getName(Handle h);

    /** Change the Short-Term Importance of a given Handle */
    /**
     * void setSTI(Handle h, AttentionValue::sti_t stiValue) {
     */

    /** Change the Long-term Importance of a given Handle */
    /**
     * void setLTI(Handle h, AttentionValue::lti_t ltiValue) {
     */

    /** Increase the Very-Long-Term Importance of a given Handle by 1 */
    /**
     * void incVLTI(Handle h) {
     */

    /** Decrease the Very-Long-Term Importance of a given Handle by 1 */
    /**
     * void decVLTI(Handle h) {
     */
    
    /** Retrieve the Short-Term Importance of a given Handle */
    /**
     * AttentionValue::sti_t getSTI(Handle h) const {
     */

    /** Retrieve the Long-term Importance of a given AttentionValueHolder */
    /**
     * AttentionValue::lti_t getLTI(Handle h) const {
     */

    /** Retrieve the Very-Long-Term Importance of a given
     * AttentionValueHolder */
    /**
     * AttentionValue::vlti_t getVLTI(Handle h) const {
     */

    /** Retrieve the outgoing set of a given link */
    /**
     * HandleSeq getOutgoing(Handle h) const {
     */

    /** Retrieve a single Handle from the outgoing set of a given link */
    /**
     * Handle getOutgoing(Handle h, int idx) const {
     */

    /** Retrieve the arity of a given link */
    /**
     * int getArity(Handle h) const {
     */

    /** Return whether s is the source handle in a link l */ 
    /**
     * bool isSource(Handle source, Handle link) const {
     */

    /** Retrieve the AttentionValue of a given Handle */
    /**
     * AttentionValue getAV(Handle h) const;
     */

    /** Change the AttentionValue of a given Handle */
    /**
     * void setAV(Handle h, const AttentionValue &av);
     */

    /** Retrieve the type of a given Handle */
    /**
     * Type getType(Handle h) const;
     */
    public native Type getType(Handle h);

    /** Retrieve the TruthValue of a given Handle
     * @note This is an unpleasant hack which is unsafe as it returns a pointer
     * to the AtomSpace TV which may be lost if the Atom the TV belongs to is
     * removed. It's much faster than having to copy the value and use smart
     * pointers though. Garbage collection should solve this.
     */
    /**
     * TruthValuePtr getTV(Handle h, VersionHandle vh = NULL_VERSION_HANDLE) const;
     */
    public native TruthValue getTV(Handle h, VersionHandle vh);

    /**
     * strength_t getMean(Handle h, VersionHandle vh = NULL_VERSION_HANDLE) const;  
     */
    public native float getMean(Handle h, VersionHandle vh);
    /**
     * confidence_t getConfidence(Handle h, VersionHandle vh = NULL_VERSION_HANDLE) const;  
     */
    public native float getConfidence(Handle h, VersionHandle vh);

    /** Change the TruthValue of a given Handle */
    /**
     * void setTV(Handle h, const TruthValue& tv, VersionHandle vh = NULL_VERSION_HANDLE);
     */
    public native void setTV(Handle h, TruthValue tv, VersionHandle vh);

    /** Change the primary TV's mean of a given Handle
     * @note By Joel: this makes no sense to me, how can you generally set a mean
     * across all TV types. I think a better solution would be to remove this
     * enforce the use of setTV.
     */
    /**
     * void setMean(Handle h, float mean) {
     */
    public native void setMean(Handle h, float mean);

    /** Clone an atom from the AtomSpace, replaces the public access to TLB::getAtom
     * that many modules were doing.
     * @param h Handle of atom to clone
     * @return A smart pointer to the atom
     * @note Any changes to the atom object must be committed using
     * AtomSpace::commitAtom for them to be merged with the AtomSpace.
     * Otherwise changes are lost.
     */
    /**
     * boost::shared_ptr<Atom> cloneAtom(const Handle& h) const;
     */

    /** Commit an atom that has been cloned from the AtomSpace.
     *
     * @param a Atom to commit
     * @return whether the commit was successful
     */
    /**
     * bool commitAtom(const Atom& a);
     */

    /**
     * bool isValidHandle(const Handle& h) const;
     */

    /** Retrieve the doubly normalised Short-Term Importance between -1..1
     * for a given Handle. STI above and below threshold normalised separately
     * and linearly.
     *
     * @param h The atom handle to get STI for
     * @param average Should the recent average max/min STI be used, or the
     * exact min/max?
     * @param clip Should the returned value be clipped to -1..1? Outside this
     * range can be returned if average=true
     * @return normalised STI between -1..1
     */
    /**
     * float getNormalisedSTI(Handle h, bool average=true, bool clip=false) const {
     */

    /** Retrieve the linearly normalised Short-Term Importance between 0..1
     * for a given Handle.
     *
     * @param h The atom handle to get STI for
     * @param average Should the recent average max/min STI be used, or the
     * exact min/max?
     * @param clip Should the returned value be clipped to 0..1? Outside this
     * range can be returned if average=true
     * @return normalised STI between 0..1
     */
    /**
     * float getNormalisedZeroToOneSTI(Handle h, bool average=true, bool clip=false) const {
     */

    /** Get hash for an atom */
    /**
     * size_t getAtomHash(const Handle& h) const;
     */

    /**
     * Returns neighboring atoms, following links and returning their
     * target sets.
     * @param h Get neighbours for the atom this handle points to.
     * @param fanin Whether directional links point to this node should be
     * considered.
     * @param fanout Whether directional links point from this node to
     * another should be considered.
     * @param linkType Follow only these types of links.
     * @param subClasses Follow subtypes of linkType too.
     */
    /**
     * HandleSeq getNeighbors(const Handle& h, bool fanin, bool fanout,
     *      Type linkType=LINK, bool subClasses=true) const {
     */
       

    /** Retrieve the incoming set of a given atom */
    /**
     * HandleSeq getIncoming(Handle h) {
     */

    /** Convenience functions... */
    /**
     * bool isNode(const Handle& h) const;
     */
    /**
     * bool isLink(const Handle& h) const;
     */

    /**
     * Gets a set of handles that matches with the given arguments.
     *
     * @param result An output iterator.
     * @param type the type of the atoms to be searched
     * @param name the name of the atoms to be searched.
     *        For searching only links, use "" or a search by type.
     * @param subclass if sub types of the given type are accepted in this search
     * @param vh only atoms that contains versioned TVs with
     *        the given VersionHandle are returned. If NULL_VERSION_HANDLE is given,
     *        it does not restrict the result.
     *
     * @return The set of atoms of a given type (subclasses optionally).
     *
     * @note The matched entries are appended to a container whose
     * OutputIterator is passed as the first argument. Example of a
     * call to this method, which would return all entries in AtomSpace:
     * @code
     *      std::list<Handle> ret;
     *      atomSpace.getHandleSet(back_inserter(ret), ATOM, true);
     * @endcode
     */
    /**
     * template <typename OutputIterator> OutputIterator
     * getHandleSet(OutputIterator result,
                 Type type,
                 const std::string& name,
                 bool subclass = true,
                 VersionHandle vh = NULL_VERSION_HANDLE) const {
     */
    

    /**
     * Returns the set of atoms of a given name (atom type and subclasses
     * optionally).
     *
     * @param result An output iterator.
     * @param name The desired name of the atoms.
     * @param type The type of the atom.
     * @param subclass Whether atom type subclasses should be considered.
     * @param vh only atoms that contains versioned TVs with the given VersionHandle are returned.
     *        If NULL_VERSION_HANDLE is given, it does not restrict the result.
     * @return The set of atoms of the given type and name.
     *
     * @note The matched entries are appended to a container whose
     * OutputIterator is passed as the first argument.
     *
     * @note Example of call to this method, which would return all entries in AtomSpace:
     * @code
     *         std::list<Handle> ret;
     *         atomSpace.getHandleSet(back_inserter(ret), ATOM, true);
     * @endcode
     */
    /**
     * template <typename OutputIterator> OutputIterator
     * getHandleSet(OutputIterator result,
                 const char* name,
                 Type type,
                 bool subclass = true,
                 VersionHandle vh = NULL_VERSION_HANDLE) const {
     */
    

    /**
     * Gets a set of handles that matches with the given type
     * (subclasses optionally).
     *
     * @param result An output iterator.
     * @param type The desired type.
     * @param subclass Whether type subclasses should be considered.
     * @param vh only atoms that contains versioned TVs with the given VersionHandle are returned.
     *        If NULL_VERSION_HANDLE is given, it does not restrict the result.
     *
     * @return The set of atoms of a given type (subclasses optionally).
     *
     * @note The matched entries are appended to a container whose OutputIterator is passed as the first argument.
     *          Example of call to this method, which would return all entries in AtomSpace:
     * @code
     *         std::list<Handle> ret;
     *         atomSpace.getHandleSet(back_inserter(ret), ATOM, true);
     * @endcode
     */
    /**
     * template <typename OutputIterator> OutputIterator
     * getHandleSet(OutputIterator result,
                 Type type,
                 bool subclass = false,
                 VersionHandle vh = NULL_VERSION_HANDLE) const {
     */
    

    /**
     * Returns the set of atoms of a given type which have atoms of a
     * given target type in their outgoing set (subclasses optionally).
     *
     * @param result An output iterator.
     * @param type The desired type.
     * @param targetType The desired target type.
     * @param subclass Whether type subclasses should be considered.
     * @param targetSubclass Whether target type subclasses should be considered.
     * @param vh only atoms that contains versioned TVs with the given VersionHandle are returned.
     *        If NULL_VERSION_HANDLE is given, it does not restrict the result.
     * @param targetVh only atoms whose target contains versioned TVs with the given VersionHandle are returned.
     *        If NULL_VERSION_HANDLE is given, it does not restrict the result.
     * @return The set of atoms of a given type and target type (subclasses
     * optionally).
     *
     * @note The matched entries are appended to a container whose OutputIterator is passed as the first argument.
     *          Example of call to this method, which would return all entries in AtomSpace:
     * @code
     *         std::list<Handle> ret;
     *         atomSpace.getHandleSet(back_inserter(ret), ATOM, true);
     * @endcode
     */
    /**
     * template <typename OutputIterator> OutputIterator
     * getHandleSet(OutputIterator result,
                 Type type,
                 Type targetType,
                 bool subclass,
                 bool targetSubclass,
                 VersionHandle vh = NULL_VERSION_HANDLE,
                 VersionHandle targetVh = NULL_VERSION_HANDLE) const {
     */
    

    /**
     * Returns the set of atoms with a given target handle in their
     * outgoing set (atom type and its subclasses optionally).
     * i.e. returns the incoming set for that handle, but filtered by the Type you specify.
     * Uses a special index, so it's more efficient than filtering it yourself.
     *
     * @param result An output iterator.
     * @param handle The handle that must be in the outgoing set of the atom.
     * @param type The type of the atom.
     * @param subclass Whether atom type subclasses should be considered.
     * @param vh only atoms that contains versioned TVs with the given VersionHandle.
     *        If NULL_VERSION_HANDLE is given, it does not restrict the result.
     * @return The set of atoms of the given type with the given handle in
     * their outgoing set.
     *
     * @note The matched entries are appended to a container whose OutputIterator is passed as the first argument.
     *          Example of call to this method, which would return all entries in AtomSpace:
     * @code
     *         // Handle h == the Handle for your choice of Atom
     *         std::list<Handle> ret;
     *         atomSpace.getHandleSet(back_inserter(ret), h, ATOM, true);
     * @endcode
     */
    /**
     * template <typename OutputIterator> OutputIterator
     * getHandleSet(OutputIterator result,
                 Handle handle,
                 Type type,
                 bool subclass,
                 VersionHandle vh = NULL_VERSION_HANDLE) const {
     */
    

    /**
     * Returns the set of atoms with the given target handles and types
     * (order is considered) in their outgoing sets, where the type and
     * subclasses of the atoms are optional.
     *
     * @param result An output iterator.
     * @param handles An array of handles to match the outgoing sets of the searched
     * atoms. This array can be empty (or each of its elements can be null), if
     * the handle value does not matter or if it does not apply to the
     * specific search.
     * Note that if this array is not empty, it must contain "arity" elements.
     * @param types An array of target types to match the types of the atoms in
     * the outgoing set of searched atoms.
     * @param subclasses An array of boolean values indicating whether each of the
     * above types must also consider subclasses. This array can be null,
     * what means that subclasses will not be considered. Note that if this
     * array is not null, it must contains "arity" elements.
     * @param arity The length of the outgoing set of the atoms being searched.
     * @param type The type of the atom.
     * @param subclass Whether atom type subclasses should be considered.
     * @param vh only atoms that contains versioned TVs with the given
     * VersionHandle are returned. If NULL_VERSION_HANDLE is given, it does not
     * restrict the result.
     * @return The set of atoms of the given type with the matching
     * criteria in their outgoing set.
     *
     * @note The matched entries are appended to a container whose OutputIterator
     * is passed as the first argument. Example of call to this method, which
     * would return all entries in AtomSpace:
     * @code
     *     std::list<Handle> ret;
     *     atomSpace.getHandleSet(back_inserter(ret), ATOM, true);
     * @endcode
     */
    /**
     * template <typename OutputIterator> OutputIterator
     * getHandleSet(OutputIterator result,
                 const HandleSeq& handles,
                 Type* types,
                 bool* subclasses,
                 Arity arity,
                 Type type,
                 bool subclass,
                 VersionHandle vh = NULL_VERSION_HANDLE) const {
     */
    

    /**
     * Returns the set of atoms whose outgoing set contains at least one
     * atom with the given name and type (atom type and subclasses
     * optionally).
     *
     * @param result An output iterator.
     * @param targetName The name of the atom in the outgoing set of the searched
     * atoms.
     * @param targetType The type of the atom in the outgoing set of the searched
     * atoms.
     * @param type type of the atom.
     * @param subclass Whether atom type subclasses should be considered.
     * @param vh return only atoms that contains versioned TVs with the given
     * VersionHandle.  If NULL_VERSION_HANDLE is given, it does not restrict
     * the result.
     * @return The set of atoms of the given type and name whose outgoing
     * set contains at least one atom of the given type and name.
     *
     * @note The matched entries are appended to a container whose
     * OutputIterator is passed as the first argument.  Example of call to this
     * method, which would return all entries in AtomSpace:
     *
     * @code
     * std::list<Handle> ret;
     * atomSpace.getHandleSet(back_inserter(ret), ATOM, true);
     * @endcode
     */
    /**
     * template <typename OutputIterator> OutputIterator
     * getHandleSet(OutputIterator result,
                 const char* targetName,
                 Type targetType,
                 Type type,
                 bool subclass,
                 VersionHandle vh = NULL_VERSION_HANDLE,
                 VersionHandle targetVh = NULL_VERSION_HANDLE) const {
     */
    

    /**
     * Returns the set of atoms with the given target names and/or types
     * (order is considered) in their outgoing sets, where the type
     * and subclasses arguments of the searched atoms are optional.
     *
     * @param result An output iterator.
     * @param names An array of names to match the outgoing sets of the searched
     * atoms. This array (or each of its elements) can be null, if
     * the names do not matter or if do not apply to the specific search.
     * Note that if this array is not null, it must contain "arity" elements.
     * @param types An array of target types to match the types of the atoms in
     * the outgoing set of searched atoms. If array of names is not null,
     * this parameter *cannot* be null as well. Besides, if an element in a
     * specific position in the array of names is not null, the corresponding
     * type element in this array *cannot* be NOTYPE as well.
     * @param subclasses An array of boolean values indicating whether each of the
     * above types must also consider subclasses. This array can be null,
     * what means that subclasses will not be considered. Not that if this
     * array is not null, it must contains "arity" elements.
     * @param arity The length of the outgoing set of the atoms being searched.
     * @param type The optional type of the atom.
     * @param subclass Whether atom type subclasses should be considered.
     * @param vh return only atoms that contains versioned TVs with the given VersionHandle.
     *        If NULL_VERSION_HANDLE is given, it does not restrict the result.
     * @return The set of atoms of the given type with the matching
     * criteria in their outgoing set.
     *
     * @note The matched entries are appended to a container whose OutputIterator is passed as the first argument.
     *          Example of call to this method, which would return all entries in AtomSpace:
     * @code
     *         std::list<Handle> ret;
     *         atomSpace.getHandleSet(back_inserter(ret), ATOM, true);
     * @endcode
     */
    /**
     * template <typename OutputIterator> OutputIterator
     * getHandleSet(OutputIterator result,
                 const char** names,
                 Type* types,
                 bool* subclasses,
                 Arity arity,
                 Type type,
                 bool subclass,
                 VersionHandle vh = NULL_VERSION_HANDLE) const {
     */
    

    /**
     * Returns the set of atoms with the given target names and/or types
     * (order is considered) in their outgoing sets, where the type
     * and subclasses arguments of the searched atoms are optional.
     *
     * @param result An output iterator.
     * @param types An array of target types to match the types of the atoms in
     * the outgoing set of searched atoms. This parameter can be null (or any of
     * its elements can be NOTYPE), what means that the type doesnt matter.
     * Not that if this array is not null, it must contains "arity" elements.
     * @param subclasses An array of boolean values indicating whether each of the
     * above types must also consider subclasses. This array can be null,
     * what means that subclasses will not be considered. Not that if this
     * array is not null, it must contains "arity" elements.
     * @param arity The length of the outgoing set of the atoms being searched.
     * @param type The optional type of the atom.
     * @param subclass Whether atom type subclasses should be considered.
     * @param vh returns only atoms that contains versioned TVs with the given
     * VersionHandle.  If NULL_VERSION_HANDLE is given, it does not restrict
     * the result.
     * @return The set of atoms of the given type with the matching
     * criteria in their outgoing set.
     *
     * @note The matched entries are appended to a container whose
     * OutputIterator is passed as the first argument.  Example of call to this
     * method, which would return all entries in AtomSpace:
     *
     * @code
     * std::list<Handle> ret;
     * atomSpace.getHandleSet(back_inserter(ret), ATOM, true);
     * @endcode
     */
    /**
     * template <typename OutputIterator> OutputIterator
     * getHandleSet(OutputIterator result,
                 Type* types,
                 bool* subclasses,
                 Arity arity,
                 Type type,
                 bool subclass,
                 VersionHandle vh = NULL_VERSION_HANDLE) const {
     */
    

    /**
     * Gets a set of handles in the Attentional Focus that matches with the given type
     * (subclasses optionally).
     *
     * @param result An output iterator.
     * @param type The desired type.
     * @param subclass Whether type subclasses should be considered.
     * @param vh returns only atoms that contains versioned TVs with the given VersionHandle.
     *        If NULL_VERSION_HANDLE is given, it does not restrict the result.
     *
     * @return The set of atoms of a given type (subclasses optionally).
     *
     * @note The matched entries are appended to a container whose
     * OutputIterator is passed as the first argument.  Example of call to this
     * method, which would return all entries in AtomSpace in the
     * AttentionalFocus:
     * @code
     *         std::list<Handle> ret;
     *         atomSpace.getHandleSet(back_inserter(ret), ATOM, true);
     * @endcode
     */
    /**
     * template <typename OutputIterator> OutputIterator
     * getHandleSetInAttentionalFocus(OutputIterator result,
                 Type type,
                 bool subclass,
                 VersionHandle vh = NULL_VERSION_HANDLE) const
     */
    

    /**
     * Gets a set of handles that matches with the given type
     * (subclasses optionally) and a given criterion.
     *
     * @param result An output iterator.
     * @param type The desired type.
     * @param subclass Whether type subclasses should be considered.
     * @param compare A criterion for including atoms. It must be something
     * that returns a bool when called.
     * @param vh returns only atoms that contains versioned TVs with the given
     * VersionHandle.  If NULL_VERSION_HANDLE is given, it does not restrict
     * the result.
     *
     * @return The set of atoms of a given type (subclasses optionally).
     *
     * @note The matched entries are appended to a container whose
     * OutputIterator is passed as the first argument.  Example of call to this
     * method, which would return all entries in AtomSpace beyond 500 LTI:
     * @code
     *         std::list<Handle> ret;
     *         atomSpace.getHandleSet(back_inserter(ret), ATOM, true, LTIAboveThreshold(500));
     * @endcode
     */
    /**
     * template <typename OutputIterator> OutputIterator
     * getHandleSetFiltered(OutputIterator result,
                 Type type,
                 bool subclass,
                 AtomPredicate* compare,
                 VersionHandle vh = NULL_VERSION_HANDLE) const {
     */
    

    /**
     * Gets a set of handles that matches with the given type
     * (subclasses optionally), sorted according to the given comparison
     * structure.
     *
     * @param result An output iterator.
     * @param type The desired type.
     * @param subclass Whether type subclasses should be considered.
     * @param compare The comparison struct to use in the sort.
     * @param vh returns only atoms that contains versioned TVs with the given
     * VersionHandle.  If NULL_VERSION_HANDLE is given, it does not restrict
     * the result.
     *
     * @return The set of atoms of a given type (subclasses optionally).
     *
     * @note The matched entries are appended to a container whose
     * OutputIterator is passed as the first argument.  Example of call to this
     * method, which would return all entries in AtomSpace, sorted by STI:
     * @code
     *         std::list<Handle> ret;
     *         AttentionValue::STISort stiSort;
     *         atomSpace.getHandleSet(back_inserter(ret), ATOM, true, stiSort);
     * @endcode
     */
    /**
     * template <typename OutputIterator, typename Compare> OutputIterator
     * getSortedHandleSet(OutputIterator result,
                 Type type,
                 bool subclass,
                 Compare compare,
                 VersionHandle vh = NULL_VERSION_HANDLE) const {
     */
    


    /**
     * Decays STI of all atoms (one cycle of importance decay).  Deprecated,
     * importance updating should be done by ImportanceUpdating Agent.
     * @deprecated ECAN should be used, but this method is still used by
     * embodiment.
     */
    /**
     * void decayShortTermImportance();
     */

    /** Get attentional focus boundary
     * Generally atoms below this threshold shouldn't be accessed unless search
     * methods are unsuccessful on those that are above this value.
     *
     * @return Short Term Importance threshold value
     */
    /**
     * AttentionValue::sti_t getAttentionalFocusBoundary() const;
     */

    /** Change the attentional focus boundary.
     * Some situations may benefit from less focussed searches.
     *
     * @param s New threshold
     * @return Short Term Importance threshold value
     */
    /**
     * AttentionValue::sti_t setAttentionalFocusBoundary(
     *  AttentionValue::sti_t s);
     */
       

    /** Get the maximum STI observed in the AtomSpace.
     * @param average If true, return an exponentially decaying average of
     * maximum STI, otherwise return the actual maximum.
     * @return Maximum STI
     */
    /**
     * AttentionValue::sti_t getMaxSTI(bool average=true)
     */

    /** Get the minimum STI observed in the AtomSpace.
     *
     * @param average If true, return an exponentially decaying average of
     * minimum STI, otherwise return the actual maximum.
     * @return Minimum STI
     */
    /**
     * AttentionValue::sti_t getMinSTI(bool average=true)
     */

    /** Update the minimum STI observed in the AtomSpace.
     * Min/max are not updated on setSTI because average is calculate by lobe
     * cycle, although this could potentially also be handled by the cogServer.
     *
     * @warning Should only be used by attention allocation system.
     * @param m New minimum STI
     */
    /**
     * void updateMinSTI(AttentionValue::sti_t m)
     */

    /**
     * Update the maximum STI observed in the AtomSpace. Min/max are not updated
     * on setSTI because average is calculate by lobe cycle, although this could
     * potentially also be handled by the cogServer.
     *
     * @warning Should only be used by attention allocation system.
     * @param m New maximum STI
     */
    /**
     * void updateMaxSTI(AttentionValue::sti_t m)
     */

    /**
     * int Nodes(VersionHandle = NULL_VERSION_HANDLE) const;
     */
    /**
     * int Links(VersionHandle = NULL_VERSION_HANDLE) const;
     */

    //! Clear the atomspace, remove all atoms
    /**
     * void clear();
     */
    public native void clear();
}
