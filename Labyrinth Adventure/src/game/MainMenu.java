/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import persistence.Database;

/**
 *
 * @author LUD1BP
 */
public class MainMenu {
    private JFrame frame;
    private LabyrinthBuilder labyrinth;
    private final JLabel gameStatLabel = new JLabel("");
    private static int score=0;
    private Database data = new Database();
    private static int time = 0;
    private JButton topListButton = new JButton("Toplist");
    private JButton mapBuilderButton = new JButton("Map Builder");
    private JButton freePlayButton = new JButton("Free Play");
    private JPanel buttonPanel = new JPanel(new GridLayout(1,2,50,50));
    public MainMenu() {
        this.frame = new JFrame("Labyrinth Adventure");
        mapBuilderButton.addActionListener(new ActionListener()
        { @Override
            public void actionPerformed (ActionEvent e) 
            {
                try
                {
                    new MapBuilder();  
                }
                catch(Exception m)
                {
                    m.printStackTrace();
                }

            }
        });
        freePlayButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new AdventureGUI();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        buttonPanel.add(topListButton);
        buttonPanel.add(mapBuilderButton);
        buttonPanel.add(freePlayButton);
        frame.add(buttonPanel);
        frame.pack();
        frame.setSize(1280,720);
        frame.setVisible(true);
    }

   
}
