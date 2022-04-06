package game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.io.IOException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import persistence.*;
import javax.swing.Timer;

public class AdventureGUI extends MapBuilder {
    private static int score=0;
    private static int time = 0;
    private String mapCreator = "Aldous-Broder Algorithm";
    private Player Steve = new Player();
    private KeyHandler keyHandler = new KeyHandler();
    private Timer timer = new Timer(1000, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(isWon())
                {
                    restartGame();
                }
                gameStatLabel.setText("Score: " + score + " Creator: " + mapCreator + " Time: " + time++);
                menu.repaint();
            }
        });
    private JMenu menu;
    /** Grafikus UI konstruktora,, melyben meghívom a labirintusgenerálást, létrehozzuk az összes UI elemet, generáljuk a játékost és a sárkányt.*/
    public AdventureGUI() throws IOException
    {
        super();
        labyrinth = new LabyrinthBuilder(false);
        cells = labyrinth.getCells();
        mainPanel = new Labyrinth(this);
        System.out.println("cell size: " + cells.size());
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem Help = new JMenuItem("Help");
        JMenuItem TopList = new JMenuItem("Toplist");
        menu = new JMenu();
        Steve.setCoords(mainPanel.getStartingCell().getrowIdx(), mainPanel.getStartingCell().getcolIdx());
        Steve.setPixelX(mainPanel.getStartingCell().getPixelX());
        Steve.setPixelY(mainPanel.getStartingCell().getPixelY());
        System.out.println(Steve);
        newGame.addActionListener(new ActionListener(){
           @Override
           /** newgame indítás menüből */
        public void actionPerformed (ActionEvent e) 
        {
           //data.storeHighScore(cells.size(), score);
           score=0;
           time = 0;
           restartGame();
        }
        });
        /** A billentyűlenyomáshoz kapcsolt eseménykezelő, mely elmozdítja a játékost, és a sárkányt is, megvizsgálja, hogy vége van e a játéknak,majd ha nem, újrarajzolja a pályát. */
        frame.addKeyListener(keyHandler);
        menu.add(newGame);
        menu.add(Help);
        menu.add(TopList);
        timer.start();
        bottomMenu.add(menu);
        bottomMenu.add(gameStatLabel);
        gameStatLabel.setText("Score: " + score);
        frame.getContentPane().add(BorderLayout.SOUTH, bottomMenu);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(1280,720);
        frame.setVisible(true);
        refresher.start();
    }


    public AdventureGUI(OracleSqlManager dbConnection) throws IOException
    {
        super(dbConnection);
        String[] mapData = dbConnection.getRandomMap();
        labyrinth = new LabyrinthBuilder(mapData[0]);
        cells = labyrinth.getCells();
        mainPanel = new Labyrinth(this);
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem Help = new JMenuItem("Help");
        JMenuItem TopList = new JMenuItem("Toplist");
        menu = new JMenu();
        mainPanel.addMouseListener(new CellMouseAdapter(cells,mainPanel,labyrinth));
        newGame.addActionListener(new ActionListener(){
            @Override
            /** newgame indítás menüből */
            public void actionPerformed (ActionEvent e)
            {
                score=0;
                time = 0;
                restartGame();
            }
        });
        refresher.start();
        frame.addKeyListener(keyHandler);
        menu.add(newGame);
        menu.add(Help);
        menu.add(TopList);
        timer.start();
        bottomMenu.add(menu);
        bottomMenu.add(gameStatLabel);
        mapCreator = mapData[1];
        gameStatLabel.setText("Score: " + score + " Creator: " + mapCreator);
        frame.getContentPane().add(BorderLayout.SOUTH, bottomMenu);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(1280,720);
        frame.setVisible(true);
    }

    public void updatePlayer()
    {
        Cell cell = getCurrentCell(Steve);
        if(keyHandler.downPressed && !(cell.getedgeDown() && DistanceManager.isClosetoEdges(Steve,cell, mainPanel.getPicSize(),Direction.DOWN)))
        {
            Steve.move(Direction.DOWN,cells.size(),cells.get(0).size());
        }
        else if(keyHandler.leftPressed && !(cell.getedgeLeft() && DistanceManager.isClosetoEdges(Steve,cell, mainPanel.getPicSize(),Direction.LEFT)))
        {
            Steve.move(Direction.LEFT,cells.size(),cells.get(0).size());
        }
        else if(keyHandler.upPressed && !(cell.getedgeUp() && DistanceManager.isClosetoEdges(Steve,cell, mainPanel.getPicSize(),Direction.UP)))
        {
            Steve.move(Direction.UP,cells.size(),cells.get(0).size());
        }
        else if(keyHandler.rightPressed &&  !(cell.getedgeRight() && DistanceManager.isClosetoEdges(Steve,cell, mainPanel.getPicSize(),Direction.RIGHT)))
        {
            Steve.move(Direction.RIGHT,cells.size(),cells.get(0).size());
        }
        Steve.setCoords(getCurrentCell(Steve).getrowIdx(),getCurrentCell(Steve).getcolIdx());
    }

    public boolean isWon()
    {
        return getCurrentCell(Steve) == mainPanel.getEndingCell();
    }

    public JFrame getFrame()
    {
        return frame;
    }

    public ArrayList<ArrayList<Cell>> getCells()
    {
        return cells;
    }

    public Player getPlayer()
    {
        return Steve;
    }
    /** újraindító metódus */
    public void restartGame()
    {
        try
            {
                refresher.stop();
          frame.dispose();
          Steve = null;
          labyrinth = null;
          mainPanel = null;
          cells = null;
          bottomMenu = null;
          timer.stop();
          new AdventureGUI(dbConnection);
            }
            catch (IOException f)
            {
                System.out.println(f.getMessage());
            }
    }
    
}


