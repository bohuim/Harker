/**
* Class Sorter encapsulates several sort methods based on common sorting
* algorithms.  In particular, selection sort, insertion sort, merge sort and
* quick sort are included.  A private reference to a SortDisplay object
* is maintained to provide a visual indication of each sort as it progresses.
* To update the display, the display.update method is called
*/
public class Sorter
{
	private SortDisplay display;
	
	/**
	* main method instantiates a sorter instance
	* Usage: called directly by the IDE or when Java is launched 
	* ------------------------------------------
	* Creates a Sorter object, but calls no methods from Sorter 
	* because the GUI SortDisplay calls sort methods in Sorter
	* 
	* @params args an array of arguments for legacy command line
	*              the values are not used
	*/
	public static void main(String[] args)
	{
		Sorter sorter = new Sorter();
	}
	
	/**
	* Constructor: Sorter()
	* Useage:  Sorter aSorter = new Sorter()
	* ________________________________________
	* constructor for Sorter objects.  Creates a new display, which controls
	* all of the sorting by means of call-backs to this class.
	*/
	public Sorter()
	{
		display = new SortDisplay(this);
	}

	/**
	 * Method: indexOfMin()
	 * Usage: sorter.indexOfMin(Comparable[], int)
	 * --------------------------------------
	 * minIndex, index of the lowest element, is initially set to startIndex
	 * a for loop starting at startIndex+1 compares the current element to element at minIndex
	 * if the element at the current index is less than element at minIndex, current index is set as minIndex
	 * when method is done traversing the array, minIndex points to the lowest element
	 * 
	 * Postcondition: Returns the index of the minimum value from
     *                a[startIndex] to end of array
	 * @param a - array of comparable objects
	 * @param startIndex - front boundary of elements to get minIndex from
	 * @return minIndex - index at which the minimum element is in
	 */
	public int indexOfMin(Comparable[] a, int startIndex)
	{
		int minIndex = startIndex;
		for (int i=startIndex+1; i<a.length; i++)
		{
		    if (a[i].compareTo(a[minIndex])<0)
		        minIndex = i;
		}
		return minIndex;
	}

	/**
	 * Method: selectionSort()
	 * Usage: sort.selectionSort(Comparable[])
	 * ------------------------------
	 * Selection sort takes the minimum value of a section of an array and places
	 * that value at the front of the section
	 * Each time an element is sorted, the section being sorted it reduced by 1
	 * 
	 * @param a - array of comparable elements to be sorted with selection sorting
	 */
	public void selectionSort(Comparable[] a)
	{
		Comparable tempStorage;
		
		for (int i=0; i<a.length; i++)
		{
		    tempStorage = a[i];
		    int minIndex = indexOfMin(a, i);
		    
		    a[i] = a[minIndex];
		    display.update();
		    a[minIndex] = tempStorage;
		    display.update();
		}
	}

	/**
	 * Method: insert()
	 * Usage: sorter.insert()
	 * -----------------------------
	 * the element at nextIndex is stored in a variable
	 * its then compared with every element before it in the array list
	 * if the element at nextIndex is less than the compared element
	 * the compared element's index is increased by 1 
	 * and the element at nextIndex is put in where the compared element was
	 * 
	 * Precondition:  a[0] to a[nextIndex - 1] are in increasing order
     * Postcondition: a[0] to a[nextIndex] are in increasing order
     * 
	 * @param a - array of comparable elements, that's sorted before nextIndex 
	 * @param nextIndex - index with the element to be sorted with the elements before it
	 */
	public void insert(Comparable[] a, int nextIndex)
	{
	    Comparable tempStorage = a[nextIndex];
	    
	    int n = nextIndex-1;
	    while (n>=0 && tempStorage.compareTo(a[n])<0)
	    {
	        a[n+1] = a[n];
            a[n] = tempStorage;
            n--;
	    }
	}

	/**
	 * Method: insertionSort()
	 * Usage: sorter.insertionSort()
	 * ---------------------------
	 * insertion sort traverse the array and sorts the current element
	 * with all elements before it using insert() so that when the last element is sorted,
	 * the whole array is sorted
	 * 
	 * @param a - array of comparable elements to be sorted with insertion 
	 */
	public void insertionSort(Comparable[] a)
	{
		for (int i=1; i<a.length; i++)//start at index 1 because 0 is sorted by itself
		{
		    insert(a,i); 
		    display.update();
		}
	}

	/**
	 * Method: mergesort()
	 * Usage: mergesort(inputArray)
	 * ----------------------------
	 * mergesort does not actually do the sorting,
	 * it just calls mergesortHelp with parameters (inputArray, 0, a.length-1),
	 * which does the actual merge sorting
	 * 
	 * Postcondition: a[lowIndex] to a[highIndex] are in increasing order
	 * @param a - array of comparable elements to be sorted with merge sort
	 */
	public void mergesort(Comparable[] a)
	{
	    mergesortHelp(a, 0, a.length-1);
	}

    /**
     * Method: mergesortHelp()
     * Usage: mergesorHelp(inputArray, lowIndex, highIndex);
     * ----------------------------------
     * Merge sorting is a recursive sorting algorithm that divides the array in half, 
     * sorts the halves separately, and merges them in order
     * 
     * Base case: section of array given by low & highIndex has 1 element (high <= low),
     *      which is "sorted" by definition therefore is left alone
     * Recursive reduction: the array is divided into 2 arrays from (0,middle) & (middle+1,length)
     *      where middle equals (high-low)/2 + low 
     *      then the arrays are passed on to mergesortHelp to be sorted
     * 
     * Postcondition: a[lowIndex] to a[highIndex] are in increasing order
     * @param a - array of comparable elements to be divided and sorted then merged
     * @param lowIndex - beginning index of section of array to be divided
     * @param highIndex - ending index of section of array to be divided
     */
    private void mergesortHelp(Comparable[] a, int lowIndex, int highIndex)
    {   
        if (highIndex > lowIndex)
        {
            int mid = (highIndex - lowIndex)/2 + lowIndex;
            
            mergesortHelp(a, lowIndex, mid);
            mergesortHelp(a, mid+1, highIndex);
            
            merge(a, lowIndex, mid, highIndex);
        }
    }
	
	/**
	* method merge()
	* Useage: merge(inputArray, lowIndex, midIndex, highIndex)
	*_______________________________________________
	* Merges the two halves of the input array into one.  The method assumes
	* that each half of the input array is sorted as follows:
	* 
	*                a[lowIndex] to a[midIndex] are in increasing order.
	*                a[midIndex + 1] to a[highIndex] are in increasing order.
	* The method creates an array to hold the output.  It then establishes 
	* two pointers into the two halves of the input array.  The values at the
	* pointer locations are compared, and the smallest is added to the output
	* array.  The corresponding pointer is then increased by one.  In the event
	* either half becomes empty, the remaining values are copied to the output
	* array.
	* Postcondition: a[lowIndex] to a[highIndex] are in increasing order.
	*
	* @param a is the input array of Comparable values
	* @param lowIndex is the index into the array a corresponding to the beginning
	*        of the first half of the array to merge
	* @param midIndex is the index of the last value in the first half of the array
	* @param highIndex is the index of the last value in the second half of the array
	*/
	private void merge(Comparable[] a, int lowIndex, int midIndex, int highIndex)
	{
		Comparable[] copy = new Comparable[a.length];
		for (int i = lowIndex; i <= highIndex; i++)
			copy[i] = a[i];
		int left = lowIndex;
		int right = midIndex + 1;
		for (int i = lowIndex; i <= highIndex; i++)
		{
			if (right > highIndex ||
				(left <= midIndex && copy[left].compareTo(copy[right]) < 0))
			{
				a[i] = copy[left];
				left++;
			}
			else
			{
				a[i] = copy[right];
				right++;
			}
			display.update();
		}
	}

	/**
	 * Method: quicksort()
	 * Usage: sorter.quicksort(inputArray)
	 * -------------------------------------
	 * quicksort() does not actual do the sorting,
	 * just calls quicksortHelp with parameters (a, 0, a.length-1),
	 * which does the actual quick sorting
	 * 
	 * Postcondition: a[lowIndex] to a[highIndex] are in increasing order
	 * @param a - array of comparable elements to be sorted with quick sort
	 */
	public void quicksort(Comparable[] a)
	{
		quicksortHelp(a, 0, a.length-1);
	}

	//performs quicksort on portion of array from lowIndex to highIndex
	/**
	 * Method: quicksortHelp()
	 * Usage: quicksortHelp(a, low, high)
	 * ------------------------------------------
	 * Quick soritng is a recursive sorting algorithm that sets a pivot point (lowIndex in this case)
	 * and calls partition which rough sort: puts every element less than pivot left of pivot, and every element bigger than pivot right of pivot
	 * then quicksortHelp is called on the sections left & right of the pivot point
	 * 
	 * Base case: section of the array given by low & highIndex has 1 element (high <= low), 
	 *         which is "sorted" by definition, therefore nothing is done to it
	 * Recursive reduction: the element at lowIndex is sorted as the pivot using partition() and the index where it lands is returned.
	 *         The array is then divided from (low,pivot-1) & (pivot+1,high) because index pivot is already sorted 
	 *         and quicksortHelp is used again on sections left & right of the pivot element
	 * 
	 * Postcondition: a[lowIndex] to a[highIndex] are in increasing order
	 * @param a - array of comparable elements to be sorted with quick sort
	 * @param lowIndex - beginning index of section of array to be sorted
     * @param highIndex - ending index of section of array to be sorted
	 */
	private void quicksortHelp(Comparable[] a, int lowIndex, int highIndex)
    {   
        if (highIndex > lowIndex)
        {
            int pivot = partition(a, lowIndex, highIndex);
            quicksortHelp(a, lowIndex, pivot);
            quicksortHelp(a, pivot+1, highIndex);
        }
    }
	
	/**
	* Method partition
	* Usuage: int pivotIndex = partition(a, lowIndex, highIndex)
	*___________________________________________________________
	*
	*Returns the index of the pivot element defined as follows:
	*                All elements on the left side of the pivot (from lowIndex)
	*                are less than or equal to the pivot.
	*                All elements on the right side of the pivot (through highIndex)
	*                are greater than or equal to the pivot.
	* The computation is performed in place.
	* @param a the array to partion
	* @param lowIndex is the index of the start of the part of array a to consider
	* @param highIndex is the index of the end of the part of array a to consider
	* @return the index of the pivot element in array a
	*/
	private int partition(Comparable[] a, int lowIndex, int highIndex)
	{
		int pivot = lowIndex;
		for (int unsorted = lowIndex + 1; unsorted <= highIndex; unsorted++)
		{
			if (a[unsorted].compareTo(a[pivot]) < 0)
			{
				Comparable temp = a[pivot];
				a[pivot] = a[unsorted];
				display.update();
				a[unsorted] = a[pivot + 1];
				display.update();
				a[pivot + 1] = temp;
				display.update();
				pivot++;
			}
		}
		return pivot;
	}
}