
import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Location;
import java.awt.Color;

/**
 * This class runs a blank world. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class Main
{
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        
        //Actor a = new ChameleonCritter();
        Actor a = new BoxBug(5);
        world.add((new Location(5,4)), a);
        world.add((new Location(6,4)), (new StealthCritter()));
        
        world.show();
    }
}
