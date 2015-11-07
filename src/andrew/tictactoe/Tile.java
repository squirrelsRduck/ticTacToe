package andrew.tictactoe;

/**
 * A TicTacToe game consists of 9 tiles
 *
 * @author Andrew
 */
public class Tile
{

    private boolean isAlreadySelected = false;  // tile already selected by a player or not
    private int associatedPlayerID = -1; //-1 implies the tile is not selected yet, else the value gives the player's ID
    private char associatedPlayerChar = '\u0000'; // similar to associatedPlayerID, but the char, not the ID
    private int tileColor;

    // Getters and setters
    /**
     * Associates this tile with a given player ID
     *
     * @param playerID The player's ID
     */
    public void setAssociatedPlayerID(int playerID)
    {
        associatedPlayerID = playerID;
        setSelectedStatus();
        TileSet.tilesRemaining -= 1;
    }

    /**
     * Gets the player ID associated with this tile
     *
     * @return the player's ID associated with this tile, -1 if nonexistent
     */
    public int getAssociatedPlayerID()
    {
        return associatedPlayerID;
    }

    /**
     * Associates this tile with a given player character
     *
     * @param playerChar A player's character
     */
    public void setAssociatedPlayerChar(char playerChar)
    {
        associatedPlayerChar = playerChar;
    }

    /**
     * Gets the player character associated with this tile
     *
     * @return Returns the player character associated with this tile
     */
    public char getAssociatedPlayerChar()
    {
        return associatedPlayerChar;
    }

    /**
     * Sets the boolean to true, noting that this tile is selected
     */
    public void setSelectedStatus()
    {
        isAlreadySelected = true;
    }

    /**
     * Figures out if the tile was already selected
     *
     * @return False means not selected already, true if already selected
     */
    public boolean getSelectedStatus()
    {
        return isAlreadySelected;
    }

    /**
     * Sets the color for the tile
     *
     * @param colorOfInterest Color to set
     */
    public void setColor(int colorOfInterest)
    {
        tileColor = colorOfInterest;
    }

    /**
     * Sets a random color to the tile
     */
    public void setRandomColor()
    {
        tileColor = TicTacToe.random.nextInt(0xffffff);
    }

    /**
     * Gets the color of the tile
     *
     * @return Returns the color of the tile
     */
    public int getColor()
    {
        return tileColor;
    }
}