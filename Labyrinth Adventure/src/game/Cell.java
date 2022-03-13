package game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Cell {
    private boolean hasBeenSelected=false;
    private int colIdx;
    private int rowIdx;

    private boolean edgeRight=  true;
    private boolean edgeLeft =  true;
    private boolean edgeDown =  true;
    private boolean edgeUp   =  true;

    /** Képkiválasztó metódus, az éleket jelentő boolokat vizsgáljuk. Ha egy bool true, akkor van edge (fal) , ha false, akkor nincs. */
    public BufferedImage selectImage() throws IOException
    {
        if(edgeRight && edgeLeft && edgeDown && edgeUp)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/brick.png");
            return i;
        }
        else if(edgeRight && edgeLeft && edgeDown )
        {
            BufferedImage i = ResourceLoader.loadImage("assets/edgelrd.png");
            return i;
        }
        else if(edgeRight && edgeLeft && edgeUp)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/edgeurl.png");
            return i;
        }
        else if(edgeRight && edgeDown && edgeUp)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/edgeurd.png");
            return i;
        }
        else if(edgeLeft && edgeDown && edgeUp)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/edgelud.png");
            return i;
        }
        else if(edgeLeft && edgeDown)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/edgeld.png");
            return i;
        }
        else if(edgeLeft && edgeUp)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/edgeul.png");
            return i;
        }
        else if(edgeLeft && edgeRight)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/edgelr.png");
            return i;
        }
        else if(edgeRight && edgeUp)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/edgeur.png");
            return i;
        }
        else if(edgeRight && edgeDown)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/edgerd.png");
            return i;
        }
        else if(edgeUp && edgeDown)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/edgeud.png");
            return i;
        }
        else if(edgeDown)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/edged.png");
            return i;
        }
        else if(edgeUp)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/edgeu.png");
            return i;
        }
        else if(edgeLeft)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/edgel.png");
            return i;
        }
        else if(edgeRight)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/edger.png");
            return i;
        }
        else
        {
            BufferedImage i = ResourceLoader.loadImage("assets/grass.png");
            return i;
        }
    }

    /** Képkiválasztó metódus, csak a játékossal. */
    public BufferedImage selectPlayerImage() throws IOException
    {
        if(edgeRight && edgeLeft && edgeDown && edgeUp)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithplayer/brick.png");
            return i;
        }
        else if(edgeRight && edgeLeft && edgeDown )
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithplayer/edgelrd.png");
            return i;
        }
        else if(edgeRight && edgeLeft && edgeUp)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithplayer/edgeurl.png");
            return i;
        }
        else if(edgeRight && edgeDown && edgeUp)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithplayer/edgeurd.png");
            return i;
        }
        else if(edgeLeft && edgeDown && edgeUp)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithplayer/edgelud.png");
            return i;
        }
        else if(edgeLeft && edgeDown)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithplayer/edgeld.png");
            return i;
        }
        else if(edgeLeft && edgeUp)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithplayer/edgeul.png");
            return i;
        }
        else if(edgeLeft && edgeRight)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithplayer/edgelr.png");
            return i;
        }
        else if(edgeRight && edgeUp)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithplayer/edgeur.png");
            return i;
        }
        else if(edgeRight && edgeDown)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithplayer/edgerd.png");
            return i;
        }
        else if(edgeUp && edgeDown)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithplayer/edgeud.png");
            return i;
        }
        else if(edgeDown)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithplayer/edged.png");
            return i;
        }
        else if(edgeUp)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithplayer/edgeu.png");
            return i;
        }
        else if(edgeLeft)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithplayer/edgel.png");
            return i;
        }
        else if(edgeRight)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithplayer/edger.png");
            return i;
        }
        else
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithplayer/grass.png");
            return i;
        }
    }

    /** Képkiválasztó metódus, csak a sárkánnyal. */
    public BufferedImage selectDragonImage() throws IOException
    {
        if(edgeRight && edgeLeft && edgeDown && edgeUp)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithdragon/brick.png");
            return i;
        }
        else if(edgeRight && edgeLeft && edgeDown )
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithdragon/edgelrd.png");
            return i;
        }
        else if(edgeRight && edgeLeft && edgeUp)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithdragon/edgeurl.png");
            return i;
        }
        else if(edgeRight && edgeDown && edgeUp)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithdragon/edgeurd.png");
            return i;
        }
        else if(edgeLeft && edgeDown && edgeUp)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithdragon/edgelud.png");
            return i;
        }
        else if(edgeLeft && edgeDown)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithdragon/edgeld.png");
            return i;
        }
        else if(edgeLeft && edgeUp)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithdragon/edgeul.png");
            return i;
        }
        else if(edgeLeft && edgeRight)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithdragon/edgelr.png");
            return i;
        }
        else if(edgeRight && edgeUp)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithdragon/edgeur.png");
            return i;
        }
        else if(edgeRight && edgeDown)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithdragon/edgerd.png");
            return i;
        }
        else if(edgeUp && edgeDown)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithdragon/edgeud.png");
            return i;
        }
        else if(edgeDown)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithdragon/edged.png");
            return i;
        }
        else if(edgeUp)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithdragon/edgeu.png");
            return i;
        }
        else if(edgeLeft)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithdragon/edgel.png");
            return i;
        }
        else if(edgeRight)
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithdragon/edger.png");
            return i;
        }
        else
        {
            BufferedImage i = ResourceLoader.loadImage("assets/textureswithdragon/grass.png");
            return i;
        }
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
         edgeUp = false;   }

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
        return hasBeenSelected;
    }

    public void sethasBeenSelected()
    {
        hasBeenSelected = !hasBeenSelected;
    }
}
