package game.model;

import game.view.Direction;

import java.util.ArrayList;
import java.util.Random;

public class Dragon {
    private Cell currentCell;
    private ArrayList<ArrayList<Cell>> cells;
    private Direction currentDirection;

    public Dragon(Cell startingCell,ArrayList<ArrayList<Cell>> cells)
    {
        this.cells = cells;
        this.currentCell = startingCell;
    }

    public boolean doTremauxPathFinding()
    {
        if(!currentCell.isStartingCell()) return false;
        currentCell.setHasBeenVisitedByDragon(true);
    }
    
    
    /** A sárkány mozgató metódusa, végülis egy hosszú elágazás a random szám függvényében*/
    public Direction selectRandomDirection()
    {
        Random rand = new Random();
        int randNum = rand.nextInt(4);
        switch(randNum){
            case 0: return Direction.LEFT;
            case 1: return Direction.RIGHT;
            case 2: return Direction.UP;
            case 3: return Direction.DOWN;
        }
        return null;
    }

    public boolean tryGoingUp()
    {
        int currentRowIdx = currentCell.getrowIdx();
        int currentColIdx = currentCell.getcolIdx();
        if(!cells.get(currentColIdx+1).get(currentRowIdx).getedgeUp())
        {
            currentCell = cells.get(currentColIdx+1).get(currentRowIdx);
            return true;
        }
        return false;
    }

    public boolean tryGoingDown()
    {
        int currentRowIdx = currentCell.getrowIdx();
        int currentColIdx = currentCell.getcolIdx();
        if(!cells.get(currentColIdx-1).get(currentRowIdx).getedgeDown())
        {
            currentCell = cells.get(currentColIdx-1).get(currentRowIdx);
            return true;
        }
        return false;
    }

    public boolean tryGoingLeft()
    {
        int currentRowIdx = currentCell.getrowIdx();
        int currentColIdx = currentCell.getcolIdx();
        if(!cells.get(currentColIdx).get(currentRowIdx-1).getedgeLeft())
        {
            currentCell = cells.get(currentColIdx).get(currentRowIdx-1);
            return true;
        }
        return false;
    }

    public boolean tryGoingRight()
    {
        int currentRowIdx = currentCell.getrowIdx();
        int currentColIdx = currentCell.getcolIdx();
        if(!cells.get(currentColIdx).get(currentRowIdx+1).getedgeRight())
        {
            currentCell = cells.get(currentColIdx).get(currentRowIdx+1);
            return true;
        }
        return false;
    }

    public int getCountOfPossibleDirections()
    {
        int retVal = 0;
        if(currentCell.getedgeDown()) retVal++;
        if(currentCell.getedgeUp()) retVal++;
        if(currentCell.getedgeLeft()) retVal++;
        if(currentCell.getedgeRight()) retVal++;
    }

    public boolean isJunction()
    {
        return getCountOfPossibleDirections()>2;
    }



    public boolean isValidMove(Direction dir)
    {
        int currentRowIdx = currentCell.getrowIdx();
        int currentColIdx = currentCell.getcolIdx();
        int rowSize = cells.size();
        int colSize = cells.get(0).size();
        switch(dir)
        {
            case LEFT: return currentRowIdx > 0 ? true : false;
            case RIGHT: return currentColIdx < rowSize-1 ? true : false;
            case UP: return currentColIdx < colSize-1 ? true : false;
            case DOWN: return currentColIdx > 0 ? true : false;
        }
        return false;
    }

    public boolean isDeadEnd()
    {
        switch(currentDirection)
        {
            case RIGHT: return currentCell.getedgeRight() && currentCell.getedgeUp() && currentCell.getedgeDown() && !currentCell.getedgeLeft();
            case LEFT: return currentCell.getedgeLeft() && currentCell.getedgeUp() && currentCell.getedgeDown() && !currentCell.getedgeRight();
            case UP: return currentCell.getedgeUp() && currentCell.getedgeRight() && currentCell.getedgeDown() && !currentCell.getedgeDown();
            case DOWN: return currentCell.getedgeDown() && currentCell.getedgeUp() && currentCell.getedgeDown() && !currentCell.getedgeLeft();
        }
    }

    pu

    public void selectNewCell()
    {
        boolean isMoveSuccessful = false;
        while(!isMoveSuccessful){
            if(isValidMove(currentDirection))
            {
                switch(currentDirection)
                {
                    case RIGHT : isMoveSuccessful = tryGoingRight(); break;
                    case LEFT  : isMoveSuccessful = tryGoingLeft(); break;
                    case DOWN  : isMoveSuccessful = tryGoingDown(); break;
                    case UP    : isMoveSuccessful = tryGoingUp(); break;
                }
            }
            else if(!isMoveSuccessful || !isValidMove(currentDirection))
            {
                currentDirection = selectRandomDirection();
            }
        }
        if(currentCell.isHasBeenVisitedByDragon())
            currentCell.setDeadEnd(true);
        else currentCell.setHasBeenVisitedByDragon(true);
    }
}
