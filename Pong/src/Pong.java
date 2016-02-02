import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Main game class of pong
 * 
 * @author Dennis Moon
 */
public class Pong extends KeyAdapter implements MouseMotionListener
{
    private ShapeDisplay display;
    
    private Ball ball;
    private Paddle p1;
    private AIPaddle p2;
    
    private boolean pressed;
    
    public Pong()
    {
        display = new ShapeDisplay(); //650 x 480
        display.addKeyListener(this);
        display.addMouseMotionListener(this);
        //listener = new ArrowListener(this, display);
        
        ball = new Ball(16);
        display.add(ball);
        
        p1 = new Paddle(605);
        display.add(p1);
        p2 = new AIPaddle(29, 16);
        display.add(p2);
        
        pressed = false;
        
        display.repaint();
    }
    
    public static void main (String[] args)
    {
        Pong pong = new Pong();
        pong.play();
    }
    
    /**
     * Method: play()
     * Usage: pong.play() called by main()
     * --------------------------------
     * updates ball & repaints every 80ms
     */
    public void play()
    {
        
        while(true)
        {
            ball.move();
            checkDeadBall();
            p2.followBall(ball);
            p1.checkCollision(ball);
            p2.checkCollision(ball);
            display.repaint();
            try { Thread.sleep(10);}
            catch (InterruptedException e1){}
        }
    }
    
    private void checkDeadBall()
    {
        if (ball.getX()<0 || 650<ball.getX()+ball.getWidth())
        {
            display.remove(ball);
            try { Thread.sleep(1000);}
            catch (InterruptedException e1){}
            ball = new Ball(16);
            display.add(ball);
        }
    }
    
    /**
     * Method: checkBounce()
     * Usage: checkBounce()
     */
    private void checkBounce()
    {
        if (ball.getX()+ball.getWidth() >= p1.getX() && ball.getX()+ball.getWidth()<=650)
            if (p1.getY() <= ball.getY()+ball.getHeight() &&  ball.getY() <= p1.getY()+p1.getHeight())
                p1.handleCollision(ball,false);
        
        if (ball.getX()<= p2.getX()+p2.getWidth())
            if (p2.getY() <= ball.getY()+ball.getHeight() &&  ball.getY() <= p2.getY()+p2.getHeight())
                p2.handleCollision(ball, false);
    }
    
    /**
     * Method: keyPressed()
     * Usage: starts moving the paddle
     * ----------------------------
     * only takes in UP & DOWN ArrowKeys 
     * and changes paddle's position accordingly
     * 
     * @param e - event fired upon keyPress
     */
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP)
        {
            p1.moveDown();
            display.repaint(605, 0, 16, 480);
            try { Thread.sleep(3);}
            catch (InterruptedException e1){}
        }
        else if (key == KeyEvent.VK_DOWN)
        {
            p1.moveUp();
            display.repaint(605, 0, 16, 480);
            try { Thread.sleep(3);}
            catch (InterruptedException e1){}
        }
        display.repaint();
    }
    
    /**
     * Method: keyReleased()
     * Usage: stops paddle from moving
     * ----------------------------
     * only takes in UP & DOWN ArrowKeys 
     * and changes paddle's position accordingly
     * 
     * @param e - event fired upon keyRelease
     */
    public void keyReleased(KeyEvent e)
    {

    }

    public void mouseDragged(MouseEvent e)
    {}

    public void mouseMoved(MouseEvent e)
    {
        p1.setY((int)(display.getHeight() - e.getPoint().getY() - p1.getHeight()/2));     
        display.repaint();
    }
}
