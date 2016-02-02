import java.awt.Color;
import java.util.*;
/**
 * Rook is a chess piece that can move in a straight line in any compass direction and can Castle with the King piece. </br></br>
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
 *     Apr 22, 2013 - class created and destinations added
 */
public class Rook extends Piece
{
    /**<b>Constructor: </b>Rook</br> 
     * <b>Usage: </b>{@code Piece rook = new Rook(color,fileName)}</br>
     * -------------------------------</br>
     * Creates a new Queen by taking in color and fileName and calling super constructor with value of 5. </br>
     * 
     * @param col - color or team of Rook
     * @param fileName - file directory to image of Rook
     */
    public Rook(Color color, String fileName)
    {
        super(color, fileName, 5);
    }
    
    /**<b>Method: </b></br> 
     * <b>Usage: </b>{@code }</br>
     * -------------------------------</br>
     * Rook can move in a straight line of N, E, S, W directions. </br>
     * Locations can be acquired by calling sweep() from 0 to 270 degrees with 90 degree intervals. </br>
     * 
     * @return set of all possible moves for a Rook
     */
    public Iterator<Location> destinations()
    {
        Set<Location> set = new LinkedHashSet<Location>();
        for (int dir=0; dir<360; dir+=90)
            sweep(set, dir);
        return set.iterator();
    }
}
