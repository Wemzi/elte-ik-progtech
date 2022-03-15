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

    private JFrame frame;
    private Labyrinth mainPanel;
    private LabyrinthBuilder labyrinth;
    private ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>();
    private Dragon Drake;
    private JMenuBar bottomMenu;
    private final JLabel gameStatLabel = new JLabel("");
    private Database data = new Database();
    private JMenu menu;
    /** Grafikus UI konstruktora,, melyben meghívom a labirintusgenerálást, létrehozzuk az összes UI elemet, generáljuk a játékost és a sárkányt.*/
    public MapBuilder() throws IOException
    {
        frame = new JFrame("Labyrinth Adventure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        labyrinth = new LabyrinthBuilder(true);
        cells = labyrinth.getCells();
        mainPanel = new Labyrinth(this);
        bottomMenu=new JMenuBar();
        menu = new JMenu("Menu");
        bottomMenu.add(menu);
        bottomMenu.add(gameStatLabel);
        frame.getContentPane().add(BorderLayout.SOUTH, bottomMenu);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setVisible(true);
        frame.setSize(600,600);
        mainPanel.addMouseMotionListener(new MouseAdapter(){
            Cell prevCell = null;
            Cell currentCell = null;
            public Cell getCurrentCell(MouseEvent e)
            {
                int picSize = mainPanel.getPicSize();
                int x = (e.getX())/picSize;
                int y = cells.size()-Math.round(e.getY()/picSize)-1;
                Cell  ret = cells.get(y).get(x);
                return ret;
            }

            @Override
            public void mouseClicked(MouseEvent e)
            {
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                //System.out.println("called");
                prevCell = currentCell;
                currentCell = getCurrentCell(e);
                if(prevCell != null )
                {
                    Direction dir = null;
                    if(currentCell.getcolIdx()> prevCell.getcolIdx())
                    {
                        dir = Direction.UP;
                        labyrinth.setCurrentCell(prevCell);
                        labyrinth.moveToAdjacentCell(dir);
                    }
                    else if(currentCell.getcolIdx()< prevCell.getcolIdx())
                    {
                        dir = Direction.DOWN;
                        labyrinth.setCurrentCell(prevCell);
                        labyrinth.moveToAdjacentCell(dir);
                    }
                    else if(currentCell.getrowIdx() > prevCell.getrowIdx())
                    {
                        dir = Direction.RIGHT;
                        labyrinth.setCurrentCell(prevCell);
                        labyrinth.moveToAdjacentCell(dir);
                    }
                    else if(currentCell.getrowIdx()<prevCell.getrowIdx())
                    {
                        dir = Direction.LEFT;
                        labyrinth.setCurrentCell(prevCell);
                        labyrinth.moveToAdjacentCell(dir);
                    }
                    if(dir != null)
                    {
                        System.out.println(dir);
                        mainPanel.repaint();
                    }

                }
            }
        });
       // frame.setResizable(false);
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

    
}



