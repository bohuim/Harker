import java.util.*;

public class Play 
{	
	public static void main (String [] args)
	{
	    Instrument piano = new Instrument();
	    Keyboard keyboard = new Keyboard(piano);
	    
	    new Thread(piano).start();
	}
}