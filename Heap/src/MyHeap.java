import java.util.*;
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
public class MyHeap<E>
{
    private ArrayList<E> list;
    private int size;

    public MyHeap()
    {
        list = new ArrayList<E>();
        list.add(null);
        size = 0;
    }
    
    /**
     * <b>Method: </b>size</br> 
     * <b>Usage: </b>{@code heap.size()}</br>
     * -------------------------------</br>
     * Returns size of heap in constant time.
     * 
     * @return size of heap
     */
    public int size()
    {
        return size;
    }
    
    /**
     * <b>Method: </b>contains</br> 
     * <b>Usage: </b>{@code heap.contains(obj)}</br>
     * -------------------------------</br>
     * Returns whether specified obj exists within heap.
     * 
     * @param o - obj to check for
     * @return true if obj exists in heap
     */
    public boolean contains(Object o)
    {
        return list.contains(o);
    }
    
    /**
     * <b>Method: </b>heapUp</br> 
     * <b>Usage: </b>{@code heapUp(index)}</br>
     * -------------------------------</br>
     * Recursive method to heapify up the element at the specified index until placed correctly. </br>
     * Parent is always located at index/2 (truncated) and elements must be comparable. 
     * <ul>
     * <li><b>Base Case</b>: parent does not exist or parent is smaller </br>
     *          - do nothing </li>
     * <li><b>Reduction</b> </br>
     *          - Switch the values of parent and current, keep heapifying up the current
     * 
     * @param index of element to heapify up
     */
    public void heapUp(int index)
    {
        int parentIndex = (int) index/2;
        Comparable target = (Comparable) list.get(index);
        Comparable parent = (Comparable) list.get(parentIndex);
        if (parentIndex!=0 && target.compareTo(parent) < 0)
        {
            list.set(index, (E) parent);
            list.set(parentIndex, (E) target);
            heapUp(parentIndex);
        }
    }
    
    /**
     * <b>Method: </b>add</br> 
     * <b>Usage: </b>{@code heap.add(e)}</br>
     * -------------------------------</br>
     * Adds the given element to the list and heapifies up the newly added element. </br>
     * Size is incremented. </br>
     * 
     * @param e - element to add
     * @return true
     */
    public boolean add(E e)
    {
        list.add(e);
        heapUp(list.size()-1);
        size++;
        return true;
    }
    
    /**
     * <b>Method: </b>heapDown</br> 
     * <b>Usage: </b>{@code heapDown(index)}</br>
     * -------------------------------</br>
     * Recursive method to heapify down the element at the specified index. </br>
     * Child can be located at either index*2 or index*2 + 1. 
     * <ul>
     *      <li><b>Base Case</b>: does not exist ({@code childIndex <= 0})</br>
     *              - do nothing </li>
     *      <li><b>Reduction</b> </br>
     *              - Switch the current and smaller child and keep heapifying down the current </li>
     * 
     * @param index of the element to heapify down
     */
    public void heapDown(int index)
    {
        int childIndex = smallerChild(index);
        Comparable target = (Comparable) list.get(index);
        Comparable child = (Comparable) list.get(childIndex);

        if (childIndex > 0)
        {
            list.set(childIndex, (E) target);
            list.set(index, (E) child);
            heapDown(childIndex);
        }
    }
    
    /**
     * <b>Method: </b>smallerChild</br> 
     * <b>Usage: </b>{@code smallerChild(index)}</br>
     * -------------------------------</br>
     * Given a index, returns index with smaller element between index*2 & index*2 +1.
     * <b>Cases</b>
     * <ol>
     *      <li>Right child is smaller: return index*2 +1</li>
     *      <li>Right child does not exist: return Left child</li>
     *      <li>no child: return -1 </li>
     * </ol>
     * 
     * @param index of the left child
     * @return index of the smaller child
     */
    private int smallerChild(int index)
    {
        int LChildIndex = index*2;
        int RChildIndex = index*2 + 1;
        Comparable LChild = null;
        if (LChildIndex < list.size())
            LChild = (Comparable) list.get(LChildIndex);
        Comparable RChild = null;
        if (RChildIndex < list.size())
            RChild = (Comparable) list.get(RChildIndex);
        
        if (RChild!=null && RChild.compareTo(LChild) < 0)
            return RChildIndex;
        if (LChild!=null)
            return LChildIndex; 
        return -1;
    }
    
    /**
     * <b>Method: </b>remove</br> 
     * <b>Usage: </b>{@code heap.remove(o)}</br>
     * -------------------------------</br>
     * Removes the specified element from the heap. </br>
     * Replaces the specified element with the last element and heapifies down newly replaced. </br>
     * Size is decremented. </br>
     * 
     * @param o - obj to remove from heap
     * @return true if successfully removed
     */
    public boolean remove(Object o)
    {
        int index = list.indexOf(o);
        if (index > 0)
        {
            E last = list.remove(list.size()-1);
            if(index!=list.size())
            {
                list.set(index, last);
                heapDown(index);
            }
            size--;
            return true;
        }
        return false;
    }
    
    /**
     * <b>Method: </b>toString</br> 
     * <b>Usage: </b>{@code heap.toString()}</br>
     * -------------------------------</br>
     * Returns the String representation of heap by excluding the first null element in the list. </br>
     * 
     * @return String representation of heap
     */
    public String toString()
    {
        return "[" + list.toString().substring(7);
    }
}
