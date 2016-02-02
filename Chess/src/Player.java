import java.awt.*;
import java.util.*;

/**
 * Player represents a Chess player, but is an abstract class for instantiation of different types of Players. </br>
 * Player does not have direct access to his/her Pieces, 
 * but instead selects a nextMove from allMoves the board returns and asks the board to execute that move. </br>
 * Keeps instance fields of name, color(team), and board. </br></br>
 * 
 * <b>Methods</b>
 * <ul>
 * 		<li>{@code getName()} returns name of Player</li>
 *      <li>{@code getColor()} returns color or team of Player</li>
 *      <li>{@code getBoard()} returns board Player is currently using</li>
 *      <li>{@code nextMove()} returns next move Player will make (should be implemented specifically by subclass)</li>
 * </ul>
 * 
 * @author Dennis Moon
 * @version Apr 29, 2013
 * 
 * @Revision
 *     Apr 29, 2013 - class created & getters and nextMove() added
 */
public abstract class Player
{
    private String name;
    private Color color;
    private Board board;
    
    /**
     * <b>Constructor: </b>Player</br> 
     * <b>Usage: </b>{@code Player p1 = new HumanPlayer();}</br>
     * -------------------------------</br>
     * Creates instance new Player with given name, color, and board through new subclass.
     * 
     * @param name of Player
     * @param color of Player's pieces
     * @param board to play on
     */
    public Player (String name, Color color, Board board)
    {
        this.name = name;
        this.color = color;
        this.board = board;
    }
    
    /**
     * <b>Method: </b>getName</br> 
     * <b>Usage: </b>{@code Player.getName()}</br>
     * -------------------------------</br>
     * Returns name of Player.
     * 
     * @return name of Player
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * <b>Method: </b>getColor</br> 
     * <b>Usage: </b>{@code Player.getColor()}</br>
     * -------------------------------</br>
     * Return color of Player's pieces, or essentially team.
     * 
     * @return color of pieces
     */
    public Color getColor()
    {
        return color;
    }
    
    /**
     * <b>Method: </b>getBoard</br> 
     * <b>Usage: </b>{@code Player.getBoard}</br>
     * -------------------------------</br>
     * Return board currently playing on.
     * 
     * @return board that contains pieces
     */
    public Board getBoard()
    {
        return board;
    }
    
    /**
     * <b>Method: </b>nextMove</br> 
     * <b>Usage: </b>{@code Player.nextMove()}</br>
     * -------------------------------</br>
     * Returns the next move this Player will execute. To be implemented specifically by each subclass of Player. 
     * 
     * @return next move to be executed
     */
    public abstract Move nextMove();
}
