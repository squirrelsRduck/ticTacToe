package andrew.tictactoe.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Specifies how to save important keyboard inputs
 *
 * @author Andrew
 */
public class Keyboard implements KeyListener
{
    // sets the default input character to null
    // This variable will be used to assign each player's character
    private static char characterOfInterest= '\u0000';
    
    /**
     * Getter for the character that was recently typed
     *
     * @return The character that was recently typed
     */
    public static char getCharOfInterest()
    {
        return characterOfInterest;                
    }
    
    @Override
    public void keyTyped(KeyEvent e)
    {
        // Temporarily sets the variable to the given key
        characterOfInterest = e.getKeyChar(); //getKeyText(e.getKeyCode()).charAt(0);
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        // Resets the variable to null
        characterOfInterest= '\u0000';
    }
}
