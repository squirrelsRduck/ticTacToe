package andrew.tictactoe.graphics;

import andrew.tictactoe.GameGUI;
import andrew.tictactoe.TicTacToe;
import andrew.tictactoe.TileSet;

/**
 * Modifies pixel array
 *
 * @author Andrew
 */
public class DrawOnScreen
{
    // I made this its own function just because the tile color selection is such an involved formula that passing it via a parameter
    // involves too much complexity, to the point where the complexity wouldn't have been pushed down if I did it that way
    /**
     * Sets the color of each tile
     *
     * @param xStart For this program, we modify starting at x-pixel 0
     * @param yStart For this program, we modify starting at y-pixel 0
     * @param pixelArrayWidth Count of pixels in horizontal modification
     * @param pixelArrayHeight Count of pixels in vertical modification
     * @param tileSet The set of 9 tiles
     */
    public static void modifyPixelArrayBasedOnTilesForTheScreen(int xStart, int yStart, int pixelArrayWidth, int pixelArrayHeight, TileSet tileSet)
    {
        for (int y = yStart; y < pixelArrayHeight; y++)
        {
            for (int x = xStart; x < pixelArrayWidth; x++)
            {
                //  Notice it may appear that we're over-writing
                // the assignment for pixelArray that was in the default 
                // constructor.  Such is not true: that previous command
                // simply linked the pixelArray to the mainImage
                GameGUI.pixelArray[y * pixelArrayWidth + x] = (tileSet.tiles[tileSet.getTileXIndex(x)][tileSet.getTileYIndex(y)]).getColor(); //  0x  RR GG BB
            }
        }
    }

    /**
     * Inserts an animated cat
     */
    public static void insertPouncingCat()
    {
        GameGUI.catAnimator += .1; // Cat speed, because the angle is based on that
        for (int catCounter = 0; catCounter < 50; catCounter++)
        // Display cat animation
        {
            // Uses unit-circle logic to generate the x and y coordinates based on cosine and sine
            //double catAnimator = System.currentTimeMillis()/100;
            double specificCatAnimator = GameGUI.catAnimator + .01 * catCounter; // Specifies for multiple cats within the loop, to display a trailing effect of cats (3D-simulation)
            int catXstart = (int) (100 + Math.cos(specificCatAnimator) * 90);
            int catYstart = (int) (250 - Math.abs(Math.sin(specificCatAnimator)) * 200); // the 200 makes the cat jump  high

            int catColor = TicTacToe.random.nextInt(0xffffff);
            int catBoundary = TicTacToe.random.nextInt(0xffffff);
            // Display cat
            for (int y = 0; y < 300; y++)  // coordinate of each y-pixel on the actual cat-sprite
            {
                for (int x = 0; x < 200; x++) // coordinate of each x-pixel on the actual cat-sprite
                {
                    int xc = x + catXstart; // where to place each x-pixel on the overall canvas
                    int yc = y + catYstart; // where to place each y-pixel on the overall canvas
                    if (xc > 398 || yc > 598 || xc < 0 || yc < 0)
                    {
                    } // makes sure not to go out of bounds on the pixelArray
                    else
                    {
                        int spritePixel = Sprite.cat.pixelArray[y * 200 + x]; // Gets the pixel from the cat sprite
                        // This next line of code ignores/doesn't-assign-to-pixelArray pixels that are of the cat's background color
                        if (spritePixel != 0xff007F46 && spritePixel != 0xff007F47 && spritePixel != 0xff007F45) // Don't forget the ff prefix for alpha channel, can be used to make transparent cat
                        {
                            if (spritePixel == 0xff58FF4C)
                            {
                                GameGUI.pixelArray[(yc) * 400 + xc] = catColor;
                            }
                            else
                            {
                                GameGUI.pixelArray[(yc) * 400 + xc] = catBoundary;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * In this game, we just animate a cat if a cat's game occurs.
     *
     * @param endGameDisplaySelector Specifies how the game ended
     */
    public static void insertGameOverAnimation(int endGameDisplaySelector)
    {
        if (endGameDisplaySelector == -2) // Cat's game
        {
            DrawOnScreen.insertPouncingCat();
        }
        // Note: the way I'm handling text, that has to be done separately
        // otherwise the pixel-drawing will over-write the text
        // In future renditions, text could be imported image files
        // to improve managability
    }
}
