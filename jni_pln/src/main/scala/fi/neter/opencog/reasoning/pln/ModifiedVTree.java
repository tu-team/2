package fi.neter.opencog.reasoning.pln;

/**
 * From file: PLNUtils.h
 * @author tero
 *
 */
public class ModifiedVTree extends VTree {
    /**
     * pHandle original_handle;
     */
	public native PHandle getHandle();
	public native void setHandle(PHandle handle);

    /**
     * ModifiedVTree()
     */
	public static native ModifiedVTree construct();

    /**
     * ModifiedVTree(const vtree& rhs,
     *            pHandle _original_handle = PHANDLE_UNDEFINED)
     */
	public static native ModifiedVTree construct(VTree rhs, PHandle originalHandle);
}
