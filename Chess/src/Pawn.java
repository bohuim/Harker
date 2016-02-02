import java.awt.Color;
import java.util.*;
/**
 * Pawn is the most basic Piece, that can only move forward and capture enemies diagonally. </br></br>
 * 
 * <b>Methods</b>
 * <ul>
 * 		<li>{@code destinations()} returns an iterator to a Set of valid destination locations</li>
 * </ul>
 * 
 * @author Dennis Moon
 * @version Apr 30, 2013
 * 
 * @Revision
 *     Apr 30, 2013 - class created and finished
 */
public class Pawn extends Piece
{   
    /**
     * <b>Constructor: </b>Piece</br> 
     * <b>Usage: </b>{@code Piece pawn = new Pawn(color, directory("imgname"));}</br>
     * -------------------------------</br>
     * Creates a new Pawn by taking in color and fileName and calling super constructor. </br>
     * 
     * @param col - color or team of Piece
     * @param fileName - file directory to image of Piece
     */
    public Pawn(Color color, String fileName)
    {
        super(color, fileName, 1);
    }

    /**<b>Method: </b>destinations</br> 
     * <b>Usage: </b>{@code piece.destinations()}</br>
     * -------------------------------</br>
     * Pawns can choose one of following moves: </br>
     * <ul>
     *  <li>One step forward - any circumstance</li>
     *  <li>Two steps forward - has not been moved</li>
     *  <li>One step diagonal - enemy Piece at diagonal location </li>
     * </ul>
     * 
     * @return iterator to set of possible destinations
     */
    public Iterator<Location> destinations()
    {
        Set<Location> set = new TreeSet<Location>();
        Location current = getLocation();
        
        //pawns can always move 1 step ahead
        Location oneAhead = current.getAdjacentLocation(getDirection() + Location.AHEAD);
        if (this.isValidDestination(oneAhead))
            set.add(oneAhead);
        
        //if 2nd row from top or from bottom and two steps ahead is valid
        int row = getLocation().getRow();
        Location twoAhead = oneAhead.getAdjacentLocation(getDirection());
        if (this.isValidDestination(oneAhead) && !wasMoved()
                && this.isValidDestination(twoAhead))
            set.add(twoAhead);
        
        for (int dir=-45; dir<=45; dir+=90)
        {
            Location diagonal = current.getAdjacentLocation(getDirection() + dir);
            if (isValidAttack(diagonal))
                set.add(diagonal);
        }
        
        return set.iterator();
    }

    /**
     * <b>Method: </b>isValidDestination</br> 
     * <b>Usage: </b>{@code isValidDestination(loc)}</br>
     * -------------------------------</br>
     * Returns whether the specified location is a valid destination for a Pawn. </br>
     * Location is valid on board and there is absolutely no piece, not even ally piece. </br>
     * 
     * @param loc to check
     * @return true if loc is available destination
     */
    public boolean isValidDestination(Location loc)
    {
        if (getBoard().isValid(loc) && getBoard().get(loc)==null)
            return true;
        return false;
    }
    
    /**
     * <b>Method: </b>isValidAttack</br> 
     * <b>Usage: </b>{@code isValidAttack(loc)}</br>
     * -------------------------------</br>
     * Returns whether specified location is a valid attacking destination for a Pawn. </br>
     * Location is valid for attack if it's a valid destination and absolutely has an enemy piece. </br>
     * 
     * @param loc to check
     * @return true if loc is available for attack
     */
    private boolean isValidAttack(Location loc)
    {
        if (super.isValidDestination(loc) && getBoard().get(loc)!=null)
            return true;
        return false;
    }
}
