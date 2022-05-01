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

    /**
     * Runs the path finding algorithm of the Dragon.
     * @return if the end of the maze has been found or not.
     * @throws InterruptedException if the waiting time has been interrupted, for example, because the player has found the way out of the maze.
     */
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

    /**
     * Tries to go to the Cell next to it, considering the size of the maze, and if it is allowed to go there.
     * @param dir the direction the Dragon should go in.
     * @return if the move was successful or not.
     */
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

    /**
     * @return the number of directions, where it is possible to go.
     */
    private int getCountOfPossibleDirections()
    {
        int retVal = 0;
        if(!currentCell.getedgeDown() && getNeighbour(Direction.DOWN) != null && !getNeighbour(Direction.DOWN).isDeadEnd()) retVal++;
        if(!currentCell.getedgeUp() && getNeighbour(Direction.UP) != null && !getNeighbour(Direction.UP).isDeadEnd()) retVal++;
        if(!currentCell.getedgeLeft() && getNeighbour(Direction.LEFT) != null && !getNeighbour(Direction.LEFT).isDeadEnd()) retVal++;
        if(!currentCell.getedgeRight() && getNeighbour(Direction.RIGHT) != null && !getNeighbour(Direction.RIGHT).isDeadEnd()) retVal++;
        return retVal;
    }

    /**
     * @return if there is any cell in the array which the Dragon hasn't seen yet.
     */
    private boolean isThereUnVisitedRoad()
    {
        return !currentCell.getedgeDown() && getNeighbour(Direction.DOWN) != null && !getNeighbour(Direction.DOWN).isHasBeenVisitedByDragon()
         || !currentCell.getedgeUp() && getNeighbour(Direction.UP) != null && !getNeighbour(Direction.UP).isHasBeenVisitedByDragon()
         || !currentCell.getedgeLeft() && getNeighbour(Direction.LEFT) != null && !getNeighbour(Direction.LEFT).isHasBeenVisitedByDragon()
         || !currentCell.getedgeRight() && getNeighbour(Direction.RIGHT) != null && !getNeighbour(Direction.RIGHT).isHasBeenVisitedByDragon();
    }

    /**
     * @return if we are in a junction or not.
     */
    private boolean isJunction()
    {
        return getCountOfPossibleDirections()>2;
    }

    /**
     * The Dragon makes a direction choice,considering visited cells and maze limits, where is the best idea to go to.
     * @return the best direction.
     */
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

    /**
     * @param dir the direction which shall be checked.
     * @return if it can go to the neighbour cell without going to the outer side of the maze.
     */
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

    /**
     * @return if we are in a dead end or not.
     */
    private boolean isDeadEnd()
    {
        return getCountOfPossibleDirections() == 1;
    }

    /**
     * @param dir the direction which shall be checked.
     * @return if the move is blocked by any edge or not.
     */
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

    /**
     * This method keeps the Dragon going until choosing a new Cell is trivial.
     * @return true if it found the ending cell, or false if there is nowhere we can go to anymore.
     */
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


    /**
     * @return a new direction, different than the previous one (follows the Right hand rule)
     */
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

    /**
     * @return the direction opposite to the current one.
     */
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


    /**
     * @param dir The direction, in which we want to get the neighbour cell.
     * @return null, if the move to the direction is not valid, otherwise the neighbour cell.
     */
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

    /**
     * @return if it still makes sense to search for routes.
     */
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

    /**
     * @return direction of one of the neighbour cells which is yet unvisited.
     */
    private Direction getDirectionOfUnvisitedRoad()
    {
        if(!currentCell.getedgeDown() && getNeighbour(Direction.DOWN) != null && !getNeighbour(Direction.DOWN).isHasBeenVisitedByDragon()) return Direction.DOWN;
        else if(!currentCell.getedgeUp() && getNeighbour(Direction.UP) != null && !getNeighbour(Direction.UP).isHasBeenVisitedByDragon()) return Direction.UP;
        else if(!currentCell.getedgeLeft() && getNeighbour(Direction.LEFT) != null && !getNeighbour(Direction.LEFT).isHasBeenVisitedByDragon()) return Direction.LEFT;
        else if(!currentCell.getedgeRight() && getNeighbour(Direction.RIGHT) != null && !getNeighbour(Direction.RIGHT).isHasBeenVisitedByDragon()) return Direction.RIGHT;
        else return null;
    }

    /**
     * Moves the Dragon to a new cell, and also handling the Cells' attributes, marking it visited, or dead end.
     * @param dir the direction where we want to go
     * @return if the move was successful or not
     */
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
