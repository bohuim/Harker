
/**
 * 
 * <b>Methods</b>
 * <ul>
 * 		<li></li>
 * </ul>
 * 
 * @author Dennis Moon
 * @version Apr 26, 2013
 * 
 * @Revision
 *     Apr 26, 2013
 */
public class HeapTester
{
    public static void main (String[] args)
    {
        MyHeap<Integer> h = new MyHeap<Integer>();
        h.add(2);
        h.add(9);
        h.add(3);
        h.add(6);
        System.out.println(h);
        System.out.println(h.size());
        h.remove(2);
        System.out.println(h);
        System.out.println(h.size());
        h.remove(6);
        System.out.println(h);
    }
}
