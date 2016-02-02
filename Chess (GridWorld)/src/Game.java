import info.gridworld.grid.*;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Main class of the Chess game
 * controls the flow of the game by having two teams
 * turning over controls and checking whether game is over 
 * 
 * @author Dennis Moon
 */
public class Game
{
    private ChessBoard board;
    private Color turn;
    private Piece target;
    private ArrayList<Piece> whiteTeam;
    private ArrayList<Piece> blackTeam;
    
    /**
     * Constructor: Game()
     * Usage: Game game = new Game();
     * -------------------------
     * makes board and adds 2 players to the game
     */
    public Game()
    {
        board = new ChessBoard(this);
        turn = Color.LIGHT_GRAY;
        makeWhiteTeam();
        makeBlackTeam();
        
        board.show();
    }
    
    /**
     * Method: makeWhiteTeam()
     * Usage: initial setup of the game
     * ------------------------------------
     * 1 king, 1 queen, 8 pawns, and 2 of everything else is 
     * created, set white and put into the grid at the right location
     */
    private void makeWhiteTeam()
    {
        whiteTeam = new ArrayList<Piece>();
        
        //pawn
        for (int i=0; i<8; i++)
        {
            whiteTeam.add((new Piece(Piece.PAWN, Color.LIGHT_GRAY)));
            board.add((new Location(6,i)), whiteTeam.get(i));
        }
        //rook
        whiteTeam.add((new Piece(Piece.ROOK, Color.LIGHT_GRAY)));
        board.add((new Location(7,0)), whiteTeam.get(8));
        whiteTeam.add((new Piece(Piece.ROOK, Color.LIGHT_GRAY)));
        board.add((new Location(7,7)), whiteTeam.get(9));
        //knight
        whiteTeam.add((new Piece(Piece.KNIGHT, Color.LIGHT_GRAY)));
        board.add((new Location(7,1)), whiteTeam.get(10));
        whiteTeam.add((new Piece(Piece.KNIGHT, Color.LIGHT_GRAY)));
        board.add((new Location(7,6)), whiteTeam.get(11));
        //bishop
        whiteTeam.add((new Piece(Piece.BISHOP, Color.LIGHT_GRAY)));
        board.add((new Location(7,2)), whiteTeam.get(12));
        whiteTeam.add((new Piece(Piece.BISHOP, Color.LIGHT_GRAY)));
        board.add((new Location(7,5)), whiteTeam.get(13));
        //queen
        whiteTeam.add((new Piece(Piece.QUEEN, Color.LIGHT_GRAY)));
        board.add((new Location(7,4)), whiteTeam.get(14));
        //king
        whiteTeam.add((new Piece(Piece.KING, Color.LIGHT_GRAY)));
        board.add((new Location(7,3)), whiteTeam.get(15));
    }
    
    /**
     * Method: makeBlackTeam()
     * Usage: initial setup of the game
     * ------------------------------------
     * 1 king, 1 queen, 8 pawns, and 2 of everything else is 
     * created, set white and put into the grid at the right location
     */
    private void makeBlackTeam()
    {
        blackTeam = new ArrayList<Piece>();
        
        //pawn
        for (int i=0; i<8; i++)
        {
            blackTeam.add((new Piece(Piece.PAWN, Color.BLACK)));
            board.add((new Location(1,i)), blackTeam.get(i));
        }
        //rook
        blackTeam.add((new Piece(Piece.ROOK, Color.BLACK)));
        board.add((new Location(0,0)), blackTeam.get(8));
        blackTeam.add((new Piece(Piece.ROOK, Color.BLACK)));
        board.add((new Location(0,7)), blackTeam.get(9));
        //knight
        blackTeam.add((new Piece(Piece.KNIGHT, Color.BLACK)));
        board.add((new Location(0,1)), blackTeam.get(10));
        blackTeam.add((new Piece(Piece.KNIGHT, Color.BLACK)));
        board.add((new Location(0,6)), blackTeam.get(11));
        //bishop
        blackTeam.add((new Piece(Piece.BISHOP, Color.BLACK)));
        board.add((new Location(0,2)), blackTeam.get(12));
        blackTeam.add((new Piece(Piece.BISHOP, Color.BLACK)));
        board.add((new Location(0,5)), blackTeam.get(13));
        //queen
        blackTeam.add((new Piece(Piece.QUEEN, Color.BLACK)));
        board.add((new Location(0,4)), blackTeam.get(14));
        //king
        blackTeam.add((new Piece(Piece.KING, Color.BLACK)));
        board.add((new Location(0,3)), blackTeam.get(15));
    }
    
    /**
     * Method: locaitonClicked(selectedLoc)
     * Usage: called by ChessBoard for game to process the game
     * ---------------------------
     * If target is null and clicked location has a piece:
     *      - if piece is current player's color, it is set as target
     * If target is already selected:
     *      - and another piece of same color is selected, target is switched
     *      - if its a valid location for that piece to move to, it is moved
     *      - if target is a rook & selected loc is a king, castle is carried out
     * After a selected target is moved, taret is set to null again
     * 
     * @param loc
     */
    public void locationClicked(Location loc)
    {
        if (target==null && board.getGrid().get(loc)!=null)
        {
            if (!board.getGrid().get(loc).getColor().equals(currentTurn()))
                board.setMessage("That's not your piece!");
            target = (Piece) board.getGrid().get(loc);
            board.setMessage(target + " at " + loc.toString());
        }
        else if (target!=null)
        {
            Piece attacked = (Piece) board.getGrid().get(loc);
            if (attacked!=null && attacked.getType()==Piece.KING && attacked.getTeam().equals(currentTurn()) &&
                    target.getType()==Piece.ROOK && castleValid(target.getLocation()))
            {
                System.out.println("castling");
                Location rookLoc = null;
                Location kingLoc = null;
                if (target.getLocation().getCol()==7)
                {
                    kingLoc = new Location(7,5);
                    rookLoc = new Location(7,6);
                }
                else
                {
                    kingLoc = new Location(7,2);
                    rookLoc = new Location(7,1);
                }
                board.remove(target.getLocation());
                board.remove(attacked.getLocation());
                
                board.add(kingLoc, target);
                board.add(rookLoc, attacked);
                
                nextPlayer();
            }
            else if (attacked!=null && attacked.getColor().equals(currentTurn())) //switch target if another piece is selected
            {
                target = (Piece) board.getGrid().get(loc);
                board.setMessage(target + " at " + loc.toString());
            }
            else if (target.moveIsValid(loc))
            {
                board.remove(target.getLocation());
                board.add(loc, target);
                
                board.setMessage(target + " moved to " + loc.toString());
                if (attacked!=null)
                {
                    board.setMessage(target + " takes " + attacked + " at " + loc.toString());
                    attacked(attacked);
                }
                
                nextPlayer();
                target = null;
            }
            else                
            {
                board.setMessage("You can't move that piece there! Select another piece");
                target = null;
            }
        }
    }
    
    /**
     * Method: castleValid(rook'sLocation)
     * Usage: called by locationClick when rook > king is selected
     * ---------------------------
     * if the rook is not in col 0 or 7, castle cannot happen
     * if any of the spaces in between is not empty, cannot happen
     * 
     * @param rookLoc - location of the rook being castled
     * @return true if castle can happen
     */
    private boolean castleValid(Location rookLoc)
    {
        if (rookLoc.getCol()==0)
        {
            for (int c=rookLoc.getCol()+1; c<3; c++)
            {
                Location checkLoc = new Location(rookLoc.getRow(), c);
                if(board.getGrid().get(checkLoc)!=null)
                    return false;
            }
            return true;
        }
        else if (rookLoc.getCol()==7)
        {
            int kingCol = 4;
            if (currentTurn().equals(Color.WHITE))
                kingCol = 3;
            for (int c=rookLoc.getCol()-1; c>kingCol; c--)
            {
                Location checkLoc = new Location(rookLoc.getRow(), c);
                System.out.println(board.getGrid().get(checkLoc));
                if(board.getGrid().get(checkLoc)!=null)
                    return false;
            }
            return true;
        }
        return false;
    }
    
    /**
     * Method: attacked(Piece)
     * Usage: ChessBoards tells game that a piece was attacked
     * --------------------------
     * the piece is removed from the list
     * 
     * @param attacked - piece to be removed
     */
    public void attacked(Piece attacked)
    {
        if (turn.equals(Color.LIGHT_GRAY))
            blackTeam.remove(attacked);
        else
            whiteTeam.remove(attacked);
        
        if (attacked.getType() == Piece.KING)
            gameOver(attacked.getColor());
    }
    
    /**
     * Method: nextPlayer()
     * Usage: called by ChessBoard after player makes a move
     * ----------------------------
     * turn is handed over to the next player depending 
     * on whose turn is was
     */
    public void nextPlayer()
    {
        if (turn.equals(Color.LIGHT_GRAY))
            turn = Color.BLACK;
        else
            turn = Color.LIGHT_GRAY;
        
        flipBoard();
    }
    
    /**
     * Method: flipBoard()
     * Usage: called when turn is switched over
     * ---------------------------------------
     * all pieces are removed from the board
     * then added back in their flipped locations
     */
    private void flipBoard()
    {
        for (Piece p : whiteTeam)
            board.remove(p.getLocation());
        for (Piece p : blackTeam)
            board.remove(p.getLocation());
        
        for (Piece p : whiteTeam)
            board.add(p.getFlippedLoc(), p);
        for (Piece p : blackTeam)
            board.add(p.getFlippedLoc(), p);
    }
    
    /**
     * Method: currentTurn()
     * Usage: called by ChessBoard to check if player is moving the his/her own piece
     * ----------------------------
     * @return color of current turn;
     */
    public Color currentTurn()
    {
        return turn;
    }
    
    /**
     * Method: gameOver(losingColor)
     * Usage: called by ChessBoard when a king is taken
     * -----------------------
     * The given color loses and system exits
     * 
     * @Precondition: only to be called by ChessBoard when a King is taken
     */
    public void gameOver(Color color)
    {
        if (turn.equals(Color.LIGHT_GRAY))
            board.setMessage("Black loses!");
        else
            board.setMessage("White loses!");
        System.exit(0);
    }
    
    /**
     * Method: main()
     * Usage: the main thread of the Chess game
     * ------------------------------
     * runs the game
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        Game game = new Game();
    }
}
