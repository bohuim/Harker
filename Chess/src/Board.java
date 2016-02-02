import java.awt.*;
import java.util.*;

/**
 * Represents a rectangular game board, containing Piece objects, through a BoundedGrid. </br></br>
 * 
 * <b>Methods</b>
 * <ul>
 * 		<li>{@code allMove(color)} returns a set of all moves available by Pieces of specified color </li>
 *      <li>{@code executeMove(move)} executes a move by placing the specified Piece at the destination </li>
 *      <li>{@code undoMove(move)} reverses a move by placing the specified Piece back to source location </li>
 *      <li>{@code checkCastle(color, dir)} returns whether Castling is available for color to the specified direction </li>
 *      <li>{@code isUnderAttack(color, location)} checks whether the specified location is under attack by color opposite of specified color</li>
 *      <li>{@code promote(pawn)} replaces given pawn with Queen at its location </li>
 *      <li>{@code reset()} calls {@code undo} on all Moves in the stack to reset the game</li>
 * </ul>
 * 
 * @author Dennis Moon
 * @version Apr 29, 2013
 * 
 * @Revision
 *     Apr 29, 2013 - class created and added allMoves()
 *     May 1, 2013 - added reverseColor() method
 *     May 10, 2013 - modified so that executeMove() & undoMove() are part of the Move class
 *     May 10, 2013 - moved checkCastle() from King to Board class & added findKing(), findRook(), isUnderAttack() methods
 *     May 12, 2013 - promotion() replaces any pawn that reaches 8th rank with a queen
 *     May 12, 2013 - executeMove() & undoMove() added back: calls Move classes execute() & undo() along with other GUI based calls
 *     May 18, 2013 - promote() made into a subclass of Move as Promotion class
 */
public class Board extends BoundedGrid<Piece>
{
    private Stack<Move> stack;
    
	/**
	 * <b>Constructor: </b>Board</br> 
	 * <b>Usage: </b>{@code Board board = new Board()}</br>
	 * -------------------------------</br>
	 * Creates a new BoundedGrid of dimension 8x8, replicating a Chess board and a new Stack of Moves.
	 */
	public Board()
	{
		super(8, 8);
		stack = new Stack<Move>();
	}

	/**
	 * <b>Method: </b>allMoves</br> 
	 * <b>Usage: </b>{@code board.allMoves(color)}</br>
	 * -------------------------------</br>
	 * Returns a set of all moves possible by all Pieces of the specified color. </br>
	 * Checks all occupied locations for the specified color and adds its possible destinations as new Moves to set. </br>
	 * 
	 * @param color of Pieces to get moves from
	 * @return Set of all moves possible by Pieces of specified color
	 */
	public Set<Move> allMoves(Color color)
	{
	    Set<Move> set = new HashSet<Move>();
	    for(Location loc : getOccupiedLocations())
	    {
	        Piece target = get(loc);
	        if (target.getColor().equals(color))
	        {
	            Iterator<Location> it = target.destinations();
	            while (it.hasNext())
	                set.add(new Move(target, it.next()));
	        }
	    }
	    return set;
	}
	
	/**
	 * <b>Method: </b>executeMove</br> 
	 * <b>Usage: </b>{@code Board.executeMove(move)}</br>
	 * -------------------------------</br>
	 * Execute move by calling {@code move.execute()} and then adds the move to stack. </br>
	 * 
	 * @param move to be executed
	 */
	public void executeMove(Move move)
	{
	    move.execute();
	    stack.add(move);
	}
	
	/**
	 * <b>Method: </b>undoMove</br> 
	 * <b>Usage: </b>{@code Board.undoMove(display)}</br>
	 * -------------------------------</br>
	 * Pops a Move off the stack and calls undo() by calling {@code stack.pop().undo()} </br>
	 */
	public void undoMove()
	{
	    if (!stack.isEmpty())
	        stack.pop().undo();
	}
	
	/**
	 * <b>Method: </b>reset</br> 
	 * <b>Usage: </b>{@code Board.reset()}</br>
	 * -------------------------------</br>
	 * Resets the game by clearing out the Stack of Moves 
	 */
	public void reset()
	{
	    while (!stack.isEmpty())
	        stack.pop().undo();
	}
	
	/**
	 * <b>Method: </b>findKing</br> 
	 * <b>Usage: </b>{@code findKing(color)}</br>
	 * -------------------------------</br>
	 * Returns pointer to King of the specified color.
.	 * 
	 * @param color of King to find
	 * @return pointer to the King
	 */
	public Piece getKing(Color color)
	{
	    for (Location loc : getOccupiedLocations())
	        if(get(loc).getColor().equals(color) && get(loc) instanceof King)
	            return get(loc);
	    return null;
	}
	
	/**
	 * <b>Method: </b>findRook</br> 
	 * <b>Usage: </b>{@code findRook(color, WEST/EAST)}</br>
	 * -------------------------------</br>
	 * Under the assumption that neither Rooks have been moved, finds the Rook of the specified color to the specified direction.
	 * 
	 * @precondition Rooks have not been moved
	 * @param color
	 * @param dir
	 * @return
	 */
	public Piece getRook(Color color, int dir)
	{
	    int row = 0;
        if (color.equals(Color.WHITE))
            row = 7;
	    int col = 0;
        if (dir == Location.EAST)
            col = 7;
        return get(new Location(row, col));
	}
	
	/**
	 * <b>Method: </b>checkCastle</br> 
	 * <b>Usage: </b>{@code Board.checkCastle(color, direction)}</br>
	 * -------------------------------</br>
	 * Checks whether castling is available with Rook towards the specified direction. </br>
	 * Following conditions must be met for castling to be available: </br>
	 * <ul>
	 *     <li>King & Rook found by {@code findKing(), findRook()} are indeed rooks</li>
	 *     <li>Neither were ever moved</li>
	 *     <li>No pieces between the King and Rook exists<li>
	 *     <li>King & locations between King & Rook are not under attack</li>
	 * </ul>
	 * 
	 * @param color of King to check Castle for
	 * @param dir of Rook relative King
	 * @return true if Castling is available
	 */
	public boolean checkCastle(Color color, int dir)
	{
	    Piece king = getKing(color);
	    Piece rook = getRook(color, dir);
	    Location rookLoc = null;
	    if (rook == null)
	        return false;
	    rookLoc = rook.getLocation();
	    
	    //Check 1: piece is indeed a rook
        if (!(king instanceof King && rook instanceof Rook))
            return false;

        //Check 2: neither King/rook piece was moved
        if (king.wasMoved() || rook.wasMoved()) return false;
        
        //Check 3: no pieces in between King&rook exists & 
        //Check 4: King & locations between it and rook are not under attack
        if (isUnderAttack(color,king.getLocation()))
            return false;
        Location targetLoc = king.getLocation().getAdjacentLocation(dir);
        while (!targetLoc.equals(rookLoc))
        {
            if (get(targetLoc)!=null || isUnderAttack(color, targetLoc))
                return false;
            targetLoc = targetLoc.getAdjacentLocation(dir);
        }
        
        return true;
	}
	
	/**
	 * <b>Method: </b>isUnderAttack</br> 
	 * <b>Usage: </b>{@code Board.isUnderAttack(color, loc)}</br>
	 * -------------------------------</br>
	 * Checks whether the specified Location is under attack by enemy team of specified color. </br>
	 * Takes {@code allMoves(enemyColor)} and checks if it {@code contains(loc)}
	 * 
	 * @param color of location being attacked
	 * @param loc to check if is under attack
	 * @return true if locaiton is under attack
	 */
	public boolean isUnderAttack(Color color, Location loc)
    {
	    for(Move move : allMoves(Game.enemyColorOf(color)))
	        if(move.getDestination().equals(loc))
	            return true;
	    return false;
    }
	
	/**
	 * <b>Method: </b>isInCheck</br> 
	 * <b>Usage: </b>{@code Board.isInCheck(color)}</br>
	 * -------------------------------</br>
	 * Returns whether King of specified color is in check.
	 * 
	 * @param color of King to check
	 * @return true if King of color is in check
	 */
	public boolean isInCheck(Color color)
	{
	    return isUnderAttack(color, getKing(color).getLocation());
	}
	
	/**
	 * <b>Method: </b>checkmate</br> 
	 * <b>Usage: </b>{@code board.checkmate(color)}</br>
	 * -------------------------------</br>
	 * Returns whether King of specified color is in checkmate. </br>
	 * Check mate requires two conditions: </br>
	 * <ul>
	 *     <li>King is currently in in check, verified through {@code isUnderAttack(color, king.getLocation()}</br>
	 *     <li>All possible destinations of King are under attack, not verified with {@code isUnderAttack(color, loc)} for efficiency</br>
	 * </ul>
	 * 
	 * @param color of King to verify checkmate
	 * @return true if King is in checkmate
	 */
	public boolean isInCheckMate(Color color)
	{   
	    Piece king = getKing(color);
	    if (!isUnderAttack(color, king.getLocation()))
            return false;
	    
	    Set<Move> allMoves = allMoves(color);
	    for (Move move : allMoves)
	    {
	        executeMove(move);
	        if(!isInCheck(color))
	        {
	            undoMove();
	            return false;
	        }
	        undoMove();
	    }
	    return true;
	}
}