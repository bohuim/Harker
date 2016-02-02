import java.util.Iterator;

/**
 * List iterator for MyArrayList class.
 * provides hasNext(), next(), remove(), set(E obj), add(E obj)
 * @author Dennis Moon
 *
 * @param <E> - class type of the elements in the ArrayList
 */
public interface MyListIterator<E> extends Iterator<E>
{   
    /**
     * Method: set(E obj)
     * Usage: it.set(obj)
     * -------------------------------------------------------------------------------------------
     * Replaces the last element returned by next with the specified element. 
     * can only be called for every next() method
     * 
     * @param obj - object to replace last returned element
     */
    void set(E obj);
    
    /**
     * Method: add(E obj)
     * Usage: it.add(obj);
     * --------------------------------------------------------------------------------------------
     * Inserts the element before the element that would be returned by next()
     * Calling add(thing) then next() will return object thing
     * 
     * @param obj - object to be inserted
     */
    void add(E obj);
}
