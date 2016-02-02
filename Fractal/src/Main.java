import java.awt.Color;
import java.util.ArrayList;

/**
 * Main class contains main() that has an instance of SketchPad and some FractalGenerator
 * after a fractal is generated to the desired step with static methods generateFractal() & drawFractal()
 * and then be drawn on a SketchPad
 *
 * @author Dennis Moon
 * @version Feb 4, 2013
 * 
 * Revision:
 *      Jan 28, 2013 - class created
 *      Jan 31, 2013 - generateFractal() implemented
 *      Feb 2, 2013 - generateFractal() modified to show process to given (n)th step
 *      Feb 4, 2013 - drawFractal() method added
 */
public class Main
{
    /**
     * <b>Method: </b>main</br> 
     * <b>Usage: </b>main thread</br>
     * -------------------------------</br>
     * Creates instance of SketchPad & a FractalGenerator
     * runs generateFractal() to given number 
     * and prints "over" after generateFractal() to signal end
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        SketchPad pad = new SketchPad();
        
        /**
        sampleSketch(pad);
        */
        
        //choose from: Sierpinski, Koch, Coastline, PythagorasTree
        
        FractalGenerator gen = new PythagorasTree();
        //generateFractal(gen, 10).draw(pad);
        System.out.println("over"); //signals end
        
        
        //Mandelbrot Fractal does not implement FractalGenerator, so it provides its own draw method

        Mandelbrot m = new Mandelbrot();
        m.draw(5, pad);
    }
    
    /**
     * <b>Method: </b>sampleSketch</br> 
     * <b>Usage: </b>{@code samepleSketch(pad)}</br>
     * -------------------------------</br>
     * Draws a simple house on the given pad
     * using SketchPad's draw line method
     * 
     * @param pad - SketchPad to draw house on
     */
    public static void sampleSketch(SketchPad pad)
    {
        //roof
        pad.drawLine(0, .5, 1, .5);
        pad.drawLine(0, .5, .5, .7);
        pad.drawLine(.5, .7, 1, .5);
        
        //door
        pad.drawLine(.4, 0, .4, .3);
        pad.drawLine(.4, .3, .6, .3);
        pad.drawLine(.6, .3, .6, 0);
        
        //window
        pad.drawLine(.1, .2, .3, .2);
        pad.drawLine(.1, .2, .1, .4);
        pad.drawLine(.1, .4, .3, .4);
        pad.drawLine(.3, .4, .3, .2);
        pad.drawLine(.2, .2, .2, .4);
        pad.drawLine(.1, .3, .3, .3);
    }
    
    /**
     * <b>Method: </b>generateFractal</br> 
     * <b>Usage: </b>{@code generateFractal(gen, level)}</br>
     * -------------------------------</br>
     * drawFractal generates the given fractal to the given (n)th curve 
     * without drawing the process in between 
     * 
     * @param generator - the FractalGenerator for methods that create the fractal
     * @param n - level fractal should be produced up to
     * @return (n)th step of the given fractalGenerator
     */
    public static Curve generateFractal(FractalGenerator generator, int n)
    {
        Curve pic = generator.step0();
        
        for (int i=0; i<n; i++)
            pic = generator.transform(pic);
        
        return pic;
    }
    
    /**
     * <b>Method: </b>drawFractal</br> 
     * <b>Usage: </b>{@code drawFractal(gen, level, pad)}</br>
     * -------------------------------</br>
     * drawFractal generates the given fractal to the given (n)th curve 
     * and shows the process to get there by pausing half a second after each curve
     * 
     * @param generator - the FractalGenerator for methods that create the fractal
     * @param n - level fractal should be produced up to
     * @param pad - SketchPad to draw process on
     */
    public static void drawFractal (FractalGenerator generator, int n, SketchPad pad)
    {
        Curve pic = generator.step0();
        pic.draw(pad);
        
        for (int i=0; i<n; i++)
        {
            pic = generator.transform(pic);
            pic.draw(pad);
            try {Thread.sleep(500); }
            catch (InterruptedException e) {}
        }
    }
}
