
/**
 * Lunar Lander
 * 
 * @author: Dennis Moon
 * @version 
 * 
 * Runs the lunarlander game by Stuart Regis, but updates every half a second
 * Displays the "Success!" upon winning the game
 */
public class LunarLander
{
    // constants are defined below
    public static final int INITIAL_VELOCITY = 40; // meters/second
    public static final int INITIAL_ALTITUDE = 1000; // meters 
    public static final int INITIAL_FUEL = 25; // thrusts available
    
    public static final int GRAVITY = 2; // gravitational acceleration in meters/second/second
    public static final int THRUST = 4; // thrust acceleration in meters/second/second
    
    public static final int SAFE_LANDING = 4; // speed at which lander can safely land in meters/second
    
    // instance variables are defined below
    private int fuel;
    private int altitude;
    private int velocity;
    private int thrustUnits; 
    
    // constructor
    public LunarLander()
    {
        // reset the simulation
        reset();
    }
    
    // returns the current altitude in meters
    public int getAltitude()
    {
        return altitude;
        //throw new RuntimeException("write me");
    }
    
    // returns the current velocity in meters per second
    public int getVelocity()
    {
        return velocity;
        //throw new RuntimeException("write me");
    }
    
    // returns the current amount of fuel
    public int getFuel()
    {
        return fuel;
        //throw new RuntimeException("write me");
    }
    
    // resets the game to the intial state
    public void reset()
    {
        velocity = INITIAL_VELOCITY;
        fuel = INITIAL_FUEL;
        altitude = INITIAL_ALTITUDE;
    }
    
    // called once per .5 second to update the 
    // lander position, velocity and fuel
    public void tick()
    {
        thrustUnits = Math.min(fuel,thrustUnits); 
        //thrust units is set to whichever is lower between fuel and number of thrusts pressed by user
        velocity = velocity + GRAVITY/2 - 2*(THRUST/2)*thrustUnits;
        //velocity is affected by gravity and thrusts pressed every half a second
        //effectiveness of thrust doubled to account for increased update speed, but same amout of fuel
        altitude = altitude - velocity/2;
        // altitude is changed according to the velocity at this moment
        fuel = fuel - thrustUnits;
        //units of fuel used up according to thrusts pressd
        thrustUnits = 0;
        //thrust units pressed set back to zero every second
    }
    
    // remember to apply one more unit of fuel on
    // the next update
    public void thrust()
    {
        thrustUnits = thrustUnits + 1;
    }      
}
