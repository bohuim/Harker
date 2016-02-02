import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.StringTokenizer;


/**
 * 
 * @author Dennis Moon
 * @version Apr 17, 2013
 * 
 * @Revision
 *     Apr 17, 2013
 */
public class MyPriorityTester
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        MyPriorityQueue<Integer> queue = new MyPriorityQueue<Integer>();
        
        while (true)
        {
            StringTokenizer token = new StringTokenizer(in.readLine());
            String type = token.nextToken();
            int x = 0;
            if (token.hasMoreTokens())
                x = Integer.parseInt(token.nextToken());
            
            if (type.equals("add"))
                queue.add(x);
            else if (type.equals("remove"))
                queue.remove();
            else if (type.equals("peek"))
                System.out.println(queue.peek());
            else if (type.equals("isEmpty"))
                System.out.println(queue.isEmpty());
            else if (type.equals("toString"))
                System.out.println(queue.toString());
        }
    }
}
