import java.awt.*;
import java.util.*;

/**
 * Piece is a single piece in a chess game, but is an abstract class because no instance of Piece should be created. </br>
 * Instance fields and methods common to all Pieces are implemented here, but each subclass of Piece has different moves that should be implemented. </br></br>
 * 
 * <b>Methods</b>
 * <ul>
 * 		<li>{@code getBoard()} returns board Piece is on</li>
 *      <li>{@code getLocation()} returns location of Piece on board</li>
 *      <li>{@code getColor()} returns color (team) of this piece</li>
 *      <li>{@code getImageFileName()} returns file directory of Piece image</li>
 *      <li>{@code getValue()} returns value of Piece</li>
 *      <li>{@code moved()} increments moved instance field</li>
 *      <li>{@code unMoved()} decrements moved instance field</li>
 *      <li>{@code wasMoved()} returns whether this Piece was ever moved</li>
 *      <li>{@code putSelfInGrid(board, loc)} adds Piece to a board at the specified location</li>
 *      <li>{@code removeSelfFromGrid()} removes Piece from the current board</li>
 *      <li>{@code moveTo(loc)} relocates Piece to specified location</li>
 *      <li>{@code isValidDestination(loc)} checks the validity of a move</li>
 *      <li>{@code destinations()} returns an iterator containing all valid destinations (should have specified implementation by subclasses</li>
 *      <li>{@code sweep(set, dir)} adds to the specified set all moves possible in the single direction given</li>
 * </ul>
 * @author Dennis Moon
 * @version Apr 23, 2013
 * 
 * @Revision
 *     Apr 22, 2013 - class created & isValidDestination() added
 *     Apr 25, 2013 - destinations() and sweep() added
 *     Apr 28, 2013 - direction field added
 *     May 3, 2013 - {@code moved}, wasMoved() and moved() field added
 */
public abstract class Piece
{
	//the board this piece is on
	private Board board;

	//the location of this piece on the board
	private Location location;
	//direction piece is facing
	private int direction;

	//the color of the piece
	private Color color;

	//the file used to display this piece
	private String imageFileName;

	//the approximate value of this piece in a game of chess
	private int value;
	
	//whether piece was ever moved
	private int moved;

	/**
	 * <b>Constructor: </b>Piece</br> 
	 * <b>Usage: </b>{@code Piece pawn = new Pawn();}</br>
	 * -------------------------------</br>
	 * Creates a new Piece of a certain subclass by taking in color, fileName, and value. </br>
	 * 
	 * @param col - color or team of Piece
	 * @param fileName - file directory to image of Piece
	 * @param val - value of Piece in Chess
	 */
	public Piece(Color col, String fileName, int val)
	{
		color = col;
		
		direction = Location.NORTH;
		if (color.equals(Color.BLACK))
		    direction = Location.SOUTH; 
		
		imageFileName = fileName;
		value = val;
		moved = 0;
	}

	/**
	 * <b>Method: </b>getBoard</br> 
	 * <b>Usage: </b>{@code Piece.getBoard()}</br>
	 * -------------------------------</br>
	 * Returns the board Piece is currently on
	 * 
	 * @return board Piece is on
	 */
	public Board getBoard()
	{
		return board;
	}

	/**
	 * <b>Method: </b>getLocation</br> 
	 * <b>Usage: </b>{@code Piece.getLocation}</br>
	 * -------------------------------</br>
	 * Returns the current location of this Piece on the board
	 * 
	 * @return location of Piece
	 */
	public Location getLocation()
	{
		return location;
	}
	
	/**
	 * <b>Method: </b>getDirection</br> 
	 * <b>Usage: </b>{@code Piece.getDirection()}</br>
	 * -------------------------------</br>
	 * Returns direction this Piece is facing
	 * 
	 * @return direction currently facing
	 */
	public int getDirection()
	{
	    return direction;
	}

	/**
	 * <b>Method: </b>getColor</br> 
	 * <b>Usage: </b>{@code Piece.getColor()}</br>
	 * -------------------------------</br>
	 * Returns the color or team of this Piece
	 * 
	 * @return color of Piece
	 */
	public Color getColor()
	{
		return color;
	}

	/**
	 * <b>Method: </b>getImageFileName</br> 
	 * <b>Usage: </b>{@code Piece.getImageFileName()}</br>
	 * -------------------------------</br>
	 * Returns the file directory and name for image used to display Piece
	 * 
	 * @return file location of image
	 */
	public String getImageFileName()
	{
		return imageFileName;
	}

	/**
	 * <b>Method: </b>getValue</br> 
	 * <b>Usage: </b>{@code Piece.getValue()}</br>
	 * -------------------------------</br>
	 * Returns the value of this Piece
	 * 
	 * @return value of Piece
	 */
	public int getValue()
	{
		return value;
	}
	
	/**
	 * <b>Method: </b>moved</br> 
	 * <b>Usage: </b>{@code Piece.moved()}</br>
	 * -------------------------------</br>
	 * Increments moved.
	 */
	public void moved()
	{
	    moved++;
	}
	
	/**
	 * <b>Method: </b>unMoved</br> 
	 * <b>Usage: </b>{@code Piece.unMoved()}</br>
	 * -------------------------------</br>
	 * Decrements moved and sets it 0 if it goes negative. 
	 */
	public void unMoved()
	{
	    moved--;
	    if (moved < 0)
	        moved = 0;
	}
	
	/**
	 * <b>Method: </b>wasMoved</br> 
	 * <b>Usage: </b>{@code Piece.wasMoved()}</br>
	 * -------------------------------</br>
	 * Returns whether this piece was ever moved.
	 * 
	 * @return whether Piece was moved
	 */
	public boolean wasMoved()
	{
	    return moved != 0;
	}

    /**
     * Puts this piece into a board. If there is another piece at the given
     * location, it is removed. <br />
     * Precondition: (1) This piece is not contained in a grid (2)
     * <code>loc</code> is valid in <code>gr</code>
     * @param brd the board into which this piece should be placed
     * @param loc the location into which the piece should be placed
     */
    public void putSelfInGrid(Board brd, Location loc)
    {
        if (board != null)
            throw new IllegalStateException(
                    "This piece is already contained in a board.");

        Piece piece = brd.get(loc);
        if (piece != null)
            piece.removeSelfFromGrid();
        brd.put(loc, this);
        board = brd;
        location = loc;
    }

    /**
     * Removes this piece from its board. <br />
     * Precondition: This piece is contained in a board
     */
    public void removeSelfFromGrid()
    {
        if (board == null)
            throw new IllegalStateException(
                    "This piece is not contained in a board.");
        if (board.get(location) != this)
            throw new IllegalStateException(
                    "The board contains a different piece at location "
                            + location + ".");

        board.remove(location);
        board = null;
        location = null;
    }

    /**
     * Moves this piece to a new location. If there is another piece at the
     * given location, it is removed. <br />
     * Precondition: (1) This piece is contained in a grid (2)
     * <code>newLocation</code> is valid in the grid of this piece
     * @param newLocation the new location
     */
    public void moveTo(Location newLocation)
    {
        if (board == null)
            throw new IllegalStateException("This piece is not on a board.");
        if (board.get(location) != this)
            throw new IllegalStateException(
                    "The board contains a different piece at location "
                            + location + ".");
        if (!board.isValid(newLocation))
            throw new IllegalArgumentException("Location " + newLocation
                    + " is not valid.");

        if (newLocation.equals(location))
            return;
        board.remove(location);
        Piece other = board.get(newLocation);
        if (other != null)
            other.removeSelfFromGrid();
        location = newLocation;
        board.put(location, this);
    }
    
    /**
     * <b>Method: </b>isValidDestination</br> 
     * <b>Usage: </b>{@code piece.isValidDestination(dest)}</br>
     * -------------------------------</br>
     * If dest is valid on board and either empty or of different color, return true.
     * 
     * @param dest - loc to move piece to
     * @return true piece can be moved to dest
     */
    public boolean isValidDestination(Location dest)
    {
        if (board.isValid(dest) && (board.get(dest)==null || !board.get(dest).color.equals(color)))
            return true;
        return false;
    }
    
    /**
     * <b>Method: </b>destinations</br> 
     * <b>Usage: </b>{@code piece.destinations()}</br>
     * -------------------------------</br>
     * Returns the iterator to a set of all possible moves of this piece. </br>
     * Should be overridden by specific piece, a subclass
     * 
     * @return iterator to set of all possible moves
     */
    public abstract Iterator<Location> destinations();
    
    /**
     * <b>Method: </b>sweep</br> 
     * <b>Usage: </b>{@code piece.sweep(set, dir)}</br>
     * -------------------------------</br>
     * Adds all possible moves in a single given direction to the specified set with a while loop.
     * 
     * @param dests - set of all possible moves
     * @param direction to check destinations
     */
    public void sweep(Set<Location> dests, int direction)
    {
        Location loc = location.getAdjacentLocation(direction);
        while (isValidDestination(loc) && board.get(loc)==null) //adds to set until first enemy or end of board if no enemy is encountered along the way
        {
            dests.add(loc);
            loc = loc.getAdjacentLocation(direction);
        }
        if (isValidDestination(loc) && board.get(loc)!=null && !board.get(loc).getColor().equals(color)) //adds final spot if occupied by enemy
            dests.add(loc);
    }
}