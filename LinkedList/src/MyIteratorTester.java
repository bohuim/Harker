import java.util.*;


/**
 * MyIteratorTester tests all methods of MyLinkedList's iterator. </br>
 * The class has a real & mine LinkedLists along with its iterators as instance field. </br>
 * Tester methods like nextTest() tests the methods of the iterator and throws exceptions. </br>
 * message() & errorMessage() are provided to be used in the class for easier reading. </br>
 * 
 * @author Dennis Moon
 * @version Feb 25, 2013
 * 
 * Revision:
 *     Feb 25, 2013 - class created: hasNextTest(), nextTest(), printList(), message(), errorMessage() added
 *     Feb 26, 2013 - removeTest() & modificationTest() added
 */
public class MyIteratorTester
{
    private MyLinkedList<Integer> mine;
    private LinkedList<Integer> real;
    
    private Iterator<Integer> myIt;
    private Iterator<Integer> realIt;
    
    /**
     * <b>Constructor: </b>MyIteratorTester</br> 
     * <b>Usage: <b>{@code MyIteratorTester tester = new MyIteratorTester();}</br>
     * -------------------------------</br>
     * creates mine & real LinkedLists and calls insertValue() to fill the LinkedLists
     */
    public MyIteratorTester()
    {
        mine = new MyLinkedList<Integer>();
        real = new LinkedList<Integer>();
    }
    
    /**
     * <b>Method: </b>main</br> 
     * <b>Usage: </b>{@code main}</br>
     * -------------------------------</br>
     * Initiates MyIteratorTester and calles the three main tester methods: nextTest(), removeTest(), modificationTest()
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        MyIteratorTester tester = new MyIteratorTester();
        
        tester.nextTest();
        tester.removeTest();
        tester.modificationTest();
        
        tester.message("\nIt works!");
    }
    
    /**
     * <b>Method: </b>newIterators</br> 
     * <b>Usage: </b>{@code newIterators()}</br>
     * -------------------------------</br>
     * clears all elements in both lists & refills them with 10 random values,
     * then the iterators are replaced
     */
    private void newIterators()
    {
        if (mine.size()<1)
        {
            for (int i=0; i<10; i++)
            {
                Integer val = new Integer((int) (Math.random()*100));
                mine.add(val);
                real.add(val);
            }
            message("New lists: ");
            printList();
            System.out.println();
        }
        
        myIt = mine.iterator();
        realIt = real.iterator();
    }
    
    /**
     * <b>Method: </b>hasNextTest</br> 
     * <b>Usage: </b>{@code hasNextTest()}</br>
     * -------------------------------</br>
     * if myIt & realIt's hasNext does not return the same values,
     * an exception is thrown with what myIt should hvve returned </br>
     * 
     * @throws RuntimeException <code> mine.hasNext()!=real.hasNext() </code>
     */
    private void hasNextTest()
    {
        boolean myVal = myIt.hasNext();
        boolean realVal = realIt.hasNext();
        message("hasNext(): " + myVal);
        
        if (myVal!=realVal)
            errorMessage("Mine did not return: " + realVal + "!");
    }
    
    /**
     * <b>Method: </b>nextTest</br> 
     * <b>Usage: </b>{@code tester.nextTest()}</br>
     * -------------------------------</br>
     * At each iteration, hasNextTest() and the values returned by next() are compared. </br>
     * If at any point in the iteration, the values next() returns do not match, an exception is thrown. </br>
     * next() is called at the end of the iteration to check that a NoSuchElementException is thrown. </br>
     * 
     * @throws RuntimeException <code>mine.next() != real.next</code>
     * @throws RuntimeException next() does not throw appropriate exception
     */
    public void nextTest()
    {
        newIterators();
        
        for (int i=0; i<real.size(); i++)
        {
            printList();
            message("Index: " + i +  ", Element: " + real.get(i));
            hasNextTest();
            
            int myVal = myIt.next();
            int realVal = realIt.next();
            message("next(): " + myVal);
            if (myVal!=realVal)
                errorMessage("Mine did not return: " + realVal + "!");
            
            System.out.println(); //empty line for easier reading
        }
        
        //both iterators should return false
        hasNextTest();
        
        //calling next() should throw a NoSuchElementExcpetion
        message("next(): called at the end of iteration");
        try 
        {
            //if exception was not thrown
            myIt.next();
            errorMessage("NoSuchElementExcpetion was not thrown");
        }
        catch (NoSuchElementException e)
        {
            //caught the NoSuchElementException
            message("NoSuchElementException caught \n");
        }
    }
    
    /**
     * <b>Method: </b>removeTest</br> 
     * <b>Usage: </b>{@code tester.removeTest()}</br>
     * -------------------------------</br>
     * Refills the lists & replaces the iterators. </br>
     * remove() is called without a next() called to check if IllegalStateExcpetion is thrown. </br></br>
     * 
     * Then all elements are removed by calling next() then remove()
     * and the toString of mine & real are compared to check that they match </br>
     * 
     * @throws RuntimeException - remove() doesn't throw appropriate exception
     * @throws RuntimeException - mine.toString() & real.toString() do not match as elements are removed
     */
    public void removeTest()
    {
        newIterators();
        message("new iterators initiated");
        
        //try removing without any values returned with next()
        message("remove(): without next() called");
        try
        {
            //IllegalStateException not thrown
            myIt.remove();
            errorMessage("IllegalStateException was not thrown \n");
        }
        catch (IllegalStateException e)
        {
            //Exception thrown & caught
            message("IllegalStateException caught \n");
        }
        
        //runs for initial size of the linkedLists to remove all elements
        int run = real.size();
        for (int i=0; i<run; i++)
        {
            printList();
            message("");
            
            message("remove(): " + myIt.next());
            realIt.next();
            
            myIt.remove();
            realIt.remove();
        }
        
        printList();
        message("");
    }
    
    /**
     * <b>Method: </b>modificationTest</br> 
     * <b>Usage: </b>{@code tester.modificationTest()}</br>
     * -------------------------------</br>
     * Refills the lists & replaces the iterators </br>
     * A new element is added after the iterators are created and next() is called to check if a ConcurrentModification error is caught </br></br>
     * 
     * Refills the lists & replaces the iterators again </br>
     * next() is called before adding a new element to the list and remove() is called to check if a ConcurrentModification error is caught </br>
     * 
     * @throws RuntimeException ConcurrentModificationException is not thrown when list is modified after iterators are created
     */
    public void modificationTest()
    {
        newIterators();
        
        message("add(0): after iterators are initiated");
        mine.add(0);
        
        message("next(): after a modification to list");
        try
        {
            //exception not thrown
            myIt.next();
            errorMessage("ConcurrentModificationException was not thrown \n");
        }
        catch (ConcurrentModificationException e)
        {
            //exception thrown & caugh
            message("ConcurrentModificationException caught \n");
        }
        
        newIterators(); 
        message("new iterators initiated");
        
        int val = myIt.next();
        message("next(): " + val);
        mine.add(0);
        message("add(0): after iterators are initiated");
        
        message("remove(): " + val);
        try
        {
            //exception not thrown
            myIt.remove();
            errorMessage("ConcurrentModificationException was not thrown");
        }
        catch (ConcurrentModificationException e)
        {
            //exception thrown & caught
            message("ConcurrentModificationException caught");
        }
    }

    /**
     * <b>Method: </b>printList</br> 
     * <b>Usage: </b>{@code printList}</br>
     * -------------------------------</br>
     * Prints real & mine LinkedList to the output using toString() and
     * throws an exception if the toStrings do not match
     * 
     * @throws RuntimeException - mine.toString() does not match real.toString
     */
    public void printList()
    {
        message("mine: " + mine.toString());
        message("real: " + real.toString());
        if (!mine.toString().equals(real.toString()))
            errorMessage("toString() do not match");
    }
    
    /**
     * <b>Method: </b>message</br> 
     * <b>Usage: </b>{@code message(s)}</br>
     * -------------------------------</br>
     * prints the given string to the output stream
     * 
     * @param s - message to print
     */
    private void message(String s)
    {
        System.out.println(s);
    }
    
    /**
     * <b>Method: </b>errorMessage</br> 
     * <b>Usage: </b>{@code tester.errorMessage()}</br>
     * -------------------------------</br>
     * throws a RuntimeException with the given message
     * 
     * @param s - message to be printed with RuntimeException
     */
    public void errorMessage(String s)
    {
        throw new RuntimeException(s);
    }
}
