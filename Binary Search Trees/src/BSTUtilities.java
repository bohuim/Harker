import java.util.Stack;

/**
 * BSTUtilities provide methods to deal with Binary Search Trees in ascending order. <br>
 * <ul>
 *      <li>{@code contains()} returns whether a specified value exists in the tree</li>
 *      <li>{@code insert()} returns a new tree with the given value inserted</li>
 *      <li>{@code delete() & deleteNode()} return a new tree with the specified value deleted</li>
 * </ul>
 * 
 * @author Dennis Moon
 * @version Mar 25, 2013
 * 
 * @Revision
 *     Mar 25, 2013 - class created & all methods implemented
 */
public abstract class BSTUtilities
{
    /**
     * <b>Method: </b>contains</br> 
     * <b>Usage: </b>{@code Util.contains(tree, value, display)}</br>
     * -------------------------------</br>
     * Returns whether the given value exists in the given tree.
     * <ul>
     *      <li>Base Case: list is empty </br>
     *              - false: list does not contain value </li>
     *      <li>Base Case: node value equals x value </br>
     *              - true: value exists </li>
     *      <li>Reduction: if x value is less than node value, go left; otherwise go right until empty list reached or value is found</li>
     * <ul>
     * 
     * @param t - tree to check given value in
     * @param x - value to check for
     * @param display - TreeDisplay to light up nodes
     * @return true if specified element exists 
     */
	public static boolean contains(TreeNode t, Comparable x, TreeDisplay display)
	{
	    if (t==null)
            return false;
	    
	    display.visit(t);
		if (t.getValue().equals(x))
		    return true;
		
		if (x.compareTo(t.getValue()) < 0)
		    return contains(t.getLeft(), x, display);
		else
		    return contains(t.getRight(), x, display);
	}
	
	/**
	 * <b>Method: </b>insert</br> 
	 * <b>Usage: </b>{@code Util.insert(tree, value, display)}</br>
	 * -------------------------------</br>
	 * Inserts the given value in ascending order in the given tree.
	 * <ul>
	 *     <li><b>Base Case</b>: list is null </br>
	 *         - return a new TreeNode with the given value x </li>
	 *     <li><b>Reduction</b>: return new tree with left or right set as a new tree </br>
	 *         - if x value is less than current node value, set left as new tree with value inserted </br>
	 *         - otherwise, set right as new tree with value inserted </li>
	 * </ul>
	 * 
	 * @param t - tree to insert value into
	 * @param x - value to insert
	 * @param display - TreeDisplay to show progress
	 * @return new tree with value inserted
	 */
	public static TreeNode insert(TreeNode t, Comparable x, TreeDisplay display)
	{   
		if (t==null)
		    return new TreeNode(x);
		if (t.getValue().equals(x))
		    return t;
		
		display.visit(t);
		if (x.compareTo(t.getValue()) < 0)
		    t.setLeft(insert(t.getLeft(), x, display));
        else
            t.setRight(insert(t.getRight(), x, display));
		return t;
	}
	
	/**
	 * <b>Method: </b>deleteNode</br> 
	 * <b>Usage: </b>{@code Util.deleteNode()}</br>
	 * -------------------------------</br>
	 * Given a tree, delete the root while maintaining ascending order and return the new root. </br>
	 * <b>Case</b>
	 * <ol>
	 *     <li><b>No successor (no right branch)</b> </br>
	 *         - return left branch as new root</li>
	 *     <li><b>Successor is immediate right node</b> </br>
	 *         - swap root & successor node values, set right node as successor as right's right node </li>
	 *     <li><b>Successor is not right node</b> </br>
	 *         - swap root & successor node values, set successor's parent's left node as successor's right node </li>
	 * </ol>
	 * For Cases 1 & 2, a while loop is run to keep track of currently traversing node and its parent. </br>
	 * 
	 * @param t - root node to delete
	 * @param display - TreeDisplay to show progress
	 * @return new tree with previous root deleted
	 */
	private static TreeNode deleteNode(TreeNode t, TreeDisplay display)
	{
	    TreeNode left = t.getLeft();
	    TreeNode right = t.getRight();
	    
	    if (right==null)
	        return left;
	    
	    TreeNode leftmost = right, prev = t;
        while (leftmost.getLeft() != null)
        {
            prev = leftmost;
            leftmost = leftmost.getLeft();
        }
        
	    if (leftmost.equals(right))
	    {
	        t.setValue(leftmost.getValue());
	        prev.setRight(leftmost.getRight());
	    }
	    if (!leftmost.equals(right))
	    {
	        t.setValue(leftmost.getValue());
	        prev.setLeft(leftmost.getRight());
	    }
	    return t;
    }
	
	/**
	 * <b>Method: </b>delete</br> 
	 * <b>Usage: </b>{@code Util.delete(tree, value, display)}</br>
	 * -------------------------------</br>
	 * Finds the node with the specified value, calls deleteNode on it, and returns the new tree.
	 * <ul>
	 *     <li><b>Base Case</b>: tree is empty </br>
	 *         - return a empty tree </li>
	 *     <li><b>Base Case</b>: node with specified value is found </br>
	 *         - return what deleteNode returns </li>
	 *     <li><b>Reduction</b>: return tree with left or right set as a new tree </br>
	 *         - if value < current node value, set left branch as new tree with the value deleted </br>
	 *         - if value > current node value, set right branch as new tree with the value deleted </li>
	 * </ul>
	 * 
	 * @param t - tree to delete value from
	 * @param x - value to detele
	 * @param display - TreeDisplay to show progress
	 * @returnn root of new tree with specified value deleted
	 */
	public static TreeNode delete(TreeNode t, Comparable x, TreeDisplay display)
	{
	    if (t==null)
	        return null;
	    if (t.getValue().equals(x))
	        return deleteNode(t, display);
	    
	    display.displayTree(t);
	    if (x.compareTo(t.getValue()) < 0)
	        t.setLeft(delete(t.getLeft(), x, display));
	    else
	        t.setRight(delete(t.getRight(), x, display));
	    return t;
	}
}