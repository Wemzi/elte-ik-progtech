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
import game.model.Labyrinth;
import game.model.Player;
import persistence.*;

public class AdventureGUI extends GUIWindow {
    private int score=0;
    private int time = 0;
    private Dragon drake;
    private String mapCreator = "Aldous-Broder Algorithm";
    private final JMenuItem newGame = new JMenuItem("New Game");
    private final Player Steve = new Player();
    private final KeyHandler keyHandler = new KeyHandler();
    private final JMenu menu = new JMenu("Menu");
    private int waitTimeBetWeenAIIterations=1500;
    private final int DEFAULT_WAITING_TIME = 1500;
    private boolean didDrakeFindThePath;
    private final ActionListener helpAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] buttons = {"OK"};
            JOptionPane.showOptionDialog(null,"Use the Arrow Keys to move, \n " +
                    "and try to be faster in getting out of the maze than the Dragon! \n " +
                    "If you see a whole brick block somewhere, means that it is yet undiscovered, \n " +
                    "You'll have to approach it from the right direction! Good luck!","Help",JOptionPane.NO_OPTION,JOptionPane.OK_OPTION,null,buttons,buttons[0]);
        }
    };
    private final JMenuBar bottomMenu = new JMenuBar();
    private Timer spriteUpdater = new Timer(SPRITE_UPDATE_FREQUENCY, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Steve.updateLook();
        }
    });
    private Thread drakeThread;
    private Timer timer = new Timer(SECONDINMS, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(isWon())
                {
                    try {
                        waitTimeBetWeenAIIterations *= 0.95;
                        restartGame();
                        score++;

                    } catch (IncorrectMapException ex) {
                        ex.printStackTrace();
                    }
                }
                else if(isLost())
                {
                    String[] buttons = {"OK"};
                    JOptionPane.showOptionDialog(null,"You lost. The score you earned is:" + score
                            + " if you have internet connection available, it should synch automatically.","Lost game",
                            JOptionPane.NO_OPTION,JOptionPane.OK_OPTION,null,buttons,buttons[0]);
                    if(dbConnection != null && dbConnection.getHighScore() < score) dbConnection.saveHighScore(score);
                    stopGame();
                }
                gameStatLabel.setText("Score: " + score + " Creator: " + mapCreator + " Time: " + time++);
                menu.repaint();
                //System.out.println("called");
            }
        });

    /**
     * Constructs the Gameplay window, with the offline Version.
     * @throws IOException if a resource file couldn't be loaded.
     * @throws IncorrectMapException if the Labyrinth can't build the labyrinth.
     */
    public AdventureGUI() throws IOException,IncorrectMapException
    {
        super();
        labyrinth = new Labyrinth(true,"");
        cells = labyrinth.getCells();
        mainPanel = new LabyrinthPanel(this,false);
        Steve.setCoords(mainPanel.getStartingCell().getrowIdx(), mainPanel.getStartingCell().getcolIdx());
        newGame.addActionListener(new ActionListener(){
           @Override
        public void actionPerformed (ActionEvent e)
        {
           score=0;
           time = 0;
           waitTimeBetWeenAIIterations = DEFAULT_WAITING_TIME;
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
        addKeyListener(keyHandler);
        help.addActionListener(helpAction);
        menu.add(help);
        menu.add(newGame);
        menu.add(backToMainMenu);
        bottomMenu.add(menu);
        bottomMenu.add(gameStatLabel);
        backToMainMenuAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopGame();
            }
        };
        getContentPane().add(BorderLayout.SOUTH, bottomMenu);
        getContentPane().add(BorderLayout.CENTER, mainPanel);
        setExtendedState(MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
        timer.start();
        refresher.start();
        spriteUpdater.start();
        drake = new Dragon(mainPanel.getStartingCell(),cells,waitTimeBetWeenAIIterations);
        drakeThread = new Thread(()-> {
            didDrakeFindThePath = drake.doTremauxPathFinding();});
        drakeThread.start();
    }


    /**
     * Constructs our gameplay window, with the online version.
     * @param dbConnection the connection to the SQL server.
     * @throws IOException if a resource couldn't be loaded.
     * @throws IncorrectMapException if the map data loaded from the SQL server is invalid.
     */
    public AdventureGUI(OracleSqlManager dbConnection) throws IOException,IncorrectMapException
    {
        super();
        this.dbConnection = dbConnection;
        String[] mapData = dbConnection.getRandomMap();
        labyrinth = new Labyrinth(false,mapData[0]);
        cells = labyrinth.getCells();
        mainPanel = new LabyrinthPanel(this,false);
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
        help.addActionListener(helpAction);
        menu.add(help);
        menu.add(newGame);
        menu.add(backToMainMenu);
        backToMainMenuAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopGame();
            }
        };
        bottomMenu.add(menu);
        addKeyListener(keyHandler);
        mapCreator = mapData[1];
        gameStatLabel.setText("Score: " + score + " Creator: " + mapCreator);
        bottomMenu.add(gameStatLabel);
        getContentPane().add(BorderLayout.SOUTH, bottomMenu);
        getContentPane().add(BorderLayout.CENTER, mainPanel);
        setExtendedState(MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
        drake = new Dragon(mainPanel.getStartingCell(),cells,waitTimeBetWeenAIIterations);
        drakeThread = new Thread(()-> {didDrakeFindThePath = drake.doTremauxPathFinding();});
        drakeThread.start();
        timer.start();
        refresher.start();
        spriteUpdater.start();
    }

    /**
     * Stops the game, resetting time and score, stopping the timers.
     */
    private void stopGame()
    {
        time = 0;
        score = 0;
        timer.stop();
        refresher.stop();
        spriteUpdater.stop();
        dispose();
    }

    /**
     * @param Steve the player.
     * @return the Cell which is the player at currently.
     */
    public Cell getCurrentCell(Player Steve)
    {
        int picSize = mainPanel.getPicSize();
        int x = (Steve.getPixelX()+Steve.myLook.getWidth()/2)/picSize;
        int y = (Steve.getPixelY()+Steve.myLook.getHeight()/2)/picSize;
        Cell  ret = cells.get(y).get(x);
        return ret;
    }


    /**
     * Updates attributes of the player according to the KeyHandler.
     */
    public void updatePlayer()
    {
        Cell cell = getCurrentCell(Steve);
        if(keyHandler.downPressed && !(cell.getedgeDown() && DistanceManager.isClosetoEdges(Steve,cell, mainPanel.getPicSize(),Direction.DOWN)))
        {
            Steve.setAmIMoving(true);
            Steve.move(Direction.DOWN,cells.size(),cells.get(0).size());
        }
        else if(keyHandler.leftPressed && !(cell.getedgeLeft() && DistanceManager.isClosetoEdges(Steve,cell, mainPanel.getPicSize(),Direction.LEFT)))
        {
            Steve.setAmIMoving(true);
            Steve.move(Direction.LEFT,cells.size(),cells.get(0).size());
        }
        else if(keyHandler.upPressed && !(cell.getedgeUp() && DistanceManager.isClosetoEdges(Steve,cell, mainPanel.getPicSize(),Direction.UP)))
        {
            Steve.setAmIMoving(true);
            Steve.move(Direction.UP,cells.size(),cells.get(0).size());
        }
        else if(keyHandler.rightPressed &&  !(cell.getedgeRight() && DistanceManager.isClosetoEdges(Steve,cell, mainPanel.getPicSize(),Direction.RIGHT)))
        {
            Steve.setAmIMoving(true);
            Steve.move(Direction.RIGHT,cells.size(),cells.get(0).size());
        }
        else
        {
            Steve.setAmIMoving(false);
        }
        Steve.setCoords(getCurrentCell(Steve).getrowIdx(),getCurrentCell(Steve).getcolIdx());
    }

    /**
     * Updates cells which are visible for the player, based on "tunnel" vision in 4 directions.
     */
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

    public ArrayList<ArrayList<Cell>> getCells()
    {
        return cells;
    }

    public Player getPlayer()
    {
        return Steve;
    }

    /**
     * Stops every timer, reconstructs the labyrinth, sets the positions of the player and drake, then starts the timers again.
     * @throws IncorrectMapException if the Labyrinth fails to build the labyrinth.
     */
    public void restartGame() throws IncorrectMapException
    {
        refresher.stop();
        timer.stop();
        time = 0;
        spriteUpdater.stop();
        drakeThread.interrupt();
        getContentPane().remove(mainPanel);
        getContentPane().remove(bottomMenu);
        if(dbConnection == null)
        {
            labyrinth = new Labyrinth(true,"");
        }
        else
        {
            String[] mapData = dbConnection.getRandomMap();
            labyrinth = new Labyrinth(false,mapData[0]);
            mapCreator = mapData[1];
        }
        cells = labyrinth.getCells();
        mainPanel = new LabyrinthPanel(this,false);
        getContentPane().add(BorderLayout.SOUTH, bottomMenu);
        getContentPane().add(BorderLayout.CENTER, mainPanel);
        drake = new Dragon(labyrinth.getStartingCell(),cells,waitTimeBetWeenAIIterations);
        drakeThread = new Thread(()-> {
                didDrakeFindThePath = drake.doTremauxPathFinding();
            System.out.println("drake returned with" + didDrakeFindThePath);
        });
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
        drakeThread.start();
        spriteUpdater.start();
        timer.start();
        refresher.start();
    }
    
}


