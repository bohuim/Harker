import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * 
 * @author Dennis Moon
 */
public class TowerDisplay extends JComponent
{
    public static final int UNIT = 10;
    private JFrame frame;
    private Tower[] tower;
    
    /**
     * Constructor: TowerDisplay()
     * Usage: TowerDisplay display = new TowerDisplay()
     * ----------------------------------
     * creates frame
     * @param d - array of disc
     */
    public TowerDisplay(Tower[] tow)
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        setPreferredSize(new Dimension(600+220, 400));
        frame.getContentPane().add(this, BorderLayout.NORTH);
        frame.pack();
        
        tower = tow;
        
        frame.setVisible(true);
    }
    
    public void addKeyListener(KeyListener k)
    {
        frame.addKeyListener(k);
    }
    
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.WHITE);
        g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        
        g2.setColor(Color.BLACK);
        for (int i=0; i<tower.length; i++)
            g2.fillRect(200*(i+1), getHeight()-300, 20, 300);
        
        g2.setColor(Color.RED);
        for (int t=0; t<tower.length; t++)
            for (int i=0; i<tower[t].getNumDisc(); i++)
            {
                int center = 10 + 200*(t+1);
                int x = center - tower[t].getDisc(i).getWidth()/2;
                g2.fillRect(x, getHeight()-(30*(i+1)), tower[t].getDisc(i).getWidth(), 30);
            }
    }
}
