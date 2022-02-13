/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.Timer;
import persistence.Database;

/**
 *
 * @author LUD1BP
 */
public class Menu {
    private JFrame frame;
    private LabyrinthBuilder labyrinth;
    private final JLabel gameStatLabel = new JLabel("");
    private static int score=0;
    private Database data = new Database();
    private static int time = 0;
    private JButton topListButton = new JButton("Toplist");
    private JButton mapBuilderButton = new JButton("Map Builder");
    private JPanel buttonPanel = new JPanel(new GridLayout(1,2,50,50));
    public Menu() {
        this.frame = new JFrame("Labyrinth Adventure");

        buttonPanel.add(topListButton);
        buttonPanel.add(mapBuilderButton);
        frame.add(buttonPanel);
        frame.pack();
        frame.setSize(1280,720);
        frame.setVisible(true);
    }

   
}
