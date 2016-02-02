import java.io.*;
import java.util.*;

/**
 * Tester for Binary Search Trees & Set that makes uses of user input. </br>
 * 
 * @author Dennis Moon
 * @version Mar 19, 2013
 * 
 * @Revision:
 *     Mar 25, 2013 - class created & BST testing implemented
 *     Mar 26, 2013 - modified to MyTreeSet because Set makes use of BST
 */
public class TreeTester
{
    /**
     * <b>Method: </b>main</br> 
     * <b>Usage: </b>{@code }</br>
     * -------------------------------</br>
     * Has a BufferedReader that takes input from System.in through InputStreamReader. </br>
     * A new set is created and an iterator is initially set as null. </br>
     * For an undecided amount of times, input is split from " " using StringTokenizers and commands are executed accordingly. </br></br>
     * ex) "add 100" adds 100 in ascending order to the set, "it" returns a new iterator
     * 
     * @param args - arguments to run a main thread
     * @throws IOException when I/O causes problems
     */
    public static void main(String[] args) throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        MyTreeSet<Integer> set = new MyTreeSet<Integer>();
        Iterator<Integer> it = null;
        
        while (true)
        {
            StringTokenizer token = new StringTokenizer(in.readLine());
            String type = token.nextToken();
            int x = 0;
            if (token.hasMoreTokens())
                x = Integer.parseInt(token.nextToken());
            
            if (type.equals("contains"))
                System.out.println(set.contains(x));
            else if (type.equals("add"))
                set.add(x);
            else if (type.equals("remove"))
                set.remove(x);
            else if (type.equals("it"))
                it = set.iterator();
            else if (type.equals("next"))
                System.out.println(" " + it.next());
            else if (type.equals("hasNext"))
                System.out.println(" " + it.hasNext());
            else if (type.equals("itRemove"))
                it.remove();
        }
    }
}
