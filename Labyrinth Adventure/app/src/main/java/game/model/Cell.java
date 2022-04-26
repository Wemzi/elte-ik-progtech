package game.model;

import game.view.ResourceLoader;

import java.awt.image.BufferedImage;
import java.io.IOException;


public class Cell {
    private int colIdx;
    private int rowIdx;
    private int pixelX;
    private int pixelY;
    private boolean hasBeenVisitedByDragon = false;
    private boolean isDeadEnd = false;

    private boolean edgeRight=  true;
    private boolean edgeLeft =  true;
    private boolean edgeDown =  true;
    private boolean edgeUp   =  true;
    private boolean isStartingCell = false;
    private boolean isEndingCell = false;
    private boolean isVisibleForPlayer = false;
    public boolean isStartingCell() {
        return isStartingCell;
    }

    public boolean isEndingCell() {
        return isEndingCell;
    }

    public void setStartingCell(boolean startingCell) {
        isStartingCell = startingCell;
    }

    public void setEndingCell(boolean endingCell) {
        isEndingCell = endingCell;
    }

    /** Képkiválasztó metódus, az éleket jelentő boolokat vizsgáljuk. Ha egy bool true, akkor van edge (fal) , ha false, akkor nincs. */
    public BufferedImage selectImage() throws IOException
    {
        if(isDeadEnd) return ResourceLoader.blue;
        if(isHasBeenVisitedByDragon())return ResourceLoader.darkness;
        else if(isStartingCell) return ResourceLoader.start;
        else if(isEndingCell) return ResourceLoader.end;
        else if(edgeRight && edgeLeft && edgeDown && edgeUp) return ResourceLoader.brick;
        else if(edgeRight && edgeLeft && edgeDown ) return ResourceLoader.edgelrd;
        else if(edgeRight && edgeLeft && edgeUp) return ResourceLoader.edgeurl;
        else if(edgeRight && edgeDown && edgeUp) return ResourceLoader.edgeurd;
        else if(edgeLeft && edgeDown && edgeUp) return ResourceLoader.edgelud;
        else if(edgeLeft && edgeDown) return ResourceLoader.edgeld;
        else if(edgeLeft && edgeUp) return ResourceLoader.edgeul;
        else if(edgeLeft && edgeRight) return ResourceLoader.edgelr;
        else if(edgeRight && edgeUp) return ResourceLoader.edgeur;
        else if(edgeRight && edgeDown) return ResourceLoader.edgerd;
        else if(edgeUp && edgeDown) return ResourceLoader.edgeud;
        else if(edgeDown) return ResourceLoader.edged;
        else if(edgeUp) return ResourceLoader.edgeu;
        else if(edgeLeft) return ResourceLoader.edgel;
        else if(edgeRight) return ResourceLoader.edger;
        else return ResourceLoader.grass;
    }

    public Cell(int colIdx, int rowIdx)
    {
        this.colIdx = colIdx;
        this.rowIdx = rowIdx;
    }



    public boolean getedgeRight()
    {
        return edgeRight;
    }

    public boolean getedgeUp()
    {
        return edgeUp;
    }

    public boolean getedgeDown()
    {
        return edgeDown;
    }

    public boolean getedgeLeft()
    {
        return edgeLeft;
    }

    public void setedgeRight()
    {
         edgeRight= false;
    }

    public void setedgeUp()
    {
         edgeUp = false;
    }

    public void setedgeDown()
    {
         edgeDown= false;
    }

    public void setedgeLeft()
    {
         edgeLeft= false;
    }

    public int getcolIdx()
    {
        return colIdx;
    }

    public int getrowIdx()
    {
        return rowIdx;
    }

    public boolean gethasBeenSelected()
    {
        return !(edgeUp &&edgeDown && edgeLeft && edgeRight);
    }

    public int getPixelX() {
        return pixelX;
    }


    public boolean isHasBeenVisitedByDragon() {
        return hasBeenVisitedByDragon;
    }

    public void setHasBeenVisitedByDragon(boolean hasBeenVisitedByDragon) {
        this.hasBeenVisitedByDragon = hasBeenVisitedByDragon;
    }

    public boolean isDeadEnd() {
        return isDeadEnd;
    }

    public void setDeadEnd(boolean deadEnd) {
        isDeadEnd = deadEnd;
    }

    public void setPixelX(int pixelX) {
        this.pixelX = pixelX;
    }

    public int getPixelY() {
        return pixelY;
    }

    public void setPixelY(int pixelY) {
        this.pixelY = pixelY;
    }

    public boolean isVisibleForPlayer() {
        return isVisibleForPlayer;
    }

    public void setVisibleForPlayer(boolean visibleForPlayer) {
        isVisibleForPlayer = visibleForPlayer;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "colIdx=" + colIdx +
                ", rowIdx=" + rowIdx ;
    }
}
