package game;

import java.awt.*;
import java.awt.image.BufferedImage;

class Player
{
    private int coordX;
    private int coordY;
    private int pixelX;
    private int pixelY;
    public BufferedImage myLook = ResourceLoader.steve;

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

    public Player()
    {
        coordX=0;
        coordY=0;
        pixelX=0;
        pixelY=0;
    }

    /** A játékos mozgató metódusa, támogatja esetleg később az átlós mozgás implementálását is */
    public void move(Direction dir,int maxX, int maxY) {
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

    public int getcoordX()
    {
        return coordX;
    }
    public int getcoordY()
    {
        return coordY;
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