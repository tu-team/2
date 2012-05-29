package fi.neter.opencog.reasoning.pln;

/**
 * From file: iAtomSpaceWrapper.h
 * @author tero
 *
 * struct iAtomSpaceWrapper : public opencog::pln::AtomLookupProvider
 */
public interface IAtomSpaceWrapper {
	
	/**
	 * virtual pHandle addAtom(tree<Vertex>&, const TruthValue& tvn,
	 *                          bool fresh)=0;
	 */
	   
	/**
	 * virtual pHandle addLink(Type T, const pHandleSeq& hs,
	 *                          const TruthValue& tvn, bool fresh=false)=0;
	 */
	   
	/**
	 * virtual pHandle addNode(Type T, const std::string& name,
	 *                          const TruthValue& tvn, bool fresh=false)=0;
	 */
	   
	/**
	 * virtual unsigned int getUniverseSize() const=0;
	 */
}
