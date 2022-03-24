package game;

class Player
{
    private int coordX=0;
    private int coordY=0;


    public Player()
    {
        coordX=0;
        coordY=0; 
    }

    public void move(int moveX, int moveY, int maxX, int maxY)
    {
        if(coordX < maxX-1 && coordX > 0 )
        {
            coordX += moveX;
        }
        else if(coordX==0 && moveX>0)
        {
            coordX += moveX;
        }
        else if(coordX>0 && moveX<0)
        {
            coordX += moveX;
        }
        if(coordY< maxY-1 && coordY > 0 )
        {
            coordY += moveY;
        }
        else if(coordY==0 && moveY>0)
        {
            coordY += moveY;
        }
        else if(coordY>0 && moveY<0)
        {
            coordY += moveY;
        }
    }

    public int getcoordX()
    {
        return coordX;
    }
    public int getcoordY()
    {
        return coordY;
    }
}