import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * 
 *
 * @author Dennis
 */
public class Canvas extends JComponent implements MouseMotionListener
{
    private JFrame frame;
    private ArrayList<Shape> points;
    private MouseEvent lastEvent;
    
    public static void main(String[] args)
    {
        Canvas c = new Canvas();
    }
    
    public Canvas()
    {
        points = new ArrayList<Shape>();
        
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setPreferredSize(new Dimension(400, 400));
        frame.getContentPane().add(this);
        frame.getContentPane().addMouseMotionListener(this);
        frame.setResizable(false);

        frame.pack();
        frame.setVisible(true);
    }
    
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        int width = this.getWidth();
        int height = this.getHeight();
        g2.setPaint(Color.WHITE);
        g2.fill(new Rectangle2D.Double(0, 0, width, height));
        
        g2.setPaint(Color.BLACK);
        for (Shape s : points)
            g2.fill(s);
    }

    public void mouseDragged(MouseEvent e)
    {
        if (lastEvent!=null)
            points.add(new Line2D.Double(e.getX(), e.getY(), lastEvent.getX(), lastEvent.getY()));
        repaint();
        lastEvent = e;
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseMoved(MouseEvent e)
    {
        // TODO Auto-generated method stub
        
    }
}
