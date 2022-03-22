/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.io.IOException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import persistence.*;
import javax.swing.Timer;

public class MapBuilder {

    protected JFrame frame;
    protected Labyrinth mainPanel;
    protected LabyrinthBuilder labyrinth;
    protected Player Steve = new Player();
    protected ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>();
    protected JMenuBar bottomMenu;
    protected final JLabel gameStatLabel = new JLabel("");
    Timer refresher;
    protected JMenu menu;
    /** Grafikus UI konstruktora,, meghívom a labirintusgenerálást, létrehozzuk az összes UI elemet, generáljuk a játékost és a sárkányt.*/
    public MapBuilder() throws IOException
    {
        ResourceLoader.initResources();
        frame = new JFrame("Labyrinth Adventure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        labyrinth = new LabyrinthBuilder(true);
        cells = labyrinth.getCells();
        bottomMenu=new JMenuBar();
        menu = new JMenu("Menu");
        bottomMenu.add(menu);
        frame.getContentPane().add(BorderLayout.SOUTH, bottomMenu);
        frame.setSize(1280,720);
        refresher = new Timer(16,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                for(ArrayList<Cell> cellRow : cells)
                {
                    for(Cell cell : cellRow)
                    {
                        try {
                            cell.selectImage();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                mainPanel.repaint();
            }
        });
    }
    public void buildMap()
    {
        mainPanel = new Labyrinth(this);
        mainPanel.addMouseMotionListener(new CellMouseAdapter(cells,mainPanel,labyrinth));
        mainPanel.addMouseListener(new CellMouseAdapter(cells,mainPanel,labyrinth));
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setVisible(true);
        refresher.start();
    }
    public JFrame getFrame()
    {
        return frame;
    }
    public ArrayList<ArrayList<Cell>> getCells()
    {
        return cells;
    }
    public JPanel getLabyrinth()
    {
        return this.mainPanel;
    }

    public Cell getCurrentCell(Player Steve)
    {
        int picSize = mainPanel.getPicSize();
        int x = (Steve.getPixelX())/picSize;
        int y = cells.size()-Math.round(Steve.getPixelY()/picSize)-1;
        Cell  ret = cells.get(y).get(x);
        return ret;
    }

    
}



