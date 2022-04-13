package game.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.io.IOException;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import game.*;
import game.model.Cell;
import game.model.LabyrinthBuilder;
import game.model.Player;
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
                    try {
                        restartGame();
                    } catch (IncorrectMapSizeException ex) {
                        ex.printStackTrace();
                    }
                }
                gameStatLabel.setText("Score: " + score + " Creator: " + mapCreator + " Time: " + time++);
                menu.repaint();
            }
        });
    private JMenu menu;
    /** Grafikus UI konstruktora,, melyben meghívom a labirintusgenerálást, létrehozzuk az összes UI elemet, generáljuk a játékost és a sárkányt.*/
    public AdventureGUI() throws IOException,IncorrectMapSizeException
    {
        super();
        labyrinth = new LabyrinthBuilder(true,"");
        cells = labyrinth.getCells();
        mainPanel = new Labyrinth(this);
        //System.out.println("cell size: " + cells.size());
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem Help = new JMenuItem("Help");
        JMenuItem TopList = new JMenuItem("Toplist");
        menu = new JMenu();
        Steve.setCoords(mainPanel.getStartingCell().getrowIdx(), mainPanel.getStartingCell().getcolIdx());
        Steve.setPixelX(mainPanel.getStartingCell().getPixelX());
        Steve.setPixelY(mainPanel.getStartingCell().getPixelY());
        //System.out.println(Steve);
        newGame.addActionListener(new ActionListener(){
           @Override
           /** newgame indítás menüből */
        public void actionPerformed (ActionEvent e) 
        {
           score=0;
           time = 0;
            try {
                restartGame();
            } catch (IncorrectMapSizeException ex) {
                ex.printStackTrace();
            }
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
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
        refresher.start();
    }


    public AdventureGUI(OracleSqlManager dbConnection) throws IOException,IncorrectMapSizeException
    {
        super(dbConnection);
        String[] mapData = dbConnection.getRandomMap();
        labyrinth = new LabyrinthBuilder(false,mapData[0]);
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
                try {
                    restartGame();
                } catch (IncorrectMapSizeException ex) {
                    ex.printStackTrace();
                }
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
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
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
    public void restartGame() throws IncorrectMapSizeException
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
          if(dbConnection != null) new AdventureGUI(dbConnection);
          else new AdventureGUI();
            }
            catch (IOException f)
            {
                System.out.println(f.getMessage());
            }
    }
    
}


