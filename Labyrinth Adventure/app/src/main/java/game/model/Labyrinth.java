package game.model;

import game.IncorrectMapException;
import game.view.Direction;

import java.util.ArrayList;
import java.util.Random;

/** tkp egy Aldous-Broder algoritmus */
public class Labyrinth
{
    private ArrayList<ArrayList<Cell>> cells = new ArrayList<>();
    private Cell currentCell;
    private final int NUMBER_OF_ROWS = 9;
    private final int NUMBER_OF_COLS = 16;
    private Cell endingCell;
    private Cell startingCell;

    public Cell getEndingCell() {
        return endingCell;
    }



    public void setEndingCell(Cell endingCell) {
        for(ArrayList<Cell> cellRow : cells)
        {
            for(Cell cell : cellRow)
            {
                if(cell.isEndingCell())
                {
                    cell.setEndingCell(false);
                }
            }
        }
        this.endingCell = endingCell;
        endingCell.setEndingCell(true);
    }

    public void setStartingCell(Cell startingCell) {
        for(ArrayList<Cell> cellRow : cells)
        {
            for(Cell cell : cellRow)
            {
                if(cell.isStartingCell())
                {
                    cell.setStartingCell(false);
                }
            }
        }
        this.startingCell = startingCell;
        startingCell.setStartingCell(true);
    }

    /**
     * Builds the labyrinth, either by Aldous-Broder algorithm, or by loading it from the database.
     * @param isMapGenerationNeeded true if we have to run the Aldous-Broder algorithm.
     * @param mapData not empty String if there is a map pulled from the database.
     * @throws IncorrectMapException if the mapData String's length is not good.
     */
    public Labyrinth(boolean isMapGenerationNeeded, String mapData) throws IncorrectMapException {
        for (int idx = 0; idx < NUMBER_OF_ROWS; idx++)
        {
            ArrayList <Cell> tmp = new ArrayList<>();
            for ( int jdx=0; jdx<NUMBER_OF_COLS; jdx++)
            {
                tmp.add(new Cell(idx,jdx));
            }
            cells.add(tmp);
        }
        if(isMapGenerationNeeded)
        {
            Random random = new Random();
            currentCell = cells.get(random.nextInt(cells.size())).get(random.nextInt(cells.get(0).size()));
            setStartingCell(currentCell);
            while(!isEndOfGeneration())
            {
                moveToAdjacentCell();
            }
            setEndingCell(currentCell);
        }
        else if(!mapData.equals("")) {
            String[] mapDataSplit = mapData.split(" ");
            if(mapDataSplit.length != NUMBER_OF_ROWS * NUMBER_OF_COLS) throw new IncorrectMapException("Incorrect map format!");
            int StringIdx=0;
            for(ArrayList<Cell> cellRow :cells)
            {
                for(Cell currentCell : cellRow) {
                    char[] cellData = mapDataSplit[StringIdx++].toCharArray();
                    if (cellData.length > 3 && cellData.length < 6) {
                        if (cellData[0] == '0') {
                            currentCell.setedgeUp();
                        }
                        if (cellData[1] == '0') {
                            currentCell.setedgeDown();
                        }
                        if (cellData[2] == '0') {
                            currentCell.setedgeLeft();
                        }
                        if (cellData[3] == '0') {
                            currentCell.setedgeRight();
                        }
                        if (cellData.length == 5) {
                            if (cellData[4] == 's') {
                                setStartingCell(currentCell);
                            } else if (cellData[4] == 'e') {
                                setEndingCell(currentCell);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @return the cell which is the starting Cell.
     */
    public Cell getStartingCell()
    {
        return startingCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    /**
     * The Aldous-Broder algorithm calls this to randomly select a new cell.
     */
    private void moveToAdjacentCell()
    {
        double random = Math.random();
        if(random < 0.25 && currentCell.getrowIdx() < cells.get(0).size()-1)
        {
            if(!cells.get(currentCell.getcolIdx()).get(currentCell.getrowIdx()+1).gethasBeenSelected())
            {
                moveToAdjacentCell(Direction.RIGHT);
            }
            else
            {
                currentCell = cells.get(currentCell.getcolIdx()).get(currentCell.getrowIdx()+1);
            }

        }
        else if(random < 0.50 && currentCell.getcolIdx() < cells.size()-1)
        {
            if(!cells.get(currentCell.getcolIdx()+1).get(currentCell.getrowIdx()).gethasBeenSelected())
            {
                moveToAdjacentCell(Direction.UP);
            }
            else
            {
                currentCell = cells.get(currentCell.getcolIdx()+1).get(currentCell.getrowIdx());
            }

        }
        else if(random < 0.75 && currentCell.getrowIdx() > 0)
        {
            if(!cells.get(currentCell.getcolIdx()).get(currentCell.getrowIdx()-1).gethasBeenSelected())
            {
                moveToAdjacentCell(Direction.LEFT);
            }
            else
            {
                currentCell = cells.get(currentCell.getcolIdx()).get(currentCell.getrowIdx()-1);
            }

        }
        else if(random < 1.00 && currentCell.getcolIdx() > 0)
        {
            if(!cells.get(currentCell.getcolIdx()-1).get(currentCell.getrowIdx()).gethasBeenSelected())
            {
                moveToAdjacentCell(Direction.DOWN);
            }
            else
            {
                currentCell = cells.get(currentCell.getcolIdx()-1).get(currentCell.getrowIdx());
            }
        }
    }
    static int counter = 0;

    /**
     * The map loader algorithm calls this method to make the path according to the map data.
     * @param dir The direction where we should make the way for the player.
     */
    public void moveToAdjacentCell(Direction dir)
    {
        //System.out.println("Called with " + dir);
        switch(dir)
        {
            case RIGHT:{
                currentCell.setedgeRight();
                currentCell = cells.get(currentCell.getcolIdx()).get(currentCell.getrowIdx()+1);
                currentCell.setedgeLeft();
                break;
            }
            case LEFT:{
                currentCell.setedgeLeft();
                currentCell = cells.get(currentCell.getcolIdx()).get(currentCell.getrowIdx()-1);
                currentCell.setedgeRight();
                break;
            }
            case UP:{
                currentCell.setedgeUp();
                currentCell = cells.get(currentCell.getcolIdx()+1).get(currentCell.getrowIdx());
                currentCell.setedgeDown();
                break;
            }
            case DOWN:{
                currentCell.setedgeDown();
                currentCell = cells.get(currentCell.getcolIdx()-1).get(currentCell.getrowIdx());
                currentCell.setedgeUp();
                break;
            }
        }
    }

    /**
     * This is the ending condition of the Aldous-Broder algorithm. We have to go until every Cell is visited at least once.
     * @return true if every cell has been visited.
     */
    public boolean isEndOfGeneration()
    {
        for(ArrayList<Cell> cellRow : cells)
        {
            for ( Cell cell : cellRow)
            {

                if(!cell.gethasBeenSelected())
                {
                    return false;
                }
            }
        }
        return true;
    }

    public Cell getCurrentCell()
    {
        return currentCell;
    }

    public ArrayList<ArrayList<Cell>> getCells()
    {
        return cells;
    }
}