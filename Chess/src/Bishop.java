import java.awt.Color;
import java.util.*;

/**
 * Bishop is a chess piece that can move in a straight line in any diagonal compass direction. </br></br>
 * 
 * <b>Methods</b>
 * <ul>
 * 		<li>{@code destinations()} returns an iterator to a Set of valid destination locations</li>
 * </ul>
 * 
 * @author Dennis Moon
 * @version May 1, 2013
 * 
 * @Revision
 *     May 1, 2013 - class created
 */
public class Bishop extends Piece
{

    /**<b>Constructor: </b>Bishop</br> 
     * <b>Usage: </b>{@code Piece king = new King(color, directory("imgname"))}</br>
     * -------------------------------</br>
     * Creates a new Queen by taking in color and fileName and calling super constructor with value of 3. </br>
     * 
     * @param col - color or team of Bishop
     * @param fileName - file directory to image of Bishop
     */
    public Bishop(Color col, String fileName)
    {
        super(col, fileName, 3);
    }

    /**<b>Method: </b></br> 
     * <b>Usage: </b>{@code }</br>
     * -------------------------------</br>
     * Rook can move in a straight line of NW, NE, SE, SW directions. </br>
     * Locations can be acquired by calling sweep() from 45 to 315 degrees with 90 degree intervals. </br>
     * 
     * @return set of all possible moves for a Rook
     */
    public Iterator<Location> destinations()
    {
        Set<Location> set = new LinkedHashSet<Location>();
        for (int dir=45; dir<360; dir+=90)
            sweep(set, dir);
        return set.iterator();
    }   
}
