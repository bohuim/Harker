import java.awt.Color;
import java.util.*;

/**
 * Queen is the most powerful Chess piece that can move like both a Bishop or Rook. </br></br>
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
 *     May 1, 2013 - class created and finshed
 */
public class Queen extends Piece
{

    /**<b>Constructor: </b>Queen</br> 
     * <b>Usage: </b>{@code Piece queen = new Queen(color, directory("imgname"))}</br>
     * -------------------------------</br>
     * Creates a new Queen by taking in color and fileName and calling super constructor with value of 9. </br>
     * 
     * @param col - color or team of Queen
     * @param fileName - file directory to image of Queen
     */
    public Queen(Color col, String fileName)
    {
        super(col, fileName, 9);
    }

    /**<b>Method: </b>destinations</br> 
     * <b>Usage: </b>{@code piece.destinations()}</br>
     * -------------------------------</br>
     * Queen can move in a straight line in any direction of 45 degrees. </br>
     * Locations can be acquired by using sweep() on 0 to 315 degrees. </br>
     * 
     * @return iterator to set of possible destinations
     */
    public Iterator<Location> destinations()
    {
        Set<Location> set = new LinkedHashSet<Location>();
        for (int dir=0; dir<360; dir+=45)
            sweep(set, dir);
        return set.iterator();
    }
}
