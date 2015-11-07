package andrew.tictactoe;

import andrew.tictactoe.input.Keyboard;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages each player and their associated data
 *
 * @author Andrew
 */
public class Player
{

    private char playerChar = '\u0000'; // the char for the given player
    public boolean selectedAlready = false; // true means the player already chose a character
    public int playerID = -1; // self-explanatory: in actual game, id's 1 and 2 are used
    public static boolean computerMode = true;
    public static boolean player1First = true;

    /**
     * Sets the playerID
     *
     * @param playerofInterestID The ID to be set
     */
    public Player(int playerofInterestID)
    {
        playerID = playerofInterestID;
    }

    // Draws prompt on screen asking for player character selection
    /**
     * Draws prompt on screen asking for player character selection
     *
     * @param graphics Graphics object
     * @param playerNumber PlayerID
     */
    public void displayPlayerSelectionPrompt(Graphics graphics, int playerNumber)
    {
        if (playerID == 1)
        {
            graphics.setColor(Color.cyan);
        }
        else
        {
            graphics.setColor(Color.green);
        }
        graphics.fill3DRect(0, 0, TicTacToe.width, TicTacToe.height, true);

        graphics.setFont(new Font("Verdana", 0, 35));

        graphics.setColor(Color.getColor(null,TicTacToe.random.nextInt(0xffffff)));
        
        graphics.drawString("Player " + playerNumber + ":", 10, 40);
        if (playerID == 1)
        {
            graphics.setColor(Color.red);
        }
        else
        {
            graphics.setColor(Color.magenta);
        }
        graphics.setFont(new Font("Verdana", 0, 30));
        graphics.drawString("Select Your", 180, 39);
        graphics.drawString("Player Icon By Typing", 10, 100);
        graphics.drawString("A Character. ", 10, 160);
        graphics.setColor(Color.black);
        graphics.drawString("Game Settings: ", 10, 220);
        graphics.drawString("Computer Opponent: ", 10, 270);

        if (computerMode)
        {
            graphics.drawString("On", 340, 270);
        }
        else
        {
            graphics.drawString("Off", 340, 270);
        }

        graphics.drawString("Up Next: ", 10, 320);
        if (player1First)
        {
            graphics.drawString("Player 1", 180, 320);
        }
        else
        {
            graphics.drawString("Player 2", 180, 320);
        }

        graphics.drawString("Press [ to toggle", 10, 370);
        graphics.drawString("computer opponent", 10, 420);
        graphics.drawString("Press ] to toggle", 10, 470);
        graphics.drawString("whose turn it is", 10, 520);
    }

    //  This does stuff if the player actually
    //  decides to select a character for themself
    /**
     * Gets and sets the player's character selection from the keyboard
     */
    public void getPlayerSelection()
    {
        if (Keyboard.getCharOfInterest() != '\u0000' && Keyboard.getCharOfInterest() != '[' && Keyboard.getCharOfInterest() != ']')
        {
            playerChar = Keyboard.getCharOfInterest();
            selectedAlready = true;
            try
            {
                Thread.sleep(600);  // This delays the input between players to 3/5 second
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (Keyboard.getCharOfInterest() == '[')
        {
            computerMode = !computerMode; // toggles computer mode
            try
            {
                Thread.sleep(600);  // This delays the input between players to 3/5 second
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (Keyboard.getCharOfInterest() == ']')
        {
            player1First = !player1First; // toggles who goes first
            try
            {
                Thread.sleep(600);  // This delays the input between players to 3/5 second
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Gets the player's character that's already set
     *
     * @return The player char if set, null otherwise
     */
    public char getPlayerChar()
    {
        return playerChar;
    }

    /**
     * Keeps trying to get the character selection from the keyboard
     */
    public void update()
    {
        if (selectedAlready == false)
        {
            getPlayerSelection();
        }
    }
}