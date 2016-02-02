/**
 * Write a description of class GuitarString here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Note
{   
    private RingBuffer buffer;    
    
    private double frequency;
    private int capacity;
    private int ticCount;
    private double[] random;
    
    public double ticSum;
    
    private static final int SAMPLE_RATE = 44100;
    private final double ENERGY_DECAY = .996;

    /**create a guitar string of the given frequency and level
     */
    public Note(int noteNum) 
    {
    	frequency = 440.0 * Math.pow(2, (noteNum-48)/12.0);
        capacity = (int) (SAMPLE_RATE/frequency + 0.5);
        buffer = new RingBuffer(capacity);
        
        random = new double[capacity];
        for (int i=0; i<capacity; i++)
            random[i] = Math.random() - 0.5;
        
        ticCount = 0;
    }
    
    /** set the buffer to white noise
     */
    public void pluck() 
    {
        for (int i = 0; i < capacity; i++)
            buffer.enqueue (random[i]);
        ticCount=0;
    }
    
    /** set the buffer to zeros
     */
    public void silence()
    {
    	for (int i = 0; i < capacity; i++)
            buffer.enqueue (0);
    	ticCount = 0;
    }
    
    /** advance the simulation one time step
     */
    public void tic() 
    {
        //double temp = ENERGY_DECAY*(buffer.dequeue() + buffer.peek())/2;
        buffer.enqueue(ENERGY_DECAY*(buffer.dequeue() + buffer.peek())/2);
        //if (temp!=0)
            ticCount++;
    }
    
    /** return the current sample
     */
    public double sample() 
    {
        return buffer.peek();
    }
    
    /** return number of tics
     */
    public int time()
    {
        return ticCount;
    }
    
    /** return if this string is plucked or not
     */
    public boolean isEmpty()
    {
    	return buffer.isEmpty();
    }
}
