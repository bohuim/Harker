/**
 * Pythagoras Tree is a FractalGenerator that creates a fractal tree </br>
 * Starting from a single square, two squares are added on </br>
 * This creates a right triangle in the middle of the squares, which the Pythagorean Theorem can be applied to </br>
 * As more suqares are added, the figure looks like a tree
 * 
 * @author Dennis Moon
 * @version Feb 1, 2013
 * 
 * Revision:
 *     Feb 1, 2013 - class created: step0 & transform added
 */
public class PythagorasTree implements FractalGenerator
{
    
    /**
     * <b>Method: </b></br> 
     * <b>Usage: </b>{@code curve.step0()}</br>
     * -------------------------------</br>
     * step0 of the Pythagoras Tree is just a square
     * Square is placed on the bottom center with sidelenght of .165
     * 
     * @return base - zigzag lines across the first quadrant
     */
    public Curve step0()
    {
        FancyCurve square = new FancyCurve();
        square.add(new Line(0.5+0.165/2, 0.5-0.165/2, 0, 0));
        square.add(new Line(0.5-0.165/2, 0.5-0.165/2, 0, 0.165));
        square.add(new Line(0.5-0.165/2, 0.5+0.165/2, 0.165, 0.165));
        square.add(new Line(0.5+0.165/2, 0.5+0.165/2, 0, 0.165));
        return square;
    }
    
    /**
     * <b>Method: </b>transform</br> 
     * <b>Usage: </b>{@code curve.transform(prev)}</br>
     * -------------------------------</br>
     * Takes the given curve and makes it into two branches
     *      one turned 45 & the other turned -45 degrees
     *      and are placed on top of previous
     * Previous is added to next as stem of the new branches
     * 
     * @param previous - nth step of the Coastline
     * @return next - (n+1)th step of the CoastlineCurve
     */
    public Curve transform(Curve previous)
    {
        double scaleVal = Math.sqrt(0.165*0.165/2)/0.165;
        
        FancyCurve[] lines = new FancyCurve[2];
        lines[0] = (FancyCurve) previous.copy();
        lines[0].translate(-(.5-0.165/2), 0);
        lines[0].scale(scaleVal, scaleVal);
        lines[1] = (FancyCurve) lines[0].copy();
        
        lines[0].rotate(45);
        lines[0].translate(.5-0.165/2, 0.165);
        
        lines[1].rotate(-45);
        lines[1].translate(.5, 0.165+0.165/2);
        
        //adds to new FancyCurve and returns
        FancyCurve next = new FancyCurve();
        for (FancyCurve l : lines)
            next.add(l);
        next.add(previous);
        
        return next;
    }
}
