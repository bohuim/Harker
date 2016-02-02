import java.awt.Color;


/**
 * HumanPlayer is a Player that uses BoardDisplay to allow user to select and move pieces. </br></br>
 * 
 * <b>Methods</b>
 * <ul>
 * 		<li>{@code nextMove()} returns next move made by human user</li>
 * </ul>
 * 
 * @author Dennis Moon
 * @version Apr 30, 2013
 * 
 * @Revision
 *     Apr 30, 2013 - classed created & nextMove() added
 *     May 10, 2013 - nextMove() modified to return Castle Moves
 *     May 16, 2013 - nextMove() modified to account for whether move will put King in check
 */
public class HumanPlayer extends Player
{
    BoardDisplay display;
    
    /**<b>Constructor: </b>HumanPlayer</br> 
     * <b>Usage: </b>{@code Player p1 = new HumanPlayer();}</br>
     * -------------------------------</br>
     * Creates an instance of Player by calling Player() with specified name, color and board; </br>
     * however, takes in an extra BoardDisplay for allowing user to select moves. </br>
     * 
     * @param name of Player
     * @param color of Player's pieces
     * @param board to play on
     */
    public HumanPlayer(String name, Color color, Board board, BoardDisplay display)
    {
        super(name, color, board);
        this.display = display;
    }

    /**
     * <b>Method: </b>nextMove</br> 
     * <b>Usage: </b>{@code Player.nextMove()}</br>
     * -------------------------------</br>
     * Returns a new move selected by user through {@code display.selectMove()} and checking if move is valid. </br>
     * An invalid move or a move that puts the King in check will cause recursive calls until a valid move is selected. </br>
     * 
     * @return new move made by user
     */
    public Move nextMove()
    {
        Move nextMove = display.selectMove();
        
        //test if move will put King in check
        getBoard().executeMove(nextMove);
        if (getBoard().isInCheck(getColor()))
        {
            System.out.println("Cannot execute move! King will be in check!");
            getBoard().undoMove();
            return nextMove();
        }
        getBoard().undoMove();
        
        display.clearColors();
        if (getBoard().allMoves(getColor()).contains(nextMove) || nextMove instanceof Castle)
            return nextMove;
        
        return nextMove();
    }
}
