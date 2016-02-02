import javax.swing.*;
import java.awt.*;
/**
 * 
 * taken from the LunarLander project by Stuart Regis
 * 
 * @author Stuart Regis
 * @version 010407
 */
public class LunarPicture extends JPanel
{
	private LunarLander myLander; // lander to query
	
	public static final int BASE_HEIGHT = 20; // base height of 3 subfigures of lander
	public static final int BASE_WIDTH = 60;  // base width of lander figure

	public LunarPicture(LunarLander lander)
	// post: LunarPicture constructed with given lander object
	{
		myLander = lander;
	}
	
	public void update()
	// post: picture is redrawn to account for current lander state
	{
		repaint();
	}
	
	public void paintComponent(Graphics g)
	// post: lander is drawn
	{
	    Graphics2D g2 = (Graphics2D) g;
	    
		super.paintComponent(g2);
		setBackground(Color.darkGray);
		g2.setColor(Color.red);
		int altitude = myLander.getAltitude();
		if (altitude <= 0 && myLander.getVelocity() > LunarLander.SAFE_LANDING) 
		{
			Font f = new Font("Serif", Font.BOLD, Math.min(48, getHeight()));
			g2.setFont(f);
			g2.drawString("CRASH!", (getWidth() - g.getFontMetrics().stringWidth("CRASH!"))/2, (getHeight() + f.getSize())/2);
		}
		else if (altitude <= 0 && myLander.getVelocity() <= LunarLander.SAFE_LANDING)
		{
		    Font f = new Font("Serif", Font.BOLD, Math.min(48, getHeight()));
			g2.setFont(f);
			g2.drawString("SUCCESS!", (getWidth() - g.getFontMetrics().stringWidth("SUCCESS!"))/2, (getHeight() + f.getSize())/2);
		  }
		else 
		{
			altitude = Math.max(0, altitude);
			int verticalSpace = getHeight() - 3 * BASE_HEIGHT - 4;
			int lowerLeftX = (getWidth() - BASE_WIDTH)/2;
			int lowerLeftY = 3 * BASE_HEIGHT + (LunarLander.INITIAL_ALTITUDE - myLander.getAltitude()) * verticalSpace/LunarLander.INITIAL_ALTITUDE + 2;
			// draw legs
			g2.drawLine(lowerLeftX, lowerLeftY, lowerLeftX, lowerLeftY - BASE_HEIGHT);
			g2.drawLine(lowerLeftX + BASE_WIDTH - 1, lowerLeftY, lowerLeftX + BASE_WIDTH - 1, lowerLeftY - BASE_HEIGHT);
			// draw struts
			g2.drawLine(lowerLeftX - 3, lowerLeftY, lowerLeftX + 3, lowerLeftY);
			g2.drawLine(lowerLeftX + BASE_WIDTH - 4, lowerLeftY, lowerLeftX + BASE_WIDTH + 2, lowerLeftY);
			
			// draw middle
			g2.fillRect(lowerLeftX, lowerLeftY - 2 * BASE_HEIGHT, BASE_WIDTH, BASE_HEIGHT);
		    // draw top
			//g2.fillOval(lowerLeftX, lowerLeftY - 3 * BASE_HEIGHT, BASE_WIDTH, BASE_HEIGHT);
		}
	}
}
