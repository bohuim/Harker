import java.awt.Color;
import java.util.*;

/**
 * RandomPlayer is AI Player that chooses its moves completely randomly, providing a weak opponent. </br></br>
 * 
 * <b>Methods</b>
 * <ul>
 * 		<li>{@code nextMove()}} returns newly selected random move</li>
 * </ul>
 * 
 * @author Dennis Moon
 * @version Apr 29, 2013
 * 
 * @Revision
 *     Apr 29, 2013 - class created and nextMove() implemented
 *     May 13, 2013 - nextMove() modified to randomly select Castling
 */
public class RandomPlayer extends Player
{
    
    /**
     * <b>Constructor: </b>Player</br> 
     * <b>Usage: </b>{@code Player p1 = new RandomPlayer();}</br>
     * -------------------------------</br>
     * Creates instance of Player with given name, color, and board.
     * 
     * @param name of Player
     * @param color of Player's pieces
     * @param board to play on
     */
    public RandomPlayer(String name, Color color, Board board)
    {
        super(name, color, board);
    }

    /**
     * <b>Method: </b>nextMove</br> 
     * <b>Usage: </b>{@code Player.nextMove()}</br>
     * -------------------------------</br>
     * Returns a new move by selecting randomly from all possible moves through array conversion and {@code Math.random()} and Castling if available
     * 
     * @return new random move
     */
    public Move nextMove()
    {
        Set<Move> set = getBoard().allMoves(getColor());
        
        Piece king = getBoard().getKing(getColor());
        Piece rookWest = getBoard().getRook(getColor(), Location.WEST);
        Piece rookEast = getBoard().getRook(getColor(), Location.EAST);
        if (getBoard().checkCastle(getColor(), Location.WEST)) 
            set.add(new Castle(king, rookWest));
        if (getBoard().checkCastle(getColor(), Location.EAST))
            set.add(new Castle(king, rookEast));
        
        return (Move) set.toArray()[(int) (Math.random()*set.size())];
    }
}
