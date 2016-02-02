import java.util.NoSuchElementException;

/**
 * MyPriorityQueue is a Queue of comparable elements stored in a Binary Tree 
 * that removes the first occurrence of the most prioritized element. </br>
 * The Binary Tree is structured so that the left branch consists of elements with priority less than or equal to the parent 
 * and the right branch consists of priorities greater than the parent. </br>
 * 
 * <b>Method</b>
 * <ul>
 *  <li>{@code add(obj)} - adds the given object to the queue </li>
 *  <li>{@code remove()} - returns and removes the element of highest priority, or first occurance if more than one elements have the same priority </li>
 *  <li>{@code peek()} - returns the element of highest priority but does not remove it </li>
 *  <li>{@code isEmpty()} - returns whether the queue is empty </li>
 * </ul>
 * 
 * @author Dennis Moon
 * @version Apr 17, 2013
 * 
 * @Revision
 *     Apr 17, 2013 - class created and finished
 */
public class MyPriorityQueue<E>
{
    private TreeNode tree;
    private int size;

    /**
     * <b>Constructor: </b>MyPriorityQueue</br> 
     * <b>Usage: </b>{@code MyPriorityQueue<E> queue = new MyPriorityQueue<E>();}</br>
     * -------------------------------</br>
     * Creates a new Binary Tree and TreeDisplay, and sets size to 0.
     */
    public MyPriorityQueue()
    {
        tree = null;
        size = 0;
    }
    
    /**
     * <b>Method: </b>add</br> 
     * <b>Usage: </b>{@code queue.add(obj)}</br>
     * -------------------------------</br>
     * Adds the given obj to the tree by calling {@code addHelp(tree, (Comparable)obj)}.  </br>
     * Updates the display and increments size. </br>
     * Always returns true because {@code add} cannot fail. </br>
     * 
     * @param obj to add to the priority queue
     * @return true
     */
    public boolean add(E obj)
    {
        tree = addHelp(tree, (Comparable)obj);
        size++;
        return true;
    }
    
    /**
     * <b>Method: </b>addHelp</br> 
     * <b>Usage: </b>{@code addHelp(tree, x)}</br>
     * -------------------------------</br>
     * Adds the given value to the specified tree and returns the tree. 
     * <ul>
     *  <li><b>Base Case</b>: list is null </br>
     *         - return a new TreeNode with the given value x </li>
     *     <li><b>Reduction</b>: return new tree with left or right set as a new tree </br>
     *         - if priority of x is less than or equal to current node value, set left as new tree with value inserted </br>
     *         - otherwise, set right as new tree with value inserted </li>
     * </ul>
     * 
     * @param t - tree to add given value to 
     * @param x - value to add
     * @return new tree with the inserted value
     */
    private TreeNode addHelp(TreeNode t, Comparable x)
    {
        if (t==null)
            return new TreeNode(x);
        
        if (x.equals(t.getValue()) || x.compareTo(t.getValue()) < 0)
            t.setLeft(addHelp(t.getLeft(), x));
        else
            t.setRight(addHelp(t.getRight(), x));
        return t;
    }
    
    /**
     * <b>Method: </b>remove</br> 
     * <b>Usage: </b>{@code queue.remove()}</br>
     * -------------------------------</br>
     * Returns the element with most priority and removes the node by calling {@code removeHelp(tree, priority)} </br>
     * Updates display and decrements size. </br>
     * 
     * @return element of highest priority
     */
    public E remove()
    {
        E priority = leftMost();
        tree = removeHelp(tree, (Comparable)priority);
        size--;
        return priority;
    }
    
    /**
     * <b>Method: </b>leftMost</br> 
     * <b>Usage: </b>{@code leftMost}</br>
     * -------------------------------</br>
     * Returns the value of the left most node of the tree by traversing with a while loop. </br>
     * 
     * @return element of highest priority
     */
    private E leftMost()
    {
        TreeNode t = tree;
        while (t.getLeft()!=null)
            t = t.getLeft();
        return (E) t.getValue();
    }
    
    /**
     * <b>Method: </b>removeHelp</br> 
     * <b>Usage: </b>{@code removeHelp(tree, priority)}</br>
     * -------------------------------</br>
     * Removes the specified value from the tree and reutrns the new tree; removes the first occurrence if more than instance exists. </br>
     * <ul>
     * <li><b>Base Case</b>: tree is empty </br>
     *         - return a empty tree </li>
     *     <li><b>Base Case</b>: node with specified value is found </br>
     *         - return what deleteNode returns </li>
     *     <li><b>Reduction</b>: return tree with left or right set as a new tree </br>
     *         - if value < current node value: set left branch as new tree with the value deleted </br>
     *         - if value > current node value: set right branch as new tree with the value deleted </li>
     * </ul>
     * 
     * @param t
     * @param x
     * @return
     */
    private TreeNode removeHelp(TreeNode t, Comparable x)
    {
        if (t==null)
            return null;
        if (t.getValue().equals(x))
            return deleteNode(t);
        
        if (x.compareTo(t.getValue()) < 0)
            t.setLeft(removeHelp(t.getLeft(), x));
        else
            t.setRight(removeHelp(t.getRight(), x));
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
    private TreeNode deleteNode(TreeNode t)
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
        
        t.setValue(leftmost.getValue());
        if (leftmost.equals(right))
            prev.setRight(leftmost.getRight());
        else
            prev.setLeft(leftmost.getRight());
        return t;
    }
    
    /**
     * <b>Method: </b>peek</br> 
     * <b>Usage: </b>{@code queue.peek()}</br>
     * -------------------------------</br>
     * Returns, but does not remove, the element of highest priority if queue is not null or tree is not null. </br>
     * 
     * @return element of highest priority
     */
    public E peek()
    {
        if (!isEmpty())
            return leftMost();
        return null;
    }
    
    /**
     * <b>Method: </b>isEmpty</br> 
     * <b>Usage: </b>{@code queue.isEmpty}</br>
     * -------------------------------</br>
     * Returns whether the queue is empty or size is 0. </br>
     * 
     * @return whehter queue is empty
     */
    public boolean isEmpty()
    {
        return size==0;
    }
    
    /**
     * <b>Method: </b>toString</br> 
     * <b>Usage: </b>{@code queue.toString}</br>
     * -------------------------------</br>
     * Returns the string representation of this queue or tree. </br>
     * Calls {@code toStringHelp(tree)}, excludes the last ", " and adds "[]" </br>
     * 
     * @return String representation of this queue
     */
    public String toString()
    {
        String list = toStringHelp(tree);
        if (list.length()!=0)
            list = list.substring(0,list.length()-2);
        return "[" + list + "]";
    }
    
    /**
     * <b>Method: </b>toStringHelp</br> 
     * <b>Usage: </b>{@code toStringHelp}</br>
     * -------------------------------</br>
     * Returns the String representation of the just tree elements with recursion. </br>
     * <ul>
     *  <li><b>Base Case</b>: {@code t == null} </br>
     *      - return empty string </li>
     *  <li><b>Reduction</b>: </br>
     *      - do an inOrder traversal of the tree, converting the node values into String as the node is visited </li>
     * </ul>
     * 
     * @param t - tree to convert into String
     * @return String representation of tree
     */
    private String toStringHelp(TreeNode t)
    {
        if (t==null)
            return "";
        return toStringHelp(t.getLeft()) + t.getValue().toString() + ", " + toStringHelp(t.getRight());
    }
}
