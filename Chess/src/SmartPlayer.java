import java.awt.*;
import java.util.*;

/**
 * SmartPlayer is an advanced AI opponent that, instead of choosing a random Move, calculates one step into the future and uses the best Move </br></br>
 * 
 * <b>Methods</b>
 * <ul>
 * 		<li>{@code nextMove()} returns the best Move available at the moment</li>
 * </ul>
 * 
 * @author Dennis Moon
 * @version May 15, 2013
 * 
 * @Revision
 *     May 15, 2013 - class created
 *     May 16, 2013 - valueOfMeanestResponse() added
 *     May 18, 2013 - valueOfBestMove() added
 */
public class SmartPlayer extends Player
{
    int level;
    
    /**<b>Constructor: </b>SmartPlayer</br> 
     * <b>Usage: </b>{@code Player p2 = new SmartPlayer(name, color, board)}</br>
     * -------------------------------</br>
     * Creates a new SmartPlayer by calling constructor of Player class.
     * 
     * @param name of SmartPlayer
     * @param color of SmartPlayer
     * @param board of chess game
     */
    public SmartPlayer(String name, Color color, int lvl, Board board)
    {
        super(name, color, board);
        level = lvl;
    }

    /**<b>Method: </b>nextMove</br> 
     * <b>Usage: </b>{@code player.nextMove()}</br>
     * -------------------------------</br>
     * Returns the best move by checking one step into the future. </br>
     * Checks the board status for SmartPlayer after all available moves, selects the best move, and undoes the move. </br>
     * 
     * @return best Move after calculating one step ahead
     */
    public Move nextMove()
    {
        Iterator<Move> it = getBoard().allMoves(getColor()).iterator();
        Move bestMove = null;
        
        int max = -1000;
        while (it.hasNext())
        {
            Move testMove = it.next();
            getBoard().executeMove(testMove);
            
            int testScore = valueOfMeanestRepsonse(level);
            if (testScore > max && !getBoard().isInCheck(getColor()))
            {
                max = testScore;
                bestMove = testMove;
            }
            getBoard().undoMove();
        }
        
        return bestMove;
    }
    
    /**
     * <b>Method: </b>valueOfMeanestResponse</br> 
     * <b>Usage: </b>{@code valueOfMeanestResponse()}</br>
     * -------------------------------</br>
     * Inter-method recursive method with {@code valueOfBestMove()}
     * <ul>
     *  <li>Base Case: {@code n == 0} </br>
     *      - return value given by {@code score()}</li>
     *  <li>Reduction:
     *      - Checks all moves opponent can make and returns value of best move opponent can make</li>
     * </ul>
     * 
     * @param n
     * @return score for most dangerous enemy response
     */
    private int valueOfMeanestRepsonse(int n)
    {
         if (n==0)
             return score();
        
        Color enemyColor = Game.enemyColorOf(getColor());
        Iterator<Move> it = getBoard().allMoves(enemyColor).iterator();
        
        int min = 0; 
        while(it.hasNext())
        {
            Move testMove = it.next();
            getBoard().executeMove(testMove);
            
            int testScore = valueOfBestMove(n-1);
            if (testScore < min)
                min = testScore;
            
            getBoard().undoMove();
        }
        return min;
    }
    
    /**
     * <b>Method: </b>valueOfBestMove</br> 
     * <b>Usage: </b>{@code valueOfBestValue()}</br>
     * -------------------------------</br>
     * Inter-method recursive method with valueOfMeanestValue().
     * <ul>
     *  <li>Base Case: {@code n == 0} </br>
     *      - returns value given by {@code score()}</li>
     *  <li>Reduction: </br>
     *      - Checks all moves possible and returns the highest int returned by {@code valueOfMeanestValue(n-1)}
     * </ul>
     * 
     * @param n - depth of recursion to calculate ahead
     * @return int value of worst move opponent can make
     */
    private int valueOfBestMove(int n)
    {
        if (n==0)
            return score();
        
        Iterator<Move> it = getBoard().allMoves(getColor()).iterator();
        
        int max = -1000;
        while(it.hasNext())
        {
            Move testMove = it.next();
            getBoard().executeMove(testMove);
            
            int testScore = valueOfMeanestRepsonse(n-1);
            if (testScore > max)
                max = testScore;
            
            getBoard().undoMove();
        }
        return max;
    }
    
    /**
     * <b>Method: </b>score</br> 
     * <b>Usage: </b>{@code score()}</br>
     * -------------------------------</br>
     * Returns the total sum of SmartPlayer's pieces subtract the sum of enemy's pieces. </br>
     * Runs a nested for loop, adding to sum SmartPlayer's pieces and subtracting enemy's pieces. </br>
     * 
     * @return sum of SmartPlayer's pieces - sum of enemy's pieces
     */
    private int score()
    {
        int sum = 0;
        for (int row=0; row<getBoard().getNumRows(); row++)
            for (int col=0; col<getBoard().getNumCols(); col++)
            {
                Piece piece = getBoard().get(new Location(row, col));
                if (piece!=null)
                {
                    if (piece.getColor().equals(getColor()))
                        sum += piece.getValue();
                    else
                        sum -= piece.getValue();
                }
            }
        return sum;
    }
}