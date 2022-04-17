/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import game.*;
import game.model.Cell;
import game.model.LabyrinthBuilder;
import game.model.Player;
import persistence.*;
import javax.swing.Timer;

public class MapBuilderGUI extends GUIWindow {

    public MapBuilderGUI(OracleSqlManager dbConnection) throws IOException,IncorrectMapSizeException
    {
        super();
        this.dbConnection = dbConnection;
        labyrinth = new LabyrinthBuilder(false,"");
        cells = labyrinth.getCells();
        frame.getContentPane().add(BorderLayout.SOUTH, bottomMenu);
        frame.setSize(1280,720);
        JMenuItem saveMap = new JMenuItem("Save Map");
        saveMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labyrinth.getCurrentCell().setEndingCell(true);
                String alias = JOptionPane.showInputDialog("Please enter an alias for the map","alias");
                dbConnection.saveMap(mainPanel.toMapDataString(),alias);
                frame.dispose();
            }
        });
        refresher = new Timer(REFRESH_TIME_FOR_60FPS,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.repaint();
            }
        });
        menu.add(saveMap);
        mainPanel = new Labyrinth(this);
        mainPanel.addMouseMotionListener(new CellMouseAdapter(cells,mainPanel,labyrinth));
        mainPanel.addMouseListener(new CellMouseAdapter(cells,mainPanel,labyrinth));
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setVisible(true);
        refresher.start();
    }
}



