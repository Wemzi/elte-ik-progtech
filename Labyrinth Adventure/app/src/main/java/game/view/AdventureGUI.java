package game.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import java.io.IOException;

import game.*;
import game.model.Cell;
import game.model.LabyrinthBuilder;
import game.model.Player;
import persistence.*;

public class AdventureGUI extends MapBuilder {
    private static int score=0;
    private static int time = 0;
    private final int REFRESH_TIME_FOR_60FPS=15;
    private final int SECONDINMS=1000;
    private MainMenu parentMenu;
    private String mapCreator = "Aldous-Broder Algorithm";
    private JMenuItem newGame = new JMenuItem("New Game");
    private JMenuItem backToMainMenu = new JMenuItem("Back to main menu");
    private JMenuItem help = new JMenuItem("Help");
    private Player Steve = new Player();
    private KeyHandler keyHandler = new KeyHandler();
    private JMenu menu;
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
    /** Grafikus UI konstruktora,, melyben meghívom a labirintusgenerálást, létrehozzuk az összes UI elemet, generáljuk a játékost és a sárkányt.*/
    public AdventureGUI(MainMenu parentMenu) throws IOException,IncorrectMapSizeException
    {
        super();
        this.parentMenu = parentMenu;
        labyrinth = new LabyrinthBuilder(true,"");
        cells = labyrinth.getCells();
        refresher = new Timer(15,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.repaint();
                updateVisibleCells();
            }
        });
        mainPanel = new Labyrinth(this);
        //System.out.println("cell size: " + cells.size();
        menu = new JMenu("Menu");
        bottomMenu = new JMenuBar();
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
        menu.add(help);
        menu.add(backToMainMenu);
        bottomMenu.add(menu);
        bottomMenu.add(gameStatLabel);
        backToMainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(frame,"Are you sure you want to quit? Any unsaved progress will be lost.","Confirmation", JOptionPane.OK_CANCEL_OPTION);
                if(response == JOptionPane.OK_OPTION)
                {
                    frame.dispose();
                }
            }
        });
        gameStatLabel.setText("Score: " + score);
        frame.getContentPane().add(BorderLayout.SOUTH, bottomMenu);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
        timer.start();
        refresher.start();
    }


    public AdventureGUI(MainMenu parentMenu,OracleSqlManager dbConnection) throws IOException,IncorrectMapSizeException
    {
        super(dbConnection);
        this.parentMenu = parentMenu;
        String[] mapData = dbConnection.getRandomMap();
        labyrinth = new LabyrinthBuilder(false,mapData[0]);
        cells = labyrinth.getCells();
        mainPanel = new Labyrinth(this);
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem Help = new JMenuItem("Help");
        JMenuItem TopList = new JMenuItem("Toplist");
        refresher = new Timer(15,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                updateVisibleCells();
                mainPanel.repaint();
            }
        });
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
        frame.addKeyListener(keyHandler);
        menu.add(newGame);
        menu.add(Help);
        menu.add(TopList);
        bottomMenu.add(menu);
        bottomMenu.add(gameStatLabel);
        mapCreator = mapData[1];
        gameStatLabel.setText("Score: " + score + " Creator: " + mapCreator);
        frame.getContentPane().add(BorderLayout.SOUTH, bottomMenu);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
        timer.start();
        refresher.start();
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

    public void updateVisibleCells()
    {
        Cell currentCell = getCurrentCell(Steve);
        for(int idx=currentCell.getrowIdx(); idx<cells.get(currentCell.getcolIdx()).size();idx++) {
            Cell cell = cells.get(currentCell.getcolIdx()).get(idx);
            cell.setVisibleForPlayer(true);
            if(cell.getedgeRight()) break;
        }
        for(int idx=currentCell.getrowIdx(); idx>=0;idx--)
        {
            Cell cell = cells.get(currentCell.getcolIdx()).get(idx);
            cell.setVisibleForPlayer(true);
            if(cell.getedgeLeft()) break;
        }
        for(int idx=currentCell.getcolIdx();idx>=0; idx--)
        {
            Cell cell = cells.get(idx).get(currentCell.getrowIdx());
            cell.setVisibleForPlayer(true);
            if(cell.getedgeDown()) break;
        }
        for(int idx=currentCell.getcolIdx();idx<cells.size(); idx++)
        {
            Cell cell = cells.get(idx).get(currentCell.getrowIdx());
            cell.setVisibleForPlayer(true);
            if(cell.getedgeUp()) break;
        }
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
        refresher.stop();
        timer.stop();
        Dimension prevWindowDimensions = mainPanel.getSize();
        frame.getContentPane().remove(mainPanel);
        frame.getContentPane().remove(bottomMenu);
        labyrinth = new LabyrinthBuilder(true,"");
        cells = labyrinth.getCells();
        mainPanel = new Labyrinth(this);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.getContentPane().add(BorderLayout.SOUTH, bottomMenu);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        mainPanel.setSize(prevWindowDimensions);
        Steve.setCoords(mainPanel.getStartingCell().getrowIdx(), mainPanel.getStartingCell().getcolIdx());
        Steve.setPixelX(mainPanel.getStartingCell().getPixelX());
        Steve.setPixelY(mainPanel.getStartingCell().getPixelY());
        System.out.println(mainPanel.getHeight());
        refresher.start();
        timer.start();
        System.out.println("minden elindult");
    }
    
}


