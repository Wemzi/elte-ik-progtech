package game;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;

public class ResourceLoader {
    public static InputStream loadResource(String resName)
     {
         return ResourceLoader.class.getClassLoader().getResourceAsStream(resName);
     }
    public static BufferedImage steve ;
    public static BufferedImage end ;
    public static BufferedImage start ;
    public static BufferedImage brick ;
    public static BufferedImage edgelrd ;
    public static BufferedImage edgeurl ;
    public static BufferedImage edgeurd ;
    public static BufferedImage edgelud ;
    public static BufferedImage edgeld ;
    public static BufferedImage edgeul ;
    public static BufferedImage edgelr ;
    public static BufferedImage edgeur ;
    public static BufferedImage edgerd ;
    public static BufferedImage edgeud ;
    public static BufferedImage edged ;
    public static BufferedImage edgeu ;
    public static BufferedImage edgel ;
    public static BufferedImage edger ;
    public static BufferedImage grass ;

    public static void initResources() throws IOException
    {
        System.out.println("yes");
        steve = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("player.png"));
        start = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("start.png"));
        end = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("end.png"));
        brick = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("brick.png"));
        edgelrd = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgelrd.png"));
        edgeurl = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgeurl.png"));
        edgeurd = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgeurd.png"));
        edgelud = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgelud.png"));
        edgeld = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgeld.png"));
        edgeul = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgeul.png"));
        edgelr = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgelr.png"));
        edgeur = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgeur.png"));
        edgerd = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgerd.png"));
        edgeud = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgeud.png"));
        edged = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edged.png"));
        edgeu = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgeu.png"));
        edgel = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edgel.png"));
        edger = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("edger.png"));
        grass = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("grass.png"));
    }
     public static BufferedImage loadImage(String resName) throws IOException
     {
         URL url = ResourceLoader.class.getClassLoader().getResource(resName);
         return ImageIO.read(url);
     }
}
