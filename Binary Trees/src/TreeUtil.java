import java.io.*;
import java.util.*;

/**
 * TreeUtil provides various methods dealing with Binary Trees that use TreeNodes. </br>
 * <b>Utilities</b>
 * <ul>
 *      <li>{@code leftmost()} and {@code rightmost()} give the left and right most leaves, respectively</li>
 *      <li>{@code maxDepth()} returns level of the deepest leaf</li>
 *      <li>{@code createRandom()} returns a random shape trees with random values</li>
 *      <li>{@code countNodes()} and {@code countLeaves} count the total number of nodes and leaves, respectively</li>
 *      <li>{@code preOrder(), inOrder(), postOrder()} traverse the tree in the order specified</li>
 *      <li>{@code saveTree(), fillList(), buildTree(), loadTree()} deal with saving and loading trees as text files</li>
 *      <li>{@code copy()} returns an exact copy of the given tree; {@code sameShape()} returns whether two trees are th exact same shape or not</li>
 *      <li>{@code stringCalc(), eval(), createExpressionTree()} are used to calculate a math expression in string form</li>
 * </ul>
 * 
 * TreeUtil also provides TwentyQuestions game by saving and loading from an expanding knowledge tree. </br></br>
 * 
 * @author Dennis Moon
 * @version Mar 19, 2013
 * 
 * @Revision
 *     Mar 18, 2013 - class created & added left/rightmost, maxDepth, & count methods </br>
 *     Mar 19, 2013 - all order methods and save/load methods finished </br>
 *     Mar 20, 2013 - TwentyQuestions game completed </br>
 *     Mar 21, 2013 - copy() & sameShape() added </br>
 *     Mar 22, 2013 - eval() & createExpressionTree() added </br>
 *     Mar 23, 2013 - stringCalc() method added & modified to take more than 2 operators per parentheses </br>
 */
public class TreeUtil
{
    
	private static Scanner in = new Scanner(System.in);
	
	/**
	 * <b>Method: </b>leftmost</br> 
	 * <b>Usage: </b>{@code TreeUtil.leftmost(tree)}</br>
	 * -------------------------------</br>
	 * Returns the value of the furthest left leaf of the specified tree. </li>
	 * Traverses through the left branch until there is no more left branch and returns the value of the latest node </li>
	 * 
	 * @param t - tree to get left most value
	 * @return value of left most leaf
	 */
	public static Object leftmost(TreeNode t)
	{
	    TreeNode left = t;
		while (left.getLeft() != null)
		    left = left.getLeft();
		return left.getValue();
	}
	
	/**
	 * <b>Method: </b>rightmost</br> 
	 * <b>Usage: </b>{@code TreeUtil.rightmost(tree)}</br>
	 * -------------------------------</br>
	 * Returns the value of furtherest right leaf of the specified tree.
	 * <ul>
	 *     <li>Base Case: no further right branches makes current the farthest right leaf </li>
	 *     <li>Reduction: get the rightMost of current </li>
	 * </ul>
	 * 
	 * @param t - tree to get right most value
	 * @return value of the right most tree
	 */
	public static Object rightmost(TreeNode t)
	{
		if (t.getRight() == null)
		    return t.getValue();
		return rightmost(t.getRight());
	}
	
	/**
	 * <b>Method: </b>maxDepth</br> 
	 * <b>Usage: </b>{@code TreeUtil.maxDepth(tree)}</br>
	 * -------------------------------</br>
	 * Returns the maximum depth of the specified tree. 
	 * <ul>
	 *     <li>Base Case: no tree has a depth of 0 </li>
	 *     <li>Reduction: maximum depth is current(1) + the bigger between max depth of left and right branches </li>
	 * </ul>
	 * 
	 * @param t - tree to find max depth
	 * @return max depth of specified tree
	 */
	public static int maxDepth(TreeNode t)
	{
 		if (t == null)
 		    return 0;
 		return 1 + Math.max(maxDepth(t.getLeft()), maxDepth(t.getRight()));
	}
	
	/**
	 * <b>Method: </b>createRandom</br> 
	 * <b>Usage: </b>{@code TreeUtil.createRandom(depth)}</br>
	 * -------------------------------</br>
	 * Generates a randomly shaped tree of random elements with a depth no bigger than the given depth. </br>
	 * 
	 * @param depth - maximum depth this tree can have
	 * @return random tree with given max depth
	 */
	public static TreeNode createRandom(int depth)
	{
		if (Math.random() * Math.pow(2, depth) < 1)
			return null;
		return new TreeNode(((int)(Math.random() * 10)),
			createRandom(depth - 1),
			createRandom(depth - 1));
	}
	
	/**
	 * <b>Method: </b>countNodes</br> 
	 * <b>Usage: </b>{@code TreeUtil.countNodes(tree)}</br>
	 * -------------------------------</br>
	 * Returns the total number of nodes or elements on this tree. 
	 * <ul>
	 *     <li>Base Case: no tree has no nodes </li>
	 *     <li>Reduction: total number is equal to current(1) + number on left branch + number on right branch </li>
	 * </ul>
	 * 
	 * @param t - tree to count number of nodes
	 * @return number of nodes in the given tree
	 */
	public static int countNodes(TreeNode t)
	{
		if (t == null)
		    return 0;
		return 1 + countNodes(t.getLeft()) + countNodes(t.getRight());
	}
	
	/**
	 * <b>Method: </b>countLeaves</br> 
	 * <b>Usage: </b>{@code TreeUtil.countLeaves(tree)}</br>
	 * -------------------------------</br>
	 * Returns the total number of leaves on a specified tree.
	 * <ul>
	 *     <li>Base Case: no tree has no leaves </li>
	 *     <li>Base Case: no branches means 1 tree </li>
	 *     <li>Reduction: add the amount of leaves on both branches of current </li>
	 * </ul>
	 * 
	 * @param t - tree to count leaves
	 * @return number of leaves on given tree
	 */
	public static int countLeaves(TreeNode t)
	{
	    if (t == null)
	        return 0;
		if (t.getRight() == null && t.getLeft() == null)
		    return 1;
		return countLeaves(t.getLeft()) + countLeaves(t.getRight());
	}

	/**
     * <b>Method: </b>preOrder()</br> 
     * <b>Usage: </b>{@code TreeUtil.preOrder(tree, display)}</br>
     * -------------------------------</br>
     * Traverses the tree in a preOrder manner. 
     * <ul>
     *     <li>Base Case: tree is null </li>
     *     <li>Reduction: visit current, left branch, then right branch </li>
     * </ul>
     * 
     * @param t - tree to traverse
     * @param display to show traversal on
     */
	public static void preOrder(TreeNode t, TreeDisplay display)
	{
	    if (t!=null)
	    {
    	    display.visit(t);
    		preOrder(t.getLeft(), display);
    		preOrder(t.getRight(), display);
	    }
	}

	/**
     * <b>Method: </b>inOrder()</br> 
     * <b>Usage: </b>{@code TreeUtil.inOrder(tree, display)}</br>
     * -------------------------------</br>
     * Traverses the tree in a inOrder manner. 
     * <ul>
     *     <li>Base Case: tree is null </li>
     *     <li>Reduction: visit left branch, current, then right branch </li>
     * </ul>
     * 
     * @param t - tree to traverse
     * @param display to show traversal on
     */
	public static void inOrder(TreeNode t, TreeDisplay display)
	{
	    if (t!=null)
	    {
    	    inOrder(t.getLeft(), display);
    	    display.visit(t);
    	    inOrder(t.getRight(), display);
	    }
	}

	/**
	 * <b>Method: </b>postOrder()</br> 
	 * <b>Usage: </b>{@code TreeUtil.postOrder(tree, display)}</br>
	 * -------------------------------</br>
	 * Traverses the tree in a postOrder manner. 
	 * <ul>
	 *     <li>Base Case: tree is null </li>
	 *     <li>Reduction: go deep left then right, and visit the elements coming back up </li>
	 * </ul>
	 * 
	 * @param t - tree to traverse
	 * @param display to show traversal on
	 */
	public static void postOrder(TreeNode t, TreeDisplay display)
	{
	    if (t!=null)
        {
            postOrder(t.getLeft(), display);
            postOrder(t.getRight(), display);
            display.visit(t);
        }
	}
	
	/**
	 * <b>Method: </b>fillList</br> 
	 * <b>Usage: </b>{@code TreeUtil.fillList(tree, list)}</br>
	 * -------------------------------</br>
	 * Fills the given list with the given tree with an preOrder traversal. 
	 * <ul>
	 *     <li>Base Case: no further tree adds an '$' to the list </li>
	 *     <li>Reduction: element.toString() is added. List is further filled with a tree from each branch </li>
	 * </ul>
	 * 
	 * @param t - tree to fill list from
	 * @param list to store String representation of the specified tree
	 */
	public static void fillList(TreeNode t, List<String> list)
	{
	    if (t == null) 
	        list.add("$");
	    else
	    {
	        list.add(t.getValue().toString());
	        fillList(t.getLeft(), list);
	        fillList(t.getRight(), list);
	    }
	}

	/**
	 * <b>Method: </b>saveTree</br> 
	 * <b>Usage: </b>{@code TreeUtil.saveTree(location, tree)}</br>
	 * -------------------------------</br>
	 * Saves the specified tree as a text file at the specified location. </br>
	 * An ArrayList is filled with representation of a tree and FileUtil.saveFile() saves the list as a text file representation.
	 * 
	 * @param fileName
	 * @param t
	 */
	public static void saveTree(String fileName, TreeNode t)
	{
	    List<String> list = new ArrayList<String>();
	    fillList(t, list);
		FileUtil.saveFile(fileName, list.iterator());
	}
	
	/**
	 * <b>Method: </b>buildTree</br> 
	 * <b>Usage: </b>{@code TreeUtil.buildTree(it)}</br>
	 * -------------------------------</br>
	 * Creates and returns a tree from the given iterator of a list of tree elements. </br>
	 * <ul>
	 *     <li>Base Case: next element is $ and no further branches are created. </li>
	 *     <li>Reduction: create a new node with given value, set its branches as trees from buildTree, and return the node. </li>
	 * </ul>
	 * 
	 * @param it - iterator of list of elements to use
	 * @return tree built as specified in the list
	 */
	public static TreeNode buildTree(Iterator<String> it)
	{
	    if (!it.hasNext())
	        return null;
	    
		String value = it.next();
		if (value.equals("$"))
		    return null;
		
		TreeNode t = new TreeNode(Integer.parseInt(value));
		t.setLeft(buildTree(it));
		t.setRight(buildTree(it));
		return t;
	}

	/**
	 * <b>Method: </b>loadTree</br> 
	 * <b>Usage: </b>{@code TreeUtil.loadTree(location)}</br>
	 * -------------------------------</br>
	 * Returns a tree from buildTree() with a text file loaded using FileUtil.loadFile() at the given location.
	 * 
	 * @param fileName - location & name of the file 
	 * @return binary tree representation of specified file
	 */
	public static TreeNode loadTree(String fileName)
	{
		return buildTree(FileUtil.loadFile(fileName));
	}

	/**
	 * <b>Method: </b>getUserInput</br> 
	 * <b>Usage: </b>{@code getUserInput()}</br>
	 * -------------------------------</br>
	 * Return one line of the user's input from Console input stream. 
	 * 
	 * @return user's input as String
	 */
	private static String getUserInput()
	{
		return in.nextLine();
	}
	
	/**
	 * <b>Method: </b>twentyQuestionsRound</br> 
	 * <b>Usage: </b>{@code twentyQuestionsRound(tree, display)}</br>
	 * -------------------------------</br>
	 * Plays a round of 20 questions by finishing the entire knowledge tree or reaching 20 questions. </br>
	 * All leaves are guesses and other nodes are questions. </br>
	 * <ul>
	 *     <li> Visits and node and gets its value </br>
	 *         - leaf: yes wins the game, no changes to the current leaf to a reason and hangs answer on left and wrong guess on right. </br>
	 *         - branch: yes continues game to left and no continues to the right by calling twentyQuestionsRound()</br>
	 *     </li>
	 * </ul>
	 * 
	 * @param t - current node & progress in game
	 * @param display to show progress of knowledege tree
	 */
	public static void twentyQuestionsRound(TreeNode t, TreeDisplay display)
	{
	    display.visit(t);
	    String prev = t.getValue().toString();
	    
		System.out.print("Is it " + prev + "? ");
		String response = getUserInput();
		
		if (t.getLeft()==null && t.getRight()==null)
		{
    		if (response.equals("no"))
    		{
    		    System.out.print("I give up. What is it? ");
    		    String ans = getUserInput();
    		    
    		    System.out.println("What distinguishes " + ans + " from " + prev + "?");
    		    System.out.print(ans + " is ");
    		    String diff = getUserInput();
    		    System.out.println();
    		    
    		    t.setValue(diff);
    		    t.setLeft(new TreeNode(ans));
    		    t.setRight(new TreeNode(prev));
    		    
    		    display.displayTree(t);
    		}
    		else
    		    System.out.println("I win!");
		}
		else
		{
		    if (response.equals("yes")) twentyQuestionsRound(t.getLeft(), display);
		    else twentyQuestionsRound(t.getRight(), display);
		}
	}
	
	/**
	 * <b>Method: </b>twentyQuestions</br> 
	 * <b>Usage: </b>{@code TreeUtil.twentyQuestions()}</br>
	 * -------------------------------</br>
	 * Plays the 20 Questions Game. </br></br>
	 * 
	 * Loads from and saves to an expanding knowledge tree that grows based on the user's inputs. </br>
	 * Runs one round of 20 questions by calling twentyQuestionsRound() until window is closed. </br>
	 */
	public static void twentyQuestions()
	{	
		TreeNode gameTree = loadTree("C:/Google Drive/Programming/Java/Binary Trees/knowledge.txt");
		TreeDisplay display = new TreeDisplay();
		display.displayTree(gameTree);
		
		while (true)
		{
    		System.out.print("Think of a person or thing! Press enter when ready ");
    		getUserInput();
    		
    		twentyQuestionsRound(gameTree, display);
    		saveTree("C:/Google Drive/Programming/Java/Binary Trees/knowledge.txt", gameTree);
		}
	}

	/**
	 * <b>Method: </b>copy</br> 
	 * <b>Usage: </b>{@code TreeUtil.copy(original)}</br>
	 * -------------------------------</br>
	 * Returns the exact copy of the given tree by creating a copy root and hanging both branches accordingly. </br>
	 * 
	 * @param t original tree
	 * @return copy of the given tree
	 */
	public static TreeNode copy(TreeNode t)
	{
		return new TreeNode(t.getValue(), t.getLeft(), t.getRight());
	}
	
	/**
	 * <b>Method: </b>sameShape</br> 
	 * <b>Usage: </b>{@code TreeUtil.sameShape(t1, t2)}</br>
	 * -------------------------------</br>
	 * Returns whether the two specified trees have the exact same shape. 
	 * <ul>
	 *     <li>Base Case: equal if both nodes do not exist </li>
	 *     <li>Base Case: not equal if either one is null but the other is not </li>
	 *     <li>Reduction: tree is same shape if both branches are the same shape </li>
	 * </ul>
	 * 
	 * @param t1 - tree one
	 * @param t2 - tree two
	 * @return true if given trees are exactly same shape
	 */
	public static boolean sameShape(TreeNode t1, TreeNode t2)
	{
	    if (t1==null && t2==null)
	        return true;
	    else if((t1!=null && t2==null) || (t1==null && t2!=null))
	        return false;
	    return sameShape(t1.getLeft(), t2.getLeft()) && sameShape(t1.getRight(), t2.getRight());
	}

	//postcondition:  returns a tree for decoding Morse code
	public static TreeNode createDecodingTree(TreeDisplay display)
	{
		TreeNode tree = new TreeNode("Morse Tree");
		display.displayTree(tree);
		insertMorse(tree, "a", ".-", display);
		insertMorse(tree, "b", "-...", display);
		insertMorse(tree, "c", "-.-.", display);
		insertMorse(tree, "d", "-..", display);
		insertMorse(tree, "e", ".", display);
		insertMorse(tree, "f", "..-.", display);
		insertMorse(tree, "g", "--.", display);
		insertMorse(tree, "h", "....", display);
		insertMorse(tree, "i", "..", display);
		insertMorse(tree, "j", ".---", display);
		insertMorse(tree, "k", "-.-", display);
		insertMorse(tree, "l", ".-..", display);
		insertMorse(tree, "m", "--", display);
		insertMorse(tree, "n", "-.", display);
		insertMorse(tree, "o", "---", display);
		insertMorse(tree, "p", ".--.", display);
		insertMorse(tree, "q", "--.-", display);
		insertMorse(tree, "r", ".-.", display);
		insertMorse(tree, "s", "...", display);
		insertMorse(tree, "t", "-", display);
		insertMorse(tree, "u", "..-", display);
		insertMorse(tree, "v", "...-", display);
		insertMorse(tree, "w", ".--", display);
		insertMorse(tree, "x", "-..-", display);
		insertMorse(tree, "y", "-.--", display);
		insertMorse(tree, "z", "--..", display);
		return tree;
	}

	//postcondition:  inserts the given letter into the decodingTree,
	//                in the appropriate position, as determined by
	//                the given Morse code sequence; lights up the display
	//                as it walks down the tree
	private static void insertMorse(TreeNode decodingTree, String letter,
									String code, TreeDisplay display)
	{
		throw new RuntimeException("Implement me!");
	}

	//precondition:  ciphertext is Morse code, consisting of dots, dashes, and spaces
	//postcondition: uses the given decodingTree to return the decoded message;
	//               lights up the display as it walks down the tree
	public static String decodeMorse(TreeNode decodingTree, String cipherText, TreeDisplay display)
	{
		throw new RuntimeException("Implement me!");
	}
	
	/**
	 * <b>Method: </b>stringCalc</br> 
	 * <b>Usage: </b>{@code TreeUtil.stringCalc(exp)}</br>
	 * -------------------------------</br>
	 * Takes out any white spaces from the given expression. A expression tree created is displayed and the calculation is returned. </br>
	 * 
	 * @param exp - string representation of a mathematical expression with possible white spaces
	 * @return int answer of the given expression
	 */
	public static int stringCalc(String exp, TreeDisplay display)
	{
	    String[] ch = exp.split(" ");
	    String newExp = "";
	    for (int i=0; i<ch.length; i++)
	        newExp += ch[i];
	    
	    TreeNode expTree = createExpressionTree(newExp);
	    display.displayTree(expTree);
	    return eval(expTree);
	}
	
	/**
	 * <b>Method: </b>eval</br> 
	 * <b>Usage: </b>{@code eval(expTree)}</br>
	 * -------------------------------</br>
	 * Returns the int value of the given expression tree. 
	 * <ul>
	 *     <li>Base Case: if left branch does not exist, because expTree cannot have only 1 branch </br>
	 *     - return the int representation of the current leaf value </li>
	 *     <li>Reduction: all nodes other than leaves are operators and each branch should return a number </br>
	 *     - therefore apply the operator on the int each branch returns </li>
	 * </ul>
	 * 
	 * @param expTree - tree with operators and numbers all in order of operations
	 * @return evaluated int answer of the expression the tree represents
	 */
	private static int eval(TreeNode expTree)
	{
	    if (expTree.getLeft()==null)
	        return Integer.parseInt((String) expTree.getValue());
	    else
	    {
	        String op = (String) expTree.getValue();
	        int n1 = eval(expTree.getLeft());
	        int n2 = eval(expTree.getRight());
	        
	        if (op.equals("+")) return n1+n2;
	        if (op.equals("-")) return n1-n2;
	        if (op.equals("*")) return n1*n2;
	        return n1/n2; //if "/"
	    }
	}
	
	/**
	 * <b>Method: </b>createExpressionTree</br> 
	 * <b>Usage: </b>{@code createExpreesionTree(string)}</br>
	 * -------------------------------</br>
	 * Returns an expression tree created according to order of operations. </br>
	 * All leaves are numbers and other nodes are operators, therefore the tree should be evaluated bottom up. </br>
	 * 
	 * <dl>
	 *     <dt>Base Case: {@code exp.length()==1}</dt>
	 *         <dd>return it as a leaf </dd>
	 *     <dt>Reduction:</dt>
	 *     <dd>Traverse the whole expression to count number of parentheses </br>
	 *         - During the traversal, the operator that occurred when the least number of parentheses were open is stored </br>
	 *         - Also, + & - operators take priority because they should be place at the top of the tree, and therefore evaluated last </br>
	 *         If open & close parentheses do not match </br>
	 *         - createExpressionTree with an open/close parentheses taken care of </br>
	 *         Otherwise createExpressionTree with operator as value, everything left of operator as left branch & right of it as right branch 
	 *     </dd>
	 * </dl>
	 * 
	 * @param exp - string string representation of a mathematical expression with no white spaces
	 * @return root of expression tree with all numbers as leaves and other nodes as operators
	 */
	private static TreeNode createExpressionTree(String exp)
	{
	    if (exp.length() == 1) //must be a number
	        return new TreeNode(exp);
	    
	    int opIndex = 0; //index of desired operator in order of operations
	    int open = 0; //number of opened parentheses
	    int lastOp = exp.length(); //number of parentheses open when the last operator was found
	    for (int i=0; i<exp.length(); i++)
	    {
	        String ch = (String) exp.subSequence(i, i+1);
	        if (ch.equals("(")) open++;
	        if (ch.equals(")")) open--;
	        if (ch.equals("+") || ch.equals("-") || ch.equals("*") ||ch .equals("/")) //if an operator is found
	            if (open <= lastOp) //if less parentheses are open than the last time an operator was found
	            {
	                lastOp = open; //change lastOp
	                
	                if (ch.equals("+")||ch.equals("-")) opIndex = i; //because leaves are evaluated first, +- should go at the top of the tree
	                else if (!(exp.contains("+")||exp.contains("-")) && (ch.equals("*")||ch.equals("/"))) opIndex = i; //only start taking */ operators when exp has no +- operators
	            }
	    }    
	    if (open>0) return createExpressionTree((String) exp.subSequence(1, exp.length())); //if too many open parentheses, disregard the first one
	    if (open<0) return createExpressionTree((String) exp.subSequence(0, exp.length()-1)); //if too many close parentheses, disregard the last one
	    
	    //return a node with the operator as its value & 2 more trees as both of its branches
	    return new TreeNode(exp.substring(opIndex, opIndex+1), 
	            createExpressionTree(exp.substring(0, opIndex)), 
	            createExpressionTree(exp.substring(opIndex+1, exp.length())));
	}
}