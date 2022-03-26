package game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CellMouseAdapter extends MouseAdapter {
    Cell prevCell = null;
    Cell currentCell = null;
    ArrayList<ArrayList<Cell>> cells;
    Labyrinth mainPanel;
    LabyrinthBuilder labyrinth;

    public CellMouseAdapter(ArrayList<ArrayList<Cell>> cells, Labyrinth mainPanel, LabyrinthBuilder labyrinth) {
        this.cells = cells;
        this.mainPanel = mainPanel;
        this.labyrinth = labyrinth;
    }

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
        Cell ret = getCurrentCell(e);
        System.out.println(ret);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        prevCell = currentCell;
        currentCell = getCurrentCell(e);
        if(prevCell == null)
        {
            currentCell.setStartingCell(true);
        }
        else if(prevCell != null )
        {
            Direction dir = null;
            if(currentCell.getcolIdx()> prevCell.getcolIdx())
            {
                dir = Direction.DOWN;
            }
            else if(currentCell.getcolIdx()< prevCell.getcolIdx())
            {
                dir = Direction.UP;
            }
            else if(currentCell.getrowIdx()>prevCell.getrowIdx())
            {
                dir = Direction.LEFT;
            }
            else if(currentCell.getrowIdx()<prevCell.getrowIdx())
            {
                dir = Direction.RIGHT;
            }
            if(dir != null)
            {
                labyrinth.setCurrentCell(currentCell);
                labyrinth.moveToAdjacentCell(dir);
                mainPanel.repaint();
            }
        }
    }
}
