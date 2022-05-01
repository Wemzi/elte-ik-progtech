/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;


import game.view.*;
import game.model.LabyrinthBuilder;
import persistence.OracleSqlManager;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;
import javax.swing.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JFrame {
    private GUIWindow gameInstance;
    private OracleSqlManager dbConnection;
    private BufferedImage background;
    private BufferedImage playOnlineImg;
    private BufferedImage playOfflineImg;
    private BufferedImage topListImg;
    private BufferedImage myMapsImg;
    private BufferedImage mapBuilderImg;
    private BufferedImage exitImg;
    private final Rectangle mapBuilderArea;
    private final Rectangle playOfflineArea;
    private final Rectangle playOnlineArea;
    private final Rectangle myMapsArea;
    private final Rectangle topListArea;
    private final Rectangle exitArea;

    /**
     * Constructs the main menu screen, starting with loading all the resources that we'll need.
     */
    public MainMenu() {
        try {
            ResourceLoader.initResources();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Hashtable<String, String> credentials = this.login(this);
        try {
            if (credentials.get("user") != null) {
                dbConnection = new OracleSqlManager(credentials.get("user"), credentials.get("pass"));
            }
        } catch (Exception e) {
            dbConnection = null;
        }
        background = ResourceLoader.bg;
        topListImg = ResourceLoader.toplist;
        myMapsImg = ResourceLoader.mymaps;
        playOfflineImg = ResourceLoader.playoffline;
        playOnlineImg = ResourceLoader.playonline;
        mapBuilderImg = ResourceLoader.mapbuilder;
        exitImg = ResourceLoader.exit;
        int width = 1920;
        int height = 1080;
        mapBuilderArea = new Rectangle(width / 7, 3 * height / 5, mapBuilderImg.getWidth(), mapBuilderImg.getHeight());
        playOnlineArea = new Rectangle(2 * width / 7, 3 * height / 5, playOnlineImg.getWidth(), playOnlineImg.getHeight());
        playOfflineArea = new Rectangle( 3 * width / 7, 3 * height / 5, playOfflineImg.getWidth(), playOfflineImg.getHeight());
        topListArea = new Rectangle(4 * width / 7, 3 * height / 5, topListImg.getWidth(), topListImg.getHeight());
        myMapsArea = new Rectangle(5 * width / 7, 3 * height / 5, myMapsImg.getWidth(), myMapsImg.getHeight());
        exitArea = new Rectangle(5 * width / 7, 4 * height / 5, exitImg.getWidth(), exitImg.getHeight());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (dbConnection != null) {
                    if (mapBuilderArea.contains(e.getPoint())) {
                        try {
                            new MapBuilderGUI(dbConnection);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (IncorrectMapException ex) {
                            ex.printStackTrace();
                        }
                    } else if (playOnlineArea.contains(e.getPoint())) {
                        startNewOnlineGame();
                    } else if (topListArea.contains(e.getPoint())) {
                        new TopList(dbConnection);
                    } else if (myMapsArea.contains(e.getPoint())) {
                        new MapList(dbConnection);
                    }
                }
                if (playOfflineArea.contains(e.getPoint())) {
                    startNewOfflineGame();
                }
                if (exitArea.contains(e.getPoint())) {
                    dispose();
                    return;

                }
            }

        });
        dispose();
        setResizable(false);
        setUndecorated(true);
        setVisible(true);
        setExtendedState(MAXIMIZED_BOTH);
    }

    /**
     * Defines how to draw the components of the main menu.
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(background,0,0,getHeight()/9*16,getHeight(),null);
        g.drawImage(playOfflineImg,playOfflineArea.x,playOfflineArea.y,null);
        g.drawImage(exitImg,exitArea.x,exitArea.y,null);
        if(dbConnection != null)
        {
            g.drawImage(playOnlineImg,playOnlineArea.x,playOnlineArea.y,null);
            g.drawImage(myMapsImg,myMapsArea.x,myMapsArea.y,null);
            g.drawImage(topListImg,topListArea.x,topListArea.y,null);
            g.drawImage(mapBuilderImg,mapBuilderArea.x,mapBuilderArea.y,null);
        }

    }

    /**
     * Displays a window, where the user can write their credentials to log into the ELTE Aramis server.
     * @param frame the parent frame.
     * @return a Hashtable containing the username and the password for the user.
     */
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
        JOptionPane.showMessageDialog(frame, panel, "Login into ELTE Aramis for Online features", JOptionPane.OK_CANCEL_OPTION);
        logininformation.put("user", username.getText());
        logininformation.put("pass", new String(password.getPassword()));
        return logininformation;
    }

    /**
     * Starts a new online game.
     */
    private void startNewOnlineGame()
    {
        try {
            this.gameInstance = new AdventureGUI(dbConnection);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IncorrectMapException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts a new offline game.
     */
    private void startNewOfflineGame()
    {
        try {
            this.gameInstance = new AdventureGUI();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IncorrectMapException e) {
            e.printStackTrace();
        }
    }

   
}
