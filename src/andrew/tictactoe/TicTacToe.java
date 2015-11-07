package andrew.tictactoe;

import java.util.Random;

/**
 * This class creates and runs the GameGUI
 * <p>
 * This class simply contains the main method. I chose to include it separate
 * from the GameGUI simply for the purpose of making it clear as to where the
 * main method is. This way, if we add networking capabilities to the program,
 * we can start a thread for such within the main method here, without any
 * confusion as to where the program's main method is or should be, when
 * considering the GUI implementation or the networking implementation. Since
 * runtime performance is no issue for this program, we can leave this class
 * here with no significantly added delay in loading
 *
 * @author Andrew
 */
public class TicTacToe
{

    private static final long serialVersionUID = 1L;

    public static int width = 400;
    public static int height = 600;

    private static GameGUI gameGui;
    public static Random random;

    /**
     * The main method for the application.
     * Initializes everything important
     * 
     * @param args Nothing important for this program
     */
    public static void main(String[] args)
    {
        random = new Random();
        gameGui = new GameGUI(width, height);  // Create a class to deal with all the graphics stuff
        gameGui.start();
    }
}
