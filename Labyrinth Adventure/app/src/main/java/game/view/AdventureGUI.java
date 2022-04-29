package game.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import java.io.IOException;

import game.*;
import game.model.Cell;
import game.model.Dragon;
import game.model.LabyrinthBuilder;
import game.model.Player;
import persistence.*;

public class AdventureGUI extends GUIWindow {
    private static int score=0;
    private static int time = 0;
    private final MainMenu parentMenu;
    private Dragon drake;
    private String mapCreator = "Aldous-Broder Algorithm";
    private final JMenuItem newGame = new JMenuItem("New Game");
    private final JMenuItem backToMainMenu = new JMenuItem("Back to main menu");
    private final JMenuItem help = new JMenuItem("Help");
    private final Player Steve = new Player();
    private final KeyHandler keyHandler = new KeyHandler();
    private final JMenu menu = new JMenu("Menu");
    private int waitTimeBetWeenAIIterations=1500;
    private boolean didDrakeFindThePath;
    private final JMenuBar bottomMenu = new JMenuBar();
    private final ActionListener backToMainMenuAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int response = JOptionPane.showConfirmDialog(frame,"Are you sure you want to quit? Any unsaved progress will be lost.","Confirmation", JOptionPane.OK_CANCEL_OPTION);
            if(response == JOptionPane.OK_OPTION)
            {
                stopGame();
            }
        }
    };
    private Timer timer = new Timer(SECONDINMS, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(isWon())
                {
                    try {
                        restartGame();
                        score++;
                    } catch (IncorrectMapException ex) {
                        ex.printStackTrace();
                    }
                }
                else if(isLost())
                {
                    String[] buttons = {"OK"};
                    JOptionPane.showOptionDialog(frame,"You lost. The score you earned is:" + score + " if you have internet connection available, it should synch automatically.","Lost game",JOptionPane.NO_OPTION,JOptionPane.OK_OPTION,null,buttons,buttons[0]);
                    if(dbConnection != null) dbConnection.saveHighScore(score);
                    stopGame();
                }
                gameStatLabel.setText("Score: " + score + " Creator: " + mapCreator + " Time: " + time++);
                menu.repaint();
            }
        });
    /** Grafikus UI konstruktora,, melyben meghívom a labirintusgenerálást, létrehozzuk az összes UI elemet, generáljuk a játékost és a sárkányt.*/
    public AdventureGUI(MainMenu parentMenu) throws IOException,IncorrectMapException
    {
        super();
        this.parentMenu = parentMenu;
        labyrinth = new LabyrinthBuilder(true,"");
        cells = labyrinth.getCells();
        mainPanel = new Labyrinth(this,false);
        Steve.setCoords(mainPanel.getStartingCell().getrowIdx(), mainPanel.getStartingCell().getcolIdx());
        Steve.setPixelX(mainPanel.getStartingCell().getPixelX());
        Steve.setPixelY(mainPanel.getStartingCell().getPixelY());
        newGame.addActionListener(new ActionListener(){
           @Override
           /** newgame indítás menüből */
        public void actionPerformed (ActionEvent e)
        {
           score=0;
           time = 0;
            try {
                restartGame();
            } catch (IncorrectMapException ex) {
                ex.printStackTrace();
            }
        }});
        refresher = new Timer(15,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                updateVisibleCells();
                mainPanel.repaint();
            }
        });
        /** A billentyűlenyomáshoz kapcsolt eseménykezelő, mely elmozdítja a játékost, és a sárkányt is, megvizsgálja, hogy vége van e a játéknak,majd ha nem, újrarajzolja a pályát. */
        frame.addKeyListener(keyHandler);
        menu.add(newGame);
        menu.add(help);
        menu.add(backToMainMenu);
        bottomMenu.add(menu);
        bottomMenu.add(gameStatLabel);
        backToMainMenu.addActionListener(backToMainMenuAction);
        frame.getContentPane().add(BorderLayout.SOUTH, bottomMenu);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
        timer.start();
        refresher.start();
        drake = new Dragon(mainPanel.getStartingCell(),cells,waitTimeBetWeenAIIterations);
            Thread drakeThread = new Thread(()-> {
                try {
                    didDrakeFindThePath = drake.doTremauxPathFinding();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            drakeThread.start();
    }



    public AdventureGUI(MainMenu parentMenu,OracleSqlManager dbConnection) throws IOException,IncorrectMapException
    {
        super();
        this.dbConnection = dbConnection;
        this.parentMenu = parentMenu;
        String[] mapData = dbConnection.getRandomMap();
        labyrinth = new LabyrinthBuilder(false,mapData[0]);
        cells = labyrinth.getCells();
        mainPanel = new Labyrinth(this,true);
        mainPanel.addMouseListener(new CellMouseAdapter(cells,mainPanel,labyrinth));
        refresher = new Timer(15,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                updateVisibleCells();
                mainPanel.repaint();
            }
        });
        newGame.addActionListener(new ActionListener(){
            @Override
            /** newgame indítás menüből */
            public void actionPerformed (ActionEvent e)
            {
                score=0;
                time = 0;
                try {
                    restartGame();
                } catch (IncorrectMapException ex) {
                    ex.printStackTrace();
                }
            }
        });
        menu.add(newGame);
        menu.add(help);
        menu.add(backToMainMenu);
        bottomMenu.add(menu);
        frame.addKeyListener(keyHandler);
        mapCreator = mapData[1];
        gameStatLabel.setText("Score: " + score + " Creator: " + mapCreator);
        bottomMenu.add(gameStatLabel);
        frame.getContentPane().add(BorderLayout.SOUTH, bottomMenu);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
        timer.start();
        refresher.start();
    }

    private void stopGame()
    {
        time = 0;
        score = 0;
        timer.stop();
        refresher.stop();
        frame.dispose();
    }

    public Cell getStartingCell()
    {
        for(ArrayList<Cell> cellRow : cells)
        {
            for(Cell cell : cellRow)
            {
                if(cell.isStartingCell())return cell;
            }
        }
        return null;
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

    public boolean isLost()
    {
        return didDrakeFindThePath == true;
    }

    public boolean isWon()
    {
        return getCurrentCell(Steve) == mainPanel.getEndingCell();
    }

    public Dragon getDrake() { return drake;}

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
    public void restartGame() throws IncorrectMapException
    {
        refresher.stop();
        timer.stop();
        Dimension prevWindowDimensions = mainPanel.getSize();
        frame.getContentPane().remove(mainPanel);
        frame.getContentPane().remove(bottomMenu);
        if(dbConnection == null)
        {
            labyrinth = new LabyrinthBuilder(true,"");
        }
        else
        {
            String[] mapData = dbConnection.getRandomMap();
            labyrinth = new LabyrinthBuilder(false,mapData[0]);
            mapCreator = mapData[1];
        }
        cells = labyrinth.getCells();
        mainPanel = new Labyrinth(this,false);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.getContentPane().add(BorderLayout.SOUTH, bottomMenu);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        mainPanel.setSize(prevWindowDimensions);
        Steve.setCoords(mainPanel.getStartingCell().getrowIdx(), mainPanel.getStartingCell().getcolIdx());
        Steve.setPixelX(mainPanel.getStartingCell().getPixelX());
        Steve.setPixelY(mainPanel.getStartingCell().getPixelY());
        drake = new Dragon(mainPanel.getStartingCell(),cells,waitTimeBetWeenAIIterations);
        try {
            drake.doTremauxPathFinding();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(mainPanel.getHeight());
        refresher.start();
        timer.start();
        System.out.println("minden elindult");
    }
    
}


