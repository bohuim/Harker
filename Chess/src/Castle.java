
/**
 * Castling is a special Move in Chess that involves the King and Rook. </br>
 * After a series of strict rules, the King can move towards the outside of the board while Rook moves inwards. </br>
 * The requirements are not checked within the Castle class; class assumes it was created after a check on the requirements has been made. </br></br>
 * 
 * <b>Methods</b>
 * <ul>
 * 		<li>{@code exeucte()} - calls execute on the King and Rook moves </li>
 *      <li>{@code undo()} - calls undo on the King and Rook moves</li>
 * </ul>
 * 
 * @author Dennis Moon
 * @version May 10, 2013
 * 
 * @Revision
 *     May 10, 2013 - class created & finished
 */
public class Castle extends Move
{
    private Move kingMove;
    private Move rookMove;

    /**<b>Constructor: </b>Castle</br> 
     * <b>Usage: </b>{@code Move move = new Castle(king, rook)}</br>
     * -------------------------------</br>
     * Creates a move by calling super constructor with king and rook's current location. </br>
     * Castle is executed under assumption that the requirements for Castling has been checked. </br>
     * The new destinations of King and Rook are determined and two Moves are created as instance fields. </br>
     * 
     * @param king to initiate Castling with
     * @param rook to initiate Castling with
     */
    public Castle(Piece king, Piece rook)
    {
        super(king, rook.getLocation()); //super constructor needs to be called
        
        int dir = king.getLocation().getDirectionToward(rook.getLocation());
        int row = king.getLocation().getRow(), kingCol = 1, rookCol = 2;
        if (dir == Location.EAST)
        {
            kingCol = 6;
            rookCol = 5;
        }
        
        kingMove = new Move(king, new Location(row, kingCol));
        rookMove = new Move(rook, new Location(row, rookCol));
    }

    /**
     * <b>Method: </b>execute</br> 
     * <b>Usage: </b>{@code Move.execute()}</br>
     * -------------------------------</br>
     * Executes both king and rook moves
     */
    public void execute()
    {
        kingMove.execute();
        rookMove.execute();
    }
    
    /**
     * <b>Method: </b>undo</br> 
     * <b>Usage: </b>{@code Move.undo()}</br>
     * -------------------------------</br>
     * Undoes both king and rook moves
     */
    public void undo()
    {
        kingMove.undo();
        rookMove.undo();
    }
}
