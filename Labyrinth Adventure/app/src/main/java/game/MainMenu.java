/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import persistence.OracleSqlManager;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author LUD1BP
 */
public class MainMenu {
    public OracleSqlManager dbConnection = new OracleSqlManager("idu27k","almafa");
    private JFrame frame;
    private LabyrinthBuilder labyrinth;
    private final JLabel gameStatLabel = new JLabel("");
    private static int score=0;
    private static int time = 0;
    private JButton topListButton = new JButton("Toplist");
    private JButton mapBuilderButton = new JButton("Map Builder");
    private JButton freePlayButton = new JButton("Offline Play");
    private JButton onlinePlayButton = new JButton("Online Play");
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
        onlinePlayButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new AdventureGUI(dbConnection.getRandomMap());
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
        buttonPanel.add(topListButton);
        buttonPanel.add(mapBuilderButton);
        buttonPanel.add(freePlayButton);
        buttonPanel.add(onlinePlayButton);
        frame.add(buttonPanel);
        frame.pack();
        frame.setSize(1280,720);
        frame.setVisible(true);
    }

   
}
