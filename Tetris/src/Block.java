import java.awt.Color;

/**
 * Object to be inserted into grids
 * One grid slot contains one block of a certain color
 * Block keeps track of the grid and location it's in
 * 
 * @version 10/11/12
 *          block can be inserted, removed, and moved around in a grid
 * @version 10/14/12
 *          block now has status that is either fixed or moving
 *  
 * @author Dennis Moon
 */
public class Block
{
    public static final int MOVING = 0;
    public static final int FIXED = 1;
    
    private MyBoundedGrid<Block> grid;
    private Location location;
    private Color color;
    private int status;

    /**
     * Constructor: Block()
     * Usage: Block block = new Block()
     * -----------------------------------
     * sets the color of this block to blue
     * sets grid and location to null
     */
    public Block()
    {
        color = Color.BLUE;
        grid = null;
        location = null;
        status = MOVING;
    }

    /**
     * Method: getColor()
     * Usage: Color color = block.getColor()
     * -----------------------------------
     * @return color of this block
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Method: setColor()
     * Usage: block.setColor(Color.yourColor)
     * -----------------------------------
     * sets the color of this block to the given color
     * @param newColor - color you want the block to be
     */
    public void setColor(Color newColor)
    {
        color = newColor;
    }

    /**
     * Method: getGrid()
     * Usage: MyBoundedGrid<Block> grid = block.getGrid();
     * -----------------------------------
     * @return grid - this block is in
     */
    public MyBoundedGrid<Block> getGrid()
    {
        return grid;
    }

    /**
     * Method: getLocation()
     * Usage: Location blockLoc = block.getLocation();
     * -----------------------------------
     * @return location - of this block in the grid
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Method: removeSelfFromGrid()
     * Usage: block.removeSelfFromGrid();
     * -----------------------------------
     * removes itself from the grid and sets grid and locaiton to null
     * 
     * @precondition this block is in a grid
     */
    public void removeSelfFromGrid()
    {
        grid.remove(location);
        grid = null;
        location = null;
    }

    /**
     * Method: putSlefInGrid()
     * Usage: block.putSelfInGrid(grid, location)
     * -----------------------------------
     * sets the given gr as grid and given loc as location
     * if there is a block in the given location, the old block is replaced by this one
     * @param gr - grid this block goes in
     * @param loc - location this block goes in in the grid
     * @precondition this block is not in a grid & loc is valid in grid
     */
    public void putSelfInGrid(MyBoundedGrid<Block> gr, Location loc)
    {
        grid = gr;
        location = loc;
        
        if (grid.get(location)!=null)
            grid.get(location).removeSelfFromGrid();
        grid.put(location, this);
    }

    /**
     * Method: moveTo()
     * Usage: block.moveTo(new Location(r,c))
     * -----------------------------------
     * moves this block to a new location in the current grid
     * replaces the old block at the location with this block
     */
    public void moveTo(Location newLocation)
    {
        MyBoundedGrid<Block> temp = grid;
        removeSelfFromGrid();
        putSelfInGrid(temp, newLocation);
    }
    
    public void setStatus(int stat)
    {
        status = stat;
    }
    
    public int getStatus()
    {
        return status;
    }

	//returns a string with the location and color of this block
    public String toString()
    {
        return "Block[location=" + location + ",color=" + color + "]";
    }
}