/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package andrew.tictactoe;

import andrew.tictactoe.GameGUI;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author Andrew
 */
public class EndGamePopupFrame extends JFrame
{

    private final JTextField txtPlayerWins;

    public EndGamePopupFrame()
    {
        setTitle("Game Over");
        setBounds(100, 100, 330, 211);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);

        txtPlayerWins = new JTextField();
        txtPlayerWins.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 24));
        txtPlayerWins.setEditable(false);
        if (GameGUI.endGameDisplaySelector != -2)
        {
            txtPlayerWins.setText("Player " + GameGUI.endGameDisplaySelector + " Wins!");
        }
        else
        {
            txtPlayerWins.setText("Cat's game!");
        }
        txtPlayerWins.setBounds((getWidth()-148)/2,15,148,36); //91, 15, 148, 36);
        getContentPane().add(txtPlayerWins);
        txtPlayerWins.setColumns(10);

        JButton btnNewGame = new JButton("New Game");
        btnNewGame.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                GameGUI.resetGame();
            }
        });
        btnNewGame.setBounds((getWidth()-115)/2, 110, 115, 29);
        getContentPane().add(btnNewGame);

        JButton btnShowMeThe = new JButton("Show me the game");
        btnShowMeThe.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });
        btnShowMeThe.setBounds((getWidth()-169)/2, 65, 169, 29);
        getContentPane().add(btnShowMeThe);
    }
}
