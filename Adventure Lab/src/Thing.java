/**
 * A item in our game. It can be placed in a room or given to a person.
 * 
 * @version 120914
 * 			edited and updated to Part 1
 * @version 120914
 *          part 2 started, weapons&armor added
 * @version 120917
 *          weapons&armor moved into subclass of Thing, called Equip
 * @version 120920
 *          consume subclass added
 * 
 * @author part 1 - Andrew Luo, Dennis Moon
 * @author Part 2 - Dennis Moon
 */
public class Thing 
{
	private String name;
	private int type;
	
	private ThingOwner owner;

	/**
	 * Constructor for non-equip items
	 * @param thingName - name of this thing
	 * @param aType - type of this thing
	 */
	public Thing(String thingName, int aType)
	{
	    name = thingName;
	    type = aType;
	}
	
	/**
	 * getName returns the name of this thing
	 * 
	 * @return name - name of this thing
	 */
	public String getName()
	{
	    return name;
	}
	
	/**
     * returns type of this thing
     * 
     * @return type of this thing
     */
    public int getType()
    {
        return type;
    }
	
	/**
	 * getOwner returns owner of this object
	 * 
	 * @return owner - the current owner of this object
	 */
	public ThingOwner getOwner()
	{
		return owner;
	}
	
	/**
	 * changeOwner removes the old owner and adds the new owner
	 * @param newOwner - new owner of the item
	 */
	public void changeOwner(ThingOwner newOwner)
	{
		if (owner != null)
			owner.remove(this);
		newOwner.add(this);
		owner = newOwner;
	}
}
