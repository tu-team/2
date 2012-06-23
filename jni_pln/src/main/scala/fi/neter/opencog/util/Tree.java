package fi.neter.opencog.util;

/**
 * From file: Tree.h
 * @author tero
 *
 */
public class Tree<T> {

	/**
	 * tree();
	 * @param <U>
	 * @return
	 */
	public static native <T> Tree<T> construct(Class<T> clazz);
	
	
	/**
	 * explicit tree(const T&);
	 * @return
	 */
	public static native <T> Tree<T> construct(T t);

	/**
	 * explicit tree(const iterator_base&);
	 */
	public static native <T> Tree<T> construct(IteratorBase<T> base);
	
	/**
	 * tree(const tree<T, tree_node_allocator>&);
	 */
	public static native <T> Tree<T> construct(Tree<T> tree);

	public native void destroy();
	
	/**
	 * tree<T, tree_node_allocator>& operator=(const tree<T, tree_node_allocator>&);
	 * @author tero
	 *
	 */
	public native Tree<T> operatorSet(Tree<T> tree);

	/// Base class for iterators, only pointers stored, no traversal logic.
	public static class IteratorBase<T>
	{
		/**
		 * iterator_base();
		 */
		public native IteratorBase<T> construct();
		
		/**
		 * iterator_base(tree_node *);
		 */
		public native IteratorBase<T> construct(TreeNode<T> treeNode);

		/**
		 * T&             operator*() const;
		 */
		public native T operatorMultiply();
		
		/**
		 * T*             operator->() const;
		 */
		public native T operatorDereference();

		
		/**
		 * /// When called, the next increment/decrement skips children of this node.
		 * void         skip_children();
		 */
		
		
		/**
		 * /// Number of children of the node pointed to by the iterator.
		 * unsigned int number_of_children() const;
		 * @return
		 */

		/**
		 * bool is_childless() const
		 * @return
		 */
			    
		/**
		 * bool has_one_child() const
		 * @return
		 */

		/**
		 * sibling_iterator begin() const;
		 * @return
		 */
		
		/**
		 * sibling_iterator end() const;
		 * @return
		 */
		
		/**
		 * sibling_iterator last_child() const;
		 */

		/**
		 * tree_node *node;
		 * @return
		 */

		/**
		 * sibling_iterator find_child(const T& t) const
		 */
	}

	/**
	 * 	/// Depth-first iterator, first accessing the node, then its children.
	 * class pre_order_iterator : public iterator_base {
	 */
	public static class PreOrderIterator<T> extends IteratorBase<T> {
		/**
		 * pre_order_iterator();
		 */
		
		/**
		 * pre_order_iterator(tree_node *);
		 */
		
		/**
		 * pre_order_iterator(const iterator_base&);
		 */
		
		/**
		 * pre_order_iterator(const sibling_iterator&);
		 */

		/**
		 * bool    operator==(const pre_order_iterator&) const;
		 */

		/**
		 * bool    operator!=(const pre_order_iterator&) const;
		 */
		
		/**
		 * pre_order_iterator&  operator++();
		 */
		
		/**
		 * pre_order_iterator&  operator--();
		 */
		
		/**
		 * pre_order_iterator   operator++(int);
		 */
		
		/**
		 * pre_order_iterator   operator--(int);
		 */
		
		/**
		 * pre_order_iterator&  operator+=(unsigned int);
		 */
		
		/**
		 * pre_order_iterator&  operator-=(unsigned int);
		 */
	};

	
	/**
	 * /// Depth-first iterator, first accessing the children, then the node itself.
	 * class post_order_iterator : public iterator_base {
	 */
	public static class PostOrderIterator<T> extends IteratorBase<T> {
		
		/**
		 * post_order_iterator();
		 */
		
		/**
		 * post_order_iterator(tree_node *);
		 */
		
		/**
		 * post_order_iterator(const iterator_base&);
		 */
		
		/**
		 * post_order_iterator(const sibling_iterator&);
		 */

		/**
		 * bool    operator==(const post_order_iterator&) const;
		 */
		
		/**
		 * bool    operator!=(const post_order_iterator&) const;
		 */
		
		/**
		 * post_order_iterator&  operator++();
		 */
		
		/**
		 * post_order_iterator&  operator--();
		 */
		
		/**
		 * post_order_iterator   operator++(int);
		 */
		
		/**
		 * post_order_iterator   operator--(int);
		 */
		
		/**
		 * post_order_iterator&  operator+=(unsigned int);
		 */
		
		/**
		 * post_order_iterator&  operator-=(unsigned int);
		 */

		/**
		 * void descend_all();
		 * /// Set iterator to the first child as deep as possible down the tree.
		 */
	};

	
	/**
	 * /// Breadth-first iterator, using a queue
	 * class breadth_first_queued_iterator : public iterator_base {
	 */
	public static class BreadthFirstQueuedIterator<T> extends IteratorBase<T> {
		/**
		 * breadth_first_queued_iterator();
		 */
		/**
		 * breadth_first_queued_iterator(tree_node *);
		 */
		/**
		 * breadth_first_queued_iterator(const iterator_base&);
		 */

		/**
		 * bool    operator==(const breadth_first_queued_iterator&) const;
		 */
		/**
		 * bool    operator!=(const breadth_first_queued_iterator&) const;
		 */
		/**
		 * breadth_first_queued_iterator&  operator++();
		 */
		/**
		 * breadth_first_queued_iterator   operator++(int);
		 */
		/**
		 * breadth_first_queued_iterator&  operator+=(unsigned int);
		 */
	}

	/// The default iterator types throughout the tree class.
	/**
	 * typedef pre_order_iterator            iterator;
	 */
	public static class Iterator<T> extends PreOrderIterator<T> {}
	/**
	 * typedef breadth_first_queued_iterator breadth_first_iterator;
	 * @author tero
	 *
	 */
	public static class BreadthFirstIterator<T> extends BreadthFirstQueuedIterator<T> {}

	
	/**
	 * /// Iterator which traverses only the nodes at a given depth from the root.
	 * class fixed_depth_iterator : public iterator_base {
	 */
	public static class FixedDepthIterator<T> extends IteratorBase<T> {
		/**
		 * fixed_depth_iterator();
		 */
		/**
		 * fixed_depth_iterator(tree_node *);
		 */
		/**
		 * fixed_depth_iterator(const iterator_base&);
		 */
		/**
		 * fixed_depth_iterator(const sibling_iterator&);
		 */
		/**
		 * fixed_depth_iterator(const fixed_depth_iterator&);
		 */

		/**
		 * bool    operator==(const fixed_depth_iterator&) const;
		 */
		/**
		 * bool    operator!=(const fixed_depth_iterator&) const;
		 */
		/**
		 * fixed_depth_iterator&  operator++();
		 */
		/**
		 * fixed_depth_iterator&  operator--();
		 */
		/**
		 * fixed_depth_iterator   operator++(int);
		 */
		/**
		 * fixed_depth_iterator   operator--(int);
		 */
		/**
		 * fixed_depth_iterator&  operator+=(unsigned int);
		 */
		/**
		 * fixed_depth_iterator&  operator-=(unsigned int);
		 * @author tero
		 *
		 */
		        
		/**
		 * tree_node *first_parent_;
		 */
	}

	/**
	 * /// Iterator which traverses only the nodes which are siblings of each other.
	 * class sibling_iterator : public iterator_base {
	 */
	public static class SiblingIterator<T> extends IteratorBase<T> {

		/**
		 * sibling_iterator();
		 */
		/**
		 * sibling_iterator(tree_node *);
		 */
		/**
		 * sibling_iterator(const sibling_iterator&);
		 */
		/**
		 * sibling_iterator(const iterator_base&);
		 */

		/**
		 * bool    operator==(const sibling_iterator&) const;
		 */
		/**
		 * bool    operator!=(const sibling_iterator&) const;
		 */
		/**
		 * sibling_iterator&  operator++();
		 */
		/**
		 * sibling_iterator&  operator--();
		 */
		/**
		 * sibling_iterator   operator++(int);
		 */
		/**
		 * sibling_iterator   operator--(int);
		 */
		/**
		 * sibling_iterator&  operator+=(unsigned int);
		 */
		/**
		 * sibling_iterator&  operator-=(unsigned int);
		 */
	}

	
	/**
	 * /// Iterator which traverses only the leaves.
	 * class leaf_iterator : public iterator_base {
	 */
	public static class LeafIterator<T> extends IteratorBase<T> {
		/**
		 * leaf_iterator();
		 */
		/**
		 * leaf_iterator(tree_node *);
		 */
		/**
		 * leaf_iterator(const sibling_iterator&);
		 */
		/**
		 * leaf_iterator(const iterator_base&);
		 */

		/**
		 * bool    operator==(const leaf_iterator&) const;
		 */
		/**
		 * bool    operator!=(const leaf_iterator&) const;
		 */
		/**
		 * leaf_iterator&  operator++();
		 */
		/**
		 * leaf_iterator&  operator--();
		 */
		/**
		 * leaf_iterator   operator++(int);
		 */
		/**
		 * leaf_iterator   operator--(int);
		 * @author tero
		 *
		 */
		/**
		 * leaf_iterator&  operator+=(unsigned int);
		 */
		/**
		 * leaf_iterator&  operator-=(unsigned int);
		 */
	}
	
	
	/**
	 * /// Iterator which traverses upwards from a node to the root of the tree
	 * class upwards_iterator : public iterator_base {
	 */
	public static class UpwardsIterator<T> extends IteratorBase<T> {
		
		/**
		 * upwards_iterator();
		 */
		/**
		 * upwards_iterator(tree_node *);
		 */
		/**
		 * upwards_iterator(const sibling_iterator&);
		 */
		/**
		 * upwards_iterator(const iterator_base&);
		 */

		/**
		 * bool    operator==(const upwards_iterator&) const;
		 */
		/**
		 * bool    operator!=(const upwards_iterator&) const;
		 */
		/**
		 * upwards_iterator&  operator++();
		 */
		/**
		 * upwards_iterator   operator++(int);
		 */
		/**
		 * upwards_iterator&  operator+=(unsigned int);
		 */
		/**
		 * upwards_iterator&  operator-=(unsigned int);
		 */
	}

	/// Return iterator to the beginning of the tree.
	/**
	 * inline pre_order_iterator   begin() const;
	 */
	/// Return iterator to the end of the tree.
	/**
	 * inline pre_order_iterator   end() const;
	 * @return
	 */
	/// Return post-order iterator to the beginning of the tree.
	/**
	 * post_order_iterator  begin_post() const;
	 * @return
	 */
	/// Return post-order iterator to the end of the tree.
	/**
	 * post_order_iterator  end_post() const;
	 * @return
	 */
	/// Return fixed-depth iterator to the first node at a given depth.
	/**
	 * fixed_depth_iterator begin_fixed(const iterator_base&, unsigned int) const;
	 */
	/// Return fixed-depth iterator to end of the nodes at given depth.
	/**
	 * fixed_depth_iterator end_fixed(const iterator_base&, unsigned int) const;
	 */
	/// Return breadth-first iterator to the first node at a given depth.
	/**
	 * breadth_first_queued_iterator begin_breadth_first() const;
	 */
	/// Return breadth-first iterator to end of the nodes at given depth.
	/**
	 * breadth_first_queued_iterator end_breadth_first() const;
	 * @return
	 */
	/// Return sibling iterator to the first child of given node.
	/**
	 * sibling_iterator     begin(const iterator_base&) const;
	 * @return
	 */
	/// Return sibling iterator to the end of the children of a given node.
	/**
	 * sibling_iterator     end(const iterator_base&) const;
	 * @return
	 */
	/// Return leaf iterator to the first leaf of the tree.
	/**
	 * leaf_iterator   begin_leaf() const;
	 */
	/// Return leaf iterator to the last leaf of the tree.
	/**
	 * leaf_iterator   end_leaf() const;
	 */

	/// Return upwards iterator for the given node
	/**
	 * template<typename iter>
	 * upwards_iterator begin_upwards(iter it) const { return upwards_iterator(it); }
	 * @return
	 */
	
	/// Return upwards iterator to above the root of the tree.
	/**
	 * upwards_iterator end_upwards() const { return upwards_iterator(NULL); }
	 */

	/// Return iterator to the parent of a node.
	/// If no parent return a NULL iter
	/**
	 * template<typename iter> iter parent(iter) const;
	 */
	/// Return iterator to the last child of a node.
	/**
	 * template<typename	iter> iter last_child(iter) const;
	 */
	/// Return iterator to the previous sibling of a node.
	/**
	 * template<typename iter> iter previous_sibling(iter) const;
	 */
	/// Return iterator to the next sibling of a node.
	/**
	 * template<typename iter> iter next_sibling(iter) const;
	 */
	/// Return iterator to the next node at a given depth.
	/**
	 * template<typename iter> iter next_at_same_depth(iter) const;
	 */

	/// Erase all nodes of the tree.
	/**
	 * void     clear();
	 */
	/// Erase element at position pointed to by iterator (plus its children), return incremented iterator.
	/**
	 * template<typename iter> iter erase(iter);
	 */
	/// Erase all children of the node pointed to by iterator.
	/**
	 * void     erase_children(const iterator_base&);
	 */

	/// Insert empty node as last/first child of node pointed to by position.
	/**
	 * template<typename iter> iter append_child(iter position);
	 * @param position
	 * @return
	 */
	/**
	 * template<typename iter> iter prepend_child(iter position);
	 */
	/// Inserts n empty nodes as last/first children of node pointed to by position.
	/**
	 * template<typename iter> iter append_children(iter position,int n);
	 */
	/**
	 * template<typename iter> iter prepend_children(iter position,int n);
	 */
	/// Insert node as last/first child of node pointed to by position.
	/**
	 * template<typename iter> iter append_child(iter position, const T& x);
	 */
	/**
	 * template<typename iter> iter prepend_child(iter position, const T& x);
	 */
	/// Insert n copies of node as last/first children of node pointed to by position.
	/**
	 * template<typename iter> iter append_children(iter position, const T& x,int n);
	 */
	/**
	 * template<typename iter> iter prepend_children(iter position, const T& x,int n);
	 */
	/// Append the node (plus its children) at other_position as last/first child of position.
	/**
	 * template<typename iter> iter append_child(iter position, iter other_position);
	 */
	/**
	 * template<typename iter> iter prepend_child(iter position, iter other_position);
	 */
	/// Append the nodes in the from-to range (plus their children) as last/first children of position.
	/**
	 * template<typename iter> iter append_children(iter position, sibling_iterator from, sibling_iterator to);
	 */
	/**
	 * template<typename iter> iter prepend_children(iter position, sibling_iterator from, sibling_iterator to);
	 * @param position
	 * @param from
	 * @param to
	 * @return
	 */

	/// Short-hand to insert topmost node in otherwise empty tree.
	/**
	 * pre_order_iterator set_head(const T& x);
	 */
	/// Insert node as previous sibling of node pointed to by position.
	/**
	 * template<typename iter> iter insert(iter position, const T& x);
	 * @param position
	 * @return
	 */
	/// Specialisation of previous member.
	/**
	 * sibling_iterator insert(sibling_iterator position, const T& x);
	 */
	/// Insert node (with children) pointed to by subtree as previous sibling of node pointed to by position.
	/**
	 * template<typename iter> iter insert_subtree(iter position, const iterator_base& subtree);
	 */
	/// Insert node (with children) pointed to by subtree as next sibling of node pointed to by position.
	/**
	 * template<typename iter> iter insert_subtree_after(iter position, const iterator_base& subtree);
	 * @param position
	 * @return
	 */
	/// Insert node as next sibling of node pointed to by position.
	/**
	 * template<typename iter> iter insert_after(iter position, const T& x);
	 */
	/// Insert node above position (below parent if it exists); returns new node
	/**
	 * template<typename iter> iter insert_above(iter position, const T& x);
	 */

	/// Replace node at 'position' with other node (keeping same children); 'position' becomes invalid.
	/**
	 * template<typename iter> iter replace(iter position, const T& x);
	 */
	/// Replace node at 'position' with subtree starting at 'from' (do not erase subtree at 'from'); see above.
	/**
	 * template<typename iter> iter replace(iter position, const iterator_base& from);
	 * @param position
	 * @return
	 */
	/// Replace string of siblings (plus their children) with copy of a new string (with children); see above
	/**
	 * sibling_iterator replace(sibling_iterator orig_begin, sibling_iterator orig_end,
	 * 	sibling_iterator new_begin,  sibling_iterator new_end);
	 */
		

	/// Move all children of node at 'position' to be siblings (after position), returns position.
	/**
	 * template<typename iter> iter flatten(iter position);
	 */
	/// Move nodes in range to be children of 'position' (end is not included, otherwise it would be called last).
	/**
	 * template<typename iter> iter reparent(iter position, sibling_iterator begin, sibling_iterator end);
	 */
	/// Move all child nodes of 'from' to be children of 'position'.
	/**
	 * template<typename iter> iter reparent(iter position, iter from);
	 */

	/// Replace node with a new node, making the old node a child of the new node.
	/**
	 * template<typename iter> iter wrap(iter position, const T& x);
	 */

	/// Move 'source' node (plus its children) to become the next sibling of 'target'.
	/**
	 * template<typename iter> iter move_after(iter target, iter source);
	 */
	/// Move 'source' node (plus its children) to become the previous sibling of 'target'.
	/**
	 * template<typename iter> iter move_before(iter target, iter source);
	 */
	/**
	 * sibling_iterator move_before(sibling_iterator target, sibling_iterator source);
	 */
	/// Move 'source' node (plus its children) to become the node at 'target' (erasing the node at 'target').
	/// Warning : only use if 'source' is not included in the subtree of 'target'
	/**
	 * template<typename iter> iter move_ontop(iter target, iter source);
	 */

	/// Merge with other tree, creating new branches and leaves only if they are not already present.
	/**
	 * void     merge(sibling_iterator, sibling_iterator, sibling_iterator, sibling_iterator,
	 * 	bool duplicate_leaves=false);
	 * @param from
	 * @param to
	 * @param deep
	 */
		
	/// Sort (std::sort only moves values of nodes, this one moves children as well).
	/**
	 * void     sort(sibling_iterator from, sibling_iterator to, bool deep=false);
	 */
	/**
	 * template<class StrictWeakOrdering>
	 * void     sort(sibling_iterator from, sibling_iterator to, StrictWeakOrdering comp, bool deep=false);
	 */
	

	/// Partition (std::partition only moves values of nodes, this one moves children as well).
	/**
	 * template<class Predicate>
	 * sibling_iterator partition(sibling_iterator from, sibling_iterator to,Predicate comp);
	 */
	
	//sorts based on comparison of iterators rather than values
	/**
	 * template<class StrictWeakOrdering>
	 * void     sort_on_subtrees(sibling_iterator from, sibling_iterator to, StrictWeakOrdering comp, bool deep=false);
	 */
	

	/// Compare two ranges of nodes (compares nodes as well as tree structure).
	/**
	 * template<typename iter>
	 * bool     equal(const iter& one, const iter& two, const iter& three) const;
	 * @return
	 */
	
	/**
	 * template<typename iter, class BinaryPredicate>
	 * bool     equal(const iter& one, const iter& two, const iter& three, BinaryPredicate) const;
	 * @return
	 */
	
	/**
	 * template<typename iter>
	 * bool     equal_subtree(const iter& one, const iter& two) const;
	 * @return
	 */
	
	/**
	 * template<typename iter, class BinaryPredicate>
	 * bool     equal_subtree(const iter& one, const iter& two, BinaryPredicate) const;
	 * @return
	 */
	
	/// Extract a new tree formed by the range of siblings plus all their children.
	/**
	 * tree     subtree(sibling_iterator from, sibling_iterator to) const;
	 */
	/**
	 * void     subtree(tree&, sibling_iterator from, sibling_iterator to) const;
	 * @param it
	 */
	/// Exchange the node (plus subtree) with its sibling node (do nothing if no sibling present).
	/**
	 * void     swap(sibling_iterator it);
	 */
	/// Exchange two nodes (plus subtrees)
	/**
	 * void     swap(iterator, iterator);
	 * @return
	 */

	/// Count the total number of nodes.
	/**
	 * int      size() const;
	 * @return
	 */
	/// Count the total number of nodes of the subtree 'it'
	/// the node 'it' is included in the count
	/**
	 * int      subtree_size(const iterator_base& it) const;
	 * @return
	 */
	/// Check if tree is empty.
	/**
	 * bool     empty() const;
	 * @return
	 */
	/// Compute the depth to the root.
	/**
	 * int      depth(const iterator_base&) const;
	 */
	/// Count the number of children of node at position.
	/**
	 * unsigned int number_of_children(const iterator_base&) const;
	 */
	/// Count the number of 'next' siblings of node at iterator.
	/**
	 * unsigned int number_of_siblings(const iterator_base&) const;
	 * @return
	 */
	/// Determine whether node at position is in the subtrees with root in the range.
	/**
	 * bool     is_in_subtree(const iterator_base& position, const iterator_base& begin,
	 * 	const iterator_base& end) const;
	 */
		
	/// Return the first iterator, in pre order, containing it in the children of from
	//pre_order_iterator find_subtree(const iterator_base& it  const iterator_base& from) const;
	/// Return the first iterator, in pre order, containing it between begin and end
	/**
	 * pre_order_iterator find_subtree(const iterator_base& it, const iterator_base& begin,
	 * 	const iterator_base& end) const;
	 */
		
	/// Determine whether the iterator is an 'end' iterator and thus not actually pointing to a node.
	/**
	 * bool     is_valid(const iterator_base&) const;
	 */

	// for debuging
	/**
	 * void validate(const iterator_base& it) const;
	 */
	/**
	 * void validate() const { validate(begin()); }
	 * @return
	 */

	/**
	 * int max_depth(const iterator_base& it) const
	 * @return
	 */
		    
	/**
	 * int max_branching(const iterator_base& it) const
	 */

	/// Determine the index of a node in the range of siblings to which it belongs.
	/**
	 * unsigned int sibling_index(sibling_iterator it) const;
	 * @return
	 */
	/// Inverse of 'index': return the n-th child of the node at position.
	/**
	 * sibling_iterator  child(const iterator_base& position, unsigned int) const;
	 * @author tero
	 *
	 */

	/// Comparator class for iterators (compares pointer values; why doesn't this work automatically?)
	/**
	 * class iterator_base_less {
	 * @return
	 */
	public static class IteratorBaseLess<T> {
		/**
		 * bool operator()(const typename tree<T, tree_node_allocator>::iterator_base& one,
		 * 	const typename tree<T, tree_node_allocator>::iterator_base& two) const
		 */
		public native boolean apply(IteratorBase<T> one, IteratorBase<T> two);
	};
	/**
	 * tree_node *head, *feet;    // head/feet are always dummy; if an iterator points to them it is invalid
	 */
}
