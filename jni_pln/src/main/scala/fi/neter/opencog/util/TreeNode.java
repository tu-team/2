package fi.neter.opencog.util;

/**
 * From file: tree.h
 * @author tero
 *
 */
public class TreeNode<T> {
	/**
	 * tree_node_<T> *parent;
	 */
	public native TreeNode<T> getParent();
	public native void setParent(TreeNode<T> parent);
	
	/**
	 * tree_node_<T> *first_child, *last_child;
	 */
	public native TreeNode<T> getFirstChild();
	public native TreeNode<T> getLastChild();
	public native void setFirstChild(TreeNode<T> child);
	public native void setLastChild(TreeNode<T> child);
	
	/**
	 * tree_node_<T> *prev_sibling, *next_sibling;
	 */
	public native TreeNode<T> getPrevSibling();
	public native TreeNode<T> getNextSibling();
	public native void setPrevSibling(TreeNode<T> prev);
	public native void setNextSibling(TreeNode<T> next);
	
	/**
	 * T data;
	 */
	public native T getData();
	public native void setData(T data);
}
