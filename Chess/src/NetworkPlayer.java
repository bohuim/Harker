import java.awt.Color;
import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * NetworkPlayer is the Player that represents the user on the other side of a network game. </br>
 * It receives a protocol through the input stream of a socket, which is converted into a move and executed. </br> </br>
 * 
 * <b>Methods</b>
 * <ul>
 * 		<li>{@code nextMove()} - returns the Move converted from protocol received from other end of network game</li>
 * </ul>
 * 
 * @author Dennis Moon
 * @version May 19, 2013
 * 
 * @Revision
 *     May 19, 2013 - class created 
 *     May 22, 2013 - modified to account for protocols that represent different types of Moves
 */
public class NetworkPlayer extends Player
{
    private BufferedReader in;
    
    /**<b>Constructor: </b>NetworkPlayer</br> 
     * <b>Usage: </b>{@code Player p = new NetworkPlayer(name, color, board, in)}</br>
     * -------------------------------</br>
     * Creates a new NetworkPlayer by calling super constructor and setting in as BufferedReader to the Socket.
     * 
     * @param name of Player
     * @param color Player has control over
     * @param board containing Pieces
     * @param in - BufferedReader connected to input stream of Socket
     */
    public NetworkPlayer(String name, Color color, Board board, BufferedReader in)
    {
        super(name, color, board);
        this.in = in;
    }

    /**<b>Method: </b></br> 
     * <b>Usage: </b>{@code }</br>
     * -------------------------------</br>
     * Receives the protocol by reading from BufferedReader connected to Socket's input stream. </br>
     * Returns the Move converted from String protocol to Game to be executed in {@code nextTurn()}
     * 
     * @return Move represented by received protocol
     */
    public Move nextMove()
    {
        String info = null;
        try
        {
            info = in.readLine();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return convertFromProtocol(info);
    }
    
    /**
     * <b>Method: </b>convertFromProtocol</br> 
     * <b>Usage: </b>{@code convertFromProtocol(string)}</br>
     * -------------------------------</br>
     * Splits the String protocol received as an array. </br>
     * part[0] indicates the type of Move received. </br>
     * part[1] indicates the location of the source as RowCol without any marker in between. </br>
     * part[2] indicates the locaiton of the destination as RowCol without any maker in between. </br>
     * ex) "Move 34 54" represents a normal Move with piece at (3,4) to be moved to (5,4) on the board.
     * 
     * @param protocol defined in a network Chess game
     * @return Move represented by protocol
     */
    private Move convertFromProtocol(String protocol)
    {
        Board board = getBoard();
        
        String[] part = protocol.split(" ");
        String type = part[0];
        Piece target = board.get(new Location(Integer.parseInt(part[1].substring(0,1)),
                                              Integer.parseInt(part[1].substring(1,2))));
        
        Location destination = new Location(Integer.parseInt(part[2].substring(0,1)),
                                            Integer.parseInt(part[2].substring(1,2)));

        Move move = null;
        if (type.equals("move")) move = new Move(target, destination);
        else if (type.equals("castle")) move = new Castle(target, board.get(destination));
        else if (type.equals("promotion")) move = new Promotion(target, destination);
        
        return move;
    }
}
