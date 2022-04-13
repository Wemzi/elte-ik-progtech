/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;


import game.view.AdventureGUI;
import game.model.LabyrinthBuilder;
import game.view.MapBuilder;
import persistence.OracleSqlManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Hashtable;
import javax.swing.*;

public class MainMenu {
    public OracleSqlManager dbConnection;
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
        Hashtable<String,String> credentials = this.login(frame);
        try
        {
            dbConnection = new OracleSqlManager(credentials.get("user"),credentials.get("pass"));
        }
        catch(Exception e)
        {
            dbConnection = null;
            e.printStackTrace();
            System.out.println("connection wasn't successful, switching to offline mode ");
        }

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
                } catch (IOException | IncorrectMapSizeException ex) {
                    ex.printStackTrace();
                }
            }
        });
        freePlayButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new AdventureGUI();
                    } catch (IOException | IncorrectMapSizeException ex) {
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
        if(dbConnection != null)
        {
            buttonPanel.add(topListButton);
            buttonPanel.add(mapBuilderButton);
            buttonPanel.add(onlinePlayButton);
            buttonPanel.add(myMapsButton);
        }
        buttonPanel.add(freePlayButton);
        frame.add(buttonPanel);
        frame.pack();
        frame.setSize(1280,720);
        frame.setVisible(true);
    }


    public Hashtable<String, String> login(JFrame frame) {
        Hashtable<String, String> logininformation = new Hashtable<String, String>();

        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
        label.add(new JLabel("User name", SwingConstants.RIGHT));
        label.add(new JLabel("Password", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField username = new JTextField();
        controls.add(username);
        JPasswordField password = new JPasswordField();
        controls.add(password);
        panel.add(controls, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(frame, panel, "Login into ELTE Aramis DB", JOptionPane.OK_CANCEL_OPTION);

        logininformation.put("user", username.getText());
        logininformation.put("pass", new String(password.getPassword()));
        return logininformation;
    }

   
}
