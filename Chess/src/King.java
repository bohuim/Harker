import java.awt.Color;
import java.util.*;
/**
 * 
 * <b>Methods</b>
 * <ul>
 * 		<li>{@code destinations()} returns an iterator to a Set of valid destination locations</li>
 * </ul>
 * 
 * @author Dennis Moon
 * @version Apr 22, 2013
 * 
 * @Revision
 *     Apr 22, 2013 - class created & constructor and destinations() added
 *     May 3, 2013 - castleCheck() method added
 *     May 12, 2013 - castleCheck() removed
 */
public class King extends Piece
{

    /**<b>Constructor: </b>King</br> 
     * <b>Usage: </b>{@code new King(color, directory("imgname"))}</br>
     * -------------------------------</br>
     * Creates a King by calling constructor of superclass Piece with given color, fileName, and value of 1000.
     * 
     * @param col - color or team of Piece
     * @param fileName - file directory to image of Piece
     */
    public King(Color color, String fileName)
    {
        super(color, fileName, 1000);
    }

    /**<b>Method: </b>destinations</br> 
     * <b>Usage: </b>{@code piece.destinations()}</br>
     * -------------------------------</br>
     * A King can move one step in any direction of its current location. </br>
     * All locations from [row-1, col-1] to  [row+1, col+1] are added to a Set and returned. </br>
     * 
     * @return iterator to set of all possible destinations
     */
    public Iterator<Location> destinations()
    {
        Set<Location> set = new TreeSet<Location>();
        for (Location loc : getBoard().getValidAdjacentLocations(getLocation()))
            if (isValidDestination(loc))
                set.add(loc);
        return set.iterator();
    }
}
