package andrew.tictactoe.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Loads image containing sprites and stores data into pixel array ssPixelArray
 *
 * @author Andrew
 */
public final class SpriteSheet
{

    public int ssWidth;
    public int ssHeight;
    public int[] ssPixelArray;

    public static SpriteSheet spriteSheet = new SpriteSheet("CatSpriteSheet.png", 200, 600);
    // This will instantiate the only spriteSheet we will use, by calling the constructor

    // Here we set the dimensions as specified
    /**
     * This instantiates the SpriteSheet and loads the image file.
     *
     * @param path Path to the image file
     * @param ssWidth Width of the sprite sheet
     * @param ssHeight Height of the sprite sheet
     */
    public SpriteSheet(String path, int ssWidth, int ssHeight)
    {
        loadSS(path);  // loads the SS
    }

    private void loadSS(String path)
    {
        try
        {
            BufferedImage ssImage = ImageIO.read(SpriteSheet.class.getResource(path));
            ssWidth = ssImage.getWidth();
            ssHeight = ssImage.getHeight();
            ssPixelArray = new int[ssWidth * ssHeight];
            ssImage.getRGB(0, 0, ssWidth, ssHeight, ssPixelArray, 0, ssWidth); // Puts image file into ssPixelArray
        } catch (IOException ex)
        {
            Logger.getLogger(SpriteSheet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}