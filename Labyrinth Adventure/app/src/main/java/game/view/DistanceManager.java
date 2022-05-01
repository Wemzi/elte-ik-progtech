package game.view;

import game.model.Cell;
import game.model.Player;

public class DistanceManager {

    public static boolean isClosetoEdges(Player steve, Cell currentCell, int picsize, Direction DIR)
    {
        double sizeMultiplier = (double)(picsize /(double)ResourceLoader.brick.getHeight());
        int xdist = Math.abs(currentCell.getPixelX() -steve.getPixelX());
        int ydist = Math.abs((currentCell.getPixelY() + picsize) - (steve.getPixelY() + steve.myLook.getHeight())) ;
        int ydistend = Math.abs((currentCell.getPixelY()) - (steve.getPixelY()));
        int xdistend = Math.abs(currentCell.getPixelX() + picsize  - (steve.getPixelX() + steve.myLook.getWidth()));
        int threshold = (int)(6.8 * sizeMultiplier);
        boolean ret = false;
        if(xdist < threshold && DIR == Direction.LEFT) ret = true;
        if(ydist < threshold && DIR == Direction.UP) ret = true;
        if(ydistend < threshold && DIR == Direction.DOWN) ret = true;
        if(xdistend < threshold && DIR == Direction.RIGHT) ret = true;
        return ret;
    }
}
