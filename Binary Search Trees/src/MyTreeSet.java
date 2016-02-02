import java.util.*;

/**
 * MyTreeSet represents a mathematical set using a Binary Search Tree that is in ascending order. </br>
 * Sets cannot contain duplicates.
 * 
 * <ul>
 *      <li>{@code size()} returns size of the set in constant times </li>
 *      <li>{@code contains()} returns whether the given obj exists in the set </li>
 *      <li>{@code add()} attempts to add the given obj to the set and returns whether it was added or not </li>
 *      <li>{@code remove()} attempts remove the specified obj from the set and returns whether it was removed or not </li>
 *      <li>{@code toString()} returns a string representation of the set in ascending order </li>
 * </ul>
 * 
 * @author Dennis Moon
 * @version Mar 25, 2013
 * 
 * @Revision
 *     Mar 25, 2013 - class created and all methods implemented
 */
public class MyTreeSet<E>
{
	private TreeNode root;
	private int size;
	private TreeDisplay display;

	/**
	 * <b>Constructor: </b>MyTreeSet</br> 
	 * <b>Usage: <b>{@code MyTreeSet<E> set = new MyTreeSet<E>}</br>
	 * -------------------------------</br>
	 * Creates a new null, sets size to 0, and create an internal TreeDisplay. </br>
	 */
	public MyTreeSet()
	{
		root = null;
		size = 0;
		display = new TreeDisplay();

		//wait 1 millisecond when visiting a node
		display.setDelay(1);
	}

	/**
	 * <b>Method: </b>size</br> 
	 * <b>Usage: </b>{@code Set.size()}</br>
	 * -------------------------------</br>
	 * Returns the size. </br>
	 * 
	 * @return size of the set
	 */
	public int size()
	{
		return size;
	}

	/**
	 * <b>Method: </b>contains</br> 
	 * <b>Usage: </b>{@code Set.contains(obj)}</br>
	 * -------------------------------</br>
	 * Returns whether Tree contains the specified element. </br>
	 * 
	 * @param obj to search for
	 * @return true if obj exists
	 */
	public boolean contains(E obj)
	{
		return BSTUtilities.contains(root, (Comparable) obj, display);
	}
	
	/**
	 * <b>Method: </b>add</br> 
	 * <b>Usage: </b>{@code set.add(obj)}</br>
	 * -------------------------------</br>
	 * Adds the given obj, increments size and returns true if obj did not already exist. Returns false otherwise. </br>
	 * 
	 * @param obj to add
	 * @return true if added
	 */
	public boolean add(E obj)
	{
	    if (!contains(obj))
	    {
    		root = BSTUtilities.insert(root, (Comparable) obj, display);
    		size++;
    		display.displayTree(root);
    		return true;
	    }
	    return false;
	}
	
	/**
	 * <b>Method: </b>remvoe</br> 
	 * <b>Usage: </b>{@code }</br>
	 * -------------------------------</br>
	 * Removes the specified obj, decrements size and returns false if obj existed. Returns false otherwise. </br>
	 * 
	 * @param obj to remove
	 * @return true if removed
	 */
	public boolean remove(E obj)
	{
	    if (contains(obj))
	    {
    		root = BSTUtilities.delete(root, (Comparable) obj, display);
    		size--;
    		display.displayTree(root);
    		return true;
	    }
	    return false;
	}

	/**
	 * <b>Method: </b>toString</br> 
	 * <b>Usage: </b>{@code set.toString()}</br>
	 * -------------------------------</br>
	 * Returns the string representation of this set in order. </br>
	 * 
	 * @return string representation of this set 
	 */
	public String toString()
	{
		return toString(root);
	}

	/**
	 * <b>Method: </b>toString</br> 
	 * <b>Usage: </b>{@code toString(root)}</br>
	 * -------------------------------</br>
	 * Returns the string representation of this set in order. </br>
	 * Resolve left, visit node, resolve right gives all elements in ascending order. </br>
	 * 
	 * @param t
	 * @return
	 */
	private String toString(TreeNode t)
	{
		if (t == null)
			return " ";
		return toString(t.getLeft()) + t.getValue() + toString(t.getRight());
	}
	
	/**
	 * <b>Method: </b>iterator</br> 
	 * <b>Usage: </b>{@code Iterator<E> it = Set.iterator()}</br>
	 * -------------------------------</br>
	 * Returns a new iterator for this set. </br></br>
	 * 
	 * @return iterator for this set
	 */
	public Iterator<E> iterator()
	{
	    return new MyTreeSetIterator();
	}
	
	/**
	 * Iterator for the MyTreeSet class that iterates in ascending order. </br>
	 * Provides the common Iterator methods {@code hasNext(), next(), remove()} </br>
	 * {@code next()} keeps track of next element to return by using a Stack of TreeNodes and {@code current} TreeNode
	 * 
	 * @author Dennis Moon
	 * @version Mar 26, 2013
	 * 
	 * @Revision
	 *     Mar 26, 2013 - class created & completed
	 */
	private class MyTreeSetIterator implements Iterator<E>
	{
	    private Stack<TreeNode> stack;
	    private TreeNode current;
	    private E lastReturned;
	    
	    /**
	     * <b>Constructor: </b>MyTreeSetIterator</br> 
	     * <b>Usage: </b>{@code Iterator<E> it = set.iterator()}</br>
	     * -------------------------------</br>
	     * Creates a new stack of TreeNodes and sets current as root node of set. </br>
	     */
	    public MyTreeSetIterator()
	    {
	        stack = new Stack<TreeNode>();
	        current = root;
	    }
	    
        /**<b>Method: </b></br> 
         * <b>Usage: </b>{@code }</br>
         * -------------------------------</br>
         * Returns whether any further elements in ascending order exists. Does not exist if stack is empty and current is null. </br></br>
         * 
         * @return false if stack & current are empty
         */
        @Override
        public boolean hasNext()
        {
            if (stack.isEmpty() && current==null)
                return false;
            return true;
        }

        /**<b>Method: </b></br> 
         * <b>Usage: </b>{@code }</br>
         * -------------------------------</br>
         * Returns the next element in line of ascending order. </br>
         * <b>Case</b>
         * <ol>
         *      <li>{@code current!=null} </br>
         *          - temp is set to leftmost leaf (which could be current), and all nodes are added to the stack during traversal </li>
         *      <li>otherwise </br>
         *          - a node is popped from the stack and set as temp
         * </ol>
         * Current is set as temp's right for the next time {@code next()} is called (whether right is null or not). </br>
         * Temp's value is returned. </br></br>
         * 
         * @throws NoSuchElementException if {@code !hasNext()}
         * @return next element in line of ascending order
         */
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            
            TreeNode temp;
            if (current!=null)
            {
                temp = current;
                while(temp.getLeft()!=null)
                {
                    stack.add(temp);
                    temp = temp.getLeft();
                }
            }
            else
                temp = stack.pop();
            current = temp.getRight();
            lastReturned = (E) temp.getValue();
            return lastReturned;
        }

        /**<b>Method: </b></br> 
         * <b>Usage: </b>{@code }</br>
         * -------------------------------</br>
         * Removes the most recently returned element from {@code next()} by calling {@code MyTreeSet.this.remove(lastReturned)} </br>
         * Works because remove does not actually delete the node, but only swaps the values; therefore, current & stack retain its ascending order. </br></br>
         * 
         * @throws IllegalStateException if {@code lastReturned==null}
         */
        public void remove()
        {
            if (lastReturned==null)
                throw new IllegalStateException();
            
            MyTreeSet.this.remove(lastReturned);
            lastReturned = null;
        }
	    
	}
}