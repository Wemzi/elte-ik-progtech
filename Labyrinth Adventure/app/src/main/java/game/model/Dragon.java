package game.model;

import game.view.Direction;

import java.util.ArrayList;
import java.util.ListIterator;

public class Dragon {
    private Cell currentCell;
    private ArrayList<ArrayList<Cell>> cells;
    private Direction currentDirection = Direction.RIGHT;
    private ArrayList<Cell> processedCells = new ArrayList<Cell>();
    Cell possibleEnd = new Cell(-1,-1);
    public Dragon(Cell startingCell,ArrayList<ArrayList<Cell>> cells)
    {
        this.cells = cells;
        this.currentCell = startingCell;
    }

    public ArrayList<Cell> doTremauxPathFinding() throws InterruptedException {
        if(!currentCell.isStartingCell()) return null;
        currentCell.setHasBeenVisitedByDragon(true);

        while(!currentCell.isEndingCell())
        {
            if(!currentCell.isJunction())
            {
                goUntilJunction();
            }
            else
            {
                System.out.println("We are in junction, have to make decision" + currentCell);
                selectNewCell(makeChoiceInJunction());
            }
            if(currentCell.isDeadEnd())
            {
                System.out.println("Turning back, dead end");
                currentDirection = getOppositeDirection();
            }
            Thread.sleep(2000);
            if(possibleEnd.isEndingCell())
            {
                System.out.println("Victory!");
                return processedCells;
            }
        }
        System.out.println("VICTORY");
        return processedCells;
    }
    
    
    /** A sárkány mozgató metódusa, végülis egy hosszú elágazás a random szám függvényében*/
    public Direction selectNewDirectionWithWallFollower(int numberOfTries)
    {
        switch(numberOfTries){
            case 0: return Direction.RIGHT;
            case 1: return Direction.DOWN;
            case 2: return Direction.LEFT;
            case 3: return Direction.UP;
            default: return Direction.RIGHT;
        }
    }

    public boolean tryGoingToNeighbourCell(Direction dir)
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
        //System.out.println("Constraint is: " + constraint + " dir " + dir + " neighbour " + !neighbour.isDeadEnd() + " " + currentCell);
        constraint &= !neighbour.isMarkedAsDeadEndByDragon();
        if(constraint)
        {
            //System.out.println("Moving to" + neighbour + " from " + currentCell);
            currentCell = neighbour;
            currentCell.setHasBeenVisitedByDragon(true);
            return true;
        }
        return false;
    }


    public Direction makeChoiceInJunction()
    {
        System.out.println("junction " + currentCell);
        Direction possibleBest = null;
        int possibleBestCount =0;
        Direction[] directions = { Direction.UP, Direction.RIGHT,Direction.DOWN, Direction.LEFT};
        for(Direction dir : directions)
        {
            Cell neighBour = getNeighbour(dir);
            if(neighBour == null) continue;
            //System.out.println("blocked? : " + dir + " " + isMoveBlockedByWall(dir));
            if(!neighBour.isHasBeenVisitedByDragon() && !isMoveBlockedByWall(dir)) {
                System.out.println("direction returned because it hasn't been visited yet " + dir +
                        currentCell);
                return dir;
            }
            else if(!neighBour.isMarkedAsDeadEndByDragon() && !isMoveBlockedByWall(dir) && dir != getOppositeDirection()) {
                System.out.println (dir +" added to possibleBest");
                possibleBest = dir;
                possibleBestCount++;
            }
        }
        if(possibleBestCount ==1)
        {
            currentCell.setMarkedAsDeadEndByDragon(true);
        }
        /*if(currentCell.isHasBeenVisitedByDragon()) {
            Cell neighBour = getNeighbour(getOppositeDirection());
            if(neighBour != null)
            {
                if (!neighBour.isDeadEnd() && !isMoveBlockedByWall(getOppositeDirection())) {
                    System.out.println("opposite dir returned from junction decision");
                    return getOppositeDirection();
                }
            }
        }*/
        System.out.println("possible best: " + possibleBest);
        return possibleBest;
    }

    public boolean isValidMove(Direction dir)
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

    public boolean isMoveBlockedByWall(Direction dir)
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

    public boolean hasNeighBourToGoTo(Cell checkedCell, Direction dir)
    {
        return getNeighbour(dir) != null && !getNeighbour(dir).isMarkedAsDeadEndByDragon();
    }

    public void goUntilJunction()
    {
        Cell originalJunction = currentCell;
        System.out.println("hey" + currentCell);
        if(currentCell.isDeadEnd())
        {
            System.out.println("trurererererere");
            for( int idx=processedCells.size()-1; idx>=0; idx--)
            {
                if(processedCells.get(idx).isJunction())
                {
                    for(int jdx = idx+1; jdx<processedCells.size(); jdx++)
                    {
                        processedCells.get(jdx).setMarkedAsDeadEndByDragon(true);
                        System.out.println("marked deadend bc processedcells isjunction == 1 ");
                    }
                    currentCell = processedCells.get(idx);
                    break;
                }
            }
        }
        int numberOfTries=0;
        while(!currentCell.isJunction())
        {
            if(!selectNewCell(currentDirection))
            {
                Direction prevDir = currentDirection;
                System.out.println("couldnt move to " + currentDirection + " " + currentCell);
                for(int idx=0;idx<3;idx++)
                {
                    currentDirection = selectNewDirection();
                    if(getNeighbour(currentDirection) != null && !getNeighbour(currentDirection).isHasBeenVisitedByDragon() && !isMoveBlockedByWall(currentDirection)
                        || (getNeighbour(currentDirection) != null && getNeighbour(currentDirection).isHasBeenVisitedByDragon() && !getNeighbour(currentDirection).isMarkedAsDeadEndByDragon()))
                    {
                        break;
                    }
                }
                if(currentDirection != prevDir) continue;
                System.out.println(currentCell);
                System.out.println("no unmarked road, going for visited ones");
            }
            //else System.out.println("couldn't move to " + currentCell);
            if (currentCell.isEndingCell())
            {
                possibleEnd = currentCell;
            }
        }
    }

    public Direction selectNewDirection()
    {
        //System.out.println("selecting new direction instead of " + currentDirection);
        switch(currentDirection)
        {
            case RIGHT: if(getNeighbour(Direction.UP) != null && !getNeighbour(Direction.UP).isMarkedAsDeadEndByDragon()) return Direction.DOWN;
            case DOWN: if(getNeighbour(Direction.DOWN) != null && !getNeighbour(Direction.LEFT).isMarkedAsDeadEndByDragon())return Direction.LEFT;
            case LEFT: if(getNeighbour(Direction.LEFT) != null && !getNeighbour(Direction.UP).isMarkedAsDeadEndByDragon())return Direction.UP;
            case UP: if(getNeighbour(Direction.RIGHT) != null && !getNeighbour(Direction.RIGHT).isMarkedAsDeadEndByDragon())return Direction.RIGHT;
            default : return Direction.RIGHT;
        }
    }

    public Direction getOppositeDirection()
    {
        switch(currentDirection)
        {
            case RIGHT: return Direction.LEFT;
            case LEFT: return Direction.RIGHT;
            case UP: return Direction.DOWN;
            case DOWN: return Direction.UP;
            default: return Direction.RIGHT;
        }
    }

    public Direction getOppositeDirection(Direction dir)
    {
        switch(dir)
        {
            case RIGHT: return Direction.LEFT;
            case LEFT: return Direction.RIGHT;
            case UP: return Direction.DOWN;
            case DOWN: return Direction.UP;
            default: return Direction.RIGHT;
        }
    }

    public Cell getNeighbour(Direction dir)
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

    public boolean selectNewCell(Direction dir)
    {
        boolean isMoveSuccessful = false;
            if(isValidMove(dir))
            {
                isMoveSuccessful = tryGoingToNeighbourCell(dir);
                if(isMoveSuccessful)
                {
                    processedCells.add(currentCell);
                }
            }
        if(currentCell.isJunction()){
            currentCell.setHasBeenVisitedByDragon(true);
            if(currentCell.getCountOfPossibleDirections() == 1 )
            {
                currentCell.setMarkedAsDeadEndByDragon(true);
                System.out.println("marked deadend bc countofpossible == 1 ");
            }
        }
        else if(currentCell.isHasBeenVisitedByDragon() && !currentCell.isJunction() && currentCell.getCountOfPossibleDirections() == 1)
        {
            currentCell.setMarkedAsDeadEndByDragon(true);
            System.out.println("Set to deadend: " + currentCell);
        }
        else if(currentCell.isJunction() && !currentCell.isHasBeenVisitedByDragon()){
            currentCell.setHasBeenVisitedByDragon(true);
        }return isMoveSuccessful;
    }
}
