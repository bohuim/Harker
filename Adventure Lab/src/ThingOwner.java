/**
 * Allows a thing to be passed around by different class that can hold things
 * 
 * @version 120914
 * 			created and updated to Part 1
 * @author Andrew Luo, Dennis Moon
 */
public interface ThingOwner 
{
	
	void add(Thing newThing);
	void remove(Thing aThing);
	String getName();
}
