package andrew.tictactoe;

// This class will reduce the updates/second to 60
// This will improve performance.  We can still display
// as fast as the computer will want to, though the frames
// won't actually be modified more than 60 times per second
// This is important to allow for decent animations

/**
 * Signals when 1/60 of a second has passed, to allow pixel re-calculations
 * 
 * @author Andrew
 */
public class Timer
{
    boolean sixtiethSecondElapsed = false;
    long startTime;
    
    /**
     * Starts the timer
     */
    public void startTimer()
    {
        startTime = System.nanoTime();
        sixtiethSecondElapsed = false;
    }

    /**
     * Updates the time elapsed since the timer was started
     */
    public void updateElapsedTime()
    {
        long nanoSecondsElapsed = System.nanoTime() - startTime;
        if(nanoSecondsElapsed > 1000000000/60)
        {
           sixtiethSecondElapsed = true;  // Signals that a 60th of a second has passed since the last resetting of the timer
        }
    }
}