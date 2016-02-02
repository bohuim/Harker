import java.awt.Color;
import java.io.*;
import java.net.*;

/**
 * NetworkHumanPlayer is the player class that connects the user to a network game. </br>
 * On each turn, NetworkHumanPlayer is able to select a move through the BoardDisplay, and the selected move is sent out as a protocol to socket. </br></br>
 * 
 * <b>Methods</b>
 * <ul>
 * 		<li>{@code nextMove()} - allows user to select Move through display and sends it out as a protocol</li>
 * </ul>
 * 
 * @author Dennis Moon
 * @version May 20, 2013
 * 
 * @Revision
 *     May 20, 2013 - class created
 *     May 22, 2013 - modified to send Protocols that incorporate Castling & Promotion
 */
public class NetworkHumanPlayer extends HumanPlayer
{
    private PrintWriter out;

    /**<b>Constructor: </b>NetworkHumanPlayer</br> 
     * <b>Usage: </b>{@code Player p1 = new NetworkHumanPlayer(name, color, board, display, out)}</br>
     * -------------------------------</br>
     * Creates NetworkHumanPlayer by calling super constructor and setting out as given PrintWriter.
     * 
     * @param name of this NetworkHumanPlayer
     * @param color of this NetworkHUmanPlayer has control over
     * @param board containing the peices
     * @param display to choose moves from
     * @param out - a PrintWriter to a Server/Socket's output stream
     */
    public NetworkHumanPlayer(String name, Color color, Board board, BoardDisplay display, PrintWriter out)
    {
        super(name, color, board, display);
        this.out = out;
    }
    
    /**
     * <b>Method: </b>nextMove</br> 
     * <b>Usage: </b>{@code player.nextMove()}</br>
     * -------------------------------</br>
     * Lets the user select a move by calling HumanPlayer's {@code nextMove()} </br>
     * Converts the move into a protocol and writes the protocol into the Socket's output stream. </br>
     * The move returned by {@code super.nextMove()} is returned to Game class to be executed in {@code game.nextTurn()}
     * 
     * @return nextMove selected by user
     */
    public Move nextMove()
    {
        Move nextMove = super.nextMove();
        out.println(convertToProtocol(nextMove));
        return nextMove;
    }
    
    /**
     * <b>Method: </b>convertToProtocol</br> 
     * <b>Usage: </b>{@code convertToProtocol(move)}</br>
     * -------------------------------</br>
     * Given a move, returns the String protocol of the move. </br>
     * Each move has a the prefixes "move," "castle," or "promotion" based what type of move. </br>
     * Protocols are defined as "AB CD" where A is the source location row, B the source col, C the destination row, D the destination col. 
     * 
     * @param move to be converted into protocol
     * @return String protocol of the given move
     */
    private String convertToProtocol(Move move)
    {
        Location source = move.getSource();
        Location destination = move.getDestination();
        
        String type = "move ";
        if (move instanceof Castle) type = "castle ";
        else if (move instanceof Castle) type = "promotion ";
        
        return type + source.getRow() + source.getCol() + " " + destination.getRow() + destination.getCol();
    }
}