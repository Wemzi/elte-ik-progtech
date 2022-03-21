package game;

import java.util.ArrayList;
/** tkp egy Aldous-Broder algoritmus */
class LabyrinthBuilder
{
    private ArrayList<ArrayList<Cell>> cells = new ArrayList<>();
    private Cell currentCell;
    private Cell startingCell;
    private Cell endingCell;
    
    public LabyrinthBuilder(boolean keepItEmpty)
    {
        for (int idx = 0; idx < 9 ; idx++)
        {
            ArrayList <Cell> tmp = new ArrayList<>();
            for ( int jdx=0; jdx<16; jdx++)
            {
                tmp.add(new Cell(idx,jdx));
            }
            cells.add(tmp);
        }

        if(!keepItEmpty)
        {
            currentCell = cells.get(0).get(0);
            while(!isEndOfGeneration())
            {
                moveToAdjacentCell();
            }
            /*for(ArrayList<Cell> currentRow : cells)
            {
                for(Cell current : currentRow)
                {
                    current.sethasBeenSelected();
                }
            }*/
        }
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    /** Aldous-Broder Algoritmusnak a szomszédos mezőt kiválasztó rész implementációja */
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
    public void moveToAdjacentCell(Direction dir)
    {
        System.out.println("Called with " + dir);
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
    
    /** Eddig megy az algoritmusunk, tehát amíg nincs minden elem legalább egyszer megjelölve */
    public boolean isEndOfGeneration()
    {
        for(ArrayList<Cell> cellRow : cells)
        {
            for ( Cell cell : cellRow)
            {

                if(!cell.gethasBeenSelected())
                {
                    counter++;
                    if(counter>10000) return true;
                    return false;
                }
            }
        }
        return true;
    }

    public ArrayList<ArrayList<Cell>> getCells()
    {
        return cells;
    }
}