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
import game.model.*;
import persistence.*;
import javax.swing.Timer;

public class MapBuilderGUI extends GUIWindow {

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
        labyrinth = new LabyrinthBuilder(false,"");
        cells = labyrinth.getCells();
        getContentPane().add(BorderLayout.SOUTH, bottomMenu);
        JMenuItem saveMap = new JMenuItem("Save Map");
        saveMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labyrinth.getCurrentCell().setEndingCell(true);
                String alias = JOptionPane.showInputDialog("Please enter an alias for the map","alias");
                Dragon drake = new Dragon(mainPanel.getStartingCell(),cells,0);
                try {
                    String[] buttons = {"OK"};
                    if(drake.doTremauxPathFinding())
                    {
                        dbConnection.saveMap(mainPanel.toMapDataString(),alias);
                    }
                    else
                    {
                        JOptionPane.showOptionDialog(null,"The AI didn't found a way out of your maze. Please start again, and make sure there is a way out of your maze.","Invalid map",JOptionPane.NO_OPTION,JOptionPane.OK_OPTION,null,buttons,buttons[0]);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                dispose();
            }
        });
        refresher = new Timer(REFRESH_TIME_FOR_60FPS,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.repaint();
            }
        });
        menu.add(saveMap);
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



