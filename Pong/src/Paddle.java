import java.awt.Color;


public class Paddle extends Shape
{
    private static int SIDE = 0;
    private static int TOP = 1;
    
    /**
     * Constructor: Paddle()
     * Usage: called by Pong
     * ----------------------------------
     * paddle's are always white, initially 210px on Y
     * with width 16, height 80
     * 
     * @param x - coordinate of this paddle
     */
    public Paddle(int x)
    {
        super(false, Color.WHITE, x, 0, 16, 16*5); //210
    }

    /**
     * Method: moveUp()
     * Usage: called by Pong when user presses up
     * ----------------------------------
     * moves paddle up 40px
     * unless it hits the top 
     */
    public void moveUp()
    {
        int newY = (int)(super.getY() - super.getWidth());
        if (newY<0)
            newY = 0;
        
        super.setY(newY);
    }
    
    /**
     * Method: moveDown()
     * Usage: called by Pong when user presses down
     * ----------------------------------
     * moves paddle down 40px
     * unless it hits the bottom
     */
    public void moveDown()
    {
        int newY = (int)(super.getY() + super.getWidth());
        if (newY>400)
            newY = 400;
        
        super.setY(newY);
    }
    
    /**
     * Method: setY()
     * Usage: called by Pong when user moves mouse
     * ------------------------------------
     * overriden so that paddle does not go off screen along y-axis
     */
    public void setY(int newY)
    {
        if (newY <= 0)
            newY = 0;
        else if (newY >= 480-super.getHeight())
            newY = (int)(480-super.getHeight());
        super.setY(newY);
    }
    
    public void checkCollision(Ball ball)
    {
        if (getX() <= ball.getRight() &&
                this.getY()<ball.getTop() &&  ball.getY()<this.getTop())
            handleCollision(ball,false);
        else if (ball.getRight()>= getX() && ball.getX()<=this.getRight()
                && ball.getY()<=this.getTop() && ball.getY()>=this.getY())
            handleCollision(ball,true);
        else if (ball.getRight()>= getX() && ball.getX()<=this.getRight()
                && ball.getTop()>=this.getY() && ball.getTop()<=this.getTop())
            handleCollision(ball,true);
    }
    
    public void handleCollision(Ball ball, boolean topOrBottom)
    {
        if (topOrBottom)
        {
            ball.changeVelX();
            ball.changeVelY();
            return; 
        }
        
        double relY = (ball.getY()-getY())/(getHeight()/2) - 1;
        double angle = Math.atan2(ball.getVelocityY(), ball.getVelocityX());
        angle += relY*Math.PI/4;
        //ball.setVelocityX(ball.VELOCITY * Math.cos(angle));
        ball.setVelocityY(ball.getVelocity() * Math.sin(angle));
        ball.changeVelX();
    }
}
