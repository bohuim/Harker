
/**
 * CoastCurve is FractalGenerator that defines the process to get the (n)th step of the Coastline Curve </br>
 * has step0 & transform() for all steps above 0 </br>
 * 
 * @author Dennis Moon
 * @version Jan 30, 2013
 * 
 * Revision:
 *     Jan 30, 2013 - created class: step0 & transform added
 */
public class Coastline implements FractalGenerator
{
    
    /**
     * <b>Method: </b></br> 
     * <b>Usage: </b>{@code curve}</br>
     * -------------------------------</br>
     * step0 of the CoastlineCurve is a zigzag across the first quandrant
     * 
     * @return base - zigzag lines across the first quadrant
     */
    public Curve step0()
    {
        FancyCurve base = new FancyCurve();
        base.add(new Line(0,.25,.5,.75));
        base.add(new Line(.25,.5,.75,.5));
        base.add(new Line(.5,.75,.5,.25));
        base.add(new Line(.75,1,.25,.5));
        return base;
    }
    
    /**
     * <b>Method: </b>transform</br> 
     * <b>Usage: </b>{@code curve}</br>
     * -------------------------------</br>
     * Makes 4 copies of the given curve and orients them in the slope of step0
     * as the nth step increases, the curve looks like a rougher version of step0
     * 
     * @param previous - nth step of the Coastline
     * @return next - (n+1)th step of the CoastlineCurve
     */
    public Curve transform(Curve previous)
    {   
        double scaleVal = .25/(.5*Math.sqrt(2)); //value each line is scaled to 
        
        FancyCurve[] lines = new FancyCurve[4];
        lines[0] = (FancyCurve) previous.copy();
        lines[0].translate(0, -0.5); //moved to origin for convenience when rotating & scaling
        lines[0].rotate(45);
        lines[0].scale(scaleVal, scaleVal); //scaled & rotated so that deltaX of result is 1/4 of deltaX of previous
        lines[0].translate(0, 0.5); //translated back
        
        lines[1] = (FancyCurve) lines[0].copy();
        lines[1].translate(0, -.5);
        lines[1].rotate(-90);
        lines[1].translate(.25,.75);
        
        lines[2] = (FancyCurve) lines[1].copy();
        lines[2].translate(.25, -.25);
        
        lines[3] = (FancyCurve) lines[0].copy();
        lines[3].translate(.75, -.25);
        
        //adds to new FancyCurve and returns
        FancyCurve next = new FancyCurve();
        for (FancyCurve l : lines)
            next.add(l);
        
        return next;
    }
}
