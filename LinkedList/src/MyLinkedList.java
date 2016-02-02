import java.util.*;

/**
 * LinkedList is data structure that is link of nodes
 * Each node stores an element and has pointers to nodes before and after itself
 * Pointers running forward and backs wards allows half the time for getting comapred to ArrayList
 * 
 * @author Dennis Moon
 * @version Feb 20, 2013
 * 
 * Revision:
 *     Feb 19, 2013 - class created & all methods in List interface & in LinkedList added
 *     Feb 20, 2013 - fixed getNode(), which was just calling getNodeFromFirst() and not getNodeFromLast()
 *     Feb 21, 2013 - iterator added
 */
public class MyLinkedList<E>
{
	private DoubleNode first;
	private DoubleNode last;
	private int size;
	private int modCount;

	/**2
	 * <b>Constructor: </b>MyLinkedList</br> 
	 * <b>Usage: </b>{@code MyLinkedList<E> list = new MyLinkedList<E>();}</br>
	 * -------------------------------</br>
	 * Creates a LinkedList and sets first, last as null and size as 0
	 */
	public MyLinkedList()
	{
		first = null;
		last = null;
		size = 0;
		modCount = 0;
	}

	/**
	 * <b>Method: </b>toString</br> 
	 * <b>Usage: </b>{@code list.toString()}</br>
	 * -------------------------------</br>
	 * Returns the string representation of this LinkedList by putting the elements between brackets 
	 * 
	 * @return String representation of this list with all its elements
	 */
	public String toString()
	{
		DoubleNode node = first;
		if (node == null)
			return "[]";
		String s = "[";
		while (node.getNext() != null)
		{
			s += node.getValue() + ", ";
			node = node.getNext();
		}
		return s + node.getValue() + "]";
	}

	/**
	 * <b>Method: </b>getNodeFromFirst</br> 
	 * <b>Usage: </b>{@code getNodeFromFirst(index)}</br>
	 * -------------------------------</br>
	 * Returns the node at the given index by searching from the front</br>
	 * just calls getFromFirstHelp(index, first) </br>
	 * 
	 * @precondition 0 <= index <= size/2
	 * @postcondition starting from first, returns the node with given index (where index 0 returns first)
	 * @param index of the node
	 * @return node at the given index
	 */
	private DoubleNode getNodeFromFirst(int index)
	{
	    //non-recursive method
//		DoubleNode current = first;
//		for (int i=0; i<index; i++)
//		    current = current.getNext();
//		return current;
	    
	    //recursive
	    return getFromFirstHelp(index, first);
	}
	
	/**
	 * <b>Method: </b>getFromFirstHelp</br> 
	 * <b>Usage: </b>{@code return getFromFirstHelp(index, first)}</br>
	 * -------------------------------</br>
	 * returns the node at the given index by traversing the list from the front with recursion </br>
	 * base case: index is 0 </br>
	 *     - return the current node </br>
	 * reduction </br>
	 *     - decrement index & traverse the list one node forwards </br>
	 * 
	 * @param index of the node
	 * @param current node the method is traversing
	 * @return node at index
	 */
	private DoubleNode getFromFirstHelp(int index, DoubleNode current)
    {
        if (index == 0)
            return current;
        return getFromFirstHelp(index-1, current.getNext());
    }
	
	/**
	 * <b>Method: </b>getNodeFromLast</br> 
	 * <b>Usage: </b>{@code getNodeFromLast(index)}</br>
	 * -------------------------------</br>
	 * Returns the node at the given index by searching from the back </br>
	 * just calls getFromLastHelp(index, last) </br>
	 * 
	 * @precondition size / 2 <= index < size
	 * @postcondition starting from first, returns the node with given index (where index 0 returns first)
	 * @param index of the node
     * @return node at the given index
	 */
	private DoubleNode getNodeFromLast(int index)
	{
	    //non-recursive method
//		DoubleNode current = last;
//		for (int i=size; i>=index; i--)
//		    current = current.getPrevious();
//		return current;
	    
	    //recursive
	    return getFromLastHelp(index, last);
	}
	
	/**
	 * <b>Method: </b>getFromLastHelp</br> 
	 * <b>Usage: </b>{@code return getFromLastHelp(index, last)}</br>
	 * -------------------------------</br>
	 * returns the node at the given index by traversing the list from the back with recursion </br>
     * base case: index is size-1 </br>
     *     - return the current node </br>
     * reduction </br>
     *     - increment index & traverse the list one node backwards </br>
	 * 
	 * @param index of the node
	 * @param current node method is traversing
	 * @return node at index
	 */
	private DoubleNode getFromLastHelp(int index, DoubleNode current)
	{
	    if (index == size-1)
	        return current;
	    return getFromLastHelp(index+1, current.getPrevious());
	}

	/**
	 * <b>Method: </b>getNode</br> 
	 * <b>Usage: </b>{@code list.getNode(index)}</br>
	 * -------------------------------</br>
	 * Returns the node at the given index by using: </br>
	 * - getNodeFromFirst() if index is in the first half </br>
	 * - getNodeFromLast() if index is in the last half </br>
	 * 
	 * @precondition 0 <= index < size
	 * @postcondition starting from first or last (whichever is closer), returns the node with given index
	 * @param index of the node
     * @return node at the given index
	 */
	private DoubleNode getNode(int index)
	{
		if (0<=index && index<=size/2)
		    return getNodeFromFirst(index);
		return getNodeFromLast(index);
	}
	
	/**
	 * <b>Method: </b>size</br> 
	 * <b>Usage: </b>{@code list.size()}</br>
	 * -------------------------------</br>
	 * @return size of this LinkedList
	 */
	public int size()
	{
		return size;
	}

	/**
	 * <b>Method: </b>get</br> 
	 * <b>Usage: </b>{@code list.get(index)}</br>
	 * -------------------------------</br>
	 * Returns the element of node at the given index </br>
	 * 
	 * @param index of the node & element
	 * @return element at the given index
	 * @throws IndexOutOfBoundsException if index is out of range: <code>index<0 || size<=index </code>
	 */
	public E get(int index)
	{
	    if(isOutOfBounds(index))
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		return (E) getNode(index).getValue();
	}

	/**
	 * <b>Method: </b>set</br> 
	 * <b>Usage: </b>{@code list.set(index, obj)}</br>
	 * -------------------------------</br>
	 * Gets the target node at the given index & its value to return later </br>
	 * Changes target's value to given obj </br>
	 * 
	 * @postcondition replaces the element at position index with obj returns the element formerly at the specified position
	 * @param index of the node to change value
	 * @param obj new element to set the node value as
	 * @return element previously at index
	 * @throws IndexOutOfBoundsException if index is out of range: <code>index<0 || size<=index </code>
	 */
	public E set(int index, E obj)
	{
	    if(isOutOfBounds(index))
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
	    
	    DoubleNode target = getNode(index);
		E old = (E) target.getValue();
		target.setValue(obj);
		return old;
	}

	/**
	 * <b>Method: </b>add</br> 
	 * <b>Usage: </b>{@code list.add(obj)}</br>
	 * -------------------------------</br>
	 * Adds the given element to the end of the list </br>
	 * 
	 * @postcondition appends obj to end of list; returns true
	 * @param obj to be added
	 * @return true
	 */
	public boolean add(E obj)
	{
	    addLast(obj);
		return true;
	}

	/**
	 * <b>Method: </b>remove</br> 
	 * <b>Usage: </b>{@code list.remove(index)}</br>
	 * -------------------------------</br>
	 * Gets the target node & its value to return later </br>
	 * Links the node before's next as target's next and node after's previous as target's previous </br>
	 * Destroys target's link to any nodes, then size is decremented </br>
	 * 
	 * @param index of the node to be removed
	 * @return element previously at the given index
	 * @throws IndexOutOfBoundsException if index is out of range: <code>index<0 || size<=index </code>
	 */
	public E remove(int index)
	{	
	    if(isOutOfBounds(index))
	        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
	    
		DoubleNode target = getNode(index);
		E element = (E) target.getValue();
		DoubleNode prev = target.getPrevious();
		DoubleNode next = target.getNext();
		
		target.setPrevious(null);
		if (prev == null)
		    first = next;
		else
		    prev.setNext(next);
		
		target.setNext(null);
		if (next == null)
		    last = prev;
		else
		    next.setPrevious(prev);
		
		size--;
		modCount++;
		return element;
	}

	/**
	 * <b>Method: </b>add</br> 
	 * <b>Usage: </b>{@code list.add(index, obj)}</br>
	 * -------------------------------</br>
	 * Adds a new element at the given index (allows up to size inclusive) </br>
	 * Elements previously at index & after it are shifted to index+1 </br>
	 * New element becomes first if original element at index had no previous </br>
	 * Size is incremented </br>
	 * 
	 * @param index of the new node
	 * @param obj to be added at the given index
	 * @throws IndexOutOfBoundsException if index is out of range: <code>index<0 || size<index </code>
	 */
	public void add(int index, E obj)
	{
	    if(!(0<=index && index<=size))
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
	    
	    if (index==size) //last or empty list
	        addLast(obj);
	    else //at least 1 element in list
	    {
	        DoubleNode newNode = new DoubleNode(obj);
	        DoubleNode old = getNode(index);
	        DoubleNode prev = old.getPrevious();
	        
	        newNode.setPrevious(prev);
	        newNode.setNext(old);
	        old.setPrevious(newNode);
	        if (prev == null)
	            first = newNode;
	        else
	            prev.setNext(newNode);
	        size++;
	        modCount++;
	    }
	}

	/**
	 * <b>Method: </b>addFirst</br> 
	 * <b>Usage: </b>{@code LinkedList.addFirst(obj)}</br>
	 * -------------------------------</br>
	 * Adds the given obj as the first element in the list </br>
	 * newNode also becomes last if list was empty </br>
	 * Size is incremented </br>
	 * 
	 * @param obj to be added to the front
	 */
	public void addFirst(E obj)
	{
		DoubleNode newNode = new DoubleNode(obj);
		DoubleNode oldF = first;
		newNode.setNext(oldF);
		first = newNode;
		if (oldF == null)
		    last = newNode;
		else
		    oldF.setPrevious(newNode);
		size++;
		modCount++;
	}

	/**
     * <b>Method: </b>addFirst</br> 
     * <b>Usage: </b>{@code LinkedList.addLast(obj)}</br>
     * -------------------------------</br>
     * Adds the given obj as the last element in the list </br>
     * newNode also becomes first if list was empty </br>
     * Size is incremented </br>
     * 
     * @param obj to be added to the back
     */
	public void addLast(E obj)
	{
	    DoubleNode newNode = new DoubleNode(obj);
        DoubleNode oldL = last;
        newNode.setPrevious(oldL);
        last = newNode;
        if (oldL==null)
            first = newNode;
        else
            oldL.setNext(newNode);
        size++;
        modCount++;
	}

	/**
	 * <b>Method: </b>getFirst</br> 
	 * <b>Usage: </b>{@code LinkedList.getFirst()}</br>
	 * -------------------------------</br>
     * Returns the first element of the LinkedList </br>
	 * 
	 * @return first element
	 * @throws NoSuchElementException if {@code size == 0}
	 */
	public E getFirst()
	{
	    if (first == null)
	        throw new NoSuchElementException("Size: 0");
		return (E) first.getValue();
	}

	/**
	 * <b>Method: </b>getLast</br> 
	 * <b>Usage: </b>{@code LinkedList.getLast()}</br>
	 * -------------------------------</br>
	 * Returns the last element of the LinkedList </br>
	 * 
	 * @return last element
	 * @throws NoSuchElementException if {@code size == 0}
	 */
	public E getLast()
	{
	    if (last == null)
	        throw new NoSuchElementException("Size: 0");
		return (E) last.getValue();
	}

	/**
	 * <b>Method: </b>removeFirst</br> 
	 * <b>Usage: </b>{@code LinkedList.removeFirst()}</br>
	 * -------------------------------</br>
	 * Stores the first element to return later </br>
     * Old first's links are broken & first is set as next</br>
     * if next is null, list is now empty & last is also set as null;
     * otherwise next's previous is set as null </br>
     * Size is decremented
	 * 
	 * @return element previously at index 0
	 * @throws NoSuchElementExcpetion if {@code size == 0}
	 */
	public E removeFirst()
	{
	    if (first == null)
            throw new NoSuchElementException("Size: 0");
	    
		E element = (E) first.getValue();
		DoubleNode next = first.getNext();
		first.setNext(null);
		first = next;
		if (next == null)
		    last = null;
		else
		    next.setPrevious(null);
		size--;
		modCount++;
		return element;
	}

	/**
	 * <b>Method: </b>removeLast</br> 
	 * <b>Usage: </b>{@code LinkedList.removeLast()}</br>
	 * -------------------------------</br>
     * Stores the last element to return later </br>
     * Old last's links are broken & last is set as prev</br>
     * if prev is null, list is now empty & first is also set as null; otherwise prev's next is set as null </br>
     * Size is decremented
     * 
     * @return element previously at index size
     * @throws NoSuchElementExcpetion if {@code size == 0}
     */
	public E removeLast()
	{
	    if (last == null)
            throw new NoSuchElementException("Size: 0");
	    
		E element = (E) last.getValue();
		DoubleNode prev = last.getPrevious();
		last.setPrevious(null);
		last = prev;
		if (prev == null)
		    first = null;
		else
		    prev.setNext(null);
		
		size--;
		modCount++;
		return element;
	}
	
	/**
	 * <b>Method: </b>isOutOfBounds</br> 
	 * <b>Usage: </b>{@code isOutOfBounds(index)}</br>
	 * -------------------------------</br>
	 * Helper method to check if given index is out of bounds that
	 * true if index is not in [0,size)
	 * 
	 * @param index to check
	 * @return true if index is out of bounds
	 */
	private boolean isOutOfBounds(int index)
	{
	    return !(0<=index && index<size);
	}

	/**
	 * <b>Method: </b>iterator</br> 
	 * <b>Usage: </b>{@code list.iterator()}</br>
	 * -------------------------------</br>
	 * Returns the iterator for this LinkedList
	 * 
	 * @return iterator for this LinkedList
	 */
	public Iterator<E> iterator()
	{
		return new MyLinkedListIterator();
	}

	/**
	 * MyLinkedListIteraor is an iterator for MyLinkedList </br>
	 * It can traverse the list forward using hasNext() & next() </br>
	 * For every next() called, a remove() removes the element just iterated over by next() </br>
	 * - Because MyLinkedList does not provide as remove(element), the index of the lastReturned is tracked </br>
	 * 
	 * @author Dennis Moon
	 * @version Feb 19, 2013
	 * 
	 * Revision:
	 *     Feb 19, 2013 - class created & all methods added
	 *     Feb 25, 2013 - remove() modified to use MyLinkedList.remove(index) by keeping track of the last returned element's index
	 */
	private class MyLinkedListIterator implements Iterator<E>
	{
		private DoubleNode nextNode;
		private boolean lastReturned;
		private int lastReturnedIndex;
		private int itMod;

		/**
		 * <b>Constructor: </b>MyLinkedListIterator</br> 
		 * <b>Usage: <b>{@code MyLinkedList.iterator()}</br>
		 * -------------------------------</br>
		 * Sets nextNode as first, lastReturned as false, lastReturnedIndex as -1
		 * and itMod is set as modCount
		 */
		public MyLinkedListIterator()
		{
			nextNode = first;
			lastReturned = false;
			lastReturnedIndex = -1;
			itMod = modCount;
		}

		/**
		 * <b>Method: </b>hasNext</br> 
		 * <b>Usage: </b>{@code it.hasNext()}</br>
		 * -------------------------------</br>
		 * @return true if next node exists
		 */
		public boolean hasNext()
		{
			return nextNode!=null;
		}

		/**
		 * <b>Method: </b>next()</br> 
		 * <b>Usage: </b>{@code it.next()}</br>
		 * -------------------------------</br>
		 * Returns the element in the iteration and moves nextNode over one 
		 * lastReturned is set as true & lastReturnedIndex is incremented
		 * 
		 * @return next element
		 * @throws NoSuchElementException if {@code hasNext() == false}
		 */
		public E next()
		{
		    checkModification();
		    if (!hasNext())
		        throw new NoSuchElementException();
			E element = (E) nextNode.getValue();
			lastReturned = true;
			lastReturnedIndex++;
			nextNode = nextNode.getNext();
			return element;
		}

		/**
		 * <b>Method: </b></br> 
		 * <b>Usage: </b>{@code }</br>
		 * -------------------------------</br>
		 * Throws <code>IllegalStateException</code> if remove is called more than once for each next() </br>
		 * Removes the last returned element by next() from the list using lastReturnedIndex </br>
		 * lastReturned is set back to false & lastReturnedIndex is decremented because all elements' indices are shifted left </br>
		 * itMod is incremented </br>
		 * 
		 * @postcondition removes the last element that was returned by next
		 * @throws IllegalStateException if {@code next()} has never been called or more than one {@code remove()} if called for each {@code next()}
		 */
		public void remove()
		{
		    checkModification();
			if (!lastReturned)
			    throw new IllegalStateException();
			
			MyLinkedList.this.remove(lastReturnedIndex);
	        lastReturned = false;
	        lastReturnedIndex--;
	        itMod++;
		}
		
		/**
		 * <b>Method: </b>checkModification</br> 
		 * <b>Usage: </b>{@code checkModification()}</br>
		 * -------------------------------</br>
		 * Iff the modification count since this iterator was made
		 * does not match the total number of modifications,
		 * a <code>ConcurrentModificationException</code> is thrown
		 * 
		 * @throws ConcurrentModificationException if iterator's mod count does not match total mod count
		 */
		private void checkModification()
		{
		    if (itMod != modCount)
		        throw new ConcurrentModificationException();
		}
	}
}