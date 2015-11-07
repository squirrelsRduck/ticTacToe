package andrew.tictactoe.graphics;

/**
 * Used to extract and contain pixel data from a SpriteSheet object
 * 
 * @author Andrew
 */
public class Sprite
{
    private final int xPixelOnSS,yPixelOnSS,sWidth,sHeight;
    public static Sprite cat = new Sprite(200,300,0,0, SpriteSheet.spriteSheet);
    public int[] pixelArray;
    
    /**
     * Creates and contains a sprite object
     * 
     * @param sWidth Sprite Width
     * @param sHeight Sprite Height
     * @param xIndexOnSS Sprite X-Index on SpriteSheet
     * @param yIndexOnSS Sprite Y-Index on SpriteSheet
     * @param catSpriteSheet The SpriteSheet to be loaded from
     */
    public Sprite(int sWidth,int sHeight,int xIndexOnSS,int yIndexOnSS, SpriteSheet catSpriteSheet)
    {
        this.sWidth = sWidth;
        this.sHeight = sHeight;        
        xPixelOnSS = xIndexOnSS*sWidth;
        yPixelOnSS = yIndexOnSS*sHeight;
        pixelArray = new int[sWidth*sHeight];
        load(); // This over-writes the portion in the pixelArray that we want to assign to the particular sprite
    }
    
    private void load()
    {
        for(int y=0;y<sHeight;y++)
        {
            for(int x= 0;x<sWidth;x++)
            {
                pixelArray[y*sWidth + x]= SpriteSheet.spriteSheet.ssPixelArray[(y+yPixelOnSS)*sWidth + (x+xPixelOnSS)];
            }
        }
    }
}
