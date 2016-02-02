import java.util.Set;
import java.util.TreeSet;

import info.gridworld.actor.Actor;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;

/**
 * ChessBoard is an extension of World made of Piece
 * chessboard 
 * 
 * @author Dennis Moon
 */
public class ChessBoard extends World<Actor>
{
    private Game game;
    private Piece target;
    
    /**
     * Constructor: ChessBoard()
     * Usage: ChessBoard board = new ChessBoard();
     * ---------------------------------
     * Creates a new world of Pieces with bounded grid of 8x8
     */
    public ChessBoard(Game game)
    {
        super((new BoundedGrid<Actor>(8,8)));
        this.game = game;
        target = null;
    }
    
    /**
     * Method: add(loc, piece)
     * Usage: only to be called when game is initiated
     * ----------------------------------
     * Adds an actor to this world at a given location.
     * 
     * @param loc the location at which to add the actor
     * @param occupant the actor to add
     */
    public void add(Location loc, Actor occupant)
    {
        occupant.putSelfInGrid(getGrid(), loc);
    }
    
    /**
     * Method: remove(loc)
     * Usage: only to be used when a piece takes the piece at given loc
     * -----------------------------------
     * Removes an actor from this world.
     * 
     * @param loc the location from which to remove an actor
     * @return the removed actor, or null if there was no actor at the given location.
     */
    public Actor remove(Location loc)
    {
        Actor occupant = getGrid().get(loc);
        if (occupant == null)
            return null;
        occupant.removeSelfFromGrid();
        return occupant;
    }
    
    /**
     * Method: locationClick(locationClicked)
     * Usage: called by gui class when user clicks a location
     * ------------------------------------------------
     * ChessBoard just passed on location to Game to be processed
     * 
     * @param loc - clicked by user
     * @return true, locationClick is always consumed in Chess
     */
    public boolean locationClicked(Location loc)
    {
        game.locationClicked(loc);
        return true;
    }
    
    /**
     * Method: getGridClasses()
     * Usage: called by gui when user selects world > grid menu
     * -------------------------------------
     * Overridden to return empty list because user should not change
     * size or type of grid in a chess game
     */
    public Set<String> getGridClasses()
    {
        return (new TreeSet<String>());
    }
    
    /**
     * Method: getOccupantClasses()
     * Usage: called by gui when suer selects an empty location in grid
     * --------------------------------------
     * Overridden to return empty list because user should not manually add pieces
     */
    public Set<String> getOccupantClasses()
    {
        return (new TreeSet<String>());
    }
}
