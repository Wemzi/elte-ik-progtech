package game.model;

import game.view.Direction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Dragon {
    private Cell currentCell;
    private ArrayList<ArrayList<Cell>> cells;
    private Direction currentDirection = Direction.RIGHT;
    private int waitTimeBetweenIterationsInMs;

    public Dragon(Cell startingCell,ArrayList<ArrayList<Cell>> cells, int waitTimeBetweenIterationsInMs)
    {
        this.cells = cells;
        this.currentCell = startingCell;
        this.waitTimeBetweenIterationsInMs = waitTimeBetweenIterationsInMs;
    }

    public Cell getCurrentCell(){ return currentCell;}

    public boolean doTremauxPathFinding() throws InterruptedException {
        ArrayList<Cell> retList = new ArrayList<>();
        if(!currentCell.isStartingCell()) return false;
        currentCell.setHasBeenVisitedByDragon(true);
        while(!currentCell.isEndingCell())
        {
            if(!isJunction() || isThereUnVisitedRoad())
            {
                if(!goUntilJunction()) return false;
            }
            else if(isDeadEnd())
            {
                currentDirection = getOppositeDirection();
            }
            else
            {
                currentDirection = makeChoiceInJunction();
                selectNewCell(currentDirection);
            }
            retList.add(currentCell);
        }
        return currentCell.isEndingCell();
    }


    private boolean tryGoingToNeighbourCell(Direction dir)
    {
        Cell neighbour = getNeighbour(dir);
        if(neighbour == null) return false;
        boolean constraint = true;
        switch(dir)
        {
            case UP:{ constraint = !currentCell.getedgeUp();break;}
            case DOWN :{ constraint = !currentCell.getedgeDown();break;}
            case LEFT:{ constraint = !currentCell.getedgeLeft();break;}
            case RIGHT:{ constraint = !currentCell.getedgeRight();break;}
        }
        constraint &= !neighbour.isDeadEnd();
        if(constraint)
        {
            currentCell = neighbour;
            currentCell.setHasBeenVisitedByDragon(true);
            return true;
        }
        return false;
    }

    private int getCountOfPossibleDirections()
    {
        int retVal = 0;
        if(!currentCell.getedgeDown() && getNeighbour(Direction.DOWN) != null && !getNeighbour(Direction.DOWN).isDeadEnd()) retVal++;
        if(!currentCell.getedgeUp() && getNeighbour(Direction.UP) != null && !getNeighbour(Direction.UP).isDeadEnd()) retVal++;
        if(!currentCell.getedgeLeft() && getNeighbour(Direction.LEFT) != null && !getNeighbour(Direction.LEFT).isDeadEnd()) retVal++;
        if(!currentCell.getedgeRight() && getNeighbour(Direction.RIGHT) != null && !getNeighbour(Direction.RIGHT).isDeadEnd()) retVal++;
        return retVal;
    }

    private boolean isThereUnVisitedRoad()
    {
        return !currentCell.getedgeDown() && getNeighbour(Direction.DOWN) != null && !getNeighbour(Direction.DOWN).isHasBeenVisitedByDragon()
         || !currentCell.getedgeUp() && getNeighbour(Direction.UP) != null && !getNeighbour(Direction.UP).isHasBeenVisitedByDragon()
         || !currentCell.getedgeLeft() && getNeighbour(Direction.LEFT) != null && !getNeighbour(Direction.LEFT).isHasBeenVisitedByDragon()
         || !currentCell.getedgeRight() && getNeighbour(Direction.RIGHT) != null && !getNeighbour(Direction.RIGHT).isHasBeenVisitedByDragon();
    }

    private boolean isJunction()
    {
        return getCountOfPossibleDirections()>2;
    }

    private Direction makeChoiceInJunction()
    {
        Direction possibleBest = null;
        int possibleBestCount =0;
        Direction[] dirs = { Direction.UP, Direction.RIGHT,Direction.DOWN, Direction.LEFT};
        List<Direction> directions = Arrays.asList(dirs);
        Collections.shuffle(directions);
        for(Direction dir : directions)
        {
            Cell neighBour = getNeighbour(dir);
            if(neighBour == null) continue;
            else if(!neighBour.isHasBeenVisitedByDragon() && !isMoveBlockedByWall(dir)) {
                return dir;
            }
            else if(!neighBour.isDeadEnd() && !isMoveBlockedByWall(dir)) {
                possibleBest = dir;
                possibleBestCount++;
            }
        }
        if(possibleBestCount ==1)
        {
            currentCell.setDeadEnd(true);
        }
        return possibleBest;
    }

    private boolean isValidMove(Direction dir)
    {
        int currentRowIdx = currentCell.getrowIdx();
        int currentColIdx = currentCell.getcolIdx();
        int colSize = cells.size();
        int rowSize = cells.get(0).size();
        switch(dir)
        {
            case LEFT: return currentRowIdx > 0 ? true : false;
            case RIGHT: return currentRowIdx < rowSize-1 ? true : false;
            case UP: return currentColIdx < colSize-1 ? true : false;
            case DOWN: return currentColIdx > 0 ? true : false;
            default: return false;
        }
    }

    private boolean isDeadEnd()
    {
        return getCountOfPossibleDirections() == 1;
    }

    private boolean isMoveBlockedByWall(Direction dir)
    {
        switch(dir)
        {
            case RIGHT: return currentCell.getedgeRight();
            case LEFT: return currentCell.getedgeLeft();
            case UP: return currentCell.getedgeUp();
            case DOWN: return currentCell.getedgeDown();
            default: return true;
        }
    }

    private boolean goUntilJunction()
    {
        while(!isJunction() || isThereUnVisitedRoad() && isEndCellTheoreticallyReachable())
        {
            Direction prevDir = currentDirection;
            if(isThereUnVisitedRoad())
            {
                currentDirection = getDirectionOfUnvisitedRoad();
            }
            if(!selectNewCell(currentDirection))
            {
                for(int idx=0;idx<4;idx++)
                {
                    currentDirection = selectNewDirection();
                    if(getNeighbour(currentDirection) != null && !getNeighbour(currentDirection).isHasBeenVisitedByDragon() && !isMoveBlockedByWall(currentDirection))
                    {
                        break;
                    }
                }
                for(int idx=0;idx<3;idx++)
                {
                    currentDirection = selectNewDirection();
                    if(getNeighbour(currentDirection) != null && getNeighbour(currentDirection).isHasBeenVisitedByDragon() && !getNeighbour(currentDirection).isDeadEnd() && !isMoveBlockedByWall(currentDirection))
                    {
                        break;
                    }
                }
                if(getNeighbour(currentDirection) == null || getNeighbour(currentDirection).isDeadEnd() || isMoveBlockedByWall(currentDirection))
                {
                    return false;
                }
            }
            else if (currentCell.isEndingCell())
            {
                return true;
            }
        }
        return true;
    }


    private Direction selectNewDirection()
    {
        switch(currentDirection)
        {
            case RIGHT: return Direction.DOWN;
            case DOWN: return Direction.LEFT;
            case LEFT: return Direction.UP;
            default : return Direction.RIGHT;
        }
    }

    private Direction getOppositeDirection()
    {
        switch(currentDirection)
        {
            case RIGHT: return Direction.LEFT;
            case UP: return Direction.DOWN;
            case DOWN: return Direction.UP;
            default: return Direction.RIGHT;
        }
    }

    private Cell getNeighbour(Direction dir)
    {
        int currentColIdx = currentCell.getcolIdx();
        int currentRowIdx = currentCell.getrowIdx();
        if(isValidMove(dir))
        {
            switch(dir)
            {
                case RIGHT: return cells.get(currentColIdx).get(currentRowIdx+1);
                case LEFT: return cells.get(currentColIdx).get(currentRowIdx-1);
                case UP: return cells.get(currentColIdx+1).get(currentRowIdx);
                case DOWN: return cells.get(currentColIdx-1).get(currentRowIdx);
            }
        }
        return null;
    }

    private boolean isEndCellTheoreticallyReachable()
    {
        boolean retVal = false;
        for(ArrayList<Cell> cellRow : cells)
        {
            for(Cell cell : cellRow)
            {
                retVal |= !cell.isHasBeenVisitedByDragon() && !cell.isBrick() || cell.isEndingCell();
            }
        }
        return retVal;
    }

    private Direction getDirectionOfUnvisitedRoad()
    {
        if(!currentCell.getedgeDown() && getNeighbour(Direction.DOWN) != null && !getNeighbour(Direction.DOWN).isHasBeenVisitedByDragon()) return Direction.DOWN;
        else if(!currentCell.getedgeUp() && getNeighbour(Direction.UP) != null && !getNeighbour(Direction.UP).isHasBeenVisitedByDragon()) return Direction.UP;
        else if(!currentCell.getedgeLeft() && getNeighbour(Direction.LEFT) != null && !getNeighbour(Direction.LEFT).isHasBeenVisitedByDragon()) return Direction.LEFT;
        else if(!currentCell.getedgeRight() && getNeighbour(Direction.RIGHT) != null && !getNeighbour(Direction.RIGHT).isHasBeenVisitedByDragon()) return Direction.RIGHT;
        else return null;
    }

    private boolean selectNewCell(Direction dir)
    {
        boolean isMoveSuccessful = false;
        if(isValidMove(dir))
        {
            isMoveSuccessful = tryGoingToNeighbourCell(dir);
        }
        if(isMoveSuccessful) {
            try {
                Thread.sleep(waitTimeBetweenIterationsInMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(isJunction())
        {
            currentCell.setHasBeenVisitedByDragon(true);
            if(isThereUnVisitedRoad())
            {
                currentDirection = getDirectionOfUnvisitedRoad();
            }
        }
        else if(currentCell.isHasBeenVisitedByDragon() && !isJunction() && getCountOfPossibleDirections() == 1)
        {
            currentCell.setDeadEnd(true);
        }
        else if(isJunction() && !currentCell.isHasBeenVisitedByDragon())
        {
            currentCell.setHasBeenVisitedByDragon(true);
        }
        return isMoveSuccessful;
    }
}
