
/**
 * Koch is FractalGenerator that defines the process to get the (n)th step of the Koch Curve </br>
 * has step0 & transform() for all steps above 0 </br>
 * 
 * @author Dennis Moon
 * @version Jan 30, 2013
 * 
 * Revision:
 *     Jan 30, 2013 - created class: step0 & transform added
 */
public class Koch implements FractalGenerator
{
    
    /**
     * <b>Method: </b>step0</br> 
     * <b>Usage: </b>{@code curve}</br>
     * -------------------------------</br>
     * step0 of the KochCurve is just a line along the x-axis
     * 
     * @return line - lying on the x-axis
     */
    public Curve step0()
    {
        FancyCurve line = new FancyCurve();
        line.add(new Line(0,1,0,0));
        return line;
    }
    
    /**
     * <b>Method: </b>transform</br> 
     * <b>Usage: </b>{@code curve}</br>
     * -------------------------------</br>
     * adds a triangle "bump" to every single line in the given curve </br>
     * - given the 0th step, a line: 1 triangle bump is produced </br>
     * - given the 1st step, 1 bump: a triangle bump on each side of the big bump + 2 side bumps
     * 
     * @param previous - nth step of the KochCurve
     * @return next - (n+1)th step of the KochCurve
     */
    public Curve transform(Curve previous)
    {
        FancyCurve[] lines = new FancyCurve[4];
        //scales line to .35
        lines[0] = (FancyCurve) previous.copy();
        lines[0].scale(.35, .35);
        
        //scales to a slightly smaller .3, rotated & tranlated next to lines[0]
        //one side of the triangle bump
        lines[1] = (FancyCurve) previous.copy();
        lines[1].scale(.3, .3);
        lines[1].rotate(60);
        lines[1].translate(.35, 0);
        
        //same as first side of th bump, except rotated the other way 
        //other side of the triangle bump
        lines[2] = (FancyCurve) previous.copy();
        lines[2].scale(.3, .3);
        lines[2].rotate(-60);
        lines[2].translate(.35+.15, .15*Math.sqrt(3));
        
        //finishes off with line next to second side of the bump
        lines[3] = (FancyCurve) lines[0].copy();
        lines[3].translate(.65, 0);
        
        //adds to new FancyCurve and returns
        FancyCurve next = new FancyCurve();
        for (FancyCurve l : lines)
            next.add(l);
        
        return next;
    }
}
