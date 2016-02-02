/**
 * Represents a single move, in which a piece moves to a destination location. </br>
 * Since a move can be undone, also keeps track of the source location and any captured victim. </br></br>
 * 
 * <b>Methods</b>
 * <ul>
 * 		<li>{@code getPiece()} returns Piece that is moving</li>
 *      <li>{@code getSource()} returns the location the Piece starts on </li>
 *      <li>{@code getDestination()} returns the location the Piece lands on </li>
 *      <li>{@code setVictim()} sets victim of this Move, if any Piece existed on destination </li>
 *      <li>{@code getVictim()} returns the victim of this Move</li>
 *      <li>{@code execute()} moves Piece from source to destination</li>
 *      <li>{@code undo()} moves Piece back to source and puts victim back in if any existed</li>
 *      <li>{@code toString()} returns String representation of this Move as: "piece from source to destination containing victim"</li>
 *      <li>{@code hashCode()} returns a hash value for this Move so that other Moves with equal Piece, source and destinations have the same hash value</li>
 * </ul>
 * 
 * @author Mr.Page
 * @version May 10, 2013
 * 
 * @Revision
 *     May 10, 2013 - first modified: execute() & undo() added
 *     May 12, 2013 - execute() modified to call Board.promote() for pawns that reached 8th rank
 */
public class Move
{
	private Piece piece;          //the piece being moved
	private Location source;      //the location being moved from
	private Location destination; //the location being moved to
	private Piece victim;         //any captured piece at the destination

	/**
	 * <b>Constructor: </b>Move</br> 
	 * <b>Usage: </b>{@code Move move = new Move(pawn, it.next())}</br>
	 * -------------------------------</br>
	 * Constructs a new move for moving the given piece to the given destination.
	 * 
	 * @param piece to be moved
	 * @param destination to be moved to
	 */
	public Move(Piece piece, Location destination)
	{
		this.piece = piece;
		this.source = piece.getLocation();
		this.destination = destination;
		victim = piece.getBoard().get(destination);

		if (source.equals(destination))
			throw new IllegalArgumentException("Both source and dest are " + source);
	}

	/**
	 * <b>Method: </b>getPiece</br> 
	 * <b>Usage: </b>{@code Move.getPiece()}</br>
	 * -------------------------------</br>
	 * Returns the piece being moved
	 * 
	 * @return piece being moved
	 */
	public Piece getPiece()
	{
		return piece;
	}

	/**
	 * <b>Method: </b>getSource</br> 
	 * <b>Usage: </b>{@code Move.getSource()}</br>
	 * -------------------------------</br>
	 * Returns location piece is being from
	 * 
	 * @return location being moved from
	 */
	public Location getSource()
	{
		return source;
	}

	/**
     * <b>Method: </b>getDestination</br> 
     * <b>Usage: </b>{@code Move.getDestination()}</br>
     * -------------------------------</br>
     * Returns location piece is being to
     * 
     * @return location being moved to
     */
	public Location getDestination()
	{
		return destination;
	}

	/**
	 * <b>Method: </b>getVictim</br> 
	 * <b>Usage: </b>{@code Move.getVictim()}</br>
	 * -------------------------------</br>
	 * Returns the victim of this move, if any was captured
	 * 
	 * @return victim of move
	 */
	public Piece getVictim()
	{
		return victim;
	}
	
	/**
	 * <b>Method: </b>execute</br> 
	 * <b>Usage: </b>{@code Move.execute}</br>
	 * -------------------------------</br>
	 * Moves the piece to destination and calls {@code piece.moved()}. </br>
	 * Calls {@code board.promote()} if piece was a Rook that reached its 8th rank.
	 */
	public void execute()
	{
	    piece.moveTo(destination);
	    piece.moved();
	}
	
	/**
	 * <b>Method: </b>undo</br> 
	 * <b>Usage: </b>{@code Move.undo()}</br>
	 * -------------------------------</br>
	 * Moves the piece back to source location and calls {@code unMoved()}. </br>
	 * Puts victim back into the board if any was captured.
	 */
	public void undo()
	{
	    piece.moveTo(source);
	    piece.unMoved();
	    
        if (victim != null)
            victim.putSelfInGrid(piece.getBoard(), destination);
	}

	/**
	 * <b>Method: </b>toString</br> 
	 * <b>Usage: </b>{@code Move.toString()}</br>
	 * -------------------------------</br>
	 * Returns a String representation of this Move as: "(piece) from (source) to (destination) containing (victim)"
	 * 
	 * @return String representation of this Move
	 */
	public String toString()
	{
		return piece + " from " + source + " to " + destination + " containing " + victim;
	}

	/**
	 * <b>Method: </b></br> 
	 * <b>Usage: </b>{@code }</br>
	 * -------------------------------</br>
	 * Returns whether the specified Move {@code x} is equal to this Move by checking the {@code piece, source, destination, victim}. </br>
	 * 
	 * @param x - Move to check 
	 * @return true if {@code piece, source, destination, victim} all match
	 */
	public boolean equals(Object x)
	{
		Move other = (Move)x;
		return piece == other.getPiece() && source.equals(other.getSource()) &&
			destination.equals(other.getDestination()) && victim == other.getVictim();
	}

	/**
	 * <b>Method: </b>hashCode</br> 
	 * <b>Usage: </b>{@code Move.hashCode()}</br>
	 * -------------------------------</br>
	 * Returns the hash value of this Move by adding hash values of {@code piece, source, destination} so that Moves of equal instance fields return the same hash values.
	 * 
	 * @return
	 */
	public int hashCode()
	{
		return piece.hashCode() + source.hashCode() + destination.hashCode();
	}
}