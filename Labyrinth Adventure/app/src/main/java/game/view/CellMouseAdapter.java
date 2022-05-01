package game.view;

import game.model.Cell;
import game.model.LabyrinthBuilder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CellMouseAdapter extends MouseAdapter {
    Cell prevCell = null;
    Cell currentCell = null;
    ArrayList<ArrayList<Cell>> cells;
    LabyrinthPanel mainPanel;
    LabyrinthBuilder labyrinth;


    public CellMouseAdapter(ArrayList<ArrayList<Cell>> cells, LabyrinthPanel mainPanel, LabyrinthBuilder labyrinth) {
        this.cells = cells;
        this.mainPanel = mainPanel;
        this.labyrinth = labyrinth;
    }

    /**
     * Calculates the cell by dividing the size of the cells' pictures and the position of the mouse.
     * @param e holds the details of the mouse pointer.
     * @return The cell which the mouse is pointing to.
     */
    public Cell getCurrentCell(MouseEvent e)
    {
        int picSize = mainPanel.getPicSize();
        int x = (e.getX())/picSize;
        int y = cells.size()-Math.round(e.getY()/picSize)-1;
        return cells.get(y).get(x);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        Cell ret = getCurrentCell(e);
        System.out.println(ret);
    }

    /**
     * If the mouse was dragged from one cell to the other, it detects the direction of the movement, and calls the main Panel to make a path between the two neighbour cells.
     * Only used in MapBuilder.
     * @param e the details of the mouse pointer.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        prevCell = currentCell;
        currentCell = getCurrentCell(e);
        if(prevCell == null)
        {
            currentCell.setStartingCell(true);
        }
        else
        {
            Direction dir = null;
            if(currentCell.getcolIdx()> prevCell.getcolIdx())
            {
                dir = Direction.UP;
            }
            else if(currentCell.getcolIdx()< prevCell.getcolIdx())
            {
                dir = Direction.DOWN;
            }
            else if(currentCell.getrowIdx()>prevCell.getrowIdx())
            {
                dir = Direction.RIGHT;
            }
            else if(currentCell.getrowIdx()<prevCell.getrowIdx())
            {
                dir = Direction.LEFT;
            }
            if(dir != null)
            {
                labyrinth.setCurrentCell(prevCell);
                labyrinth.getCurrentCell().setVisibleForPlayer(true);
                labyrinth.moveToAdjacentCell(dir);
                mainPanel.repaint();
            }
        }
    }
}
