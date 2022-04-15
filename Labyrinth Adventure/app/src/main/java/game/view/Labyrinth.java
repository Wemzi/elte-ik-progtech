/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.view;

import game.model.Cell;
import game.model.Player;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author lkcsdvd
 */
public class Labyrinth extends JPanel{
    private ArrayList<ArrayList<Cell>> cells;
    private MapBuilder board;
    private int picsize;
    private boolean startOfGame;

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

    public Cell getCurrentCell(MouseEvent e)
    {
        int x = (e.getX())/picsize;
        int y = (cells.size())-Math.round(e.getY()/picsize);
        Cell  ret = cells.get(y).get(x);
        return ret;
    }
    
    public Labyrinth(MapBuilder board)
    {
        this.board=board;

        System.out.println(board.frame.getHeight());
        cells = board.getCells();
        System.out.println(getHeight());
        picsize = getHeight() > getWidth() ?  getWidth()/cells.size() : getHeight()/cells.size();
        startOfGame = true;
        if(board instanceof AdventureGUI)
        {
         ((AdventureGUI) board).getPlayer().setCoords(getStartingCell().getrowIdx(),getStartingCell().getcolIdx());
        }
    }

    public int countDistance(int szam1, int szam2)
    {
        return szam2-szam1;
    }
    /** Grafikai rajzoló metódus, itt hívjuk meg a képkiválasztó függvényeket, és helyezzük őket a megfelelő helyre az ablakon belül. */
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
                    img = cell.selectImage();
                }
                catch(IOException e)
                {
                    System.out.println("Error loading file");
                }
                gr.drawImage(img, jdx * picsize,(board.getLabyrinth().getHeight()-(idx+1)*(picsize)), picsize, picsize, null);
                cell.setPixelX(jdx*picsize);
                cell.setPixelY((idx)*(picsize));
            }
        }
        if(board instanceof AdventureGUI) // player
        {
            ((AdventureGUI) board).updatePlayer();
            gr.drawImage(ResourceLoader.steve,((AdventureGUI) board).getPlayer().getPixelX(),(board.getLabyrinth().getHeight()-(((AdventureGUI) board).getPlayer().getPixelY()+(picsize))), ResourceLoader.steve.getWidth(), ResourceLoader.steve.getHeight(), null);
            if(startOfGame)
            {
                startOfGame = false;
                Player Steve = ((AdventureGUI) board).getPlayer();
                Steve.setCoords(getStartingCell().getrowIdx(),getStartingCell().getcolIdx());
                Steve.setPixelX(getStartingCell().getPixelX());
                Steve.setPixelY(getStartingCell().getPixelY());
            }
        }
    }

    public String toMapDataString()
    {
        String ret = "";
        for(ArrayList<Cell> cellRow : cells )
        {
            for(Cell cell : cellRow)
            {
                ret += (cell.getedgeUp()?"1":"0")+(cell.getedgeDown()?"1":"0")+(cell.getedgeLeft()?"1":"0")+(cell.getedgeRight()?"1":"0")+(cell.isStartingCell()?"s":"")+(cell.isEndingCell()?"e":"")+" ";
            }
        }
        return ret;
    }

    public int getPicSize()
    {
        return picsize;
    }
    
}
