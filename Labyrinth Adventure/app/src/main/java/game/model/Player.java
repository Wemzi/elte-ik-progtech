package game.model;

import game.view.Direction;
import game.view.ResourceLoader;

import java.awt.image.BufferedImage;

public class Player
{
    private int coordX;
    private int coordY;
    private int pixelX;
    private int pixelY;
    private Direction myDirection = Direction.UP;
    private boolean amIMoving = false;
    public BufferedImage[] myLooks = ResourceLoader.steved;
    public BufferedImage myLook = ResourceLoader.steved[0];

    public void setAmIMoving(boolean amIMoving) {
        this.amIMoving = amIMoving;
    }

    public void setPixelX(int pixelX) {
        this.pixelX = pixelX;
    }

    public void setPixelY(int pixelY) {
        this.pixelY = pixelY;
    }

    public int getPixelX() {
        return pixelX;
    }

    public int getPixelY() {
        return pixelY;
    }

    /**
     * Swaps out the image, if the player is moving, and also if it changes directions.
     */
    public void updateLook()
    {
        switch(myDirection)
        {
            case RIGHT:myLooks = ResourceLoader.stever; break;
            case LEFT: myLooks = ResourceLoader.stevel; break;
            case UP:   myLooks = ResourceLoader.steveu ; break;
            case DOWN: myLooks = ResourceLoader.steved; break;
        }
        if(amIMoving)
        {
            myLook = myLook == myLooks[0] ? myLooks[1] : myLooks[0];
        }
    }

    public Player()
    {
        coordX=0;
        coordY=0;
        pixelX=0;
        pixelY=0;
    }

    /**
     * Handling the pixel movement of the player.
     * @param dir The direction where the player wants to move.
     */
    public void move(Direction dir, int maxX, int maxY) {
        myDirection = dir;
        switch (dir) {
            case RIGHT: {
                pixelX+=3;
                break;
            }
            case UP: {
                pixelY+=3;
                break;
            }
            case LEFT: {
                pixelX-=3;
                break;
            }
            case DOWN: {
                pixelY-=3;
                break;
            }
        }
    }

    public void setCoords(int coordX, int coordY)
    {
        this.coordX = coordX;
        this.coordY = coordY;

    }

    @Override
    public String toString() {
        return "Player{" +
                "coordX=" + coordX +
                ", coordY=" + coordY +
                ", pixelX=" + pixelX +
                ", pixelY=" + pixelY +
                '}';
    }
}