import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Knights are interesting chess pieces that can move in any rotation of a L shape. </br></br>
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
 *     May 1, 2013 - class created and finished
 */
public class Knight extends Piece
{

    /**<b>Constructor: </b>Knight</br> 
     * <b>Usage: </b>{@code Piece knight = new Knight(color,fileName)}</br>
     * -------------------------------</br>
     * Creates a new Knight by taking in color and fileName and calling super constructor with value of 3. </br>
     * 
     * @param col - color or team of Knight
     * @param fileName - file directory to image of Knight
     */
    public Knight(Color col, String fileName)
    {
        super(col, fileName, 3);
    }

    /**<b>Method: </b></br> 
     * <b>Usage: </b>{@code }</br>
     * -------------------------------</br>
     * Knights can move in any rotation of L shape: two steps in a direction and a right turn either left or right. </br>
     * 
     * @return set of all possible moves for a Knight
     */
    public Iterator<Location> destinations()
    {
        Set<Location> set = new LinkedHashSet<Location>();
        for (int dir=0; dir<360; dir+=90)
        {
            Location temp = getLocation().getAdjacentLocation(dir);
            for (int turn=-45; turn<90; turn+=90)
            {
                if (isValidDestination(temp.getAdjacentLocation(dir + turn)))
                    set.add(temp.getAdjacentLocation(dir + turn));
            }
        }
        return set.iterator();
    }
}
