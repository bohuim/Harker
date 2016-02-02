import java.awt.Color;
import java.io.*;
import java.net.*;

/**
 * ChessClient provides the Game class extension for connecting to a host for a Network Chess game. </br>
 * Keeps instance field of the Socket and BufferedReader/PrintWriter to Socket's input/output streams. </br>
 * Client-side user is always black and makes the second move. </br>
 * 
 * @author Dennis Moon
 * @version May 20, 2013
 * 
 * @Revision
 *     May 20, 2013
 */
public class ChessClient extends Game
{
    private Socket clientSocket;
    
    private BufferedReader in;
    private PrintWriter out;
    
    public static void main(String[] args) throws IOException
    {
        ChessClient client = new ChessClient("127.0.0.1", 1807); //127.0.0.1 directs request back to this computer as host
        client.play();
    }
    
    /**
     * <b>Constructor: </b>ChessClient</br> 
     * <b>Usage: </b>{@code ChessClient client = new ChessClient(host, port)}</br>
     * -------------------------------</br>
     * Client keeps its own instance of Game by calling default super constructor and attempts to connect to the server. </br>
     * New BufferedReader & PrintWriters are created to Socket's input & output streams. </br>
     * Since client-side user is always Black who goes second, display is flipped. </br> 
     * First player is setup as NetworkPlayer that makes moves by receiving protocols through the BufferedReader. </br>
     * User on this end of network game is setup as NetworkHumanPlayer that sends off its selected Moves through PrintWriter. </br>
     * 
     * @param host ip of server
     * @param port server is listening on
     * @throws IOException when BufferedReader/PrintWriter encounters problems with Socket's streams 
     */
    public ChessClient(String host, int port) throws IOException
    {
        connectToServer(host, port);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        
        getDisplay().flip();
        setPlayer(1, new NetworkPlayer("Enemy", Color.WHITE, getBoard(), in));
        setPlayer(2, new NetworkHumanPlayer("Me", Color.BLACK, getBoard(), getDisplay(), out));
    }
    
    /**
     * <b>Method: </b>connectToServer</br> 
     * <b>Usage: </b>{@code connectToServer(port)}</br>
     * -------------------------------</br>
     * Attempts to connect to the specified host ip at the specified port number. </br>
     * An error is thrown if no such host or port exists. </br>
     * 
     * @param host ip of server 
     * @param port server is listening on
     */
    private void connectToServer(String host, int port)
    {
        try 
        {
            clientSocket = new Socket(host, port);  
        } 
        catch (UnknownHostException e) 
        {
            System.err.println("Don't know about host: " + host);
            System.exit(1);
        } 
        catch (IOException e)
        {
            System.err.println("Couldn't get I/O for the connection to: " + host);
            System.exit(1);
        }
    }
}
