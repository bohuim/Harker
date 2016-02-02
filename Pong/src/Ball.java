import java.awt.Color;

/**
 *Etension of the Shape class
 * is a white circle of length 2
 * 
 * @author Dennis Moon
 */
public class Ball extends Shape
{
    public int velocity;
    private double velX;
    private double velY;
    
    /**
     * Constructor: Ball()
     * Usage: Ball ball = new Ball()
     * ------------------------
     * makes a white circle shape of length 2
     * starts at (50,80)
     */
    public Ball(int vel)
    {
        super(false, Color.WHITE, 317,240, 16, 16);

        velocity = vel;
        velX = velocity*Math.cos(-29*Math.PI/180);
        velY = velocity*Math.sin(-29*Math.PI/180);
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
        
        if (super.getY()<=4 || super.getY()>=480-4-super.getHeight())
            changeVelY();
    }
    
    public int getVelocity()
    {
        return velocity;
    }
    
    public void changeVelX()
    {
        velX = velX * -1;
    }
    
    public void changeVelY()
    {
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
