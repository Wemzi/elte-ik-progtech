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
    protected Player Steve;
    protected ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>();
    protected JMenuBar bottomMenu;
    protected final JLabel gameStatLabel = new JLabel("");
    Timer refresher;
    protected JMenu menu;
    /** Grafikus UI konstruktora,, meghívom a labirintusgenerálást, létrehozzuk az összes UI elemet, generáljuk a játékost és a sárkányt.*/
    public MapBuilder() throws IOException
    {
        ResourceLoader.initResources();
        Steve = new Player();
        frame = new JFrame("Labyrinth Adventure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        labyrinth = new LabyrinthBuilder(true);
        cells = labyrinth.getCells();
        bottomMenu=new JMenuBar();
        menu = new JMenu("Menu");
        bottomMenu.add(menu);
        frame.getContentPane().add(BorderLayout.SOUTH, bottomMenu);
        frame.setSize(1280,720);
        refresher = new Timer(15,new ActionListener(){
            int ticks = 0;
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
                ticks++;
                if(ticks==60)
                {
                    System.out.println(60);
                    ticks = 0;
                }
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
        int x = (Steve.getPixelX()+Steve.myLook.getWidth()/2)/picSize;
        int y = (Steve.getPixelY()+Steve.myLook.getHeight())/picSize;
        Cell  ret = cells.get(y).get(x);
        //System.out.println(ret + " " + Steve.getPixelY() + " "  +  Steve.getPixelX() );
        return ret;
    }

    
}



