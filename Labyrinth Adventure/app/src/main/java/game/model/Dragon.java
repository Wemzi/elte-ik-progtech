package game.model;

import game.view.Direction;

import java.util.ArrayList;

public class Dragon {
    private Cell currentCell;
    private ArrayList<ArrayList<Cell>> cells;
    private Direction currentDirection = Direction.RIGHT;

    public Dragon(Cell startingCell,ArrayList<ArrayList<Cell>> cells)
    {
        this.cells = cells;
        this.currentCell = startingCell;
    }

    public ArrayList<Cell> doTremauxPathFinding() throws InterruptedException {
        ArrayList<Cell> retList = new ArrayList<Cell>();
        if(!currentCell.isStartingCell()) return null;
        currentCell.setHasBeenVisitedByDragon(true);
        while(!currentCell.isEndingCell())
        {
            if(!isJunction())
            {
                goUntilJunction();
            }
            else
            {
                System.out.println("We are in junction, have to make decision" + currentCell);
                selectNewCell(makeChoiceInJunction());
                /*{
                    currentCell.setDeadEnd(true);
                }*/
            }
            if(isDeadEnd())
            {
                System.out.println("Turning back, dead end");
                currentDirection = getOppositeDirection();
                goUntilJunction();
            }
            retList.add(currentCell);
            Thread.sleep(2000);
            System.out.println(currentCell);
        }
        System.out.println("VICTORY");
        return retList;
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
        constraint &= !neighbour.isDeadEnd();
        if(constraint)
        {
            //System.out.println("Moving to" + neighbour + " from " + currentCell);
            currentCell = neighbour;
            currentCell.setHasBeenVisitedByDragon(true);
            return true;
        }
        return false;
    }

    public int getCountOfPossibleDirections()
    {
        int retVal = 0;
        if(!currentCell.getedgeDown() && getNeighbour(Direction.DOWN) != null && !getNeighbour(Direction.DOWN).isDeadEnd()) retVal++;
        if(!currentCell.getedgeUp() && getNeighbour(Direction.UP) != null && !getNeighbour(Direction.UP).isDeadEnd()) retVal++;
        if(!currentCell.getedgeLeft() && getNeighbour(Direction.LEFT) != null && !getNeighbour(Direction.LEFT).isDeadEnd()) retVal++;
        if(!currentCell.getedgeRight() && getNeighbour(Direction.RIGHT) != null && !getNeighbour(Direction.RIGHT).isDeadEnd()) retVal++;
        return retVal;
    }

    public boolean isJunction()
    {
        return getCountOfPossibleDirections()>2;
    }

    public Direction makeChoiceInJunction()
    {
        Direction possibleBest = null;
        int possibleBestCount =0;
        Direction[] directions = { Direction.UP, Direction.RIGHT,Direction.DOWN, Direction.LEFT};
        for(Direction dir : directions)
        {
            Cell neighBour = getNeighbour(dir);
            //System.out.println("got neighbour: " + neighBour);
            if(neighBour == null) continue;
            System.out.println("blocked? : " + isMoveBlockedByWall(dir));
            if(!neighBour.isHasBeenVisitedByDragon() && !isMoveBlockedByWall(dir)) {
                System.out.println("direction returned because it hasn't been visited yet " + dir +
                        currentCell);
                return dir;
            }
            else if(!neighBour.isDeadEnd() && !isMoveBlockedByWall(dir)) {
                possibleBest = dir;
                possibleBestCount++;
            }
        }
        System.out.println("possible best count : " + possibleBestCount);
        if(possibleBestCount ==1)
        {
            currentCell.setDeadEnd(true);
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

    public boolean isDeadEnd()
    {
        return getCountOfPossibleDirections() == 1;
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

    public void goUntilJunction()
    {
        //ArrayList<Cell>
        System.out.println("hey" + currentCell);
        int numberOfTries=0;
        while(!isJunction())
        {
            if(!selectNewCell(currentDirection))
            {
                Direction prevDir = currentDirection;
                //System.out.println("couldnt move to " + currentDirection + " " + currentCell);
                for(int idx=0;idx<3;idx++)
                {
                    currentDirection = selectNewDirection();
                    if(getNeighbour(currentDirection) != null && !getNeighbour(currentDirection).isHasBeenVisitedByDragon() && !isMoveBlockedByWall(currentDirection))
                    {
                        break;
                    }
                }
                if(currentDirection != prevDir) continue;
                System.out.println(currentCell);
                System.out.println("no unmarked road, going for visited ones");
                System.out.println((getNeighbour(selectNewDirection()) != null) + " " + getNeighbour(selectNewDirection()).isHasBeenVisitedByDragon() + " " + getNeighbour(selectNewDirection()).isDeadEnd());
                for(int idx=0;idx<3;idx++)
                {
                    currentDirection = selectNewDirection();
                    if(getNeighbour(selectNewDirection()) != null && getNeighbour(selectNewDirection()).isHasBeenVisitedByDragon() && !getNeighbour(selectNewDirection()).isDeadEnd())
                    {
                        System.out.println("new direction found: " +currentDirection);
                        numberOfTries = 0;
                        break;
                    }
                }
            }
            if (currentCell.isEndingCell())
            {
                return;
            }
        }
        System.out.println("found junction, stopping");
    }

    public boolean isNewJunction()
    {
        return currentCell.isHasBeenVisitedByDragon();
    }

    public Direction selectNewDirection()
    {
        //System.out.println("selecting new direction instead of " + currentDirection);
        switch(currentDirection)
        {
            case RIGHT: return Direction.DOWN;
            case DOWN: return Direction.LEFT;
            case LEFT: return Direction.UP;
            case UP: return Direction.RIGHT;
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
                isMoveSuccessful = tryGoingToNeighbourCell(dir);}
        if(isJunction()){
            currentCell.setHasBeenVisitedByDragon(true);
        }
        else if(currentCell.isHasBeenVisitedByDragon() && !isJunction() && getCountOfPossibleDirections() == 1)
        {
            currentCell.setDeadEnd(true);
            System.out.println("Set to deadend: " + currentCell);
        }
        else if(isJunction() && !currentCell.isHasBeenVisitedByDragon()){
            currentCell.setHasBeenVisitedByDragon(true);
        }return isMoveSuccessful;
    }
}
