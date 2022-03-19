package game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;

public class ResourceLoader {
    public static InputStream loadResource(String resName)
     {
         return ResourceLoader.class.getClassLoader().getResourceAsStream(resName);
     }

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
        brick = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("assets/brick.png"));
        edgelrd = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("assets/edgelrd.png"));
        edgeurl = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("assets/edgeurl.png"));
        edgeurd = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("assets/edgeurd.png"));
        edgelud = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("assets/edgelud.png"));
        edgeld = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("assets/edgeld.png"));
        edgeul = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("assets/edgeul.png"));
        edgelr = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("assets/edgelr.png"));
        edgeur = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("assets/edgeur.png"));
        edgerd = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("assets/edgerd.png"));
        edgeud = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("assets/edgeud.png"));
        edged = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("assets/edged.png"));
        edgeu = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("assets/edgeu.png"));
        edgel = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("assets/edgel.png"));
        edger = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("assets/edger.png"));
        grass = ImageIO.read(ResourceLoader.class.getClassLoader().getResource("assets/grass.png"));
    }
     public static BufferedImage loadImage(String resName) throws IOException
     {
         URL url = ResourceLoader.class.getClassLoader().getResource(resName);
         return ImageIO.read(url);
     }
}
