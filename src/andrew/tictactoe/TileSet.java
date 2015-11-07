package andrew.tictactoe;

import andrew.tictactoe.input.Mouse;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * This is the set of 9 tiles and related properties
 *
 * @author Andrew
 */
public class TileSet
{
    int tileWidth;
    int tileHeight;
    boolean playerSelector = true;  // used to toggle between the characters
    // when deciding the color/char to set
    // to the selected tile. true means
    // player 1's turn, false means player
    // 2's turn
    public static int tilesRemaining = 9;
    public Tile tiles[][];

    // These arrays count the number
    // of tiles each player has in a given line
    int p1rowCompletion[];
    int p1columnCompletion[];
    int p1diagonalCompletion[];

    int p2rowCompletion[];
    int p2columnCompletion[];
    int p2diagonalCompletion[];

    /**
     * This creates the set of 9 tiles
     */
    public TileSet()
    {
        tileWidth = TicTacToe.width / 3;
        tileHeight = TicTacToe.height / 3;
        tiles = new Tile[3][3];
        // Creates each tile and sets to a random color
        for (int a = 0; a < 3; a++)
        {
            for (int b = 0; b < 3; b++)
            {
                tiles[a][b] = new Tile();
                tiles[a][b].setRandomColor();
            }
        }
    }

    // Getters for the index of each tile, when given a pixel-coordinate
    /**
     * Gets the x-index for the tile over which the mouse resides
     *
     * @param x X-pixel on JFrame
     * @return The x-index for the tile over which the mouse resides
     */
    public int getTileXIndex(int x)
    {
        return (x / (400 / 3)) % 3;
    }

    /**
     * Gets the y-index for the tile over which the mouse resides
     *
     * @param y Y-pixel on JFrame
     * @return The y-index for the tile over which the mouse resides
     */
    public int getTileYIndex(int y)
    {
        return (y / (600 / 3)) % 3;
    }

    /**
     * Gets the index for the tile over which the mouse resides
     *
     * @param x The x-index for the tile over which the mouse resides
     * @param y The y-index for the tile over which the mouse resides
     * @return
     */
    public int getTile(int x, int y)
    {
        return ((y / (600 / 3)) % 3) * 3 + ((x / (400 / 3)) % 3);
    }

    /**
     * Identifies if there is a winner. If yes, then who.
     *
     * @param player1 Player 1
     * @param player2 Player 2
     * @return -1 ==> no winner yet. -2==>cat's game. else, winnerID.
     */
    public int theWinnerIs(Player player1, Player player2)
    {
        // Determines if the game is over: -1 means no, other numbers are the winners IDs

        updateLineCompletionStatus(player1, player2);
        for (int a = 0; a < 3; a++)
        {
            if (p1rowCompletion[a] == 3)
            {
                return player1.playerID;
            }
            if (p2rowCompletion[a] == 3)
            {
                return player2.playerID;
            }
            if (p1columnCompletion[a] == 3)
            {
                return player1.playerID;
            }
            if (p2columnCompletion[a] == 3)
            {
                return player2.playerID;
            }
        }

        if (p1diagonalCompletion[0] == 3)
        {
            return player1.playerID;
        }

        if (p2diagonalCompletion[0] == 3)
        {
            return player2.playerID;
        }

        if (p1diagonalCompletion[1] == 3)
        {
            return player1.playerID;
        }

        if (p2diagonalCompletion[1] == 3)
        {
            return player2.playerID;
        }

        if (tilesRemaining == 0)
        {
            return -2; // flag for a cat's game
        }
        return -1;
    }

    /**
     * Displays the characters associated with the tiles
     *
     * @param graphics Graphics object
     */
    public void displayTileCharacters(Graphics graphics)
    {
        graphics.setColor(new Color(100, 100, 100));
        graphics.setFont(new Font("Verdana", 0, 100));
        for (int x = 0; x < 3; x++)
        {
            for (int y = 0; y < 3; y++)
            {
                char currentCharacter = tiles[x][y].getAssociatedPlayerChar();
                if (currentCharacter != '\u0000')
                {
                    // Positioning calculations: coordinates are lower left corner
                    // of surrounding rectangle. Lots of padding on characters
                    // makes it difficult to understand the calculations for
                    // positioning
                    graphics.drawString(Character.toString(currentCharacter), 20 + 400 / 3 * x, 120 + 800 / 4 * y);
                }
            }
        }
    }

    /**
     * Manages tile selection
     *
     * @param player1 Player 1
     * @param player2 Player 2
     */
    public void update(Player player1, Player player2)
    {
        if (Mouse.getMouseLeftClickStatus() == true && (!Player.computerMode || (Player.computerMode&&playerSelector)))
        {
            int x = Mouse.getMouseX();
            int y = Mouse.getMouseY();
            int xTile = getTileXIndex(x);
            int yTile = getTileYIndex(y);

            // This if statement makes sure the user is pointing to
            // the JFrame object
            if (x > -1 && y > -1)
            {
                // Change to the other player
                if (tiles[xTile][yTile].getSelectedStatus() == false)
                {
                    if (!playerSelector) // Player 2 tile assignments
                    {
                        tiles[xTile][yTile].setAssociatedPlayerID(player2.playerID);
                        tiles[xTile][yTile].setAssociatedPlayerChar(player2.getPlayerChar());
                        tiles[xTile][yTile].setColor(0x000000); //black
                    }
                    else // Player 1 tile assignments
                    {
                        tiles[xTile][yTile].setAssociatedPlayerID(player1.playerID);
                        tiles[xTile][yTile].setAssociatedPlayerChar(player1.getPlayerChar());
                        tiles[xTile][yTile].setColor(0xFFFFFF);  //white
                    }
                    tiles[xTile][yTile].setSelectedStatus();
                    playerSelector = !playerSelector;  // Turn passes to next player
                }
            }
        }
        else if (Player.computerMode == true && (!playerSelector) && !GameGUI.gameOver && tilesRemaining != 0)
        {
            makeArtificiallyIntelligentMove(player1, player2);
        }
    }

    private void makeRandomMoveIfPossible(Player player2)
    {
        // Iterate through the tiles, add un-selected tiles to an ArrayList
        // If there are no such tiles, then it's a cat's game
        // Random generate an integer between 0 and Arraylist's size-1
        // Have the computer select that tile
        ArrayList<Integer> possibleChoices = new ArrayList<>();

        int tileIndex = 0;
        for (int y = 0; y < 3; y++)
        {
            for (int x = 0; x < 3; x++)
            {
                if (tiles[x][y].getSelectedStatus() == false)
                {
                    possibleChoices.add(tileIndex); // Adds index from 0 to 8, to the ArrayList, if not chosen already
                }
                tileIndex++;
            }
        }
        int remainingChoices = possibleChoices.size();  // Count of the # of choices
        if (remainingChoices != 0)
        {
            int selectedByComputer = TicTacToe.random.nextInt(remainingChoices); // Random index of ArrayList
            int selectedTileIndex = possibleChoices.get(selectedByComputer); // Corresponding tile's index
            int xTile = selectedTileIndex % 3;
            int yTile = selectedTileIndex / 3;
            tiles[xTile][yTile].setAssociatedPlayerID(player2.playerID);
            tiles[xTile][yTile].setAssociatedPlayerChar(player2.getPlayerChar());
            tiles[xTile][yTile].setColor(0x000000);
        }
        playerSelector = !playerSelector;  // Turn goes back to player 1
    }

    private void updateLineCompletionStatus(Player player1, Player player2)
    {
        // These denote how many tiles each player has
        p1rowCompletion = new int[3];
        p1columnCompletion = new int[3];
        p1diagonalCompletion = new int[2];

        p2rowCompletion = new int[3];
        p2columnCompletion = new int[3];
        p2diagonalCompletion = new int[2];

        //  Counts user tiles in each column
        for (int x = 0; x < 3; x++)
        {
            for (int y = 0; y < 3; y++)
            {
                if (tiles[x][y].getAssociatedPlayerID() == player1.playerID)
                {
                    p1columnCompletion[x] += 1;
                }
                else if (tiles[x][y].getAssociatedPlayerID() == player2.playerID)
                {
                    p2columnCompletion[x] += 1;
                }
            }
        }

        // Counts user tiles in each row
        for (int y = 0; y < 3; y++)
        {
            for (int x = 0; x < 3; x++)
            {
                if (tiles[x][y].getAssociatedPlayerID() == player1.playerID)
                {
                    p1rowCompletion[y] += 1;
                }
                else if (tiles[x][y].getAssociatedPlayerID() == player2.playerID)
                {
                    p2rowCompletion[y] += 1;
                }
            }
        }

        //  Counts user tiles in \
        for (int y = 0; y < 3; y++)
        {
            if (tiles[y][y].getAssociatedPlayerID() == player1.playerID)
            {
                p1diagonalCompletion[0] += 1;
            }
            else if (tiles[y][y].getAssociatedPlayerID() == player2.playerID)
            {
                p2diagonalCompletion[0] += 1;
            }
        }

        //  Counts user tiles in /
        for (int y = 0; y < 3; y++)
        {
            if (tiles[y][2 - y].getAssociatedPlayerID() == player1.playerID)
            {
                p1diagonalCompletion[1] += 1;
            }
            else if (tiles[y][2 - y].getAssociatedPlayerID() == player2.playerID)
            {
                p2diagonalCompletion[1] += 1;
            }
        }
    }

    private void makeArtificiallyIntelligentMove(Player player1, Player player2)
    {
        updateLineCompletionStatus(player1, player2);
        boolean computerMoved = false;

        // Selects the tile for the computer
        for (int a = 0; a < 3; a++)
        {
            for (int b = 0; b < 3; b++)
            {
                if (p2rowCompletion[a] == 2 && p1rowCompletion[a] == 0)
                { // If computer can win with current move
                    if (tiles[b][a].getSelectedStatus() == false)
                    {
                        setPlayerToTile(player2, b, a);
                        computerMoved = true;
                        playerSelector = !playerSelector;  // Turn goes back to player 1
                        return;
                    }
                }
                else if (p2columnCompletion[a] == 2 && p1columnCompletion[a] == 0)
                { // If computer can win with current move
                    if (tiles[a][b].getSelectedStatus() == false)
                    {
                        setPlayerToTile(player2, a, b);
                        computerMoved = true;
                        playerSelector = !playerSelector;  // Turn goes back to player 1
                        return;
                    }
                }
                else if (p2diagonalCompletion[0] == 2 && p1diagonalCompletion[0] == 0)
                { // If computer can win with current move
                    if (tiles[a][a].getSelectedStatus() == false)
                    {
                        setPlayerToTile(player2, a, a);
                        computerMoved = true;
                        playerSelector = !playerSelector;  // Turn goes back to player 1
                        return;
                    }
                }
                else if (p2diagonalCompletion[1] == 2 && p1diagonalCompletion[1] == 0)
                { // If computer can win with current move
                    if (tiles[a][2 - a].getSelectedStatus() == false)
                    {
                        setPlayerToTile(player2, a, 2 - a);
                        computerMoved = true;
                        playerSelector = !playerSelector;  // Turn goes back to player 1
                        return;
                    }
                }
            }
        }

        // Medium priority: preventing human from winning
        for (int a = 0; a < 3; a++)
        {
            for (int b = 0; b < 3; b++)
            {
                if (p1rowCompletion[a] == 2 && p2rowCompletion[a] == 0)
                { // If computer can prevent human from winning
                    if (tiles[b][a].getSelectedStatus() == false)
                    {
                        setPlayerToTile(player2, b, a);
                        computerMoved = true;
                        playerSelector = !playerSelector;  // Turn goes back to player 1
                        return;
                    }
                }
                else if (p1columnCompletion[a] == 2 && p2columnCompletion[a] == 0)
                { // If computer can prevent human from winning
                    if (tiles[a][b].getSelectedStatus() == false)
                    {
                        setPlayerToTile(player2, a, b);
                        computerMoved = true;
                        playerSelector = !playerSelector;  // Turn goes back to player 1
                        return;
                    }
                }
                else if (p1diagonalCompletion[0] == 2 && p2diagonalCompletion[0] == 0)
                { // If computer can prevent human from winning
                    if (tiles[a][a].getSelectedStatus() == false)
                    {
                        setPlayerToTile(player2, a, a);
                        computerMoved = true;
                        playerSelector = !playerSelector;  // Turn goes back to player 1
                        return;
                    }
                }
                else if (p1diagonalCompletion[1] == 2 && p2diagonalCompletion[1] == 0)
                { // If computer can prevent human from winning
                    if (tiles[a][2 - a].getSelectedStatus() == false)
                    {
                        setPlayerToTile(player2, a, 2 - a);
                        computerMoved = true;
                        playerSelector = !playerSelector;  // Turn goes back to player 1
                        return;
                    }
                }
            }
        }

        // Lesser priority moves
        // This chooses the middle tile if not already selected
        if (tiles[1][1].getSelectedStatus() == false)
        {
            setPlayerToTile(player2, 1, 1);
            computerMoved = true;
            playerSelector = !playerSelector;  // Turn goes back to player 1
            return;
        }

        // This chooses another tile in line with a previous tile of the player,
        // if the opponent has no tiles in that line
        for (int a = 0; a < 3; a++)
        {
            for (int b = 0; b < 3; b++)
            {
                if (p2diagonalCompletion[1] == 1 && p1diagonalCompletion[1] == 0)
                {
                    if (tiles[a][2 - a].getSelectedStatus() == false)
                    {
                        setPlayerToTile(player2, a, 2 - a);
                        computerMoved = true;
                        playerSelector = !playerSelector;  // Turn goes back to player 1
                        return;
                    }
                }

                else if (p2diagonalCompletion[0] == 1 && p1diagonalCompletion[0] == 0)
                {
                    if (tiles[a][2 - a].getSelectedStatus() == false)
                    {
                        setPlayerToTile(player2, a, a);
                        computerMoved = true;
                        playerSelector = !playerSelector;  // Turn goes back to player 1
                        return;
                    }
                }

                else if (p2rowCompletion[a] == 1 && p1rowCompletion[a] == 0)
                {
                    if (tiles[b][a].getSelectedStatus() == false)
                    {
                        setPlayerToTile(player2, b, a);
                        computerMoved = true;
                        playerSelector = !playerSelector;  // Turn goes back to player 1
                        return;
                    }
                }
                else if (p2columnCompletion[a] == 1 && p1columnCompletion[a] == 0)
                {
                    if (tiles[a][b].getSelectedStatus() == false)
                    {
                        setPlayerToTile(player2, a, b);
                        computerMoved = true;
                        playerSelector = !playerSelector;  // Turn goes back to player 1
                        return;
                    }
                }
            }
        }

        // This is the safeguard, makes a random move if all other conditions fail
        if (computerMoved == false)
        {
            // Move to same line as another tile if other player isnt on that line
            makeRandomMoveIfPossible(player2);
        }
    }

    private void setPlayerToTile(Player player, int xTile, int yTile)
    {
        tiles[xTile][yTile].setAssociatedPlayerID(player.playerID);
        tiles[xTile][yTile].setAssociatedPlayerChar(player.getPlayerChar());
        tiles[xTile][yTile].setColor(0x000000);
    }
}
