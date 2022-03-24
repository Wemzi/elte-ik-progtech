package game;

import java.util.Random;


public class Dragon {
   private int coordX;
   private int coordY;
   private int maxX;
   private int maxY;

    public Dragon(int maxX, int maxY)
    {
        this.maxX=maxX;
        this.maxY=maxY;
        coordX = new Random().nextInt(maxX);
        coordY = new Random().nextInt(maxY);
        System.out.println("coordX : " + coordX + " coordY : "+ coordY );
    }
    
    
    /** A sárkány mozgató metódusa, végülis egy hosszú elágazás a random szám függvényében*/
    public void randomMove(int rand)
    {
        
        if(rand==1)
        {
            if(coordX < maxX-1 && coordX > 0 )
            {
                coordX += 1;
            }
            else if(coordX==0 )
            {
                coordX += 1;
            }
            else if(coordX>0)
            {
                coordX += 1;
            }
        }
        else if(rand==2)
        {
            if(coordY< maxY-1 && coordY > 0 )
            {
                coordY += 1;
            }
            else if(coordY==0 )
            {
                coordY += 1;
            }
            else if(coordY>0 )
            {
                coordY += 1;
            }
        }
        else if(rand == 3)
        {
            if(coordY< maxY-1 )
            {
                coordY += -1;
            }
            else if(coordY==0 )
            {
                coordY += -1;
            }
            else if(coordY>0 )
            {
                coordY += -1;
            }
        }
        else if(rand==4)
        {
            if(coordX < maxX-1 && coordX > 0 )
            {
                coordX += -1;
            }
            else if(coordX==0 )
            {
                coordX += -1;
            }
            else if(coordX>0)
            {
                coordX += -1;
            }

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
