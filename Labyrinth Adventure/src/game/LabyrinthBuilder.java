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
            for ( int jdx=0; jdx<9; jdx++)
            {
                tmp.add(new Cell(idx,jdx));
            }
            cells.add(tmp);
        }


        if(!keepItEmpty)
        {
                currentCell = cells.get(0).get(0);
                currentCell.sethasBeenSelected();
            while(!isEndOfGeneration())
            {
                moveToAdjacentCell();
            }
            for(ArrayList<Cell> currentRow : cells)
            {
                for(Cell current : currentRow)
                {
                    current.sethasBeenSelected();
                }

            }
        }
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    /** Aldous-Broder Algoritmusnak a szomszédos mezőt kiválasztó rész implementációja */
    private void moveToAdjacentCell()
    {
        double random = Math.random();
        currentCell.sethasBeenSelected();
        if(random < 0.25 && currentCell.getrowIdx() < cells.get(0).size()-1)
        {
            if(!cells.get(currentCell.getcolIdx()).get(currentCell.getrowIdx()+1).gethasBeenSelected())
            {
                currentCell.setedgeRight();
                //System.out.println("jobbra");
                currentCell = cells.get(currentCell.getcolIdx()).get(currentCell.getrowIdx()+1);
                currentCell.setedgeLeft();
                currentCell.sethasBeenSelected();
                return;
            }
            currentCell = cells.get(currentCell.getcolIdx()).get(currentCell.getrowIdx()+1);
        }
        else if(random < 0.50 && currentCell.getcolIdx() < cells.size()-1)
        {
            if(!cells.get(currentCell.getcolIdx()+1).get(currentCell.getrowIdx()).gethasBeenSelected())
            {
                //System.out.println("fel");
                currentCell.setedgeUp();
                currentCell = cells.get(currentCell.getcolIdx()+1).get(currentCell.getrowIdx());
                currentCell.setedgeDown();
                currentCell.sethasBeenSelected();
                return;
            }
            currentCell = cells.get(currentCell.getcolIdx()+1).get(currentCell.getrowIdx());
        }
        else if(random < 0.75 && currentCell.getrowIdx() > 0)
        {
            if(!cells.get(currentCell.getcolIdx()).get(currentCell.getrowIdx()-1).gethasBeenSelected())
            {
               // System.out.println("balra");
                currentCell.setedgeLeft();
                currentCell = cells.get(currentCell.getcolIdx()).get(currentCell.getrowIdx()-1);
                currentCell.setedgeRight();
                currentCell.sethasBeenSelected();
                return;
            }
            currentCell = cells.get(currentCell.getcolIdx()).get(currentCell.getrowIdx()-1);
        }
        else if(random < 1.00 && currentCell.getcolIdx() > 0)
        {
            if(!cells.get(currentCell.getcolIdx()-1).get(currentCell.getrowIdx()).gethasBeenSelected())
            {
               // System.out.println("le");
                currentCell.setedgeDown();
                currentCell = cells.get(currentCell.getcolIdx()-1).get(currentCell.getrowIdx());
                currentCell.setedgeUp();
                currentCell.sethasBeenSelected();
                return;
            }
            currentCell = cells.get(currentCell.getcolIdx()-1).get(currentCell.getrowIdx());
        }
        else return;
    }

    public void moveToAdjacentCell(Direction dir)
    {
        switch(dir)
        {
            case RIGHT:{
                currentCell.setedgeRight();
                currentCell = cells.get(currentCell.getcolIdx()).get(currentCell.getrowIdx()+1);
                currentCell.setedgeLeft();
            }
            case LEFT:{
                currentCell.setedgeLeft();
                currentCell = cells.get(currentCell.getcolIdx()).get(currentCell.getrowIdx()-1);
                currentCell.setedgeRight();
                currentCell.sethasBeenSelected();
            }
            case UP:{
                currentCell.setedgeUp();
                currentCell = cells.get(currentCell.getcolIdx()+1).get(currentCell.getrowIdx());
                currentCell.setedgeDown();
                currentCell.sethasBeenSelected();
            }
            case DOWN:{
                currentCell.setedgeDown();
                currentCell = cells.get(currentCell.getcolIdx()-1).get(currentCell.getrowIdx());
                currentCell.setedgeUp();
                currentCell.sethasBeenSelected();
            }
        }
    }
    
    /** Eddig megy az algoritmusunk, tehát amíg nincs minden elem legalább egyszer megjelölve */
    public boolean isEndOfGeneration()
    {
        boolean end = true;
        for(int idx=0; idx < cells.size() ; idx++)
        {
            for ( int jdx=0; jdx < cells.get(0).size() ; jdx++)
            {
                end = end && cells.get(idx).get(jdx).gethasBeenSelected();
            }
        }
        return end;
    }

    public ArrayList<ArrayList<Cell>> getCells()
    {
        return cells;
    }
}