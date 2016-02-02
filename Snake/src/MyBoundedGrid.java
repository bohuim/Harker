import java.util.ArrayList;

//A MyBoundedGrid is a rectangular grid with a finite number of rows and columns.
public class MyBoundedGrid<E>
{
	private Object[][] occupantArray;  // the array storing the grid elements
	private int rows;
	private int cols;
	private ArrayList<Location> occupied;

	/**
	 * Constructor: MyBoundedGrid()
	 * Usage: MyBoundedGrid grid = new MyBoundedGrid(r, c);
	 * -----------------------------------------------------
	 * constructs a MyBoundedGrid by making a 2D Array of the given dimensions
	 * @param rows- number of rows
	 * @param cols - number of columns
	 */
	public MyBoundedGrid(int rows, int cols)
	{
		occupantArray = new Object[rows][cols];
		this.rows = rows;
		this.cols = cols;
		occupied = new ArrayList<Location>();
	}

	/**
	 * Method: getNumRows()
	 * Usage: int rows = grid.getNumRows();
	 * ------------------------------------
	 * @return rows - number of rows in this grid
	 */
	public int getNumRows()
	{
	    return rows;
	}

	/**
     * Method: getNumCols()
     * Usage: int rows = grid.getNumCols();
     * ------------------------------------
     * @return rows - number of rows in this grid
     */
	public int getNumCols()
	{
		return cols;
	}

	/**
     * Method: isValid()
     * Usage: boolean locationValidity = grid.isValid(someLocation);
     * ------------------------------------
     * checks if the row and column of location is less than the rows and columns of this grid
     * @return true if location exists in grid
     * @precondition: location is not null
     */
	public boolean isValid(Location loc)
	{
	    if (0<=loc.getRow() && loc.getRow()<=rows-1 && 0<=loc.getCol() && loc.getCol()<=cols-1)
	        return true;
		return false;
	}

	/**
     * Method: get()
     * Usage: E object = grid.get(someLocation);
     * ------------------------------------
     * @return object or null at that location
     * @precondition location is valid
     */
	public E get(Location loc)
	{
		return (E) occupantArray[loc.getRow()][loc.getCol()];

		//(You will need to promise the return value is of type E.)
	}

	/**
     * Method: put()
     * Usage: E oldObj = grid.put(someLoc, someObj);
     * ------------------------------------
     * puts the given object at the given location
     * and returns the objec that was there
     * @return oldObj - object that was at the given location
     * @precondition loc is valid
     */
	public E put(Location loc, E obj)
	{
		Object oldObj = get(loc);
		occupantArray[loc.getRow()][loc.getCol()] = obj;
		occupied.add(loc);
		return (E) oldObj;
	}

	/**
     * Method: remove(Location loc)
     * Usage: E removedObj = grid.remove(someLoc);
     * ------------------------------------
     * removes and returns the object at the given location
     * @return oldObj - that was removed from loc
     * @precondition loc is valid
     */
	public E remove(Location loc)
	{
	    Object oldObj = get(loc);
        occupantArray[loc.getRow()][loc.getCol()] = null;
        occupied.remove(loc);
        return (E) oldObj;
	}

	/**
     * Method: getOccupiedLocations()
     * Usage: ArrayList<Location> list = grid.getOccupiedLocations()
     * ------------------------------------
     * @return list of locations that are not null
     */
	public ArrayList<Location> getOccupiedLocations()
	{
		return occupied;
	}
}