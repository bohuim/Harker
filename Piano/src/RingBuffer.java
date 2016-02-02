
/**
 * Write a description of class RingBuffer1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RingBuffer
{
    private double[] sample;
    private int first = 0;
    private int last = 0;
    
    private int size;
    private double dequeued;

    /**constructor to create an empty buffer,
     * with given max capacity
     */
    public RingBuffer(int capacity)
    {
         sample = new double [capacity];
         for (int i = 0; i < sample.length; i++)
            enqueue(0);
    }
    
    /**return number of items currently in the buffer 
    */
    public int size()
    {      
        size = 0;
        for (int i = 0; i < sample.length; i++)
        {
            if (!(sample[i] == 0))
                size++;
        }
        
        return size;
    }
    
    /**is the buffer empty (size equals zero)? 
     */
    public boolean isEmpty()
    {
        if (size() == 0)
            return true;
        return false;
    }
    
    /**is the buffer full (size equals capacity)? 
     */
    public boolean isFull()
    {
        if (size() == sample.length)
            return true;
        return false;
    }
    
    /**add item x to end
     */
    public void enqueue(double x)
    {
         sample[last] = x;
         if (last+1 == sample.length) last = 0;
         else last++;
    }
    
    /**delete and return item form the front
     */
    public double dequeue()
    {
        dequeued = sample[first];
        sample[first] = 0;
        if (first+1 == sample.length) first = 0;
        else first++;
        
        return dequeued;
    }
    
    /**return (but do not delete) item from the front
     */
    public double peek()
    {
        return sample[first];
    }
}
