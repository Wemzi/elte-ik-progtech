/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amoba;

import javax.swing.JButton;

/**
 *
 * @author lkcsdvd
 */
public class XOButton extends JButton {
    private final int coordX;
    private final int coordY;
    private final int coord;
    
<<<<<<< HEAD
<<<<<<< HEAD
    /** konstruktor, melyben eltároljuk a koordinátákat. */
    XOButton(int coordX, int coordY)
=======
    
    XOButton(int coordX, int coordY, int coord)
>>>>>>> 0db88027f7257daf3bb3091d8ffe000def999f7b
=======
    
    XOButton(int coordX, int coordY, int coord)
>>>>>>> remastered
    {
        super();
        this.coordX=coordX;
        this.coordY=coordY;
        this.coord = coord;
<<<<<<< HEAD
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public int getCoord() {
        return coord;
=======
>>>>>>> remastered
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public int getCoord() {
        return coord;
    }
    
    public int getAtloFirstElem()
    {
        int tmpX=getCoordX();
        int tmpY=getCoordY();
            if(coordX == coordY)
            {
            System.out.println(0);
            return 0;
            }
            else
            {
                while(tmpX > 0 && tmpY > 0 )
                {
                    tmpX--;
                    tmpY--;
                }
                return tmpX > 0 ? tmpX : tmpY;
                
            }
    }
    
    public int getAtloFirstElem2(int size)
    {
        int tmpX=getCoordX();
        int tmpY=getCoordY();

                while(tmpX > 0  && tmpY < size )
                {
                    tmpX--;
                    tmpY++;
                }
                return  size * tmpX + tmpY;

    }


        
    
    
    public int getAtloFirstElem()
    {
        int tmpX=getCoordX();
        int tmpY=getCoordY();
            if(coordX == coordY)
            {
            System.out.println(0);
            return 0;
            }
            else
            {
                while(tmpX > 0 && tmpY > 0 )
                {
                    tmpX--;
                    tmpY--;
                }
                return tmpX > 0 ? tmpX : tmpY;
                
            }
    }
    
    public int getAtloFirstElem2(int size)
    {
        int tmpX=getCoordX();
        int tmpY=getCoordY();

                while(tmpX > 0  && tmpY < size )
                {
                    tmpX--;
                    tmpY++;
                }
                return  size * tmpX + tmpY;

    }


        
    
    
}
