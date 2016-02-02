import java.awt.Color;


/**
 * Extension of the shape class
 * A rect shape with length 4 & width 100
 * 
 * @author Dennis Moon
 */
public class Obstacle extends Shape
{
    
    public Obstacle(Color color, double x, double y, double width, double height)
    {
        super(false, color, x, y, width, height);
    }
    
    private boolean containsPoint(double x, double y)
    {
        double xLow = super.getX();
        double xHigh = super.getX()+super.getWidth();
        double yLow = super.getY();
        double yHigh = super.getY()+super.getHeight();
        
        if ((xLow<=x && x<=xHigh) && (yLow<=y && y<=yHigh))
            return true;
        return false;
    }
    
    public boolean overlapsWith(Ball ball)
    {
        if (containsPoint(ball.getX(), ball.getY()-2) ||
                containsPoint(ball.getX()+2, ball.getY()) ||
                containsPoint(ball.getX(), ball.getY()+2) ||
                containsPoint(ball.getX()-2, ball.getY()))
            return true;
        return false;
    }
    
    public boolean handleCollision(Ball ball)
    {
        if (containsPoint(ball.getX(), ball.getY()-2) || containsPoint(ball.getX(), ball.getY()+2))
            ball.setVelocityY(ball.getVelocityY()*-1);
        if (containsPoint(ball.getX()+2, ball.getY()) || containsPoint(ball.getX()-2, ball.getY()))
            ball.setVelocityX(ball.getVelocityX()*-1);
        return false;
    }
}
