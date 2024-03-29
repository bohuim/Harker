import java.awt.Color;

/**
 * An obstacle that disappears when hit
 * 
 * @author Dennis Moon
 */
public class Brick extends Obstacle
{
    
    public Brick(Color color, double x, double y)
    {
        super(color, x, y, 6,4);
    }
    
    public boolean handleCollision(Ball ball)
    {
        super.handleCollision(ball);
        return true;
    }
}
