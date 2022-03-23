package game;

import java.awt.*;

class Player
{
    private int coordX;
    private int coordY;
    private int pixelX;
    private int pixelY;
    public Rectangle solidArea;

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
                if (coordX < maxX - 1) pixelX+=3;
                break;
            }
            case UP: {
                if (coordY < maxY - 1) pixelY+=3;
                break;
            }
            case LEFT: {
                if (coordX > 0) pixelX-=3;
                break;
            }
            case DOWN: {
                if (coordY > 0) pixelY-=3;
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
                ", solidArea=" + solidArea +
                '}';
    }
}