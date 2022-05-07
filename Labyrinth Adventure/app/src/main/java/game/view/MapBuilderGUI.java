/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import game.*;
import game.model.Dragon;
import game.model.Labyrinth;
import persistence.*;

import javax.swing.Timer;

public class MapBuilderGUI extends GUIWindow {

    private final ActionListener helpAction = new ActionListener() {
        String[] Buttons = {"OK"};
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showOptionDialog(null,"You can build a labyrinth here. To select a starting cell,\n " +
                    "start holding down the left mouse button, and drag your cursor to make the way! \n " +
                    "Use your right mouse button to select an ending cell, and once you're ready, \n " +
                    "you'll be able to save it in the bottom menu.","Help",JOptionPane.NO_OPTION,JOptionPane.OK_OPTION,null,Buttons,Buttons[0]);
        }
    };
    /**
     * The window responsible for building maps.
     * @param dbConnection the SQL connection, where it can save the map to.
     * @throws IOException if we couldn't load an asset.
     * @throws IncorrectMapException if something went wrong while generating the empty maze.
     */
    public MapBuilderGUI(OracleSqlManager dbConnection) throws IOException,IncorrectMapException
    {
        super();
        this.dbConnection = dbConnection;
        labyrinth = new Labyrinth(false,"");
        cells = labyrinth.getCells();
        getContentPane().add(BorderLayout.SOUTH, bottomMenu);
        JMenuItem saveMap = new JMenuItem("Save Map");
        saveMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(labyrinth.getEndingCell() == null)
                {
                    labyrinth.getCurrentCell().setEndingCell(true);
                }
                String alias = JOptionPane.showInputDialog(null,"Please enter an alias for the map","Saving map",JOptionPane.QUESTION_MESSAGE);
                Dragon drake = new Dragon(mainPanel.getStartingCell(),cells,0);
                    String[] buttons = {"OK"};
                    if(drake.doTremauxPathFinding())
                    {
                        dbConnection.saveMap(labyrinth.toMapDataString(),alias);
                        dispose();
                    }
                    else
                    {
                        JOptionPane.showOptionDialog(null,"The AI didn't found a way out of your maze." +
                                " Please continue making the maze, and make sure there is a way out of your maze.","Invalid map",
                                JOptionPane.NO_OPTION,JOptionPane.OK_OPTION,null,buttons,buttons[0]);
                    }
            }
        });
        refresher = new Timer(REFRESH_TIME_FOR_60FPS,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.repaint();
            }
        });
        menu.add(saveMap);
        help.addActionListener(helpAction);
        menu.add(help);
        mainPanel = new LabyrinthPanel(this,false);
        mainPanel.addMouseMotionListener(new CellMouseAdapter(cells,mainPanel,labyrinth));
        mainPanel.addMouseListener(new CellMouseAdapter(cells,mainPanel,labyrinth));
        getContentPane().add(BorderLayout.CENTER, mainPanel);
        dispose();
        setExtendedState(MAXIMIZED_BOTH);
        setResizable(false);
        setUndecorated(true);
        setVisible(true);
        refresher.start();
    }
}



