import java.awt.Color;

/**
 * Promotion is a special Move in Chess that involves a Pawn that has moved to the end of the enemy line. </br>
 * A Pawn that reaches its 8th rank (other end of the board), it can be upgraded into a Queen. </br>
 * The requirement is not checked in the Promotion class; class assumes a check has been made before Promotion class is created. </br></br>
 * 
 * <b>Methods</b>
 * <ul>
 * 		<li>{@code execute()} - upgrades Pawn into Queen, and inserts Queen into what would have been the destination for Pawn</li>
 *      <li>{@code undo()} - degrades Queen back into Pawn, and inserts Pawn into source location</li>
 * </ul>
 * 
 * @author Dennis Moon
 * @version May 18, 2013
 * 
 * @Revision
 *     May 18, 2013 - class created & finished
 */
public class Promotion extends Move
{
    private Piece promoted;
    
    /**<b>Constructor: </b>Promotion</br> 
     * <b>Usage: </b>{@code Move move = new Promotion(pawn, dest)}</br>
     * -------------------------------</br>
     * Creates a Move by calling super constructor and sets promoted piece as a new Queen of pawn's color.
     * 
     * @param piece - pawn to promote
     * @param destination of promotion
     */
    public Promotion(Piece pawn, Location destination)
    {
        super(pawn, destination);
        
        String imgName = "white_queen";
        if (pawn.getColor().equals(Color.BLACK))
            imgName = "black_queen";
        promoted = new Queen(pawn.getColor(), Game.directory(imgName));
    }
    
    /**
     * <b>Method: </b>execute</br> 
     * <b>Usage: </b>{@code Move.execute()}</br>
     * -------------------------------</br>
     * Inserts the Queen into the destination location and removes the promoted pawn from the board.
     */
    public void execute()
    {
        Piece pawn = getPiece();
        promoted.putSelfInGrid(pawn.getBoard(), getDestination());
        pawn.removeSelfFromGrid();
    }
    
    /**
     * <b>Method: </b>undo</br> 
     * <b>Usage: </b>{@code Move.undo()}</br>
     * -------------------------------</br>
     * Inserts the promoted pawn back into source location and removes the Queen. 
     */
    public void undo()
    {
        getPiece().putSelfInGrid(promoted.getBoard(), getSource());
        promoted.removeSelfFromGrid();
    }
}
