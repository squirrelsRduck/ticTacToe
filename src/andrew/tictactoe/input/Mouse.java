package andrew.tictactoe.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Specifies how to save important mouse inputs
 *
 * @author Andrew
 */
public class Mouse implements MouseListener, MouseMotionListener
{

    private static int mouseX = -1;
    private static int mouseY = -1;
    private static boolean mouseLeftClick = false;

    /**
     * Gets the xPixel pointed to by the mouse
     *
     * @return The xPixel pointed to by the mouse
     */
    public static int getMouseX()  // This is if we want to get the mouse location outside this class
    {
        return mouseX;
    }

    /**
     * Gets the yPixel pointed to by the mouse
     *
     * @return The yPixel pointed to by the mouse
     */
    public static int getMouseY()  // This is if we want to get the mouse location outside this class
    {
        return mouseY;
    }

    /**
     * This allows other classes to access the mouseLeftClick boolean
     *
     * @return The mouseLeftClick boolean
     */
    public static boolean getMouseLeftClickStatus()
    {
        return mouseLeftClick;
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        // Resets the boolean to false upon letting go of the mouse button
        mouseLeftClick = false;
    }

    @Override
    public void mouseDragged(MouseEvent arg0)
    {
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        // Sets the mouse location in integer component form
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        // Sets the mouseLeftClick boolean to true when the mouse is pressed
        mouseLeftClick = true;
    }
}
