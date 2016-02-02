import java.awt.Color;

/**
 * Extension of the Shape class
 * is a white circle of length 2
 * 
 * @author Dennis Moon
 */
public class Ball extends Shape
{
    private double velX;
    private double velY;
    
    /**
     * Constructor: Ball()
     * Usage: Ball ball = new Ball()
     * ------------------------
     * makes a white circle shape of length 2
     * starts at (50,80)
     */
    public Ball()
    {
        super(true, Color.WHITE, 50,80, 2, 2);
        
        velX = 0.309;
        velY = -0.951;
    }
    
    /**
     * Method: move()
     * Usage: ball.move()
     * ------------------------
     * updates the ball's x & y coordinate according to the ball's velocity
     * ball bounces off left, right & top boundaries but not the bottom
     */
    public void move()
    {
        super.setX(super.getX() + velX);
        super.setY(super.getY() + velY);
        
        if (super.getX()<=0 || super.getX()>=98)
            velX = velX * -1;
        if (super.getY()<=0)
            velY = velY * -1;
    }
    
    public double getVelocityX()
    {
        return velX;
    }
    
    public double getVelocityY()
    {
        return velY;
    }
    
    public void setVelocityX(double newVelX)
    {
        velX = newVelX;
    }
    
    public void setVelocityY(double newVelY)
    {
        velY = newVelY;
    }
}
