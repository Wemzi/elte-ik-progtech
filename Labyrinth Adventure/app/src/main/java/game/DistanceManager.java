package game;

public class DistanceManager {

    public static boolean isClosetoEdges(Player steve, Cell currentCell, int picsize, Direction DIR)
    {
        int xdist = Math.abs(currentCell.getPixelX() -steve.getPixelX());
        int ydist = Math.abs((currentCell.getPixelY()) -steve.getPixelY()) ;
        int ydistend = Math.abs((currentCell.getPixelY()) - (steve.getPixelY() + steve.myLook.getHeight()));
        int xdistend = Math.abs(currentCell.getPixelX() + picsize  - (steve.getPixelX() + steve.myLook.getWidth()));
        int threshold = 10;
        //System.out.println(xdist + " " + ydist + " " + xdistend + " " + ydistend) ;
        boolean ret = false;
        if(xdist < threshold && DIR == Direction.LEFT) ret = true;
        if(ydist < threshold && DIR == Direction.UP) ret = true;
        if(ydistend < threshold && DIR == Direction.DOWN) ret = true;
        if(xdistend < threshold && DIR == Direction.RIGHT) ret = true;
        return ret;
    }
}
