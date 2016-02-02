import java.util.LinkedList;
import java.util.NoSuchElementException;


/**
 * MyLinkedListTester2 tests all methods of MyLinkedList that is not part of the List interface. </br>
 * A instance of a real LinkedList and MyLinkedList are kept as instance fields of MyLinkedTester2. </br>
 * The class provides various testing methods like addTest() and removeTest() to be used in the main method. </br>
 * random(), message(), and errorMessage() are convenience methods provided for easier reading
 * 
 * @author Dennis Moon
 * @version Feb 19, 2013
 * 
 * Revision:
 *     Feb 19, 2013 - tester created: random(), errorMessage(), printList() added
 *     Feb 20, 2013 - addToIndexTest() added
 *     Feb 21, 2013 - addToIndexTest() changed to addTest(), removeTest()
 *     Feb 27, 2013 - removeTest() and getTest() modified to account for size 0 lists
 *     
 */
public class MyLinkedListTester2
{
    private LinkedList<Integer> real;
    private MyLinkedList<Integer> mine;
    
    /**
     * <b>Constructor: </b>MyLinkedListTester2</br> 
     * <b>Usage: <b>{@code MyLinkedListTester2 tester = new MyLinkedListTester2}</br>
     * -------------------------------</br>
     * Initiates real & mine LinkedLists
     */
    public MyLinkedListTester2()
    {
        real = new LinkedList<Integer>();
        mine = new MyLinkedList<Integer>();
    }
    
    /**
     * <b>Method: </b>main</br> 
     * <b>Usage: </b>{@code main}</br>
     * -------------------------------</br>
     * Initiates MyLinkedTester2 and uses its various testing methods </br>
     * A for loop is run 1000 times. Each loop prints both string representations of both LinkedLists and tests get() and size. </br>
     * addTest() and removeTest() are randomly picked each loop. </br>
     * it is assumed the methods work if all 1000 loops are completed without any of the testing methods throwing an exception
     * 
     * @param args - array of string arguments to run the main method
     */
    public static void main(String[] args)
    {
        MyLinkedListTester2 tester = new MyLinkedListTester2();
        
        for (int i=0; i<1000; i++)
        {   
            tester.printList();
            System.out.println();
            
            tester.stringTest();
            tester.getTest();
            tester.sizeTest();
            
            int chance = tester.random(2);
            if (chance == 0)
                tester.addTest();
            else if (chance == 1)
                tester.removeTest();
        }
        
        System.out.println("\nIt works!");
    }
    
    /**
     * <b>Method: </b>addTest</br> 
     * <b>Usage: </b>{@code tester.addTest()}</br>
     * -------------------------------</br>
     * addTest tests the {@code add(index,obj), addFirst(), addLast()} methods by picking randomly </br>
     * a random Integer value within [0,100) is chosen for all thress methods </br>
     * {@code add(index, obj)} </br>
     * - chooses a random index within [0,size] and {@code add(index,value)} is called on both mine & real </br>
     * - if {@code get(index)} of mine & real do not match, an exception is thrown </br>
     * {@code addFirst()} </br>
     * - addFirst(value) is called on both mine & real </br>
     * - if {@code get(0)} of mine & real do not match, an exception is thrown </br>
     * {@code addLast()} </br>
     * - addLast(value) is called on both mine & real </br>
     * - if {@code get(size-1)} of mine & real do not match, an exception is thrown </br>
     */
    public void addTest()
    {
        int value = random(100);
        int chance = random(3);
        
        if (chance == 0)
        {
            int index = random(mine.size()+1);
            message("add(" + index + ", " + value + ")");
            mine.add(index, value);
            real.add(index, value);
            if (mine.get(index)!=real.get(index))
                errorMessage("add(" + index + ", " + value + ") did not add correctly - Mine: " + mine.get(index) + ", Real: " + real.get(index));
        }
        else if (chance == 1)
        {
            message("addFirst(" + value + ")");
            mine.addFirst(value);
            real.addFirst(value);
            if (mine.get(0)!=real.get(0))
                errorMessage("addFirst(" + value + ") did not add correctly - Mine: " + mine.get(0) + ", Real: " + real.get(0));
        }
        else if (chance == 2)
        {
            message("addLast(" + value + ")");
            mine.addLast(value);
            real.addLast(value);
            if (mine.get(mine.size()-1)!=real.get(real.size()-1))
                errorMessage("addLast(" + value + ") did not add correctly - Mine: " + mine.get(mine.size()-1) + ", Real: " + real.get(real.size()-1));
        }
    }
    
    /**
     * <b>Method: </b>removeTest</br> 
     * <b>Usage: </b>{@code tester.removeTest()}</br>
     * -------------------------------</br>
     * removeTest test {@code removeFirst()} and {@code removeLast()} methods by picking randomly </br>
     * tries to catch a NoSuchElementException for a size 0 list </br>
     * {@code removeFrist()} </br>
     * - the removed previous first elements are stored </br>
     * - if the removed values do not match, an exception is thrown </br>
     * {@code removeLast()} </br>
     * - the removed previous last elements are stored </br>
     * - if the removed values do not match, an exception is thrown </br>
     * 
     * @throws RuntimeException if {@code size() == 0} and NoSuchElementException is not thrown
     * @throws RuntimeException if values returned by mine & real do not match
     */
    public void removeTest()
    {
        int chance = random(2);
        int myVal, realVal;
        
        if (mine.size() == 0)
        {
            if (chance==0) message("removeFirst(): when size is 0");
            else message("removeLast(): when size is 0");
            try
            {
                //exception not caught
                if (chance==0) mine.removeFirst();
                else mine.removeLast();
                errorMessage("NoSuchElementException was not thrown");
            }
            catch (NoSuchElementException e)
            {
                //exception caught
                message("NoSuchElementException caught");
            }
        }
        else if (chance == 0)
        {
            message("removeFirst()");
            myVal = mine.removeFirst();
            realVal = real.removeFirst();
            if (myVal!=realVal)
                errorMessage("removeFirst() does not match - Mine: " + myVal + ", Real: " + realVal);
        }
        else
        {
            message("removeLast()");
            myVal = mine.removeLast();
            realVal = real.removeLast();
            if (myVal!=realVal)
                errorMessage("removeLast() does not match - Mine: " + myVal + ", Real: " + realVal);
        }
    }
    
    /**
     * <b>Method: </b>stringTest</br> 
     * <b>Usage: </b>{@code tester.stringTest}</br>
     * -------------------------------</br>
     * the string representations of mine & real are compared to ensure that something did not go wrong </br>
     * for example: {@code removeTest()} only tests whether the returned values match, therefore stringTest tests whether the element was removed properly </br>
     * 
     * @throws RuntimeException if toString() of mine & real do not match
     */
    public void stringTest()
    {
        if (!mine.toString().equals(real.toString()))
            errorMessage("toString() do not match");
    }
    
    /**
     * <b>Method: </b>getTest</br> 
     * <b>Usage: </b>{@code tester.getTest()}</br>
     * -------------------------------</br>
     * getTest tests {@code getFirst()} and {@code getLast()} methods by picking randomly </br>
     * tries to catch a NoSuchElementException for a size 0 list </br>
     * {@code getFirst()} and {@code getLast()}: </br>
     * - if the returned values do not match, an exception is thrown </br>
     * 
     * @throws RuntimeException if {@code size == 0} and NoSuchElementException is not thrown
     * @throws RuntimeException if values returned by last do not match
     */
    public void getTest()
    {
        int chance = random(2);
        int mineVal, realVal;
        
        if (mine.size() == 0)
        {
            if (chance==0) message("getFirst(): when size is 0");
            else message("getLast(): when size is 0");
            try
            {
                //exception not caught
                if (chance==0) mine.getFirst();
                else mine.getLast();
                errorMessage("NoSuchElementException was not thrown");
            }
            catch (NoSuchElementException e)
            {
                //exception caught
                message("NoSuchElementException caught \n");
            }
        }
        else if (chance == 0)
        {
            mineVal = mine.getFirst();
            realVal = real.getFirst();
            if (mineVal!=realVal)
                errorMessage("getFirst() does not match - Mine: " + mineVal + ", Real: " + realVal);
        }
        else
        {
            mineVal = mine.getFirst();
            realVal = real.getFirst();
            if (mineVal!=realVal)
                errorMessage("getLast() does not match - Mine: " + mineVal + ", Real: " + realVal);
        }
    }
    
    /**
     * <b>Method: </b>sizeTest</br> 
     * <b>Usage: </b>{@code tester.sizeTest()}</br>
     * -------------------------------</br>
     * sizeTest tests whether the size of the LinkedLists are equal </br>
     * an exception is thrown if the sizes do not match
     */
    public void sizeTest()
    {
        int mySize = mine.size();
        int realSize = real.size();
        if (mySize!=realSize)
            errorMessage("size() does not match - Mine: " + mySize + ", Real: " + realSize);
    }
    
    /**
     * <b>Method: </b>printList</br> 
     * <b>Usage: </b>{@code printList}</br>
     * -------------------------------</br>
     * Print real & mine LinkedList to the output using toString()
     */
    public void printList()
    {
        System.out.println("mine: " + mine.toString());
        System.out.println("real: " + real.toString());
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

    /**
     * <b>Method: </b>random</br> 
     * <b>Usage: </b>{@code }</br>
     * -------------------------------</br>
     * generates a random int between [0,n)
     * 
     * @param n - upper limit of random number
     * @return random int between [0,n)
     */
    public int random(int n)
    {
        return (int)(Math.random() * n);
    }
}
