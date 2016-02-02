import java.awt.Color;
import java.io.*;
import java.net.*;

/**
 * ChessServer provides the Game class extension for hosting a server for a network Chess game. </br>
 * Keeps instance fields of the serverSocket, clientSocket, and input/output streams to the client socket. </br>
 * Server-side host is always White and makes the first move</br>
 * 
 * @author Dennis Moon
 * @version May 20, 2013
 * 
 * @Revision
 *     May 20, 2013 - class created & finished
 */
public class ChessServer extends Game 
{
    private ServerSocket serverSocket;
    private Socket clientSocket;
    
    private BufferedReader in;
    private PrintWriter out;
    
    public static void main(String[] args) throws IOException
    {
        ChessServer server = new ChessServer(1807);
        server.play();
    }
    
    /**
     * <b>Constructor: </b>ChessServer</br> 
     * <b>Usage: </b>{@code ChessServer = new ChessServer(port#)}</br>
     * -------------------------------</br>
     * Server has its own instance of Game by calling default constructor of Game and opens up a server on the specified port number. </br>
     * When a client is accepted, BufferedReader and PrintWriters to the clientSocket's input & output streams are setup. </br>
     * User hosting always makes first the move and is setup as NetworkHumanPlayer connected to the output stream for telling client-side what Move was chosen. </br> 
     * Connecting user is setup as NetworkPlayer that receives Moves from client-side and applies to Server instance of the game. </br>
     * 
     * @param port number to start server on
     * @throws IOException when BufferedReader/PrintWriter encounters problems with the Socket's streams
     */
    public ChessServer(int port) throws IOException
    {
        startServer(port);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        
        setPlayer(1, new NetworkHumanPlayer("Me", Color.WHITE, getBoard(), getDisplay(), out));
        setPlayer(2, new NetworkPlayer("Enemy", Color.BLACK, getBoard(), in));
    }
    
    /**
     * <b>Method: </b>setup</br> 
     * <b>Usage: </b>{@code setup(port)}</br>
     * -------------------------------</br>
     * Creates a new ServerSocket with specified port and waits until a client connects.
     * 
     * @param port number to listen on
     */
    private void startServer(int port)
    {
        try { serverSocket = new ServerSocket(port); } 
        catch (IOException e) 
        {
            System.out.println("Could not listen on port: " + port);
            System.exit(-1);
        }
        
        try { clientSocket = serverSocket.accept(); } 
        catch (IOException e)
        {
            System.out.println("Accept failed: " + port);
            System.exit(-1);
        }
        
        System.out.println("client accepted");
    }
}
