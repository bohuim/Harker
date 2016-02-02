import java.awt.Color;
import java.util.ArrayList;

import info.gridworld.actor.Actor;
import info.gridworld.grid.*;

/**
 * Piece is a one chess piece
 * it knows if it's dead, and holds it's flipped location
 * 
 * @author Dennis Moon
 */
public class Piece extends Actor
{   
    public static final int PAWN = 0;
    public static final int ROOK = 1;
    public static final int KNIGHT = 2;
    public static final int BISHOP = 3;
    public static final int QUEEN = 4;
    public static final int KING = 5;
    
    private Color team;
    private int type;
    private Location flippedLoc;
    
    /**
     * Constructor: Piece(teamColor)
     * Usage: used by game to create teams
     * -------------------------------------
     * Piece takes in a color either black or white
     * 
     * @Precondition: given color is black or white
     */
    public Piece(int type, Color color)
    {
        this.type = type;
        team = color;
        setColor(color);
        flippedLoc = null;
    }
    
    /**
     * Method: getType()
     * Usage: Piece.getType()
     * ------------------------
     * @return type of this piece
     */
    public int getType()
    {
        return type;
    }
    
    /**
     * Method: getTeam()
     * Usage: used by chessboard to determine if this piece is on the current player's team
     * -------------------------
     * @return team - color of the team
     */
    public Color getTeam()
    {
        return team;
    }
    
    /**
     * Method: putSelfInGrid(grid, location)
     * Usage: used by board or game to add a piece
     * -------------------------
     * Same as super class but 
     * calculates flipped location and stores it for future use
     */
    public void putSelfInGrid(Grid<Actor> gr, Location loc)
    {
        super.putSelfInGrid(gr, loc);
        flippedLoc = (new Location(7-getLocation().getRow(), 7-getLocation().getCol()));
    }
    
    /**
     * Method: getFlippedLoc()
     * Usage: used by game class to flip the locs of all pieces
     * ----------------------------------
     * @return flippedLoc;
     */
    public Location getFlippedLoc()
    {
        return flippedLoc;
    }
    
    /**
     * Method: getImageSuffix()
     * Usage: used by gui to get different images of the same class
     * -----------------------------------
     * @return "_<PieceType>" ex.) "_Pawn"
     */
    public String getImageSuffix()
    {
        if (team.equals(Color.BLACK))
        {
            if (type == PAWN) return "";
            else if (type == ROOK) return "_Rook";
            else if (type == KNIGHT) return "_Knight";
            else if (type == BISHOP) return "_Bishop";
            else if (type == QUEEN) return "_Queen";
            else return "_King";
        }
        else
        {
            if (type == PAWN) return "_White";
            else if (type == ROOK) return "_Rook_White";
            else if (type == KNIGHT) return "_Knight_White";
            else if (type == BISHOP) return "_Bishop_White";
            else if (type == QUEEN) return "_Queen_White";
            else return "_King_White";
        }
    }
    
    /**
     * Method: moveIsValid(newLoc)
     * Usage: called by world when moving chess pieces
     * ------------------------------------
     * true is 
     * 
     * @Precondition: grid this Piece is in only has Piece objects
     * @return whether given loc is valid for this piece
     */
    public boolean moveIsValid(Location loc)
    {
        Piece existingPiece = (Piece) getGrid().get(loc);
        
        if (type == PAWN) return validForPawn(loc);
        else if (type == ROOK) return validForRook(loc);
        else if (type == KNIGHT) return validForKnight(loc);
        else if (type == BISHOP) return validForBishop(loc);
        else if (type == QUEEN) return validForQueen(loc);
        else return validForKing(loc);
    }
    
    /**
     * Method: validForPawn(newLoc)
     * Usage: called by moveIsValid on a Pawn Piece
     * ------------------------------
     * given loc is valid when:
     *      - 1 step ahead & that loc has no piece
     *      - 1 step diagonal to either direction & that loc has a piece
     *      - 2 setps ahead if pawn is in start position
     *      
     * @param loc - to check if valid
     * @return true if any of the described conditions are met
     */
    private boolean validForPawn(Location loc)
    {
        
        Location ahead1 = getLocation().getAdjacentLocation(Location.NORTH);
        if (loc.equals(ahead1) && getGrid().get(ahead1)==null)
            return true;
        
        Location diagLeft = ahead1.getAdjacentLocation(Location.WEST);
        if (loc.equals(diagLeft) && getGrid().get(diagLeft)!=null)
            return true;
        
        Location diagRight = ahead1.getAdjacentLocation(Location.EAST);
        if (loc.equals(diagRight) && getGrid().get(diagRight)!=null)
            return true;
        
        Location ahead2 = ahead1.getAdjacentLocation(Location.NORTH);
        if (loc.equals(ahead2) && getLocation().getRow()==6)
            return true;
        
        return false;
    }
    
    /**
     * Method: validForRook(newLoc)
     * Usage: called by moveIsValid on a Rook Piece
     * ------------------------------
     * given loc is valid when:
     *      - the direction in the new loc is divisible by 90
     *      - no other piece can be in a bishop's path except the one on the destination loc
     *      
     * @param loc - to check if valid
     * @return true if any of the described conditions are met
     */
    private boolean validForRook(Location loc)
    {
        int direction = getLocation().getDirectionToward(loc);
        if (direction%90 == 0)
        {
            Location nextLoc = getLocation().getAdjacentLocation(direction);
            while (!nextLoc.equals(loc))
            {
                if (getGrid().get(nextLoc)!=null)
                    return false;
                nextLoc = nextLoc.getAdjacentLocation(direction);
            }
            return true;
        }
        return false;
    }
    
    /**
     * Method: validForKnight(newLoc)
     * Usage: called by moveIsValid on a Knight Piece
     * ------------------------------
     * given loc is valid when:
     *      - new loc is in a clockwise or counter clockwise L shape
     *      
     * @param loc - to check if valid
     * @return true if any of the described conditions are met
     */
    private boolean validForKnight(Location loc)
    {
        int rowDiff = Math.abs(getLocation().getRow() - loc.getRow());
        int colDiff = Math.abs(getLocation().getCol() - loc.getCol());
        
        if ((rowDiff==2 && colDiff==1) || (rowDiff==1 && colDiff==2))
            return true;
        return false;
    }
    
    /**
     * Method: validForBishop(newLoc)
     * Usage: called by moveIsValid on a Bishop Piece
     * ------------------------------
     * given loc is valid when:
     *      - direction in the new loc is divisble by 45 but not 90
     *      - no other piece can be in a bishop's path except the one on the destination loc
     *      
     * @param loc - to check if valid
     * @return true if any of the described conditions are met
     */
    private boolean validForBishop(Location loc)
    {
        int direction = getLocation().getDirectionToward(loc);
        if ((direction%45 == 0) && (direction%90 != 0))
        {
            Location nextLoc = getLocation().getAdjacentLocation(direction);
            while (!nextLoc.equals(loc))
            {
                System.out.println(getGrid().get(nextLoc));
                if (getGrid().get(nextLoc)!=null)
                    return false;
                nextLoc = nextLoc.getAdjacentLocation(direction);
            }
            return true;
        }
        return false;
    }
    
    /**
     * Method: validFor(newLoc)
     * Usage: called by moveIsValid on a  Piece
     * ------------------------------
     * given loc is valid when:
     *      - new loc satisfies rook, bishop conditions
     *      
     * @param loc - to check if valid
     * @return true if any of the described conditions are met
     */
    private boolean validForQueen(Location loc)
    {
        if (validForRook(loc) || validForBishop(loc))
            return true;
        return false;
    }
    
    /**
     * Method: validFor(newLoc)
     * Usage: called by moveIsValid on a  Piece
     * ------------------------------
     * given loc is valid when:
     *      - new loc is in the immediate surrounding of king
     *      
     * @param loc - to check if valid
     * @return true if any of the described conditions are met
     */
    private boolean validForKing(Location loc)
    {
         if (getGrid().getEmptyAdjacentLocations(getLocation()).contains(loc) ||
                 getGrid().getOccupiedAdjacentLocations(getLocation()).contains(loc))
                 return true;
         return false;
    }
    
    /**
     * Method: toString()
     * Usage: mainly used for setMessage in ChessBoard
     * --------------------------------
     * @return each piece is displayed as PieceType (teamColor)
     */
    public String toString()
    {
        String str = "";
        if (type == PAWN) str = "Pawn ";
        else if (type == ROOK) str = "Rook ";
        else if (type == KNIGHT) str = "Knight ";
        else if (type == BISHOP) str = "Bishop ";
        else if (type == QUEEN) str = "Queen ";
        else str = "King ";
        
        if (team.equals(Color.BLACK))
            return str + "(Black)";
        else
            return str + "(White)";
    }
}
