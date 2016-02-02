import java.awt.Color;

/**
 * Shape is a 2d geometric figure like a circle or rect
 * 
 * @author Dennis Moon
 */
public class Shape
{
    private boolean isRound;
    private Color color;
    private double x;
    private double y;
    private double width;
    private double height;
    
    /**
     * Constructor: Shape()
     * Usage: create a new Shape
     * ------------------------------
     * @param isRound
     * @param color
     * @param x - coordinate of top left corner of shape
     * @param y - coordinate of top left corner of shape
     * @param width - of shape
     * @param height - of shape
     */
    public Shape(boolean isRound, Color color, 
            double x, double y,
            double width, double height)
    {
        this.isRound = isRound;
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Method: isRound
     * Usage: shape.isRound()
     * -----------------------
     * @return whether this is round or not
     */
    public boolean isRound()
    {
        return isRound;
    }
    
    /**
     * Method: getColor
     * Usage: shape.getColor()
     * -----------------------
     * @return color of this shape
     */
    public Color getColor()
    {
        return color;
    }
    
    /**
     * Method: getX()
     * Usage: shape.getX()
     * -----------------------
     * @return x coordinate of shape's top left corner
     */
    public double getX()
    {
        return x;
    }
    
    /**
     * Method: getY()
     * Usage: shape.getY()
     * -----------------------
     * @return y coordinate of shape's top left corner
     */
    public double getY()
    {
        return y;
    }
    
    /**
     * Method: getWidth()
     * Usage: shape.getWidth()
     * -----------------------
     * @return width of this shape
     */
    public double getWidth()
    {
        return width;
    }
    
    /**
     * Method: getHeight()
     * Usage: shape.getHeight()
     * -----------------------
     * @return height of this shape
     */
    public double getHeight()
    {
        return height;
    }
    
    /**
     * Method: setColor()
     * Usage: shape.setColor()
     * ---------------------
     * @param color to be changed
     */
    public void setColor(Color color)
    {
        this.color = color;
    }
    
    /**
     * Method: setX()
     * Usage: display.setX()
     * ----------------------
     * @param newX - new X coordinate of this ball
     */
    public void setX(double newX)
    {
        x = newX;
    }
    
    /**
     * Method: setY()
     * Usage: display.setY()
     * ----------------------
     * @param newY - new Y coordinate of this ball
     */
    public void setY(double newY)
    {
        y = newY;
    }
}
