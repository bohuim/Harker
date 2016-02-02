/**
 * A MyLocation class contains a row and column # as (row,col)
 * Row takes priority over col when comparing:
 *      (0,1) < (0,3)
 *      (1,3) < (2,0)
 * 
 * @author Dennis Moon
 */
public class MyLocation implements Comparable
{
	private int row;
	private int col;

	/**
	 * Constructor: MyLocation()
	 * Usage: MyLocation loc = new MyLocation(row, col);
	 * -----------------------------
	 * creates a MyLocation object with the given row & col
	 * 
	 * @param r - row of this MyLocation
	 * @param c - column of this MyLocaiton
	 */
	public MyLocation(int r, int c)
	{
		row = r;
		col = c;
	}

	/**
	 * Method: row()
	 * Usage: int rowNum = loc.row()
	 * ------------------------------
	 * @return row of this MyLocation
	 */
	public int row()
	{
		return row;
	}

	/**
     * Method: col()
     * Usage: int colNum = loc.col()
     * ------------------------------
     * @return col of this MyLocation
     */
	public int col()
	{
		return col;
	}

	/**
	 * Method: equals()
	 * Usage: boolean locsAreEqual = loc1.equals(loc2);
	 * ------------------------------
	 * Returns false if Object other is not a MyLocation 
	 * otherwise returns true or false depending on whether the row & col # are the same
	 * 
	 * @return true if the row & col of both locs are same
	 */
	public boolean equals(Object other)
	{
		if (!(other instanceof MyLocation))
			return false;
		MyLocation loc = (MyLocation)other;
		return row == loc.row() && col == loc.col();
	}

	/**
	 * Method: toString()
	 * Usage: myLocatio.toString()
	 * -----------------------
	 * @return this location as a string "(row,col)"
	 */
	public String toString()
	{
		return "(" + row + ", " + col + ")";
	}

	/**
	 * Method: compareTo()
	 * Usage: int compareNum = loc1.compareTo(loc2)
	 * ----------------------------
	 * compares this MyLocation relative to the given MyLocation x
	 * if the rows are the same: returns difference of cols
	 * otherwise returns difference of rows because rows take priority over cols when comparing
	 * 
	 * @return difference of cols if rows are equal, or difference of rows if rows are different
	 */
	public int compareTo(Object x)
	{
		MyLocation loc = (MyLocation) x;
		if (row == loc.row())
		    return col - loc.col();
		return row - loc.row();
	}
}