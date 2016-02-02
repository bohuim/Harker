
/**
 * Sierpinski is FractalGenerator that defines the process to get the (n)th step of Sierpinski Triangle
 * has step0 & transform() for all steps above 0
 * 
 * @author Dennis Moon
 * @version Feb 3, 2013
 * 
 * Revision:
 *     Jan 29, 2013 - created class: step0 & transform added
 *     Feb 3, 2013 - fixed transform to pass previous to next
 */
public class Sierpinski implements FractalGenerator
{

    /**
     * <b>Method: </b>step0</br> 
     * <b>Usage: </b>{@code curve}</br>
     * -------------------------------</br>
     * step0 of Sierpinski's Triangle is just a normal triangle </br>
     * with one of the vertex on the origin and each side of length 1 </br>
     * 
     * @return triangle - a equilateral triangle 
     */
    public Curve step0()
    {
        FancyCurve triangle = new FancyCurve();
        Line base = new Line(0, 1, 0, 0);
        
        Line side1 = (Line) base.copy();
        side1.rotate(60);
        
        Line side2 = (Line) base.copy();
        side2.rotate(120);
        side2.translate(1, 0);
        
        triangle.add(base);
        triangle.add(side1);
        triangle.add(side2);
        
        return triangle;
    }
    
    /**
     * <b>Method: </b>transform</br> 
     * <b>Usage: </b>{@code curve}</br>
     * -------------------------------</br>
     * Makes 3 copies of the given triangle </br>
     * and stacks them in a way that they form a larger triangle </br>
     * 
     * @param triangle - any nth step of the Sierpinski Triangle
     * @return newTriangle - (n+1)th step of the Sierpinski Triangle
     */
    public Curve transform(Curve triangle)
    {
        FancyCurve[] tri = new FancyCurve[2];
        //scale first one to half the size
        triangle.scale(.5, .5);
        
        //translate copy of first one to be contiguous to first one
        tri[0] = (FancyCurve) triangle.copy();
        tri[0].translate(.5, 0);
        
        //stack third on the top verticies of the bottom two
        tri[1] = (FancyCurve) triangle.copy();
        tri[1].translate(.25, .25*Math.sqrt(3));
        
        //adds (n+1)th triangle to new FancyCurve and returns 
        FancyCurve newTriangle = new FancyCurve();
        newTriangle.add(triangle);
        for (FancyCurve t : tri)
            newTriangle.add(t);
        
        return newTriangle;
    }
}
