package andrew.tictactoe;

import andrew.tictactoe.graphics.DrawOnScreen;
import andrew.tictactoe.input.Keyboard;
import andrew.tictactoe.input.Mouse;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

// This handles all the GUI processes and pushes down the
// calculations to appropriately simple levels
/**
 * This contains the main game loop
 *
 * @author Andrew
 */
public class GameGUI extends Canvas implements Runnable
{

    private static final long serialVersionUID = 1L;

    private final int width, height;

    private Thread mainGUIthread;
    private JFrame mainFrame;
    private boolean isRunning = false;
    public static BufferedImage mainImage;
    public static int[] pixelArray;
    private static TileSet tileSet;
    private final Mouse mouse;
    private Graphics graphics;
    private final Keyboard keyboard;
    private static Player player1;
    private static Player player2;
    public static boolean gameOver = false;
    public static int winnerID = -1;  // Saves the player's ID during the first 3-in-a-row completion
    public static double catAnimator = 0;
    static BufferStrategy bufferStrategy;
    public static int endGameDisplaySelector = -1;
    static boolean firstReset = true;
    static boolean resetting = false;
    static boolean firstTurnDecided = false;

    /**
     * Creates a GameGUI with the specified dimensions
     *
     * @param width Width
     * @param height Height
     */
    public GameGUI(int width, int height)
    {
        this.width = width;
        this.height = height;

        tileSet = new TileSet();  // Creates a set of tiles

        // Input listeners
        mouse = new Mouse();
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        keyboard = new Keyboard();
        addKeyListener(keyboard);
    }

    /**
     * Stop method for mainGUIthread
     */
    public synchronized void stop()
    {
        isRunning = false;
        try
        {
            mainGUIthread.join();
        }
        catch (InterruptedException e)
        {
        }
    }

    @Override
    public void run()  // called implicitly by mainGUIthread.start() in the start() method below
    {
        Timer timer = new Timer();
        timer.startTimer();
        while (isRunning)
        {
            timer.updateElapsedTime();
            if (timer.sixtiethSecondElapsed)
            {
                update();
                timer.startTimer();
            }
            render();
        }
    }

    /**
     * Handles the updates necessary in the game
     * <p>
     * This includes modifying the pixel array
     */
    public void update()
    {
        if (player1.selectedAlready && player2.selectedAlready)
        // Handles the tile assignments
        {
            // Sets who moves first
            if (GameGUI.firstTurnDecided == false)
            {
                tileSet.playerSelector = Player.player1First;
                GameGUI.firstTurnDecided = true;
            }
            tileSet.update(player1, player2);

            // Modifies the pixel array
            DrawOnScreen.modifyPixelArrayBasedOnTilesForTheScreen(0, 0, width, height, tileSet);

        }
        // This gets the player character selections at the start of the program
        player1.update();
        player2.update();

        if (!gameOver && player1.selectedAlready && player2.selectedAlready)
        {
            endGameDisplaySelector = tileSet.theWinnerIs(player1, player2);
        }
        DrawOnScreen.insertGameOverAnimation(endGameDisplaySelector); // Displays the end-game message 
    }

    /**
     * Starts the game loop
     * <p>
     * This includes creating the pixel array to use, creating the main GUI
     * thread, creating the JFrame, two Player objects, and starting the main
     * GUI thread.
     */
    public synchronized void start()
    {
        isRunning = true;
        mainGUIthread = new Thread(this, "Display");

        pixelArray = new int[width * height];

        // Creates the JFrame object
        mainFrame = new JFrame();

        mainFrame.setPreferredSize(new Dimension(width, height)); // Sets dimensions
        mainImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR); // creates bufferedImage object
        // This statement links mainImage to pixelArray behind-the-scenes.  When we write to pixelArray
        // after this statement, the data will be linked to mainImage
        pixelArray = ((DataBufferInt) mainImage.getRaster().getDataBuffer()).getData();

        // Sets properties of JFrame
        mainFrame.setResizable(false);
        mainFrame.setTitle("TicTacToe");  // Title of frame
        mainFrame.add(this);  // Adds to canvas
        mainFrame.pack();  // Sets size of component
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        this.requestFocusInWindow();  // Makes sure to listen for events without requiring clicking in window first
        mainFrame.setVisible(true);

        // Create the players
        player1 = new Player(1); // Creates player with playerID 1
        player2 = new Player(2); // Creates player with playerID 2

        mainGUIthread.start();
    }

    /**
     * Creates a button to reset the game
     */
    public void allowReset()
    {
        //Resets the game

        graphics.setColor(Color.red);
        graphics.fill3DRect(10, 525, 70, 30, true);

        graphics.setColor(Color.black);
        graphics.setFont(new Font("Verdana", 0, 24));
        graphics.drawString("Reset", 10, 550);
        if (firstReset)
        {
            try
            {
                EndGamePopupFrame endGameMessage = new EndGamePopupFrame();
                endGameMessage.setVisible(true);
                Thread.sleep(200); // Delays resetting the game
                firstReset = false;

            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(GameGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        int x = Mouse.getMouseX();
        int y = Mouse.getMouseY();
        boolean leftClickStatus = Mouse.getMouseLeftClickStatus();

        if (leftClickStatus)
        {
            if (x < 80 && y < 555 && GameGUI.gameOver && x >= 10 && y > 524)
            {
                GameGUI.resetGame();
            }
        }
    }

    public static void resetGame()
    {
        endGameDisplaySelector = -1;
        gameOver = false;
        winnerID = -1;
        tileSet = new TileSet();
        TileSet.tilesRemaining = 9;
        player1 = new Player(1);
        player2 = new Player(2);
        firstReset = true;
        firstTurnDecided = false;
        resetting = true;
    }

    /**
     * Displays the pixel array and any text necessary
     */
    public void render()
    {
        bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null)
        {
            createBufferStrategy(3);  // triple buffering
            return; // Exits function after creating the buffer strategy
        }

        // This graphics object will be used to draw the mainImage to the bufferStrategy
        graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(mainImage, 0, 0, getWidth(), getHeight(), null);

        // Displays the player selection prompts if necessary
        if (player1.selectedAlready == false)
        {
            player1.displayPlayerSelectionPrompt(graphics, 1);
        }
        else if (player2.selectedAlready == false)
        {
            player2.displayPlayerSelectionPrompt(graphics, 2);
        }
        else // Display the player characters on the tiles
        {
            tileSet.displayTileCharacters(graphics);
        }

        if (endGameDisplaySelector == -2)  // cat's game
        {
            graphics.setColor(Color.cyan);
            graphics.fill3DRect(5, 460, TicTacToe.width * 14 / 15, TicTacToe.height / 10, true);

            graphics.setColor(new Color(255, 0, 0));
            graphics.setFont(new Font("Verdana", 0, 35));
            graphics.drawString("It's my game meow!", 10, 500);
            gameOver = true;
            allowReset();
        }
        else if (endGameDisplaySelector != -1) // We have a winner
        {
            int textColor = TicTacToe.random.nextInt(0xffffff);
            graphics.setColor(Color.getColor(null, textColor));
            graphics.setFont(new Font("Verdana", 0, 80));
            graphics.drawString("Player ", 50, 100);
            if (!gameOver) // Makes sure another player can't win after the first player wins
            {
                winnerID = endGameDisplaySelector;
            }
            graphics.drawString(Integer.toString(winnerID), 180, 300);
            graphics.drawString("Wins", 100, 500);
            gameOver = true;

            allowReset();
        }

        graphics.dispose(); // Releases system resources after each loop in main thread
        if (!resetting)
        {
            bufferStrategy.show();  // Displays the contents in the JFrame    
        }
        else
        {
            // This avoids drawing tile characters if they were reset
            resetting = false;
        }
    }
}