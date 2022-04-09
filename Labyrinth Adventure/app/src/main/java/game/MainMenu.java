/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;


import persistence.OracleSqlManager;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author LUD1BP
 */
public class MainMenu {
    public OracleSqlManager dbConnection = new OracleSqlManager("IDU27K","almafa");
    private JFrame frame;
    private LabyrinthBuilder labyrinth;
    private final JLabel gameStatLabel = new JLabel("");
    private static int score=0;
    private static int time = 0;
    private JButton topListButton = new JButton("Toplist");
    private JButton mapBuilderButton = new JButton("Map Builder");
    private JButton freePlayButton = new JButton("Offline Play");
    private JButton onlinePlayButton = new JButton("Online Play");
    private JButton myMapsButton = new JButton("My maps");
    private JPanel buttonPanel = new JPanel(new GridLayout(1,2,50,50));
    public MainMenu() {
        this.frame = new JFrame("Labyrinth Adventure");
        mapBuilderButton.addActionListener(new ActionListener()
        { @Override
            public void actionPerformed (ActionEvent e) 
            {
                try
                {
                    MapBuilder mapBuilderWindow = new MapBuilder(dbConnection);
                    mapBuilderWindow.buildMap();
                }
                catch(Exception m)
                {
                    m.printStackTrace();
                }
            }
        });
        myMapsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MapList(dbConnection);
            }
        });
        onlinePlayButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new AdventureGUI(dbConnection);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        freePlayButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new AdventureGUI();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
        });
        topListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new TopList(dbConnection);
            }
        });
        buttonPanel.add(topListButton);
        buttonPanel.add(mapBuilderButton);
        buttonPanel.add(freePlayButton);
        buttonPanel.add(onlinePlayButton);
        buttonPanel.add(myMapsButton);
        frame.add(buttonPanel);
        frame.pack();
        frame.setSize(1280,720);
        frame.setVisible(true);
    }

   
}
