/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.view;

import game.model.Cell;
import game.model.Dragon;
import game.model.Player;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author lkcsdvd
 */
public class LabyrinthPanel extends JPanel{
    private ArrayList<ArrayList<Cell>> cells;
    private GUIWindow board;
    private int picsize;
    private boolean startOfGame;
    private boolean debugMode;

    /**
     * @return the starting cell of the maze.
     */
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

    /**
     * @return the ending cell of the maze.
     */
    public Cell getEndingCell()
    {
        for(ArrayList<Cell> cellRow : cells)
        {
            for(Cell cell : cellRow)
            {
                if(cell.isEndingCell())return cell;
            }
        }
        return null;
    }

    /**
     * Constructs the panel, where we'll draw the labyrinth, the player and the drake.
     * @param board the parent JFrame, which constructed this panel.
     * @param debugMode decides if we want to turn some extra debug information visible.
     */
    public LabyrinthPanel(GUIWindow board, boolean debugMode)
    {
        this.debugMode = debugMode;
        this.board=board;
        cells = board.getCells();
        System.out.println(getHeight());
        picsize = getHeight() > getWidth() ?  getWidth()/cells.size() : getHeight()/cells.size();
        startOfGame = true;
        if(board instanceof AdventureGUI)
        {
         ((AdventureGUI) board).getPlayer().setCoords(getStartingCell().getrowIdx(),getStartingCell().getcolIdx());
        }
    }

    /**
     * makes the game content visible for us.
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        picsize = getHeight() > getWidth() ?  getWidth()/cells.size() : getHeight()/cells.size();
        Graphics2D gr = (Graphics2D)g;
        BufferedImage img = null;
        for(int idx=cells.size()-1; idx>=0;idx--)
        {
            for (int jdx=0; jdx<cells.get(0).size(); jdx++)
            {
                Cell cell = cells.get(idx).get(jdx);
                try
                {
                    img = cell.selectImage(debugMode);
                }
                catch(IOException e)
                {
                    System.out.println("Error loading file");
                }
                gr.drawImage(img, jdx * picsize,(board.getLabyrinth().getHeight()-(idx+1)*(picsize)), picsize, picsize, null);
                cell.setPixelX(jdx*picsize);
                cell.setPixelY((idx)*(picsize));
                if(board instanceof AdventureGUI)
                {
                    Dragon drake = ((AdventureGUI) board).getDrake();
                    if(drake.getCurrentCell().equals(cell) && cell.isVisibleForPlayer())
                    {
                        gr.drawImage(ResourceLoader.drake, jdx * picsize,(board.getLabyrinth().getHeight()-(idx+1)*
                                (picsize)), picsize, picsize, null);
                    }
                }
            }
        }
        if(board instanceof AdventureGUI) // player
        {
            Player Steve = ((AdventureGUI) board).getPlayer();
            if(startOfGame)
            {
                startOfGame = false;
                Steve.setCoords(getStartingCell().getrowIdx(),getStartingCell().getcolIdx());
                Steve.setPixelX(getStartingCell().getPixelX());
                Steve.setPixelY(getStartingCell().getPixelY());
            }
            else
            {
                ((AdventureGUI) board).updatePlayer();
                gr.drawImage(Steve.myLook,((AdventureGUI) board).getPlayer().getPixelX(),(board.getLabyrinth().getHeight()-
                                (((AdventureGUI) board).getPlayer().getPixelY()+Steve.myLook.getHeight())), Steve.myLook.getWidth(),
                        Steve.myLook.getHeight(), null);
            }


        }
    }

    /**
     * @return the string representation of the map, which we built in the MapBuilder.
     */

    public int getPicSize()
    {
        return picsize;
    }
    
}
